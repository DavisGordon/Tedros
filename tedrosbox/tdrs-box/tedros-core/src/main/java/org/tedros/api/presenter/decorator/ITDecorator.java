package org.tedros.api.presenter.decorator;

import org.tedros.api.presenter.ITPresenter;
import org.tedros.api.presenter.view.ITView;

import javafx.scene.layout.StackPane;

/***
 * The view decorator contract.
 * 
 * @author Davis Gordon
 *
 * @param <P>
 */
@SuppressWarnings("rawtypes")
public interface ITDecorator<P extends ITPresenter> {
	
	/**
	 * Initialize the decorator
	 */
	public void decorate();
	/**
	 * Get the presenter
	 * @return P the presenter
	 */
	public P getPresenter();
	/**
	 * Set the presenter
	 * @param presenter
	 */
	public void setPresenter(P presenter);
	/**
	 * Get the view
	 * @return <V> V the view
	 */
	public <V extends ITView> V getView();
	/**
	 * Get the screen saver 
	 * @return StackPane
	 */
	public StackPane getScreenSaverPane();
	
	/**
	 * Show the screen saver
	 */
	public void showScreenSaver();
	/**
	 * Get the app unique id
	 * @return String
	 */
	public String getApplicationUUID();

}
