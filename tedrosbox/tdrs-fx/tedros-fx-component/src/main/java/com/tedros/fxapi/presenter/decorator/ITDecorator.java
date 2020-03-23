package com.tedros.fxapi.presenter.decorator;

import javafx.scene.layout.StackPane;

import com.tedros.core.presenter.ITPresenter;
import com.tedros.core.presenter.view.ITView;

@SuppressWarnings("rawtypes")
public interface ITDecorator<P extends ITPresenter> {
	
	public P getPresenter();
	
	public void setPresenter(P presenter);
	
	public <V extends ITView> V getView();
	
	public void decorate();
	
	public StackPane getScreenSaverPane();
	
	public void showScreenSaver();
	
	public String getApplicationUUID();

}
