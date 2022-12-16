package org.tedros.api.presenter.view;

import java.net.URL;

import org.tedros.api.presenter.ITPresenter;
import org.tedros.core.control.TProgressIndicator;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

/**
 * The view contract.
 * <p>
 * The view is a passive interface that displays data (the ITModelView) 
 * and routes user commands (events) to the ITBehavior holded by 
 * the presenter to act upon that data.
 * </p>
 * @author Davis Gordon
 * */
@SuppressWarnings("rawtypes")
public interface ITView<P extends ITPresenter>{
	
	/**
	 * Initialize the presenter
	 * */
	public void tInitializePresenter();
	
	/**
	 * Returns the presenter
	 * */
	public P gettPresenter();
	
	/**
	 * Shows a modal
	 * */
	public void tShowModal(Node message, boolean closeModalOnMouseClick);
	
	/**
	 * Hides a modal
	 * */
	public void tHideModal();
	
	/**
	 * The modal visible property
	 * */
	public ReadOnlyBooleanProperty tModalVisibleProperty();
	
	/**
	 * Returns the progress indicator
	 * */
	public TProgressIndicator gettProgressIndicator();
	
	/**
	 * Returns the view id
	 * */
	public String gettViewId();
	
	/**
	 * Sets the view id
	 * */
	public void settViewId(String id);
	
	/**
	 * Returns the pane to the the form
	 * */
	public StackPane gettFormSpace();
	
	/**
	 * Returns the URL for the FXML
	 * */
	public URL gettFxmlURL();
	
	/**
	 * Sets the URL for the FXML
	 * */
	public void settFxmlURL(URL fxmlUrl);
	
	/**
	 * Loads the view
	 * */
	public void tLoad();
	
	/**
	 * Set the view state
	 * */
	public void settState(TViewState state);
	
	/**
	 * @return the view state
	 * */
	public TViewState gettState();

	/**
	 * @return the stateProperty
	 */
	public ReadOnlyObjectProperty<TViewState> tStateProperty();
	

		
}
