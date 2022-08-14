/**
 * 
 */
package org.tedros.common.model;

import org.tedros.server.entity.ITFileEntity;
import org.tedros.server.model.ITImportModel;

/**
 * @author Davis Gordon
 *
 */
public class TMimeTypeImport implements ITImportModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9005823419176309968L;

	private String rules;
	
	private ITFileEntity file;
	
	public TMimeTypeImport() {
		file = new TFileEntity();
	}
	

	/**
	 * @return the rules
	 */
	public String getRules() {
		return rules;
	}

	/**
	 * @param rules the rules to set
	 */
	public void setRules(String rules) {
		this.rules = rules;
	}


	/**
	 * @return the file
	 */
	public ITFileEntity getFile() {
		return file;
	}


	/**
	 * @param file the file to set
	 */
	public void setFile(ITFileEntity file) {
		this.file = file;
	}


}
