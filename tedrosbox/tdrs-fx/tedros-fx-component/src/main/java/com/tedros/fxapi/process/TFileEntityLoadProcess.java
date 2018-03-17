package com.tedros.fxapi.process;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.MessageFormat;
import java.util.Properties;

import javax.naming.InitialContext;

import com.tedros.core.ejb.service.TFileEntityService;
import com.tedros.ejb.base.service.TResult;
import com.tedros.fxapi.exception.TProcessException;
import com.tedros.global.model.TFileEntity;
import com.tedros.util.TResourceUtil;

public class TFileEntityLoadProcess extends TProcess<TResult<TFileEntity>>{
	
	private TFileEntityService service;
	private Properties p;
	private InitialContext ctx;
	private TFileEntity value;
		
	
	public TFileEntityLoadProcess() throws TProcessException {
		try {
			setAutoStart(true);
			initRemote();
			ctx = new InitialContext(p);
			service = (TFileEntityService) ctx.lookup("TFileEntityServiceRemote");
			
		} catch (Exception e) {
			throw new TProcessException(e, e.getMessage(), "Cant connect with the server");
		}
	}
	
	public void load(TFileEntity entidade){
		value = entidade;
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
		
	@SuppressWarnings("unused")
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
	
	@Override
	protected TTaskImpl<TResult<TFileEntity>> createTask() {
		
		return new TTaskImpl<TResult<TFileEntity>>() {
        	
        	@Override
			public String getServiceNameInfo() {
				return getProcessName();
			};
        	
			protected TResult<TFileEntity> call() throws IOException, MalformedURLException {
        		
        		TResult<TFileEntity> result = null;
        		try {
	        		if(service!=null){
	        			result = service.loadBytes(value);
	        		}
        		} catch (Exception e) {
					setException(e);
					e.printStackTrace();
				} 
        	    return result;
        	}
		};
	}
	
	

}
