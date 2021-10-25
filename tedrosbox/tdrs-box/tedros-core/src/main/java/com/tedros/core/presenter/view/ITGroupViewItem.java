package com.tedros.core.presenter.view;

import com.tedros.core.ITModule;
import com.tedros.core.model.ITModelView;
import com.tedros.core.presenter.ITPresenter;

import javafx.beans.property.ReadOnlyBooleanProperty;

/**
 * An item to the group view
 * */
@SuppressWarnings("rawtypes")
public interface ITGroupViewItem {
	
	/**
	 * Returns the class of the view
	 * 
	 * @return Class 
	 * */
	Class<? extends ITView> getViewClass();

	/**
	 * Sets the view class
	 * 
	 * @param viewClass
	 * */
	void setViewClass(Class<? extends ITView> viewClass);

	/**
	 * Returns the model view class
	 * 
	 * @return Class
	 * */
	Class<? extends ITModelView> getModelViewClass();

	/**
	 * Sets the model view class
	 * 
	 * @param modelViewClass
	 * */
	void setModelViewClass(Class<? extends ITModelView> modelViewClass);

	/**
	 * Returns the button title for this view to show on the group view tool bar.  
	 * */
	String getButtonTitle();

	/**
	 * Sets the button title for this view to show on the group view tool bar.  
	 * */
	void setButtonTitle(String buttonTitle);
	
	/**
	 * If true join the header of this view with the main group view.
	 * Default value: false;
	 * */
	void setJoinHeader(boolean join);
	
	
	/**
	 * Return the value for join header.
	 * */
	boolean isJoinHeader();

	/**
	 * Returns the item id
	 * */
	String getId();

	/**
	 * Sets the item id
	 * */
	void setId(String id);

	/**
	 * Returns the view instance of the module
	 * */
	ITView<?> getViewInstance(ITModule module);

	/**
	 * Returns true if the view was initialized
	 * */
	boolean isViewInitialized();

	/**
	 * the view initialized property to bind
	 * */
	ReadOnlyBooleanProperty viewInitializedProperty();

	/**
	 * Returns the presenter class of the view
	 * */
	Class<? extends ITPresenter> getPresenterClass();

	/**
	 * Sets the presenter class of the view
	 * */
	void setPresenterClass(Class<? extends ITPresenter> presenterClass);
	
	/**
	 * Returns the group view
	 * */
	ITGroupView gettGroupView();

	/**
	 * Sets the group view
	 * */
	void settGroupView(ITGroupView tGroupView);
	
	/**
	 * Sets the module
	 * */
	void setModule(ITModule module);
	
	/**
	 * Returns the module
	 * */
	ITModule getModule();

}