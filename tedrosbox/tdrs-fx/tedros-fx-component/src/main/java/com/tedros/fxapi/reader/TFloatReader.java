/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 27/03/2014
 */
package com.tedros.fxapi.reader;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import com.tedros.fxapi.util.TMaskUtil;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class TFloatReader extends TTextReader<Float> {
	
	private SimpleFloatProperty valueProperty;

	public TFloatReader() {
		
	}
	
	public TFloatReader(Float value) {
		setValue(value);
	}
	
	public void setValue(Float value){
		valueProperty().setValue(value);
	}
	
	public Float getValue(){
		return valueProperty!=null ? valueProperty.getValue() : null;
	}
	
	public SimpleFloatProperty valueProperty() {
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
			valueProperty = new SimpleFloatProperty();
		valueProperty.addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				if(arg2!=null)
					setText(getMask()!=null ? TMaskUtil.applyMask(String.valueOf(arg2), getMask()) : String.valueOf(arg2) );
			}
		});
	}
	
}
