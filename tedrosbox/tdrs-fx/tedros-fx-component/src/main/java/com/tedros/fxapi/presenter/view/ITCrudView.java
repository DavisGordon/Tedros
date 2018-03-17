package com.tedros.fxapi.presenter.view;

import com.tedros.fxapi.presenter.ITCrudPresenter;
import com.tedros.fxapi.presenter.model.TEntityModelView;


@SuppressWarnings("rawtypes")
public interface ITCrudView<M extends TEntityModelView> {
	
	public ITCrudPresenter<M> getCrudPresenter();
	
}
