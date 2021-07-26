/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package com.tedros.fxapi.builder;

import java.lang.annotation.Annotation;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.text.Text;

import com.tedros.core.TInternationalizationEngine;
import com.tedros.fxapi.annotation.text.TText;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class TTextBuilder 
extends TBuilder 
implements ITControlBuilder<com.tedros.fxapi.control.TText, SimpleStringProperty>, ITReaderBuilder<Text, SimpleStringProperty> {
	
	@Override
	public com.tedros.fxapi.control.TText build(Annotation annotation, final SimpleStringProperty property) throws Exception {
		TText tAnnotation = (TText) annotation;
		final com.tedros.fxapi.control.TText control = new com.tedros.fxapi.control.TText();
		control.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0,String arg1, String arg2) {
				property.setValue(TInternationalizationEngine.getInstance(null).getString(arg2));
			}
		});
		callParser(tAnnotation, control);
		
		return control;
	}	
}