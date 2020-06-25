package com.tedros.ejb.base.util;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @author Davis Gordon
 */ 
public class ServiceLocator {
	
	
	private static final String MODULE_NAME = "tdrs-app-covid-semfome-ejb-1.0-SNAPSHOT"; 

	private static final String PKG_INTERFACES = "org.jboss.ejb.client.naming";
	
	private static ServiceLocator instance = new ServiceLocator();

	private static InitialContext initialContext;
	

	public static ServiceLocator getInstance() {
		return instance;
	}

	/**
	 * Retorna o session bean através do seu nome + o sufixo de ejb
	 * @param <T>
	 * @param ejbName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> classe) {
		try {
			
			String appName = "";
			String beanName = classe.getSimpleName();
			String interfaceName = classe.getName();
	 
	        // Create a look up string name
	        String jndiName = "ejb:" + appName + "/" + MODULE_NAME + "//" + beanName + "!" + interfaceName;
			
	        return (T) getCtx().lookup(jndiName);
			
		} catch (NamingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Retorna o contexto jndi
	 * @return
	 */
	private static InitialContext getCtx() {
		if (initialContext == null) {
			try {
				Properties props = loadConfigDefault();
				initialContext = new InitialContext(props);
			} catch (NamingException e) {
				throw new RuntimeException(e);
			}
		}
		return initialContext;
	}

	/**
	 * Carrega propriedades JNDI defaults
	 * @return
	 */
	private static Properties loadConfigDefault() {
		Properties props = new Properties();
		props.put(Context.URL_PKG_PREFIXES, PKG_INTERFACES);
		return props;
	}


}