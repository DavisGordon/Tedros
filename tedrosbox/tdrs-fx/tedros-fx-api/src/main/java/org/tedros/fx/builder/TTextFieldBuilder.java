/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package org.tedros.fx.builder;

import java.lang.annotation.Annotation;

import org.tedros.fx.annotation.control.TTextField;

import javafx.beans.property.Property;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class TTextFieldBuilder 
extends TBuilder
implements ITControlBuilder<org.tedros.fx.control.TTextField, Property<String>> {

	public org.tedros.fx.control.TTextField build(final Annotation annotation, final Property<String> attrProperty) throws Exception {
		TTextField tAnnotation = (TTextField) annotation;
		final org.tedros.fx.control.TTextField control = new org.tedros.fx.control.TTextField();
		callParser(tAnnotation, control);
		control.textProperty().bindBidirectional(attrProperty);
		return control;
	}
	
}
