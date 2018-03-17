package com.tedros.core.presenter.view;

import com.tedros.core.ITModule;
import com.tedros.core.model.ITModelView;
import com.tedros.core.presenter.ITPresenter;

import javafx.beans.property.ReadOnlyBooleanProperty;

@SuppressWarnings("rawtypes")
public interface ITGroupViewItem {
	
	Class<? extends ITView> getViewClass();

	void setViewClass(Class<? extends ITView> viewClass);

	Class<? extends ITModelView> getModelViewClass();

	void setModelViewClass(Class<? extends ITModelView> modelViewClass);

	String getButtonTitle();

	void setButtonTitle(String buttonTitle);

	String getId();

	void setId(String id);

	ITView<?> getViewInstance(ITModule module);

	boolean isViewInitialized();

	ReadOnlyBooleanProperty viewInitializedProperty();

	Class<? extends ITPresenter> getPresenterClass();

	void setPresenterClass(Class<? extends ITPresenter> presenterClass);
	
	ITGroupView gettGroupView();

	void settGroupView(ITGroupView tGroupView);
	
	void setModule(ITModule module);
	
	ITModule getModule();

}