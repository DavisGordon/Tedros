/**
 * 
 */
package com.tedros.ejb.base.model;

import com.tedros.ejb.base.entity.ITFileEntity;

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
