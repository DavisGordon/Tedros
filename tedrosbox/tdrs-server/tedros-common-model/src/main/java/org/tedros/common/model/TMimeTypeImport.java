<<<<<<<< HEAD:tedrosbox/app-solidarity/app-solidarity-model/src/main/java/com/solidarity/model/ProdutoImport.java
/**
 * 
 */
package com.solidarity.model;

import com.tedros.common.model.TFileEntity;
import com.tedros.ejb.base.entity.ITFileEntity;
import com.tedros.ejb.base.model.ITImportModel;

/**
 * @author Davis Gordon
 *
 */
public class ProdutoImport implements ITImportModel {

	private static final long serialVersionUID = -5030633206012895009L;

	private String rules;
	
	private ITFileEntity file;
	
	public ProdutoImport() {
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
========
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
>>>>>>>> tedrosbox_v17_globalweb:tedrosbox/tdrs-server/tedros-common-model/src/main/java/org/tedros/common/model/TMimeTypeImport.java
