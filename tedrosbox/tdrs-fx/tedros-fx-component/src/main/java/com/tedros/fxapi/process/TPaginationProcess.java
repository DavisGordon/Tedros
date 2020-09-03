package com.tedros.fxapi.process;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

import com.tedros.core.service.remote.ServiceLocator;
import com.tedros.ejb.base.controller.ITEjbController;
import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.ejb.base.result.TResult;
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
	 * @param pagination - The pagination data
	 * */
	public void pageAll(TPagination pagination){
		operation = PAGEALL;
		this.pagination = pagination;
	}
	
	/**
	 * <pre>Add an entity to find </pre>
	 * @param entidade - The entity to find
	 * */
	public void findAll(E entidade, TPagination pagination){
		value = entidade;
		operation = FINDALL;
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
        		
        		TResult<Map<String, Object>> result = null;
        		try {
	        		ServiceLocator loc = ServiceLocator.getInstance();
	        		ITEjbController<E> service = (ITEjbController<E>) loc.lookup(serviceJndiName);
	        		if(service!=null){
		        		switch (operation) {
							case FINDALL :
								result = service.findAll(value, pagination.getStart(), pagination.getTotalRows());
								break;
							case PAGEALL :
								result = service.pageAll(entityType, pagination.getStart(), pagination.getTotalRows());
								break;
						}
	        		}
	        		loc.close();
        		} catch (Exception e) {
					setException(e);
					e.printStackTrace();
				} 
        		
        	    return result;
        	}
		};
	}

}
