package com.tedros.fxapi.presenter.view;

import javafx.scene.control.ListView;

import com.tedros.fxapi.presenter.model.TEntityModelView;

@SuppressWarnings("rawtypes")
public interface ITEntityListDetailView<M extends TEntityModelView> extends ITDetailView {
	
	public abstract ListView<M> getListView();

	public abstract void setListView(ListView<M> listView);
	
}