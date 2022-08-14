/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package org.tedros.fx.builder;

import java.lang.annotation.Annotation;

import org.tedros.fx.annotation.control.TMaskField;

import javafx.beans.property.Property;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public final class TMaskFieldBuilder
extends TBuilder
implements ITControlBuilder<org.tedros.fx.control.TMaskField, Property<String>> {
	
	public org.tedros.fx.control.TMaskField build(final Annotation annotation, final Property<String> attrProperty) throws Exception {
		TMaskField tAnnotation = (TMaskField) annotation;
		final org.tedros.fx.control.TMaskField control = new org.tedros.fx.control.TMaskField(tAnnotation.mask());
		callParser(tAnnotation, control);
		control.textProperty().bindBidirectional(attrProperty);
		return control;
	}
	
}
