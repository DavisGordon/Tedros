/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package com.tedros.fxapi.builder;

import java.lang.annotation.Annotation;

import javafx.beans.property.Property;

import com.tedros.fxapi.annotation.control.TTextAreaField;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public final class TTextAreaFieldBuilder 
extends TBuilder
implements ITControlBuilder<com.tedros.fxapi.control.TTextAreaField, Property<String>> {

	private static TTextAreaFieldBuilder instance;
	
	private TTextAreaFieldBuilder(){
		
	}
	
	public static TTextAreaFieldBuilder getInstance(){
		if(instance==null)
			instance = new TTextAreaFieldBuilder();
		return instance;
	}

	public com.tedros.fxapi.control.TTextAreaField build(final Annotation annotation, final Property<String> attrProperty) throws Exception {
		TTextAreaField tAnnotation = (TTextAreaField) annotation;
		final com.tedros.fxapi.control.TTextAreaField control = new com.tedros.fxapi.control.TTextAreaField();
		callParser(tAnnotation, control);
		control.textProperty().bindBidirectional(attrProperty);
		return control;
	}
}
