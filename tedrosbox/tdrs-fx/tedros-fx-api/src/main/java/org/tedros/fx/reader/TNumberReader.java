/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 27/03/2014
 */
package org.tedros.fx.reader;

import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.text.Text;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public abstract class TNumberReader<N extends Number, P extends Property<N>> extends Text {

	private P valueProperty;
	
	public TNumberReader() {
		
	}
	
	public TNumberReader(N value) {
		setValue(value);
	}
	
	public abstract Class<P> getPropertyClass();
	
	public void setValue(N value){
		valueProperty().setValue(value);
	}
	
	public N getValue(){
		return valueProperty!=null ? valueProperty.getValue() : null;
	}
	
	public P valueProperty() {
		try {
			buildValueProperty();
			return valueProperty;
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void buildValueProperty() throws InstantiationException, IllegalAccessException {
		if(valueProperty==null)
			valueProperty = (P) getPropertyClass().newInstance();
		valueProperty.addListener(new ChangeListener<N>() {
			@Override
			public void changed(ObservableValue<? extends N> arg0, N arg1, N arg2) {
				if(arg2!=null)
					setText(String.valueOf(arg2));
			}
		});
	}	
}
