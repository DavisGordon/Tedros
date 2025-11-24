/**
 * 
 */
package org.tedros.core.service.remote;

import java.text.MessageFormat;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.tedros.util.TResourceUtil;

/**
 * @author Davis Gordon
 *
 */
public class TEjbServiceLocator {
	
	private static TEjbServiceLocator instance;
	
	private InitialContext ctx;
	
	private static String URL = "http://{0}:8080/tomee/ejb";
	private static String IP = "127.0.0.1";
	
	private TEjbServiceLocator(){
	}
	
	static {
		try {
			instance = new TEjbServiceLocator();
		}catch (Exception e) {
			  throw new RuntimeException("Exception while creating singleton instance");
		}
	}
	
	public static TEjbServiceLocator getInstance(){
		return instance;
	}
	
	@SuppressWarnings("unchecked")
	public <E> E lookup(String jndi) throws NamingException{
		ctx = new InitialContext(getProperties());
		return (E) ctx.lookup(jndi);
	}
	
	public void close(){
		try {
			ctx.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static Properties getProperties(){
		Properties properties = TResourceUtil.getPropertiesFromConfFolder("remote-config.properties");
		if(properties!=null){
			URL = properties.getProperty("url");
			IP = properties.getProperty("server_ip");
		}
		
		String serviceURL = MessageFormat.format(URL, IP);
		
		properties = new Properties();
		properties.put("java.naming.factory.initial", "org.apache.openejb.client.RemoteInitialContextFactory");
		properties.put("java.naming.provider.url", serviceURL);
		
		return properties;
	}
}
