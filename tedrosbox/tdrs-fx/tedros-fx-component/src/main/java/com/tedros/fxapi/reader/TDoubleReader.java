/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 27/03/2014
 */
package com.tedros.fxapi.reader;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import com.tedros.fxapi.util.TMaskUtil;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class TDoubleReader extends TTextReader<Double> {
	
	private SimpleDoubleProperty valueProperty;

	public TDoubleReader() {
		
	}
	
	public TDoubleReader(Double value) {
		setValue(value);
	}
	
	public void setValue(Double value){
		valueProperty().setValue(value);
	}
	
	public Double getValue(){
		return valueProperty!=null ? valueProperty.getValue() : null;
	}
	
	public SimpleDoubleProperty valueProperty() {
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
			valueProperty = new SimpleDoubleProperty();
		valueProperty.addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				if(arg2!=null)
					setText(getMask()!=null ? TMaskUtil.applyMask(String.valueOf(arg2), getMask()) : String.valueOf(arg2) );
			}
		});
	}
	
}
