/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package com.tedros.fxapi.builder;

import java.lang.annotation.Annotation;

import javafx.beans.property.Property;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class TCheckBoxFieldBuilder 
extends TBuilder 
implements ITControlBuilder<com.tedros.fxapi.control.TCheckBoxField, Property<Boolean>> {
	
	public com.tedros.fxapi.control.TCheckBoxField build(final Annotation annotation, final Property<Boolean> attrProperty) throws Exception {
		final com.tedros.fxapi.control.TCheckBoxField control = new com.tedros.fxapi.control.TCheckBoxField();
		callParser(annotation, control);
		//TRequiredCheckBoxParser.getInstance().parse(annotation, null, control);
		control.selectedProperty().bindBidirectional(attrProperty);
		return control;
	}
	
}
