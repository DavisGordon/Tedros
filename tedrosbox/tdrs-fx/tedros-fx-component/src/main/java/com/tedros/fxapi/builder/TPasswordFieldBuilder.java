/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package com.tedros.fxapi.builder;

import java.lang.annotation.Annotation;

import javafx.beans.property.Property;

import com.tedros.fxapi.annotation.control.TPasswordField;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public final class TPasswordFieldBuilder 
extends TBuilder
implements ITControlBuilder<com.tedros.fxapi.control.TPasswordField, Property<String>> {

	private static TPasswordFieldBuilder instance;
	
	private TPasswordFieldBuilder(){
		
	}
	
	public static  TPasswordFieldBuilder getInstance(){
		if(instance==null)
			instance = new TPasswordFieldBuilder();
		return instance;	
	}

	public com.tedros.fxapi.control.TPasswordField build(final Annotation annotation, final Property<String> attrProperty) throws Exception {
		TPasswordField tAnnotation = (TPasswordField) annotation;
		final com.tedros.fxapi.control.TPasswordField control = new com.tedros.fxapi.control.TPasswordField();
		callParser(tAnnotation, control);
		control.textProperty().bindBidirectional(attrProperty);
		return control;
	}
}
