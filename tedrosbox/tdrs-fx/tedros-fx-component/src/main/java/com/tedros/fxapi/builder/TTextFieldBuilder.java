/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package com.tedros.fxapi.builder;

import java.lang.annotation.Annotation;

import javafx.beans.property.Property;

import com.tedros.fxapi.annotation.control.TTextField;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class TTextFieldBuilder 
extends TBuilder
implements ITControlBuilder<com.tedros.fxapi.control.TTextField, Property<String>> {

	private static TTextFieldBuilder instance;
	
	private TTextFieldBuilder(){
		
	}
	
	public static TTextFieldBuilder getInstance(){
		if(instance==null)
			instance = new TTextFieldBuilder();
		return instance;
	}
	
	public com.tedros.fxapi.control.TTextField build(final Annotation annotation, final Property<String> attrProperty) throws Exception {
		TTextField tAnnotation = (TTextField) annotation;
		final com.tedros.fxapi.control.TTextField control = new com.tedros.fxapi.control.TTextField();
		callParser(tAnnotation, control);
		control.textProperty().bindBidirectional(attrProperty);
		return control;
	}
	
}
