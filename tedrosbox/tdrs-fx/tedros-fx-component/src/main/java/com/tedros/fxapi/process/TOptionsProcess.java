package com.tedros.fxapi.process;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.naming.InitialContext;

import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.ejb.base.service.ITEjbService;
import com.tedros.ejb.base.service.TResult;
import com.tedros.fxapi.exception.TProcessException;
import com.tedros.util.TResourceUtil;

public abstract class TOptionsProcess extends TProcess<List<TResult<Object>>>{
	
	private Class<? extends ITEntity> entityType;
	private ITEjbService<?> service;
	private Properties p;
	private InitialContext ctx;
	private Map<String, Object> objects;
	private int operation;
	private static final int FILTER = 1;
	private static final int LISTALL = 2;
	
	public TOptionsProcess(Class<? extends ITEntity> entityType, String serviceJndiName, boolean remoteMode) throws TProcessException {
		try {
			this.entityType = entityType;
			setAutoStart(true);
			if (remoteMode)	initRemote();	else initLocal();
			ctx = new InitialContext(p);
			service = (ITEjbService<?>) ctx.lookup(serviceJndiName);
			objects = new HashMap<>();
		} catch (Exception e) {
			throw new TProcessException(e, e.getMessage(), "N�o foi possivel conectar com o servi�o solicitado!");
		}
	}
	
	public TOptionsProcess(Class<? extends ITEntity> entityType) throws TProcessException {
		try {
			this.entityType = entityType;
			setAutoStart(true);
			objects = new HashMap<>();
		} catch (Exception e) {
			throw new TProcessException(e, e.getMessage(), "N�o foi possivel conectar com o servi�o solicitado!");
		}
	}
	
	public ITEjbService<?> getService(){
		return service;
	}
	
	public void filter(Map<String, Object> values){
		operation = FILTER;
		this.objects = values;
	}
	
	public void listAll(){
		operation = LISTALL;
	}
	
	private void initRemote() {
		
		Properties prop = TResourceUtil.getPropertiesFromConfFolder("remote-config.properties");
		
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
	
	public abstract List<TResult<Object>> doFilter(Map<String, Object> objects);
	
	@Override
	protected TTaskImpl<List<TResult<Object>>> createTask() {
		
		return new TTaskImpl<List<TResult<Object>>>() {
        	
        	@Override
			public String getServiceNameInfo() {
				return getProcessName();
			};
        	
			@SuppressWarnings("unchecked")
			protected List<TResult<Object>> call() throws IOException, MalformedURLException {
        		
        		List<TResult<Object>> resultados = new ArrayList<>();
        		try {
	        		switch (operation) {
						case FILTER :
							resultados.addAll(doFilter(objects));
							break;
						case LISTALL :
							resultados.add(service.listAll(entityType));
							break;
					}
	        		objects.clear();
	        		
	    		} catch (Exception e) {
					setException(e);
					e.printStackTrace();
				} 
        	    return resultados;
        	}
		};
	}
	
	

}
