package com.tedros.core.model;

import com.tedros.ejb.base.model.ITModel;

public interface ITFilterModelView<M extends ITModel> extends ITModelView<M>{

	public void cleanFields();
	
}