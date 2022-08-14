package org.tedros.fx.collections;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * <pre>
 * This implementation of {@link ITObservableList} have methods which indicates when the list or your itens are changed.
 * 
 * <strong>Important:</strong>
 * Use the {@link TFXCollections} iTObservableList() method to get a proxy instance of this Class, 
 * the proxy returned will calculate the property t_hashCodeProperty every time the list or a item is changed.
 * </pre>
 * @see SimpleListProperty
 * @author Davis Gordon
 * */
public class TSimpleObservableList<E> extends SimpleListProperty<E> implements ITObservableList<E> {
	
	private SimpleIntegerProperty t_hashCodeProperty;
	
	public TSimpleObservableList() {
		super(FXCollections.<E>observableArrayList());
	}
	
	public ReadOnlyIntegerProperty tHashCodeProperty() {
		initHashCodeProperty();
		return t_hashCodeProperty;
	}
	
	public void tBuildHashCodeProperty(){
		initHashCodeProperty();
		t_hashCodeProperty.setValue(hashCode());
	}

	private void initHashCodeProperty() {
		if(t_hashCodeProperty==null)
			t_hashCodeProperty = new SimpleIntegerProperty(hashCode());
	}

	@SuppressWarnings("rawtypes")
	public Class gettGenericClass() {
		return getClass();
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode( get(),  false);
	}

	
	
}
