/**
 * 
 */
package com.tedros.fxapi.presenter.model;

import com.tedros.ejb.base.model.ITImportModel;
import com.tedros.fxapi.property.TSimpleFileEntityProperty;

import javafx.beans.property.SimpleStringProperty;

/**
 * 
 * Define an import model view type
 * 
 * @author Davis Gordon
 *
 */
public abstract class TImportModelView<M extends ITImportModel> extends TModelView<M> {

	public TImportModelView(M model) {
		super(model);
	}
	
	/**
	 * @return the file
	 */
	public abstract TSimpleFileEntityProperty getFile();

	/**
	 * @param file the file to set
	 */
	public abstract void setFile(TSimpleFileEntityProperty file);

	/**
	 * @return the rules
	 */
	public abstract SimpleStringProperty getRules();

	/**
	 * @param rules the rules to set
	 */
	public abstract void setRules(SimpleStringProperty rules);

}
