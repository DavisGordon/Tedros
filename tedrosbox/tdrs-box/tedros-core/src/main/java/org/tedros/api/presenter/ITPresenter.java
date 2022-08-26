package org.tedros.api.presenter;

import org.tedros.api.presenter.view.ITView;
import org.tedros.core.ITModule;

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
