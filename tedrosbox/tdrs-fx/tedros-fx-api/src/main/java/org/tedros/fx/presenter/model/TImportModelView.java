/**
 * 
 */
package org.tedros.fx.presenter.model;

import org.tedros.common.model.TFileEntity;
import org.tedros.fx.property.TSimpleFileProperty;
import org.tedros.server.model.ITImportModel;

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
	public abstract TSimpleFileProperty<TFileEntity> getFile();

	/**
	 * @param file the file to set
	 */
	public abstract void setFile(TSimpleFileProperty<TFileEntity> file);

	/**
	 * @return the rules
	 */
	public abstract SimpleStringProperty getRules();

	/**
	 * @param rules the rules to set
	 */
	public abstract void setRules(SimpleStringProperty rules);
	
	@Override
	public void removeAllListener() {
		getFile().invalidate();
		super.removeAllListener();
	}

}
