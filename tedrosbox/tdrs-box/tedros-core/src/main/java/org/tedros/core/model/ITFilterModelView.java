package org.tedros.core.model;

import org.tedros.server.model.ITModel;

/**
 * Filter model view 
 * */
@Deprecated
public interface ITFilterModelView<M extends ITModel> extends ITModelView<M>{

	/**
	 * Clean the fields
	 * */
	public void cleanFields();
	
}