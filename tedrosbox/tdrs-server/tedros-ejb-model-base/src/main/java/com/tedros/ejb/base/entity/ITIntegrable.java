/**
 * 
 */
package com.tedros.ejb.base.entity;

import java.util.Date;

/**
 * @author Davis Gordon
 *
 */
public interface ITIntegrable {
	
	void setIntegratedEntityId(Long id);
	
	void setIntegratedModelView(String className);
	
	void setIntegratedModulePath(String path);
	
	void setIntegratedDate(Date date);
	
	void setIntegratedAppUUID(String appUUID);
	
	Long getIntegratedEntityId();
	
	String getIntegratedModelView();
	
	String getIntegratedModulePath();
	
	Date getIntegratedDate();
	
	String getIntegratedAppUUID();
	
	String getIntegratedViewName();
	 
	void setIntegratedViewName(String integratedViewName);
			
	
	

}
