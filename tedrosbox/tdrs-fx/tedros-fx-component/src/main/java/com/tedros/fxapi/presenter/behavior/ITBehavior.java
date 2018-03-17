package com.tedros.fxapi.presenter.behavior;

import javafx.collections.ObservableList;

import com.tedros.core.presenter.ITPresenter;
import com.tedros.core.presenter.view.ITView;
import com.tedros.fxapi.domain.TViewMode;
import com.tedros.fxapi.form.ITModelForm;
import com.tedros.fxapi.presenter.model.TModelView;

@SuppressWarnings("rawtypes")
public interface ITBehavior<M extends TModelView, P extends ITPresenter> {
	
	public P getPresenter();
	
	public void setPresenter(P presenter);
	
	public <V extends ITView> V getView();
	
	public void setModelView(M modelView);
	
	public void removeAllListenerFromModelView();
	
	public void removeAllListenerFromModelViewList();
	
	public <T> T removeListenerFromModelView(String listenerId);
	
	public <T extends TModelView> T getModelView();
	
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
	
	public String getApplicationUUID();

}
