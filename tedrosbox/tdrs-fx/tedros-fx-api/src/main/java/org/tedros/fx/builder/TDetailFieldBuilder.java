/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package org.tedros.fx.builder;

import java.lang.annotation.Annotation;

import org.tedros.fx.annotation.control.TDetailField;

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
implements ITControlBuilder<org.tedros.fx.control.TDetailField, Property> {

	
	@SuppressWarnings({ "unchecked" })
	public org.tedros.fx.control.TDetailField build(final Annotation annotation, final Property attrProperty) throws Exception {
		
		TDetailField tAnnotation = (TDetailField) annotation;
		
		org.tedros.fx.control.TDetailField control = 
				new org.tedros.fx.control.TDetailField(tAnnotation.modelView(), tAnnotation.model(), 
						attrProperty, tAnnotation.showButtons());
		callParser(tAnnotation, control);
		return control;
	}

	
}
