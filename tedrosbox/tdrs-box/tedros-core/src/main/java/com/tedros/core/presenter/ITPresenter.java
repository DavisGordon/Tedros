package com.tedros.core.presenter;

import com.tedros.core.ITModule;
import com.tedros.core.presenter.view.ITView;

import javafx.beans.property.BooleanProperty;

/**
 * The presenter responsable to load the view.
 * 
 * @author Davis Gordon
 * */
@SuppressWarnings("rawtypes")
public interface ITPresenter<V extends ITView> {
	
	/**
	 * Returns true if the view are loaded
	 * */
	public boolean isViewLoaded();
	
	/**
	 * The view loaded property to bind
	 * */
	public BooleanProperty viewLoadedProperty();
	
	/**
	 * Returns the view
	 * */
	public V getView();
	
	/**
	 * Load the view
	 * */
	public void loadView();
	
	public void setView(V view);
	
	/**
	 * Initialize the presenter
	 * */
	public void initialize();
	
	/**
	 * Stops the presenter
	 * */
	public void stop();
	
	/**
	 * Returns the module of this presenter
	 * */
	public ITModule getModule();
	
	/**
	 * Sets the module of this presenter
	 * */
	public void setModule(ITModule module);
	
}
