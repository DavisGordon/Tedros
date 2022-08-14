package org.tedros.fx.collections;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;

/**
 * <pre>
 * This interface type of {@link ObservableList} have methods which indicates when the list or your itens are changed.
 * 
 * <strong>Important:</strong>
 * Use the {@link TFXCollections} iTObservableList() method to get a proxy instance of this Class, 
 * the proxy returned will calculate the property tHashCodeProperty every time the list or a item is changed.
 * </pre>
 * @see ObservableList
 * @author Davis Gordon
 * */
public interface ITObservableList<E> extends ObservableList<E> {
	
	/**
	 * <pre>
	 * Return the tHashCodeProperty, this property indicates when this list or your itens are changed.
	 * </pre>
	 * 
	 * @return {@link SimpleIntegerProperty}
	 * */
	public ReadOnlyIntegerProperty tHashCodeProperty(); 
	
	/**
	 * <pre>
	 * Calculate and change the tHashCodeProperty
	 * </pre>
	 * 
	 * @return {@link SimpleIntegerProperty}
	 * */
	public void tBuildHashCodeProperty();
	
	
	/**
	 * <pre>
	 * Return the Class of this object, used when called by a proxy.
	 * </pre>
	 * 
	 * @return {@link SimpleIntegerProperty}
	 * */
	@SuppressWarnings("rawtypes")
	public Class gettGenericClass();

}
