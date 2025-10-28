/**
 * 
 */
package org.tedros.server.model;

import org.tedros.server.entity.ITFileEntity;

/**
 * @author Davis Gordon
 *
 */
public interface ITImportModel extends ITModel {

	
	String getRules();
	
	void setRules(String rules);
	
	ITFileEntity getFile();
	
	void setFile(ITFileEntity file);
	
}
