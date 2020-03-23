package com.tedros.fxapi.builder;

import com.tedros.fxapi.presenter.dynamic.view.TDynaView;
import com.tedros.fxapi.presenter.model.TModelView;

@SuppressWarnings("rawtypes")
public class TCrudEntityViewBuilder extends TBuilder 
implements ITViewBuilder<TDynaView> {
	
	private static TCrudEntityViewBuilder instance;
	
	private TCrudEntityViewBuilder() {
		
	}
	
	public static TCrudEntityViewBuilder getInstance() {
		if(instance==null)
			instance = new TCrudEntityViewBuilder();
		return instance;
	}

	@Override
	public TDynaView build(Class<? extends TModelView> modelViewClass) {
		return null;
	}

	

}
