package org.tedros.core.model;

import java.util.Map;
import java.util.Set;

import org.tedros.core.repository.TRepository;
import org.tedros.server.model.ITModel;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.SetChangeListener;

/**
 * The model view contract
 * */
@SuppressWarnings("rawtypes")
public interface ITModelView<M extends ITModel> {

	String getModelViewId();

	void setModelViewId(String modelViewId);

	/**
	 * <pre>
	 * Get the value to display in components.
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

	void formatToString(TFormatter formatter);

	/**
	 * <pre>
	 * Get the model.
	 * </pre>
	 * */
	M getModel();

	/**
	 * <pre>
	 * Set a new model and reload the values in the model view properties. 
	 * </pre>
	 * */
	void reload(M model);

	int hashCode();

	/**
	 * <pre>
	 * To be called by hashCode methods. 
	 * The hashCode are built over the ITModel object instead this TModelView object
	 * the reason is because we need to know when the model is changed and we do this
	 * observing the hashcode value. 
	 * 
	 * 
	 *	Example:
	 *	public class ExampleModelView extends TModelView&lt;ExampleModel&gt;{
	 *		...
	 *		<i>@</i>Override
	 *		public int hashCode() {
	 *			return reflectionHashCode("someFieldToBeExcluded");
	 *		}
	 *	}
	 * 
	 * </pre>
	 * @param excludeFields
	 * */
	int reflectionHashCode(String... excludeFields);

	/**
	 * <pre>
	 * Return the last hashCode to check changes 
	 * </pre>
	 * */
	int getLastHashCode();

	/**
	 * <pre>
	 * Return true if the model are changed.
	 * </pre>
	 * */
	boolean isChanged();

	/**
	 * <pre>
	 * The property for the lastHashCode value.
	 * </pre>
	 * */
	SimpleIntegerProperty lastHashCodeProperty();

	/**
	 * <pre>
	 * A property to check when the model view are reloaded.
	 * </pre>
	 * */
	SimpleLongProperty loadedProperty();

	/**
	 * <pre>
	 * Return the value of the getDisplayProperty method.
	 * </pre>
	 * */
	String toString();

	/**
	 * <pre>
	 * Perform a equals in getId and getDisplayProperty;
	 * </pre>
	 * */
	boolean equals(Object obj);

	/**
	 * Return the {@link ObservableValue} created to the field name. 
	 * */
	<T extends Observable> T getProperty(String fieldName);

	void removeAllListener();

	<T> T removeListener(String listenerId);

	Map<String, Set<String>> getListenerKeys();

	TRepository getListenerRepository();

	/**
	 * @param fieldName
	 * @param invalidationListener
	 */
	void addListener(String fieldName, InvalidationListener invalidationListener);

	/**
	 * @param fieldName
	 * @param changeListener
	 */
	void addListener(String fieldName, ChangeListener changeListener);

	/**
	 * @param fieldName
	 * @param changeListener
	 */
	void addListener(String fieldName, MapChangeListener changeListener);

	/**
	 * @param fieldName
	 * @param changeListener
	 */
	void addListener(String fieldName, SetChangeListener changeListener);

	/**
	 * @param fieldName
	 * @param changeListener
	 */
	void addListener(String fieldName, ListChangeListener changeListener);

}