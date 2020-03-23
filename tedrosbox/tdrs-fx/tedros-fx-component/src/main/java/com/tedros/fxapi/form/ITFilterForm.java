package com.tedros.fxapi.form;

import com.tedros.core.model.ITFilterModelView;

@SuppressWarnings("rawtypes")
public interface ITFilterForm<F extends ITFilterModelView> extends ITForm {

	public F getFilterModelView();
	
	public void addFieldBox(TFieldBox fieldBox);
	
}
