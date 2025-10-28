package org.tedros.api.presenter;

import org.tedros.api.presenter.behavior.ITBehavior;
import org.tedros.api.presenter.decorator.ITDecorator;
import org.tedros.core.model.ITModelView;
import org.tedros.server.model.ITModel;

@SuppressWarnings("rawtypes")
public interface ITDynaPresenter<M extends ITModelView> extends ITModelViewPresenter {

	ITDecorator<? extends ITPresenter> getDecorator();

	ITBehavior<M, ? extends ITPresenter> getBehavior();

	Class<? extends ITModel> getModelClass();

	String canInvalidate();
	
}