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
	
	public static String URL = "http://{0}:8081/tomee/ejb";
	public static String IP = "127.0.0.1";
	
	private Properties getProp(){
		
		String serviceURL = MessageFormat.format(URL, IP);
		
		Properties P = new Properties();
		P.put("java.naming.factory.initial", "org.apache.openejb.client.RemoteInitialContextFactory");
		P.put("java.naming.provider.url", serviceURL); 
		return P;
	}
	
	private TServiceLocator(){
		
	}
	
	public static TServiceLocator getInstance(){
		return new TServiceLocator();
	}
	
	public static TServiceLocator getInstance(String url, String ip){
		if(locator ==null)
			locator = new TServiceLocator();
		if(StringUtils.isNotBlank(url))
			URL = url;
		if(StringUtils.isNotBlank(ip))
			IP = ip;
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
	
	public <E> E lookupWithRetry(String jndi) {
        
		int attempt = 0;
	    long delay = 1000;

	    while (attempt < 60) { // máx 60s
	        attempt++;
	        try {
	            return lookup(jndi);
	        } catch (NamingException e) {
	            System.out.println("Attempt " + attempt + " - "+jndi+" unavailable. Waiting " + delay + "ms...");
	            try { Thread.sleep(delay); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); return null; }
	            delay = Math.min(delay * 2, 5000); // max 5s
	        }
	    }
	    throw new IllegalStateException("Timeout wainting "+jndi+" to be available.");
    }

}
