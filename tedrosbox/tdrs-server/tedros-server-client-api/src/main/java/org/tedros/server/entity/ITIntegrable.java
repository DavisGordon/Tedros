/**
 * 
 */
package org.tedros.server.entity;

import java.util.Date;

/**
 * @author Davis Gordon
 *
 */
public interface ITIntegrable {
	
	void setIntegratedEntityId(Long id);

	void setIntegratedEntity(String className);
	
	void setIntegratedModelView(String className);
	
	void setIntegratedModulePath(String path);
	
	void setIntegratedDate(Date date);
	
	void setIntegratedAppUUID(String appUUID);
	
	Long getIntegratedEntityId();
	
	String getIntegratedEntity();
	
	String getIntegratedModelView();
	
	String getIntegratedModulePath();
	
	Date getIntegratedDate();
	
	String getIntegratedAppUUID();
	
	String getIntegratedViewName();
	 
	void setIntegratedViewName(String integratedViewName);
			
	
	

}
