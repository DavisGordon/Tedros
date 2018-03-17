package com.tedros.core.presenter.view;

import java.net.URL;

import com.tedros.core.control.TProgressIndicator;
import com.tedros.core.presenter.ITPresenter;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;

@SuppressWarnings("rawtypes")
public interface ITView<P extends ITPresenter>{
	
	public void tInitialisePresenter();
	
	public P gettPresenter();
	
	public void tShowModal(Node message, boolean closeModalOnMouseClick);
	
	public void tHideModal();
	
	public TProgressIndicator gettProgressIndicator();
	
	public String gettViewId();
	
	public void settViewId(String id);
	
	public StackPane gettFormSpace();
	
	public URL gettFxmlURL();
	
	public void settFxmlURL(URL fxmlUrl);
	
	public void tLoad();
		
}
