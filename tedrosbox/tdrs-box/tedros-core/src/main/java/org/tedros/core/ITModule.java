package org.tedros.core;

import org.tedros.api.presenter.view.ITView;
import org.tedros.core.model.ITModelView;

import javafx.collections.ObservableList;

/**
 * Define a module contract for an application
 * 
 *  @author Davis Gordon
 * */
public interface ITModule {

	/**
	 * Run at module start  
	 * */
	public void tStart();

	/**
	 * Executed when the user swap to other module
	 * */
	public boolean tStop();
	
	/**
	 * Show the view inside the module
	 * */
	@SuppressWarnings("rawtypes")
	public void tShowView(ITView view);

	/**
	 * Verify if the associated views can be stop.
	 * 
	 * Return a String message if it cannot be stop else empty/null 
	 * @return String
	 * */
	public String tCanStop();
	
	/**
	 * Lookup and show the associated view of the modelView class
	 * */
	@SuppressWarnings("rawtypes")
	<M extends ITModelView> void tLookupAndShow(Class<M> modelViewClass);
	

	/**
	 * Lookup and show the associated view of the modelView and load the modelView
	 * */
	@SuppressWarnings("rawtypes")
	<M extends ITModelView> void tLookupAndShow(M modelView);
	

	/**
	 * Lookup and show the associated view of the modelView and load the modelView
	 * */
	@SuppressWarnings("rawtypes")
	<M extends ITModelView> void tLookupAndShow(ObservableList<M> modelsView);
	

}