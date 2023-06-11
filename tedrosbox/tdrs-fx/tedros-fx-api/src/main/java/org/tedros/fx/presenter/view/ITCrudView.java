package org.tedros.fx.presenter.view;

import org.tedros.fx.model.TEntityModelView;
import org.tedros.fx.presenter.ITCrudPresenter;


@SuppressWarnings("rawtypes")
public interface ITCrudView<M extends TEntityModelView> {
	
	public ITCrudPresenter<M> getCrudPresenter();
	
}
