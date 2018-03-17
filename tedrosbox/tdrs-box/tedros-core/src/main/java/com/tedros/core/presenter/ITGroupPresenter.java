package com.tedros.core.presenter;

import com.tedros.core.presenter.view.ITGroupViewItem;
import com.tedros.core.presenter.view.ITView;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.collections.ObservableList;

@SuppressWarnings("rawtypes")
public interface ITGroupPresenter<V extends ITView<ITGroupPresenter>> extends ITPresenter<V> {
	
	public void setViewTitle(String title);
	
	public String getViewTitle();
	
	public ObservableList<ITGroupViewItem> getGroupViewItemList();
	
	public void setGroupViewItemList(ObservableList<ITGroupViewItem> groupItemList);
	
	public ReadOnlyStringProperty viewTitleProperty();
	
	
}
