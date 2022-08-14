package org.tedros.fx.presenter.dynamic;

import org.tedros.fx.presenter.behavior.ITBehavior;
import org.tedros.fx.presenter.decorator.ITDecorator;
import org.tedros.fx.presenter.model.TModelView;
import org.tedros.server.model.ITModel;

public interface ITDynaPresenter<M extends TModelView> {

	ITDecorator<TDynaPresenter<M>> getDecorator();

	ITBehavior<M, TDynaPresenter<M>> getBehavior();

	Class<? extends ITModel> getModelClass();

	String canInvalidate();

}