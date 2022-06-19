package com.tedros.fxapi.presenter.dynamic;

import com.tedros.ejb.base.model.ITModel;
import com.tedros.fxapi.presenter.behavior.ITBehavior;
import com.tedros.fxapi.presenter.decorator.ITDecorator;
import com.tedros.fxapi.presenter.model.TModelView;

public interface ITDynaPresenter<M extends TModelView> {

	ITDecorator<TDynaPresenter<M>> getDecorator();

	ITBehavior<M, TDynaPresenter<M>> getBehavior();

	Class<? extends ITModel> getModelClass();

	String canInvalidate();

}