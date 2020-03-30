package com.tedros.fxapi.process;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.commons.lang3.StringUtils;

import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.ejb.base.service.ITEjbService;
import com.tedros.ejb.base.service.TResult;
import com.tedros.ejb.base.service.TResult.EnumResult;
import com.tedros.fxapi.exception.TProcessException;
import com.tedros.util.TResourceUtil;


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
	
	private Class<E> entityType;
	private ITEjbService<E> service;
	private Properties p;
	private InitialContext ctx;
	private List<E> values;
	private int operation;
	
	private int serverType;
	
	/**
	 * Return an ejb service
	 * 
	 * @return {@link ITEjbService}
	 * */
	public ITEjbService<E> getService(){
		return service;
	}
	
	/**
	 * Define the server application endpoint to be used.
	 * 
	 * @param serverType - 1 for tomee and 2 for jboss
	 * */
	public void setServerType(int serverType) {
		this.serverType = serverType;
	}
		
	/**
	 * Constructor
	 * 
	 * @param entityType - The entity class
	 * @param serviceJndiName - The ejb service jndi name 
	 * @param remoteMode - false for local services and true to use configured endpoint
	 * */
	public TEntityProcess(Class<E> entityType, String serviceJndiName, boolean remoteMode) throws TProcessException {
		init(entityType, serviceJndiName, remoteMode);
	}

	@SuppressWarnings({"unchecked"})
	private void init(Class<E> entityType, String serviceJndiName, boolean remoteMode) throws TProcessException {
		
		this.serverType = SERVER_TYPE_TOMEE;
		this.entityType = entityType;
		
		try {
			
			if(StringUtils.isNotBlank(serviceJndiName)){
				setAutoStart(true);
				if (remoteMode)	initRemote();	else initLocal();				
				
				ctx = new InitialContext(p);
				service = (ITEjbService<E>) ctx.lookup(serviceJndiName);
				values = new ArrayList<>();
			}
		} catch (Exception e) {
			throw new TProcessException(e, e.getMessage(), "Was not possible to connect to the remote server: "+serviceJndiName);
		} 
	}
	
	/**
	 * <pre>Constructor
	 * 
	 * Use remote server 
	 * </pre>
	 * @param entityType - The entity class
	 * @param serviceJndiName - The ejb service jndi name 
	 * @param serverType - 1 for tomee and 2 for jboss
	 * */
	public TEntityProcess(Class<E> entityType, String serviceJndiName, int serverType) throws TProcessException {
		init(entityType, serviceJndiName, true);
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
	 * <pre>Execute the operation</pre>
	 * @param resultList 
	 * */
	public abstract void execute(List<TResult<E>> resultList);
	
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
	        		execute(resultList);
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
							case FIND :
								for (E entity : values)
									resultList.add(service.find(entity));
								break;
							case LIST :
								resultList.add(service.listAll(entityType));
								break;
							case SEARCH :
								for (E entity : values){
									TResult<List<E>> result = service.findAll(entity);
									if(result.getResult().equals(EnumResult.SUCESS)){
										for(E v : result.getValue()){
											resultList.add(new TResult<E>(EnumResult.SUCESS, v));
										}
									}
									else 
										resultList.add(new TResult<E>(EnumResult.ERROR, result.getMessage()));		
								}
								break;
						}
		        		values.clear();
	        		}
        		} catch (Exception e) {
					setException(e);
					e.printStackTrace();
				} 
        		
        	    return resultList;
        	}
		};
	}
	
	

	private void initRemote() {
		
		Properties prop = TResourceUtil.getPropertiesFromConfFolder("remote-config.properties");
		
		if(serverType == 1){
			String url = "http://{0}:8080/tomee/ejb";
			String ip = "127.0.0.1";
			
			if(prop!=null){
				url = prop.getProperty("url");
				ip = prop.getProperty("server_ip");
			}	
			
			String serviceURL = MessageFormat.format(url, ip);
			
			p = new Properties();
			p.put("java.naming.factory.initial", "org.apache.openejb.client.RemoteInitialContextFactory");
			p.put("java.naming.provider.url", serviceURL);
		}else{
			String url = "jnp://{0}:1099";
			String ip = "127.0.0.1";
			
			if(prop!=null){
				url = prop.getProperty("url");
				ip = prop.getProperty("server_ip");
			}
			
			String serviceURL = MessageFormat.format(url, ip);
			
			p = new Properties(); 
			p.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory"); 
			p.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces"); 
			p.put(Context.PROVIDER_URL, serviceURL); 
			 
		}
	}

	private void initLocal() {
		p = new Properties();
		p.put("java.naming.factory.initial", "org.apache.openejb.client.LocalInitialContextFactory");
		
		p.put("tedrosDataSource", "new://Resource?type=DataSource");
		p.put("tedrosDataSource.UserName", "tdrs");
		p.put("tedrosDataSource.Password", "xpto");
		p.put("tedrosDataSource.JdbcDriver", "org.h2.Driver");
		p.put("tedrosDataSource.JdbcUrl", "jdbc:h2:file:C:/Tedros/box/data/db;");
		p.put("tedrosDataSource.JtaManaged", "true");
		
		// change some logging
		p.put("log4j.category.OpenEJB.options ", " debug");
		p.put("log4j.category.OpenEJB.startup ", " debug");
		p.put("log4j.category.OpenEJB.startup.config ", " debug");
		
		// set some openejb flags
		p.put("openejb.jndiname.format ", " {ejbName}/{interfaceClass}");
		p.put("openejb.descriptors.output ", " true");
		p.put("openejb.validation.output.level ", " verbose");
	}
	
	

}
