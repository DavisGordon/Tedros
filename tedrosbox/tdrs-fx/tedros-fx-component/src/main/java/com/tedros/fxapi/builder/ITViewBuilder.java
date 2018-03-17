package com.tedros.fxapi.builder;

import com.tedros.core.presenter.view.ITView;
import com.tedros.fxapi.presenter.model.TModelView;

@SuppressWarnings("rawtypes")
public interface ITViewBuilder<V extends ITView> extends ITBuilder {
	
	public V build(Class<? extends TModelView> modelViewClass);

}
