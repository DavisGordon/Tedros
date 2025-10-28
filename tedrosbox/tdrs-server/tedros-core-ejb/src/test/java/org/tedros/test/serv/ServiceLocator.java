<<<<<<<< HEAD:tedrosbox/app-solidarity/app-solidarity-webapp/src/main/java/com/solidarity/ejb/service/ServiceLocator.java
/**
 * 
 */
package com.solidarity.ejb.service;

import java.text.MessageFormat;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @author Davis Gordon
 *
 */
public class ServiceLocator {
	
	private static ServiceLocator locator;
	
	private InitialContext ctx;
	
	private final Properties p ;
	
	private String url = "http://{0}:8080/tomee/ejb";
	private String ip = "127.0.0.1";
	
	private ServiceLocator(){
		
		String serviceURL = MessageFormat.format(url, ip);
		
		p = new Properties();
		p.put("java.naming.factory.initial", "org.apache.openejb.client.RemoteInitialContextFactory");
		p.put("java.naming.provider.url", serviceURL); 
		
		
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
========
/**
 * 
 */
package org.tedros.test.serv;

import java.text.MessageFormat;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.tedros.util.TResourceUtil;

/**
 * @author Davis Gordon
 *
 */
public class ServiceLocator {
	
	private static ServiceLocator locator;
	
	private InitialContext ctx;
	
	private static String URL = "http://{0}:8080/tomee/ejb";
	private static String IP = "127.0.0.1";
	
	private static Properties getProp(){
		Properties PROP = TResourceUtil.getPropertiesFromConfFolder("remote-config.properties");
		if(PROP!=null){
			URL = PROP.getProperty("url");
			IP = PROP.getProperty("server_ip");
		}
		
		String serviceURL = MessageFormat.format(URL, IP);
		
		Properties P = new Properties();
		P.put("java.naming.factory.initial", "org.apache.openejb.client.RemoteInitialContextFactory");
		P.put("java.naming.provider.url", serviceURL); 
		return P;
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
>>>>>>>> tedrosbox_v17_globalweb:tedrosbox/tdrs-server/tedros-core-ejb/src/test/java/org/tedros/test/serv/ServiceLocator.java
