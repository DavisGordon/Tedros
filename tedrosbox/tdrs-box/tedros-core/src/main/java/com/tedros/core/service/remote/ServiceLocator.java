/**
 * 
 */
package com.tedros.core.service.remote;

import java.text.MessageFormat;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.tedros.util.TResourceUtil;

/**
 * @author Davis Gordon
 *
 */
public class ServiceLocator {
	
	private static ServiceLocator locator;
	
	private InitialContext ctx;
	
	private static final Properties p;
	private static final Properties prop;
	
	private static String url = "http://{0}:8080/tomee/ejb";
	private static String ip = "127.0.0.1";
	
	static{
		prop = TResourceUtil.getPropertiesFromConfFolder("remote-config.properties");
		if(prop!=null){
			url = prop.getProperty("url");
			ip = prop.getProperty("server_ip");
		}
		
		String serviceURL = MessageFormat.format(url, ip);
		
		p = new Properties();
		p.put("java.naming.factory.initial", "org.apache.openejb.client.RemoteInitialContextFactory");
		p.put("java.naming.provider.url", serviceURL); 
	}
	
	private ServiceLocator(){
	}
	
	public static ServiceLocator getInstance(){
		if(locator ==null)
			locator = new ServiceLocator();
		return locator;
	}
	
	@SuppressWarnings("unchecked")
	public <E> E lookup(String jndi) throws NamingException{
		ctx = new InitialContext(p);
		return (E) ctx.lookup(jndi);
	}
	
	public void close(){
		try {
			ctx.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
