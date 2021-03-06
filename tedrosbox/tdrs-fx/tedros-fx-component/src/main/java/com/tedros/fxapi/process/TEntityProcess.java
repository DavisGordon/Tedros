package com.tedros.fxapi.process;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.tedros.core.service.remote.ServiceLocator;
import com.tedros.ejb.base.controller.ITEjbController;
import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.ejb.base.result.TResult;


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
public abstract class TEntityProcess<E extends ITEntity> extends TProcess<List<TResult<E>>>{

	/**
	 * Constant for TomEE
	 * */
	public static final int SERVER_TYPE_TOMEE = 1; 
	
	/**
	 * Constant for JBoss
	 * */
	public static final int SERVER_TYPE_JBOSS_EAPx = 2;
	
	private static final int SAVE = 1; 
	private static final int DELETE = 2;
	private static final int FIND = 3;
	private static final int LIST = 4;
	private static final int SEARCH = 5;
	private static final int FINDBYID = 6;
	
	private Class<E> entityType;
	private List<E> values;
	private int operation;
	
	private String serviceJndiName;
	
	/**
	 * Constructor
	 * 
	 * @param entityType - The entity class
	 * @param serviceJndiName - The ejb service jndi name 
	 * */
	public TEntityProcess(Class<E> entityType, String serviceJndiName){
		setAutoStart(true);
		this.values = new ArrayList<>();
		this.entityType = entityType;
		this.serviceJndiName = serviceJndiName;
		
	}
	
	/**
	 * <pre>Add an entity to save </pre>
	 * @param entidade - The entity to save
	 * */
	public void save(E entidade){
		values.add(entidade);
		operation = SAVE;
	}
	/**
	 * <pre>Add an entity to delete</pre>
	 * @param entidade 
	 * */
	public void delete(E entidade){
		values.add(entidade);
		operation = DELETE;
	}
	/**
	 * <pre>Add an entity to find by id</pre>
	 * @param entidade 
	 * */
	public void findById(E entidade){
		values.add(entidade);
		operation = FINDBYID;
	}
	/**
	 * <pre>Add an entity to find</pre>
	 * @param entidade 
	 * */
	public void find(E entidade){
		values.add(entidade);
		operation = FIND;
	}
	/**
	 * <pre>Add an entity to search</pre>
	 * @param entidade 
	 * */
	public void search(E entidade){
		values.add(entidade);
		operation = SEARCH;
	}
	/**
	 * <pre>List all entitys</pre>
	 * */
	public void list(){
		operation = LIST;
	}
	/**
	 * <pre>The last operation called by the call method.
	 * Override it for filter or address the results. 
	 * </pre>
	 * @param resultList - the result list to be returned by the process
	 * */
	public void runAfter(List<TResult<E>> resultList) {
		
	}
	/**
	 * <pre>The first operation called by the call method.
	 * Override it for custom operation. 
	 * Returning false the process is finalized otherwise 
	 * it continues processing.
	 * </pre>
	 * @param resultList - the result list to be returned by the process
	 * @return boolean - false the process is finalized otherwise 
	 * it continues processing
	 * */
	public boolean runBefore(List<TResult<E>> resultList) {
		return true;
	}
	/**
	 * <pre>Create the task to be executed</pre>
	 * @return TTaskImpl 
	 * */
	@Override
	protected TTaskImpl<List<TResult<E>>> createTask() {
		
		return new TTaskImpl<List<TResult<E>>>() {
        	
        	@Override
			public String getServiceNameInfo() {
				return getProcessName();
			};
        	
        	@SuppressWarnings("unchecked")
			protected List<TResult<E>> call() throws IOException, MalformedURLException {
        		
        		List<TResult<E>> resultList = new ArrayList<>();
        		try {
	        		if(!runBefore(resultList)) {
	        			return resultList;
	        		};
	        		ServiceLocator loc = ServiceLocator.getInstance();
	        		ITEjbController<E> service = (ITEjbController<E>) loc.lookup(serviceJndiName);
	        		if(service!=null){
		        		switch (operation) {
							case SAVE :
								for (E entity : values)
									resultList.add(service.save(entity));
								break;
							case DELETE :
								for (E entity : values)
									resultList.add(service.remove(entity));
								break;
							case FINDBYID :
								for (E entity : values)
									resultList.add(service.findById(entity));
								break;
							case FIND :
								for (E entity : values)
									resultList.add(service.find(entity));
								break;
							case LIST :
								resultList.add(service.listAll(entityType));
								break;
							case SEARCH :
								for (E entity : values){
									TResult result = service.findAll(entity);
									resultList.add(result);
									/*if(result.getResult().equals(EnumResult.SUCESS)){
										for(E v : result.getValue()){
											resultList.add(new TResult<E>(EnumResult.SUCESS, v));
										}
									}
									else 
										resultList.add(new TResult<E>(EnumResult.ERROR, result.getMessage()));*/		
								}
								break;
						}
		        		values.clear();
	        		}
	        		loc.close();
        		} catch (Exception e) {
					setException(e);
					e.printStackTrace();
				} 
        		
        		runAfter(resultList);
        	    return resultList;
        	}
		};
	}

}
