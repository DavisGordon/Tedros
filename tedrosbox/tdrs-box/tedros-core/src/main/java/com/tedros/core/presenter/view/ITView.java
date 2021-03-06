package com.tedros.core.presenter.view;

import java.net.URL;

import com.tedros.core.control.TProgressIndicator;
import com.tedros.core.presenter.ITPresenter;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

/**
 * The view
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

		
}
