package com.tedros.fxapi.form;

import com.tedros.core.presenter.ITPresenter;

import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.Node;

/**
 * Define an {@link ITForm}
 * */
@SuppressWarnings("rawtypes")
public interface ITForm {
	
	public void tInitializeForm();
	
	public void tReloadForm();
	
	public TFieldBox gettFieldBox(String fieldName);
	
	public ObservableMap<String, TFieldBox> gettFieldBoxMap();
	
	public void settPresenter(ITPresenter presenter);
	
	public ITPresenter gettPresenter();
	
	public void setId(String id);
	
	public String getId();
	
	public ObservableList<Node> getChildren();
	
}
