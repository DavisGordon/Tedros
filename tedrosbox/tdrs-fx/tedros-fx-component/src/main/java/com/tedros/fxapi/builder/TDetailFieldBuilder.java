/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package com.tedros.fxapi.builder;

import java.lang.annotation.Annotation;

import com.tedros.fxapi.annotation.control.TDetailField;

import javafx.beans.property.Property;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public class TDetailFieldBuilder 
extends TBuilder
implements ITControlBuilder<com.tedros.fxapi.control.TDetailField, Property> {

	
	@SuppressWarnings({ "unchecked" })
	public com.tedros.fxapi.control.TDetailField build(final Annotation annotation, final Property attrProperty) throws Exception {
		
		TDetailField tAnnotation = (TDetailField) annotation;
		
		com.tedros.fxapi.control.TDetailField control = 
				new com.tedros.fxapi.control.TDetailField(tAnnotation.modelViewClass(), tAnnotation.modelClass(), attrProperty, tAnnotation.showButtons());
		callParser(tAnnotation, control);
		return control;
	}

	
}
