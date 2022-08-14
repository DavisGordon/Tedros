package org.tedros.fx.form;

import org.tedros.core.model.ITFilterModelView;

@SuppressWarnings("rawtypes")
public interface ITFilterForm<F extends ITFilterModelView> extends ITForm {

	public F getFilterModelView();
	
	public void addFieldBox(TFieldBox fieldBox);
	
}
