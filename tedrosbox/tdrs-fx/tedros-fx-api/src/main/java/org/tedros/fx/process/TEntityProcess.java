package org.tedros.fx.process;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.tedros.core.context.TedrosContext;
import org.tedros.core.security.model.TUser;
import org.tedros.core.service.remote.ServiceLocator;
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
	private TSelect<E> select;
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
	 * <pre>Add a custom select statements to search</pre>
	 * @param entidade 
	 * */
	public void search(TSelect<E> select){
		this.select = select;
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
        	
        	@SuppressWarnings({ "unchecked", "rawtypes" })
			protected List<TResult<E>> call() throws IOException, MalformedURLException {
        		ServiceLocator loc = ServiceLocator.getInstance();
        		List<TResult<E>> resultList = new ArrayList<>();
        		try {
	        		if(!runBefore(resultList)) {
	        			return resultList;
	        		};
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
		        		switch (operation) {
							case SAVE :
								for (E entity : values)
									resultList.add(service!=null 
										? service.save(entity) 
											: secure.save(user.getAccessToken(), entity));
								break;
							case DELETE :
								for (E entity : values)
									resultList.add(service!=null 
											? service.remove(entity)
													: secure.remove(user.getAccessToken(), entity));
								break;
							case FINDBYID :
								for (E entity : values)
									resultList.add(service!=null 
											? service.findById(entity)
													: secure.findById(user.getAccessToken(), entity));
								break;
							case FIND :
								for (E entity : values)
									resultList.add(service!=null 
											? service.find(entity)
													: secure.find(user.getAccessToken(), entity));
								break;
							case LIST :
								TResult res = service!=null 
										? service.listAll(entityType)
												: secure.listAll(user.getAccessToken(), entityType);
								resultList.add(res);
								break;
							case SEARCH :
								if(select==null) {
									for (E entity : values){
										TResult result = service!=null 
												? service.findAll(entity)
														: secure.findAll(user.getAccessToken(), entity);
										resultList.add(result);		
									}
								}else {
									TResult result = service!=null 
											? service.search(select)
													: secure.search(user.getAccessToken(), select);
									resultList.add(result);	
								}
								break;
						}
		        		values.clear();
	        		}
	        		
        		} catch (Exception e) {
        			setException(e);
					buildExceptionResult(resultList, e);	
				} finally {
					loc.close();
				}
        		
        		runAfter(resultList);
        	    return resultList;
        	}
		};
	}

	/**
	 * @param resultList
	 * @param e
	 */
	protected void buildExceptionResult(List<TResult<E>> resultList, Exception e) {
		e.printStackTrace();
		TResult<E> result = new TResult<>(TState.ERROR, true, e.getCause().getMessage());
		resultList.add(result);
	}

}
