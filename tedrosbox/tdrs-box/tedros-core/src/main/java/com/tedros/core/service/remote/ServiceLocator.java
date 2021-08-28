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
	
	private static final Properties P;
	private static final Properties PROP;
	
	private static String URL = "http://{0}:8080/tomee/ejb";
	private static String IP = "127.0.0.1";
	
	static{
		PROP = TResourceUtil.getPropertiesFromConfFolder("remote-config.properties");
		if(PROP!=null){
			URL = PROP.getProperty("url");
			IP = PROP.getProperty("server_ip");
		}
		
		String serviceURL = MessageFormat.format(URL, IP);
		
		P = new Properties();
		P.put("java.naming.factory.initial", "org.apache.openejb.client.RemoteInitialContextFactory");
		P.put("java.naming.provider.url", serviceURL); 
	}
	
	private ServiceLocator(){
	}
	
	public static ServiceLocator getInstance(){
		if(locator ==null)
			locator = new ServiceLocator();
		return locator;
	}
	
	/*public static ServiceLocator getInstance(TUser user){
		P.put("java.naming.security.principal", user.getLogin());
		P.put("java.naming.security.credentials", user.getPassword());
		if(locator ==null)
			locator = new ServiceLocator();
		return locator;
	}*/
	
	@SuppressWarnings("unchecked")
	public <E> E lookup(String jndi) throws NamingException{
		ctx = new InitialContext(P);
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
