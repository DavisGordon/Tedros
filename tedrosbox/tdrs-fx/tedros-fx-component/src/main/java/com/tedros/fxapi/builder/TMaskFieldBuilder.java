/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package com.tedros.fxapi.builder;

import java.lang.annotation.Annotation;

import javafx.beans.property.Property;

import com.tedros.fxapi.annotation.control.TMaskField;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public final class TMaskFieldBuilder
extends TBuilder
implements ITControlBuilder<com.tedros.fxapi.control.TMaskField, Property<String>> {
	
	public com.tedros.fxapi.control.TMaskField build(final Annotation annotation, final Property<String> attrProperty) throws Exception {
		TMaskField tAnnotation = (TMaskField) annotation;
		final com.tedros.fxapi.control.TMaskField control = new com.tedros.fxapi.control.TMaskField(tAnnotation.mask());
		callParser(tAnnotation, control);
		control.textProperty().bindBidirectional(attrProperty);
		return control;
	}
	
}
