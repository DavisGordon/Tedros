package com.tedros.core;

import com.tedros.core.model.ITModelView;
import com.tedros.core.presenter.view.ITView;

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

	public String canStop();
	

	/**
	 * Lookup and show the associated view of the modelView and load the modelView
	 * */
	@SuppressWarnings("rawtypes")
	<M extends ITModelView> void tLookupViewAndLoadModelView(M modelView);
	
	/**
	 * Lookup and show the associated view of the modelView class
	 * */
	@SuppressWarnings("rawtypes")
	<M extends ITModelView> void tLookupAndShowView(Class<M> modelViewClass);
	
	

}