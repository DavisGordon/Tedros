package org.tedros.fx.process;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.security.model.TUser;
import org.tedros.core.service.remote.ServiceLocator;
import org.tedros.fx.exception.TProcessException;
import org.tedros.fx.presenter.page.TPagination;
import org.tedros.server.controller.ITBaseController;
import org.tedros.server.controller.ITEjbController;
import org.tedros.server.controller.ITSecureEjbController;
import org.tedros.server.entity.ITEntity;
import org.tedros.server.query.TSelect;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;


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
	private static final int SEARCHALL = 3;
	
	private Class<E> entityType;
	private E value;
	private TPagination pagination;
	private int operation;
	private TSelect<E> select;
	
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
	 * <pre>Add an entity to find </pre>
	 * @param entidade - The entity to find
	 * @param pagination - the pagination info
	 * */
	public void searchAll(TSelect<E> select, TPagination pagination){
		operation = SEARCHALL;
		this.select = select;
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
	        			if(StringUtils.isNotBlank(pagination.getOrderBy()) && value!=null) {
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
							case SEARCHALL :
								result = service!=null
										? service.search(select, pagination.getStart(), pagination.getTotalRows())
												: secure.search(user.getAccessToken(), select, pagination.getStart(), pagination.getTotalRows());
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
