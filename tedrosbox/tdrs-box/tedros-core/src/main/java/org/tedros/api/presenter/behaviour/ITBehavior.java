package org.tedros.api.presenter.behaviour;

import org.tedros.api.form.ITModelForm;
import org.tedros.api.presenter.ITPresenter;
import org.tedros.api.presenter.view.ITView;
import org.tedros.api.presenter.view.TViewMode;
import org.tedros.core.model.ITModelView;
import org.tedros.core.repository.TRepository;

import javafx.collections.ObservableList;

@SuppressWarnings("rawtypes")
public interface ITBehavior<M extends ITModelView, P extends ITPresenter> {
	
	public P getPresenter();
	
	public void setPresenter(P presenter);
	
	public <V extends ITView> V getView();
	
	public void setModelView(M modelView);
	
	
	public void removeAllListenerFromModelView();
	
	public void removeAllListenerFromModelViewList();
	
	public <T> T removeListenerFromModelView(String listenerId);
	
	public M getModelView();
	
	public TRepository getListenerRepository();
	
	public void loadModelView(M modelView);
	
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
