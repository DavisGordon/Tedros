package com.tedros.core;

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
	public void tStop();
	
	/**
	 * Show the view inside the module
	 * */
	@SuppressWarnings("rawtypes")
	public void tShowView(ITView view);
	

}