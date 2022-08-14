package org.tedros.fx.presenter.view;

import org.tedros.fx.presenter.model.TEntityModelView;

import javafx.scene.control.ListView;

@SuppressWarnings("rawtypes")
public interface ITEntityListDetailView<M extends TEntityModelView> extends ITDetailView {
	
	public abstract ListView<M> getListView();

	public abstract void setListView(ListView<M> listView);
	
}