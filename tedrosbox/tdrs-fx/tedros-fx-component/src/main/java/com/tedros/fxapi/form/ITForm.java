package com.tedros.fxapi.form;

import java.util.Map;

import com.tedros.core.module.TObjectRepository;
import com.tedros.core.presenter.ITPresenter;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;

/**
 * Define an {@link ITForm}
 * */
@SuppressWarnings("rawtypes")
public interface ITForm {
	
	public void tInitializeForm();
	
	public void tReloadForm();
	
	public TFieldBox gettFieldBox(String fieldName);
	
	public Map<String, TFieldBox> gettFieldBoxMap();
	
	public void settPresenter(ITPresenter presenter);
	
	public ITPresenter gettPresenter();
	
	public void setId(String id);
	
	public String getId();
	
	public ObservableList<Node> getChildren();
	
	public TObjectRepository gettObjectRepository();
	
	public void tDispose();
	
	public ReadOnlyBooleanProperty tLoadedProperty();
	
	public boolean isLoaded();
}
