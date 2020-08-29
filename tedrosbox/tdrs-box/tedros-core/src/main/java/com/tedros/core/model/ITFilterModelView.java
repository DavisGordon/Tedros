package com.tedros.core.model;

import com.tedros.ejb.base.model.ITModel;

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