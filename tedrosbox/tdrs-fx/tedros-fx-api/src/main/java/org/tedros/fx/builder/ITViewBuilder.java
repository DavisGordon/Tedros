package org.tedros.fx.builder;

import org.tedros.api.presenter.view.ITView;
import org.tedros.fx.presenter.model.TModelView;

@SuppressWarnings("rawtypes")
public interface ITViewBuilder<V extends ITView> extends ITBuilder {
	
	public V build(Class<? extends TModelView> modelViewClass);

}
