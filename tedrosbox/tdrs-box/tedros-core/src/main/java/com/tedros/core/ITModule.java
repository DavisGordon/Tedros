package com.tedros.core;

import com.tedros.core.presenter.view.ITView;

public interface ITModule {

	public void tStart();

	/**
	 * Stop action, called when a module is chage.
	 * */
	public void tStop();
	
	@SuppressWarnings("rawtypes")
	public void tShowView(ITView view);

}