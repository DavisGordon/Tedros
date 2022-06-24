package com.tedros.fxapi.presenter.behavior;

import com.tedros.core.model.ITModelView;
import com.tedros.core.module.TObjectRepository;
import com.tedros.core.presenter.ITPresenter;
import com.tedros.core.presenter.view.ITView;
import com.tedros.fxapi.domain.TViewMode;
import com.tedros.fxapi.form.ITModelForm;
import com.tedros.fxapi.presenter.model.TModelView;

import javafx.collections.ObservableList;

@SuppressWarnings("rawtypes")
public interface ITBehavior<M extends TModelView, P extends ITPresenter> {
	
	public P getPresenter();
	
	public void setPresenter(P presenter);
	
	public <V extends ITView> V getView();
	
	public void setModelView(ITModelView modelView);
	
	
	public void removeAllListenerFromModelView();
	
	public void removeAllListenerFromModelViewList();
	
	public <T> T removeListenerFromModelView(String listenerId);
	
	public <T extends TModelView> T getModelView();
	
	public TObjectRepository getListenerRepository();
	
	public void loadModelView(ITModelView modelView);
	
	/**
	 * Set the form mode. 
	 * */
	public void setViewMode(TViewMode mode);
	
	/**
	 * Get the form mode
	 * */
	public TViewMode getViewMode();
	
	public void setModelViewList(ObservableList<M> models); 
	
	public ObservableList<M> getModels();
	
	public void load();
	
	public String getFormName();
	
	public void setForm(ITModelForm form);
	
	public ITModelForm<M> getForm();
	
	public String getApplicationUUID();
	
	/**
	 * invalidate the behavior
	 * @return 
	 * */
	public boolean invalidate();
	
	public String canInvalidate();

}
