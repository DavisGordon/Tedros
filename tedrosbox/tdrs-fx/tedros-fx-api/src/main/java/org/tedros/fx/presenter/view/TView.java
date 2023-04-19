package org.tedros.fx.presenter.view;

import java.io.IOException;
import java.net.URL;
import java.util.function.Consumer;

import org.tedros.api.presenter.ITPresenter;
import org.tedros.api.presenter.view.ITView;
import org.tedros.api.presenter.view.TViewState;
import org.tedros.core.control.TProgressIndicator;
import org.tedros.fx.modal.TModalPane;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;


@SuppressWarnings("rawtypes")
public abstract class TView<P extends ITPresenter> 
extends StackPane implements ITView<P>{

	private URL fxmlURL;
	private String tViewId;
	private final P presenter;
	private TModalPane modalPane;
	private TProgressIndicator progressIndicator;
	private SimpleObjectProperty<TViewState> stateProperty= new SimpleObjectProperty<>(TViewState.CREATED);
	
	public TView(P presenter) {
		this.presenter = presenter;
		tInitializePresenter();
	}
	
	public TView(P presenter, URL fxmlURL) {
		this.presenter = presenter;
		this.fxmlURL = fxmlURL;
		tLoadFXML();
		tInitializePresenter();
	}
	
	@SuppressWarnings("unchecked")
	public void tInitializePresenter(){
		if(this.presenter == null)
			throw new IllegalStateException();

		this.presenter.setView(this);
		this.presenter.initialize();
	}
	
	@Override
	public void tLoad() {
		gettPresenter().loadView();
	}
	
	public void tLoadFXML() {
		
		if(gettFxmlURL()==null)
			throw new IllegalArgumentException("ERROR: FXML not defined!");

		try{
			FXMLLoader fxmlLoader = new FXMLLoader(gettFxmlURL());
			fxmlLoader.setRoot(this);
	        fxmlLoader.setController(this);
	        fxmlLoader.load();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public P gettPresenter(){
		return this.presenter;
	}
	
	/**
	 * Show the modal
	 * */
	public void tShowModal(Node message, boolean closeModalOnMouseClick) {
		initializeModalPane();
		modalPane.showModal(message, closeModalOnMouseClick);
	 }
	
	/**
	 * Show the modal
	 * */
	public void tShowModal(Node message, boolean closeModalOnMouseClick, Consumer<Node> closeCallback) {
		initializeModalPane();
		modalPane.showModal(message, closeModalOnMouseClick, closeCallback);
	 }
	
	public ReadOnlyBooleanProperty tModalVisibleProperty() {
		initializeModalPane();
		return  modalPane.visibleProperty();
	}
	
	/**
	 * Close the modal
	 * */
	public void tHideModal() {
		if(modalPane!=null) {
			modalPane.hideModal();
		}
	}

	public TProgressIndicator gettProgressIndicator() {
		initializeProgressIndicator();
		return progressIndicator;
	}

	@Override
	public URL gettFxmlURL() {
		return fxmlURL;
	}

	@Override
	public void settFxmlURL(URL fxmlUrl) {
		this.fxmlURL = fxmlUrl;	
	}
	
	public String gettViewId() {
		return tViewId;
	}
	
	
	public void settViewId(String id) {
		this.tViewId = id;

	}

	public TModalPane getModalPane() {
		return modalPane;
	}

	public void setModalPane(TModalPane modalPane) {
		this.modalPane = modalPane;
	}
	
	private void initializeModalPane() {
		if(modalPane==null) {
			modalPane = new TModalPane(this);
		}
	}

	private void initializeProgressIndicator() {
		if(progressIndicator==null)
			progressIndicator = new TProgressIndicator(this);
	}
	
	public void settState(TViewState state) {
		this.stateProperty.setValue(state);
	}
	
	public TViewState gettState() {
		return this.stateProperty.getValue();
	}

	/**
	 * @return the stateProperty
	 */
	public ReadOnlyObjectProperty<TViewState> tStateProperty() {
		return stateProperty;
	}
	
	
}
