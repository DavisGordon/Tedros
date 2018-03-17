package com.tedros.core.presenter;

import com.tedros.core.ITModule;
import com.tedros.core.presenter.view.ITView;

import javafx.beans.property.BooleanProperty;


@SuppressWarnings("rawtypes")
public interface ITPresenter<V extends ITView> {
	
	public boolean isViewLoaded();
	
	public BooleanProperty viewLoadedProperty();
	
	public V getView();
	
	public void loadView();
	
	public void setView(V view);
	
	public void initialize();
	
	public void stop();
	
	public ITModule getModule();
	
	public void setModule(ITModule module);
	
}
