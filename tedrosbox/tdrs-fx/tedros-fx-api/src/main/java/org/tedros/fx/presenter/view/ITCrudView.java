package org.tedros.fx.presenter.view;

import org.tedros.fx.presenter.ITCrudPresenter;
import org.tedros.fx.presenter.model.TEntityModelView;


@SuppressWarnings("rawtypes")
public interface ITCrudView<M extends TEntityModelView> {
	
	public ITCrudPresenter<M> getCrudPresenter();
	
}
