package org.tedros.core.model;

import java.util.Map;
import java.util.Set;

import org.tedros.core.repository.TRepository;
import org.tedros.server.model.ITModel;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.SetChangeListener;

/**
 * The model view contract
 * */
public interface ITModelView<M extends ITModel> {
	
	
	/**
	 * <pre>
	 * The toString property 
	 * @return SimpleStringProperty
	 * </pre>
	 * */
	SimpleStringProperty toStringProperty();
	
	/**
	 * <pre>
	 * Format the toString value
	 * </pre>
	 * @see java.util.Formatter
	 * */
	void formatToString(String format, ObservableValue<?>... fields);
	
	/**
	 * Removes all listener
	 * */
	void removeAllListener();
	
	/**
	 * Removes the listener
	 * */
	<T> T removeListener(String listenerId);
	
	/**
	 * Returns all listeners key
	 * */
	Map<String, Set<String>> getListenerKeys();
	
	/**
	 * Returns the listener repository
	 * */
	TRepository getListenerRepository();
	
	/**
	 * Adds a {@link InvalidationListener} on the repository 
	 * 
	 * @param fieldName
	 * @param invalidationListener
	 */
	void addListener(final String fieldName, InvalidationListener invalidationListener);
	
	/**
	 * Adds a {@link ChangeListener} on the repository
	 * 
	 * @param fieldName
	 * @param changeListener
	 */
	@SuppressWarnings("rawtypes")
	void addListener(final String fieldName, ChangeListener changeListener);
	
	/**
	 * Adds a {@link MapChangeListener} on the repository
	 * 
	 * @param fieldName
	 * @param changeListener
	 */
	@SuppressWarnings("rawtypes")
	void addListener(final String fieldName, final MapChangeListener mapChangeListener);
	
	/**
	 * Adds a {@link SetChangeListener} on the repository
	 * 
	 * @param fieldName
	 * @param changeListener
	 */
	@SuppressWarnings("rawtypes")
	void addListener(final String fieldName, final SetChangeListener setChangeListener);

	/**
	 * Adds a {@link ListChangeListener} on the repository
	 * 
	 * @param fieldName
	 * @param changeListener
	 */
	@SuppressWarnings("rawtypes")
	void addListener(final String fieldName, final ListChangeListener listChangeListener);

	Observable getProperty(String fieldName);
	
}