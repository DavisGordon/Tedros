package org.tedros.api.presenter.behavior;

import org.tedros.api.form.ITModelForm;
import org.tedros.api.presenter.ITPresenter;
import org.tedros.api.presenter.view.ITView;
import org.tedros.api.presenter.view.TViewMode;
import org.tedros.core.model.ITModelView;
import org.tedros.core.repository.TRepository;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
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
	 * Loads the behavior
	 */
	void load();

	/**
	 * Gets the ITPresenter
	 * @return P the presenter
	 */
	P getPresenter();
	
	/***
	 * Sets the ITPresenter
	 * @param presenter
	 */
	void setPresenter(P presenter);
	
	/**
	 * Gets the ITView
	 * @return V the view
	 */
	<V extends ITView> V getView();

	/**
	 * Sets the model view to be loaded 
	 * into a form and shown in the view
	 * @param modelView
	 */
	void setModelView(M modelView);
	
	/**
	 * Gets the currently model view in edit 
	 * and loaded by a form in the view
	 * @return M the current ITModelView
	 */
	M getModelView();
	
	/**
	 * Removes all listeners from the current model
	 */
	void removeAllListenerFromModelView();
	/**
	 * Removes all listeners from models in the list
	 */
	void removeAllListenerFromModelViewList();
	
	/**
	 * Removes and returns the expected listener
	 * @param listenerId
	 * @return T the expected listeher
	 */
	<T> T removeListenerFromModelView(String listenerId);
	
	/**
	 * Gets the behavior listener repository
	 * @return TRepository
	 */
	TRepository getListenerRepository();
	
	/**
	 * Sets and load the model on the view
	 * @param modelView
	 */
	void loadModelView(M modelView);
	
	/**
	 * Sets the form mode. 
	 * @param mode
	 * */
	void setViewMode(TViewMode mode);
	
	/**
	 * Gets the form mode
	 * @return TViewMode
	 * */
	TViewMode getViewMode();
	
	/**
	 * Gets the models list
	 * @return ObservableList
	 */
	ObservableList<M> getModels();

	/**
	 * Sets a list of models
	 * @param models
	 */
	void setModelViewList(ObservableList<M> models); 
	

	/**
	 * Sets and load the model list
	 * @param models
	 */
	void loadModelViewList(ObservableList<M> models); 
	
	/**
	 * Returns the loaded form name
	 * @return String
	 */
	String getFormName();
	
	/**
	 * Sets the form to show
	 * @param form
	 */
	void setForm(ITModelForm form);
	
	/**
	 * Gets the form loaded for the current model
	 * @return ITModelForm
	 */
	ITModelForm<M> getForm();
	
	/**
	 * Gets the App Unique Id
	 * @return String
	 */
	String getApplicationUUID();
	
	/**
	 * invalidate the behavior
	 * @return boolean 
	 * */
	boolean invalidate();
	
	/**
	 * Asks the behavior if it can be invalidated to close
	 * @return String with the message of what cannot be 
	 * invalidated, null if it is ok to invalidate
	 */
	String canInvalidate();
	
	/**
	 * Set the view state as Ready
	 */
	void setViewStateAsReady();

	/**
	 * Builds the form as the mode param
	 * @param mode
	 */
	void buildForm(TViewMode mode);

	/**
	 * Builds the form. 
	 * Build the form as TViewMode.READER 
	 * if getViewMode() is null
	 */
	void buildForm();

	/***
	 * Clear the form
	 */
	void clearForm();

	/**
	 * @return the invalidate value
	 */
	Boolean getInvalidate();

	/**
	 * @return the invalidateProperty
	 */
	SimpleBooleanProperty invalidateProperty();

	/**
	 * Sets the invalidate value
	 * @param val the invalidate to set
	 */
	void setInvalidate(boolean val);

	/**
	 * Gets the modelViewProperty
	 * @return SimpleObjectProperty<M>
	 */
	SimpleObjectProperty<M> modelViewProperty();

	/**
	 * Gets the formProperty
	 * @return the ReadOnlyObjectProperty
	 */
	ReadOnlyObjectProperty<ITModelForm<M>> formProperty();

}
