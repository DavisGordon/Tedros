package com.tedros.core.presenter;

import com.tedros.core.ITModule;
import com.tedros.core.model.ITModelView;
import com.tedros.core.presenter.view.ITView;

import javafx.beans.property.ReadOnlyBooleanProperty;

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
	public ReadOnlyBooleanProperty viewLoadedProperty();
	
	/**
	 * Returns the view
	 * */
	public V getView();
	
	/**
	 * Load the view
	 * */
	public void loadView();
	
	/**
	 * Set the viewLoadedProperty
	 * */
	public void setViewLoaded(boolean loaded);
	
	/**
	 * Set the view
	 * */
	public void setView(V view);
	
	/**
	 * Initialize the presenter
	 * */
	public void initialize();
	
	/**
	 * Load the model view 
	 * */
	public void loadModelView(ITModelView modelView);
	
	/**
	 * invalidate the presenter
	 * @return 
	 * */
	public boolean invalidate();
	
	/**
	 * Returns the module of this presenter
	 * */
	public ITModule getModule();
	
	/**
	 * Sets the module of this presenter
	 * */
	public void setModule(ITModule module);

	public String canInvalidate();
	
}
