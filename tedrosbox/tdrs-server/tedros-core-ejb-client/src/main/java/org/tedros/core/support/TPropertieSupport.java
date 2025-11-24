package org.tedros.core.support;

import org.tedros.common.model.TFileEntity;
import org.tedros.core.setting.model.TPropertie;

import jakarta.ejb.Remote;

@Remote
public interface TPropertieSupport {
	
	static final String JNDI_NAME = "TPropertieSupportRemote";
	
	/**
	 * Return a system property by key.
	 * 
	 * @param key
	 * @return the property value
	 * @throws Exception
	 */
	String getValue(String key) throws Exception;

	/**
	 * Return a file property by key.
	 * 
	 * @param key
	 * @return the file 
	 * @throws Exception
	 */
	TFileEntity getFile(String key) throws Exception;
	
	/**
	 * Return true if the system property exists
	 * 
	 * @param key
	 * @return true or false
	 * @throws Exception
	 */
	boolean exist(String key) throws Exception;
	
	/**
	 * Create a system property
	 * 
	 * @param propertie
	 * @return true or false
	 * @throws Exception
	 */
	boolean create(TPropertie propertie) throws Exception;
	
		
}
