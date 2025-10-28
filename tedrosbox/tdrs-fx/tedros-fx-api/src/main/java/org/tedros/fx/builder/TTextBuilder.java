/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package org.tedros.fx.builder;

import java.lang.annotation.Annotation;

import org.tedros.core.TLanguage;
import org.tedros.fx.annotation.text.TText;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.text.Text;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class TTextBuilder 
extends TBuilder 
implements ITControlBuilder<org.tedros.fx.control.TText, SimpleStringProperty>, ITReaderBuilder<Text, SimpleStringProperty> {
	
	@Override
	public org.tedros.fx.control.TText build(Annotation annotation, final SimpleStringProperty property) throws Exception {
		TText tAnnotation = (TText) annotation;
		final org.tedros.fx.control.TText control = new org.tedros.fx.control.TText();
		control.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0,String arg1, String arg2) {
				property.setValue(TLanguage.getInstance(null).getString(arg2));
			}
		});
		callParser(tAnnotation, control);
		
		return control;
	}	
}