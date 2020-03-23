package com.tedros.core.presenter;

import com.tedros.core.presenter.view.ITGroupViewItem;
import com.tedros.core.presenter.view.ITView;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.collections.ObservableList;

/**
 * Group Presenter 
 * */
@SuppressWarnings("rawtypes")
public interface ITGroupPresenter<V extends ITView<ITGroupPresenter>> extends ITPresenter<V> {
	
	/**
	 * Sets a title for the group view
	 * */
	public void setViewTitle(String title);
	
	/**
	 * Returns the group view title
	 * */
	public String getViewTitle();
	
	/**
	 * Returns the observable list with all group views
	 * 
	 * @return ObservableList of ITGroupViewItem
	 * */
	public ObservableList<ITGroupViewItem> getGroupViewItemList();
	
	/**
	 * Sets an observable list with all group views
	 * 
	 * @param ObservableList of ITGroupViewItem
	 * */
	public void setGroupViewItemList(ObservableList<ITGroupViewItem> groupItemList);
	
	/**
	 * Returns the title property to bind
	 * */
	public ReadOnlyStringProperty viewTitleProperty();
	
	
}
