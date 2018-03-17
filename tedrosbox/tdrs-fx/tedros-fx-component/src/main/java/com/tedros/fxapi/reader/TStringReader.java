package com.tedros.fxapi.reader;



import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import com.tedros.fxapi.util.TMaskUtil;

public class TStringReader extends TTextReader<String> {
	
	private SimpleStringProperty valueProperty;
	
	public TStringReader() {
			
	}
	
	public TStringReader(String value) {
		setValue(value);
	}
	
	public TStringReader(String value, String mask) {
		setValue(value);
		setMask(mask);
	}
	
	public void setValue(String value){
		valueProperty().setValue(value);
	}
	
	public String getValue(){
		return valueProperty!=null ? valueProperty.getValue() : null;
	}
	
	public SimpleStringProperty valueProperty() {
		buildValueProperty();
		return valueProperty;
	}

	private void buildValueProperty() {
		if(valueProperty==null)
			valueProperty = new SimpleStringProperty();
		valueProperty.addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if(getMask()!=null && arg2!=null){
					setText(TMaskUtil.applyMask(arg2, getMask()));
				}else{
					setText(arg2);
				}
			}
		});
	}
	
	
}
