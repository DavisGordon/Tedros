package org.tedros.api.presenter;

import org.tedros.core.model.ITModelView;

import javafx.collections.ObservableList;

/**
 * Group Presenter 
 * */
@SuppressWarnings("rawtypes")
public interface ITModelViewPresenter {
	
	/**
	 * Lookup and show the associated view of the modelView class
	 * */
	<M extends ITModelView> void lookupAndShow(Class<M> modelViewClass);

	/**
	 * Return true if this presenter can load the model view type
	 * */
	boolean isLoadable(Class<? extends ITModelView> modelViewClass);
	
	/**
	 * lookup the view associated with the model view and show it
	 * */
	<M extends ITModelView> void lookupAndShow(M modelView);
	
	/**
	 * lookup the view associated with the first model view at the list and show 
	 * */
	<M extends ITModelView> void lookupAndShow(ObservableList<M> modelView);
	
}
