/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 27/03/2014
 */
package com.tedros.fxapi.reader;

import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class TDateTimeReader extends TTextReader<Date> {
	
	private SimpleObjectProperty<Date> valueProperty;
	private SimpleStringProperty datePatternProperty;
	
	public TDateTimeReader() {

	}
	
	public TDateTimeReader(Date value) {
		setValue(value);
	}
	
	public void setValue(Date value){
		valueProperty().setValue(value);
	}
	
	public Date getValue(){
		return valueProperty!=null ? valueProperty.getValue() : null;
	}
	
	public SimpleObjectProperty<Date> valueProperty() {
		buildValueProperty();
		return valueProperty;
	}

	private void buildValueProperty() {
		if(valueProperty==null)
			valueProperty = new SimpleObjectProperty<>();
		valueProperty.addListener(new ChangeListener<Date>() {
			@Override
			public void changed(ObservableValue<? extends Date> arg0, Date arg1, Date arg2) {
				if(arg2!=null){
					if(datePatternProperty!=null && datePatternProperty.getValue()!=null)
						setText(new SimpleDateFormat(datePatternProperty.getValue()).format(arg2));
					else
						setText(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(arg2));
				}
			}
		});
	}

	public final SimpleStringProperty datePatternProperty() {
		buildDatePatternProperty();
		return datePatternProperty;
	}
	
	private void buildDatePatternProperty() {
		if(datePatternProperty==null)
			datePatternProperty = new SimpleStringProperty();
		datePatternProperty.addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				final SimpleObjectProperty<Date> value =  valueProperty();
				if(value.getValue()!=null && arg2!=null){
					setText(new SimpleDateFormat(arg2).format(value.getValue()));
				}
				
			}
		});
	}

	public final void setDatePattern(String datePattern) {
		datePatternProperty().setValue(datePattern);
	}	

}
