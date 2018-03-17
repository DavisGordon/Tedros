package com.tedros.fxapi.presenter.view;

import java.net.URL;

import com.tedros.fxapi.presenter.TFilterCrudPresenter;
import com.tedros.fxapi.presenter.model.TEntityModelView;

@SuppressWarnings("rawtypes")
public abstract class TFilterCrudView<P extends TFilterCrudPresenter, M extends TEntityModelView<?>> 
extends TCrudView<P, M> implements ITFilterCrudView<M>{
	
	public TFilterCrudView(P presenter) {
		super(presenter);
	}
	
	public TFilterCrudView(P presenter, URL fxmlURL) {
		super(presenter, fxmlURL);
	}
		
	public P gettPresenter(){
		return super.gettPresenter();
	}
	
	
	
}
