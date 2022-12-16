package org.tedros.api.presenter.behaviour;

import org.tedros.api.form.ITModelForm;
import org.tedros.api.presenter.ITPresenter;
import org.tedros.api.presenter.view.ITView;
import org.tedros.api.presenter.view.TViewMode;
import org.tedros.core.model.ITModelView;
import org.tedros.core.repository.TRepository;

import javafx.collections.ObservableList;
/**
 * The view behavior contract.
 * <p>
 * Act upon the data and view events.</p>
 * @author Davis Gordon
 * 
 * */
@SuppressWarnings("rawtypes")
public interface ITBehavior<M extends ITModelView, P extends ITPresenter> {
	
	/**
	 * Load the behavior
	 */
	void load();

	/**
	 * Get the ITPresenter
	 * @return P the presenter
	 */
	P getPresenter();
	
	/***
	 * Set the ITPresenter
	 * @param presenter
	 */
	void setPresenter(P presenter);
	
	/**
	 * Get the ITView
	 * @return V the view
	 */
	<V extends ITView> V getView();
	
	/**
	 * Define the model to be loaded 
	 * into a form and shown in the view
	 * @param modelView
	 */
	void setModelView(M modelView);
	
	/**
	 *Get the current model in edit 
	 *and loaded by a form in the view
	 * @return M the current ITModelView
	 */
	M getModelView();
	
	/**
	 * Remove all listeners from the current model
	 */
	void removeAllListenerFromModelView();
	/**
	 * Remove all listeners from models in the list
	 */
	void removeAllListenerFromModelViewList();
	
	/**
	 * Remove and return the expected listener
	 * @param listenerId
	 * @return T the expected listeher
	 */
	<T> T removeListenerFromModelView(String listenerId);
	
	/**
	 * Get the behavior listener repository
	 * @return TRepository
	 */
	TRepository getListenerRepository();
	
	/**
	 * Set and load the model on the view
	 * @param modelView
	 */
	void loadModelView(M modelView);
	
	/**
	 * Set the form mode. 
	 * @param mode
	 * */
	void setViewMode(TViewMode mode);
	
	/**
	 * Get the form mode
	 * @return TViewMode
	 * */
	TViewMode getViewMode();
	
	/**
	 * Get the models list
	 * @return ObservableList
	 */
	ObservableList<M> getModels();

	/**
	 * Set a list of models
	 * @param models
	 */
	void setModelViewList(ObservableList<M> models); 
	

	/**
	 * Set and load the model list
	 * @param models
	 */
	void loadModelViewList(ObservableList<M> models); 
	
	/**
	 * Return the loaded form name
	 * @return String
	 */
	String getFormName();
	
	/**
	 * Set the form to show
	 * @param form
	 */
	void setForm(ITModelForm form);
	
	/**
	 * Get the form loaded for the current model
	 * @return ITModelForm
	 */
	ITModelForm<M> getForm();
	
	/**
	 * Get the App Unique Id
	 * @return String
	 */
	String getApplicationUUID();
	
	/**
	 * invalidate the behavior
	 * @return boolean 
	 * */
	boolean invalidate();
	
	/**
	 * Ask the behavior if it can be invalidated to close
	 * @return String with the message of what cannot be 
	 * invalidated, null if it is ok to invalidate
	 */
	String canInvalidate();

}
