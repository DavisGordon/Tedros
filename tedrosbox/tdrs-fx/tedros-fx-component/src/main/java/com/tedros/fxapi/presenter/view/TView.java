package com.tedros.fxapi.presenter.view;

import java.io.IOException;
import java.net.URL;

import com.tedros.core.control.TProgressIndicator;
import com.tedros.core.presenter.ITPresenter;
import com.tedros.core.presenter.view.ITView;
import com.tedros.fxapi.modal.TModalPane;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;


@SuppressWarnings("rawtypes")
public abstract class TView<P extends ITPresenter> 
extends StackPane implements ITView<P>{

	private URL fxmlURL;
	private final P presenter;
	private TModalPane modalPane;
	
	private TProgressIndicator progressIndicator;
	private String tViewId;
	
	public TView(P presenter) {
		this.presenter = presenter;
		tInitialisePresenter();
	}
	
	public TView(P presenter, URL fxmlURL) {
		this.presenter = presenter;
		this.fxmlURL = fxmlURL;
		tLoadFXML();
		tInitialisePresenter();
	}
	
	@SuppressWarnings("unchecked")
	public void tInitialisePresenter(){
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
	 * Abre modal
	 * */
	public void tShowModal(Node message, boolean closeModalOnMouseClick) {
		initializeModalPane();
		modalPane.showModal(message, closeModalOnMouseClick);
	 }
	
	/**
	 * Fecha modal
	 * */
	public void tHideModal() {
		if(modalPane!=null)
			modalPane.hideModal();
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
		if(modalPane==null)
			modalPane = new TModalPane(this);
	}

	private void initializeProgressIndicator() {
		if(progressIndicator==null)
			progressIndicator = new TProgressIndicator(this);
	}
	
	
}
