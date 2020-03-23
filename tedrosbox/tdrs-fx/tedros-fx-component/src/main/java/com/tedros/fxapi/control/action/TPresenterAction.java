package com.tedros.fxapi.control.action;

import com.tedros.core.presenter.ITPresenter;


/**
 * <pre>
 * Specifies an action to run before, after or replace an action call.
 * 
 * To replace the entire call the method runBefore just need to return false.
 * </pre>
 * @author davis.dun
 * */
public abstract class TPresenterAction<P extends ITPresenter<?>> {
	
	/**
	 * Run before execution of the specified action if false is returned the flow is interrupted.  
	 * */
	public abstract boolean runBefore(P presenter);
	
	/**
	 * Run after execution of the specified action.  
	 * */
	public abstract void runAfter(P presenter);
}
