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
	
	P getPresenter();
	
	void setPresenter(P presenter);
	
	<V extends ITView> V getView();
	
	void setModelView(M modelView);
	
	
	void removeAllListenerFromModelView();
	
	void removeAllListenerFromModelViewList();
	
	<T> T removeListenerFromModelView(String listenerId);
	
	M getModelView();
	
	TRepository getListenerRepository();
	
	void loadModelView(M modelView);
	
	/**
	 * Set the form mode. 
	 * */
	void setViewMode(TViewMode mode);
	
	/**
	 * Get the form mode
	 * */
	TViewMode getViewMode();
	
	void setModelViewList(ObservableList<M> models); 
	

	void loadModelViewList(ObservableList<M> models); 
	
	ObservableList<M> getModels();
	
	void load();
	
	String getFormName();
	
	void setForm(ITModelForm form);
	
	ITModelForm<M> getForm();
	
	String getApplicationUUID();
	
	/**
	 * invalidate the behavior
	 * @return 
	 * */
	boolean invalidate();
	
	String canInvalidate();

}
