package org.tedros.api.presenter;

import org.tedros.api.presenter.view.ITGroupViewItem;
import org.tedros.api.presenter.view.ITView;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.collections.ObservableList;

/**
 * Group Presenter 
 * */
@SuppressWarnings("rawtypes")
public interface ITGroupPresenter<V extends ITView<ITGroupPresenter>> 
extends ITModelViewPresenter, ITPresenter<V> {
	
	/**
	 * Sets a title for the group view
	 * */
	void setViewTitle(String title);
	
	/**
	 * Returns the group view title
	 * */
	String getViewTitle();
	
	/**
	 * Returns the observable list with all group views
	 * 
	 * @return ObservableList of ITGroupViewItem
	 * */
	ObservableList<ITGroupViewItem> getGroupViewItemList();
	
	/**
	 * Sets an observable list with all group views
	 * 
	 * @param ObservableList of ITGroupViewItem
	 * */
	void setGroupViewItemList(ObservableList<ITGroupViewItem> groupItemList);
	
	/**
	 * Returns the title property to bind
	 * */
	ReadOnlyStringProperty viewTitleProperty();
	
	
}
