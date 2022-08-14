/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 27/03/2014
 */
package org.tedros.fx.reader;

import org.tedros.fx.util.TMaskUtil;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class TIntegerReader extends TTextReader<Integer> {
	
	private SimpleIntegerProperty valueProperty;

	public TIntegerReader() {
		
	}
	
	public TIntegerReader(Integer value) {
		setValue(value);
	}
	
	public void setValue(Integer value){
		valueProperty().setValue(value);
	}
	
	public Integer getValue(){
		return valueProperty!=null ? valueProperty.getValue() : null;
	}
	
	public SimpleIntegerProperty valueProperty() {
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
			valueProperty = new SimpleIntegerProperty();
		valueProperty.addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				if(arg2!=null)
					setText(getMask()!=null ? TMaskUtil.applyMask(String.valueOf(arg2), getMask()) : String.valueOf(arg2) );
			}
		});
	}
}
