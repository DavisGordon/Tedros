package com.tedros.fxapi.process;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.tedros.core.context.TedrosContext;
import com.tedros.core.security.model.TUser;
import com.tedros.core.service.remote.ServiceLocator;
import com.tedros.ejb.base.controller.ITBaseController;
import com.tedros.ejb.base.controller.ITEjbController;
import com.tedros.ejb.base.controller.ITSecureEjbController;
import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.TState;
import com.tedros.fxapi.exception.TProcessException;
import com.tedros.fxapi.presenter.paginator.TPagination;


/**
 * <pre>Process with basic CRUD tasks.
 * 
 * Two application server types can be used, Apache TomEE and JBOSS EAP / WildFly.
 * The application server endpoint can be configured at remote-config.properties
 * located at ..\.tedros\CONF in the USER folder.</pre>
 * 
 * @author Davis Gordon
 * 
 * */
public abstract class TPaginationProcess<E extends ITEntity> extends TProcess<TResult<Map<String, Object>>>{

	/**
	 * Constant for TomEE
	 * */
	public static final int SERVER_TYPE_TOMEE = 1; 
	
	/**
	 * Constant for JBoss
	 * */
	public static final int SERVER_TYPE_JBOSS_EAPx = 2;
	
	private static final int PAGEALL = 1; 
	private static final int FINDALL = 2;
	
	private Class<E> entityType;
	private E value;
	private TPagination pagination;
	private int operation;
	
	private String serviceJndiName;
	
	/**
	 * Constructor
	 * 
	 * @param entityType - The entity class
	 * @param serviceJndiName - The ejb service jndi name 
	 * */
	public TPaginationProcess(Class<E> entityType, String serviceJndiName) throws TProcessException {
		setAutoStart(true);
		this.entityType = entityType;
		this.serviceJndiName = serviceJndiName;
		
	}
	
	/**
	 * <pre>Add a pagination </pre>
	 * @param entidade - The entity to page
	 * @param pagination - The pagination data
	 * */
	public void pageAll(E entidade, TPagination pagination){
		operation = PAGEALL;
		value = entidade;
		this.pagination = pagination;
	}
	
	/**
	 * <pre>Add an entity to find </pre>
	 * @param entidade - The entity to find
	 * @param pagination - the pagination info
	 * */
	public void findAll(E entidade, TPagination pagination){
		operation = FINDALL;
		value = entidade;
		this.pagination = pagination;
	}
	
	
	/**
	 * <pre>Create the task to be executed</pre>
	 * @return TTaskImpl 
	 * */
	@Override
	protected TTaskImpl<TResult<Map<String, Object>>> createTask() {
		
		return new TTaskImpl<TResult<Map<String, Object>>>() {
        	
        	@Override
			public String getServiceNameInfo() {
				return getProcessName();
			};
        	
        	@SuppressWarnings("unchecked")
			protected TResult<Map<String, Object>> call() throws IOException, MalformedURLException {
        		
        		ServiceLocator loc = ServiceLocator.getInstance();
        		TResult<Map<String, Object>> result = null;
        		try {
        			TUser user = TedrosContext.getLoggedUser();
	        		ITBaseController base = (ITBaseController) loc.lookup(serviceJndiName);
	        		ITEjbController<E> service = null;
	        		ITSecureEjbController<E> secure = null;
	        		if(base instanceof ITEjbController)
	        			service = (ITEjbController<E>) base;
	        		if(base instanceof ITSecureEjbController) {
	        			if(user==null || user.getAccessToken()==null)
	        				throw new IllegalStateException("The remote service "+serviceJndiName+" is secured and a logged user is required!");
	        			secure = (ITSecureEjbController<E>) base;
	        		}
        			
        			if(service!=null || secure!=null){
	        			if(StringUtils.isNotBlank(pagination.getOrderBy())) {
	        				value.setOrderBy(new ArrayList<>());
	        				value.addOrderBy(pagination.getOrderBy());
	        			}
		        		switch (operation) {
							case FINDALL :
								result = service!=null
									? service.findAll(value, pagination.getStart(), pagination.getTotalRows(), pagination.isOrderByAsc(), true)
											: secure.findAll(user.getAccessToken(), value, pagination.getStart(), pagination.getTotalRows(), pagination.isOrderByAsc(), true);
								break;
							case PAGEALL :
								result = service!=null
										? service.pageAll(value, pagination.getStart(), pagination.getTotalRows(), pagination.isOrderByAsc())
												: secure.pageAll(user.getAccessToken(), value, pagination.getStart(), pagination.getTotalRows(), pagination.isOrderByAsc());
								break;
						}
	        		}
	        		
        		} catch (Exception e) {
					setException(e);
					e.printStackTrace();
					result = new TResult<>(TState.ERROR,true, e.getCause().getMessage());
				}finally {
					loc.close();
				}
        		
        	    return result;
        	}
		};
	}

}
