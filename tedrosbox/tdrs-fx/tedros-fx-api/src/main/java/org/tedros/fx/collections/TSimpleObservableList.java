package org.tedros.fx.collections;

import java.util.Objects;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

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
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(get(), t_hashCodeProperty);
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof TSimpleObservableList))
			return false;
		TSimpleObservableList other = (TSimpleObservableList) obj;
		return Objects.equals(get(), other.get()) && Objects.equals(t_hashCodeProperty, other.t_hashCodeProperty);
	}	
}
