/**
 * 
 */
package org.tedros.server.service;

import java.text.MessageFormat;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Davis Gordon
 *
 */
public class TServiceLocator {
	
	private static TServiceLocator locator;
	
	private InitialContext ctx;
	
	private String URL = "http://{0}:8081/tomee/ejb";
	private String IP = "localhost";
	
	private Properties getProp(){
		
		String serviceURL = MessageFormat.format(URL, IP);
		
		Properties P = new Properties();
		P.put("java.naming.factory.initial", "org.apache.openejb.client.RemoteInitialContextFactory");
		P.put("java.naming.provider.url", serviceURL); 
		return P;
	}
	
	private TServiceLocator(){
		
	}
	
	public static TServiceLocator getInstance(String url, String ip){
		if(locator ==null)
			locator = new TServiceLocator();
		if(StringUtils.isNotBlank(url))
			locator.URL = url;
		if(StringUtils.isNotBlank(ip))
			locator.IP = ip;
		return locator;
	}
	
	@SuppressWarnings("unchecked")
	public <E> E lookup(String jndi) throws NamingException{
		ctx = new InitialContext(getProp());
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
