/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 27/03/2014
 */
package com.tedros.fxapi.reader;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class TBooleanReader extends TTextReader<Boolean> {
	
	private SimpleBooleanProperty valueProperty;
	private SimpleStringProperty trueStringValueProperty;
	private SimpleStringProperty falseStringValueProperty;
	
	public TBooleanReader() {
		
	}
	
	public TBooleanReader(Boolean value, String trueStringValue, String falseStringValue) {
		setFalseStringValue(falseStringValue);
		setTrueStringValue(trueStringValue);
		setValue(value);
	}
	
	public TBooleanReader(String trueStringValue, String falseStringValue) {
		setFalseStringValue(falseStringValue);
		setTrueStringValue(trueStringValue);
	}
	
	public void setValue(Boolean value){
		valueProperty().setValue(value);
	}
	
	public void setTrueStringValue(String trueStringValue) {
		trueStringValueProperty().setValue(trueStringValue);
	}
	
	public void setFalseStringValue(String falseStringValue) {
		falseStringValueProperty().setValue(falseStringValue);
	}
	
	public Boolean getValue(){
		return valueProperty!=null ? valueProperty.getValue() : null;
	}
	
	public SimpleBooleanProperty valueProperty() {
		buildValueProperty();
		return valueProperty;
	}

	private void buildValueProperty() {
		if(valueProperty==null)
			valueProperty = new SimpleBooleanProperty();
		valueProperty.addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				if(arg2!=null && valueProperty!=null && trueStringValueProperty!=null && falseStringValueProperty!=null){
					if(arg2)
						setText(trueStringValueProperty.getValue());
					else
						setText(falseStringValueProperty.getValue());
				}
			}
		});
	}

	public SimpleStringProperty trueStringValueProperty() {
		buildTrueStringValueProperty();
		return trueStringValueProperty;
	}
	
	private void buildTrueStringValueProperty() {
		if(trueStringValueProperty==null)
			trueStringValueProperty = new SimpleStringProperty();
		trueStringValueProperty.addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if(arg2!=null && valueProperty!=null && trueStringValueProperty!=null && falseStringValueProperty!=null){
					if(valueProperty.getValue())
						setText(trueStringValueProperty.getValue());
					else
						setText(falseStringValueProperty.getValue());
				}
			}
		});
	}
	
	public SimpleStringProperty falseStringValueProperty() {
		buildFalseStringValueProperty();
		return falseStringValueProperty;
	}
	
	private void buildFalseStringValueProperty() {
		if(falseStringValueProperty==null)
			falseStringValueProperty = new SimpleStringProperty();
		falseStringValueProperty.addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if(arg2!=null && valueProperty!=null && trueStringValueProperty!=null && falseStringValueProperty!=null){
					if(valueProperty.getValue())
						setText(trueStringValueProperty.getValue());
					else
						setText(falseStringValueProperty.getValue());
				}
			}
		});
	}
	
}
