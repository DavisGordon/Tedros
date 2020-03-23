/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 17/03/2014
 */
package com.tedros.fxapi.presenter.view.group;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tedros.core.ITModule;
import com.tedros.core.model.ITModelView;
import com.tedros.core.presenter.ITPresenter;
import com.tedros.core.presenter.view.ITGroupView;
import com.tedros.core.presenter.view.ITGroupViewItem;
import com.tedros.core.presenter.view.ITView;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.fxapi.presenter.model.TModelView;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class TViewItem implements ITGroupViewItem {
	
	private String id;
	private String buttonTitle;
	
	private ITView<?> view;
	private Class<? extends ITView<?>> viewClass;
	private Class<? extends ITPresenter> presenterClass;
	
	private Class<? extends ITModelView> modelViewClass;
	private SimpleBooleanProperty viewInitializedProperty;
	
	private ITGroupView tGroupView;
	
	private ITModule module;
	
	public TViewItem() {
		
	}
	
	public TViewItem(Class<? extends ITView> viewClass, Class<? extends TModelView> modelViewClass, String buttonTitle) {
		this.viewClass = (Class<ITView<?>>) viewClass;
		this.modelViewClass = modelViewClass;
		this.buttonTitle = buttonTitle; 
	}
	
	public TViewItem(Class<? extends ITView> viewClass, Class<? extends ITPresenter> presenterClass, Class<? extends TModelView> modelViewClass, String buttonTitle) {
		this.viewClass = (Class<ITView<?>>) viewClass;
		this.presenterClass = presenterClass;
		this.modelViewClass = modelViewClass;
		this.buttonTitle = buttonTitle; 
	}
	
	public TViewItem(Class<? extends ITView> viewClass, Class<? extends TModelView<?>> modelViewClass, String id, String buttonTitle) {
		this.id = id;
		this.viewClass = (Class<ITView<?>>) viewClass;
		this.modelViewClass = modelViewClass;
		this.buttonTitle = buttonTitle;
	}
	
	public TViewItem(Class<? extends ITView> viewClass, String id, String buttonTitle, Class<? extends TEntityModelView> modelViewClass) {
		this.id = id;
		this.viewClass = (Class<ITView<?>>) viewClass;
		this.modelViewClass = modelViewClass;
		this.buttonTitle = buttonTitle;
	}
	
	public TViewItem(String id, String buttonTitle, Class<? extends ITView> viewClass, Class<? extends ITPresenter> presenterClass, Class<? extends TModelView> modelViewClass) {
		this.id = id;
		this.viewClass = (Class<ITView<?>>) viewClass;
		this.presenterClass = presenterClass;
		this.modelViewClass = modelViewClass;
		this.buttonTitle = buttonTitle;
	}
	
	/* (non-Javadoc)
	 * @see com.tedros.fxapi.presenter.view.group.ITGroupViewItem#getViewClass()
	 */
	@Override
	public final Class<? extends ITView> getViewClass() {
		return viewClass;
	}

	/* (non-Javadoc)
	 * @see com.tedros.fxapi.presenter.view.group.ITGroupViewItem#setViewClass(java.lang.Class)
	 */
	@Override
	public final void setViewClass(Class<? extends ITView> viewClass) {
		this.view = null;
		this.viewClass =  (Class<? extends ITView<?>>) viewClass;
	}
	
	/* (non-Javadoc)
	 * @see com.tedros.fxapi.presenter.view.group.ITGroupViewItem#getModelViewClass()
	 */
	@Override
	public final Class<? extends ITModelView> getModelViewClass() {
		return modelViewClass;
	}
	
	/* (non-Javadoc)
	 * @see com.tedros.fxapi.presenter.view.group.ITGroupViewItem#setModelViewClass(java.lang.Class)
	 */
	@Override
	public final void setModelViewClass(Class<? extends ITModelView> modelViewClass) {
		this.modelViewClass = modelViewClass;
	}
	/* (non-Javadoc)
	 * @see com.tedros.fxapi.presenter.view.group.ITGroupViewItem#getButtonTitle()
	 */
	@Override
	public final String getButtonTitle() {
		return buttonTitle;
	}
	/* (non-Javadoc)
	 * @see com.tedros.fxapi.presenter.view.group.ITGroupViewItem#setButtonTitle(java.lang.String)
	 */
	@Override
	public final void setButtonTitle(String buttonTitle) {
		this.buttonTitle = buttonTitle;
	}

	/* (non-Javadoc)
	 * @see com.tedros.fxapi.presenter.view.group.ITGroupViewItem#getId()
	 */
	@Override
	public final String getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see com.tedros.fxapi.presenter.view.group.ITGroupViewItem#setId(java.lang.String)
	 */
	@Override
	public final void setId(String id) {
		this.id = id;
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, false);
	}
	
	/* (non-Javadoc)
	 * @see com.tedros.fxapi.presenter.view.group.ITGroupViewItem#getViewInstance()
	 */
	@Override
	public final ITView<?> getViewInstance(ITModule module) {
		if(view == null){
			try {
				view = getViewClass().getConstructor(ITModule.class, Class.class).newInstance(module, getModelViewClass());
				viewInitializedProperty();
				viewInitializedProperty.bind(view.gettPresenter().viewLoadedProperty());
				
				/*if(!viewInitializedProperty.get())
					viewInitializedProperty.set(true);*/
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return view;
	}
	
	/* (non-Javadoc)
	 * @see com.tedros.fxapi.presenter.view.group.ITGroupViewItem#isViewInitialized()
	 */
	@Override
	public boolean isViewInitialized() {
		return viewInitializedProperty().get();
	}

	/* (non-Javadoc)
	 * @see com.tedros.fxapi.presenter.view.group.ITGroupViewItem#viewInitializedProperty()
	 */
	@Override
	public final ReadOnlyBooleanProperty viewInitializedProperty() {
		if(viewInitializedProperty==null)
			viewInitializedProperty = new SimpleBooleanProperty(view!=null);
		return viewInitializedProperty;
	}

	/* (non-Javadoc)
	 * @see com.tedros.fxapi.presenter.view.group.ITGroupViewItem#getPresenterClass()
	 */
	@Override
	public final Class<? extends ITPresenter> getPresenterClass() {
		return presenterClass;
	}

	/* (non-Javadoc)
	 * @see com.tedros.fxapi.presenter.view.group.ITGroupViewItem#setPresenterClass(java.lang.Class)
	 */
	@Override
	public final void setPresenterClass(
			Class<? extends ITPresenter> presenterClass) {
		this.presenterClass = presenterClass;
	}

	@Override
	public ITGroupView gettGroupView() {
		return this.tGroupView;
	}

	@Override
	public void settGroupView(ITGroupView tGroupView) {
		this.tGroupView = tGroupView;
	}

	public ITModule getModule() {
		return module;
	}

	public void setModule(ITModule module) {
		this.module = module;
	}

	
}
