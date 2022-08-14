package org.tedros.core.model;

import java.util.Map;

import java.util.Set;

import org.tedros.core.module.TObjectRepository;
import org.tedros.server.model.ITModel;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.SetChangeListener;

/**
 * The model view 
 * */
public interface ITModelView<M extends ITModel> {
	
	/**
	 * <pre>
	 * Get the value to display in components.
	 * </pre>
	 * */
	public SimpleStringProperty getDisplayProperty();
	
	/**
	 * Removes all listener
	 * */
	public void removeAllListener();
	
	/**
	 * Removes the listener
	 * */
	public <T> T removeListener(String listenerId);
	
	/**
	 * Returns all listeners key
	 * */
	public Map<String, Set<String>> getListenerKeys();
	
	/**
	 * Returns the listener repository
	 * */
	public TObjectRepository getListenerRepository();
	
	/**
	 * Adds a {@link InvalidationListener} on the repository 
	 * 
	 * @param fieldName
	 * @param invalidationListener
	 */
	public void addListener(final String fieldName, InvalidationListener invalidationListener);
	
	/**
	 * Adds a {@link ChangeListener} on the repository
	 * 
	 * @param fieldName
	 * @param changeListener
	 */
	@SuppressWarnings("rawtypes")
	public void addListener(final String fieldName, ChangeListener changeListener);
	
	/**
	 * Adds a {@link MapChangeListener} on the repository
	 * 
	 * @param fieldName
	 * @param changeListener
	 */
	@SuppressWarnings("rawtypes")
	public void addListener(final String fieldName, final MapChangeListener mapChangeListener);
	
	/**
	 * Adds a {@link SetChangeListener} on the repository
	 * 
	 * @param fieldName
	 * @param changeListener
	 */
	@SuppressWarnings("rawtypes")
	public void addListener(final String fieldName, final SetChangeListener setChangeListener);

	/**
	 * Adds a {@link ListChangeListener} on the repository
	 * 
	 * @param fieldName
	 * @param changeListener
	 */
	@SuppressWarnings("rawtypes")
	public void addListener(final String fieldName, final ListChangeListener listChangeListener);

	public Observable getProperty(String fieldName);
	
}