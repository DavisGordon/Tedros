/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 27/03/2014
 */
package com.tedros.fxapi.reader;

import java.math.BigInteger;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import com.tedros.fxapi.util.TMaskUtil;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class TBigIntegerReader extends TTextReader<BigInteger> {
	
	private SimpleObjectProperty<BigInteger> valueProperty;

	public TBigIntegerReader() {
		
	}
	
	public TBigIntegerReader(BigInteger value) {
		setValue(value);
	}
	
	public void setValue(BigInteger value){
		valueProperty().setValue(value);
	}
	
	public BigInteger getValue(){
		return valueProperty!=null ? valueProperty.getValue() : null;
	}
	
	public SimpleObjectProperty<BigInteger> valueProperty() {
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
			valueProperty = new SimpleObjectProperty<>();
		valueProperty.addListener(new ChangeListener<BigInteger>() {
			@Override
			public void changed(ObservableValue<? extends BigInteger> arg0, BigInteger arg1, BigInteger arg2) {
				if(arg2!=null)
					setText(getMask()!=null ? TMaskUtil.applyMask(String.valueOf(arg2), getMask()) : String.valueOf(arg2) );
			}
		});
	}
	
}
