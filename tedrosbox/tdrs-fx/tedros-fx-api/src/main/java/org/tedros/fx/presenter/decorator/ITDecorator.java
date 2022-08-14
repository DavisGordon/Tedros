package org.tedros.fx.presenter.decorator;

import org.tedros.core.presenter.ITPresenter;
import org.tedros.core.presenter.view.ITView;

import javafx.scene.layout.StackPane;

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
