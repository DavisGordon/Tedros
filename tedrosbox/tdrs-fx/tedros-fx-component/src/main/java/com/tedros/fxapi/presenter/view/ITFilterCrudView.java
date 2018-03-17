package com.tedros.fxapi.presenter.view;

import com.tedros.core.model.ITModelView;

import javafx.scene.control.TableView;


public interface ITFilterCrudView<M extends ITModelView<?>> {
	
	public TableView<M> getTableViewFilter();
}
