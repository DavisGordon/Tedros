/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package org.tedros.fx.builder;

import java.lang.annotation.Annotation;

import org.tedros.fx.annotation.control.TTextAreaField;

import javafx.beans.property.Property;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public final class TTextAreaFieldBuilder 
extends TBuilder
implements ITControlBuilder<org.tedros.fx.control.TTextAreaField, Property<String>> {

	public org.tedros.fx.control.TTextAreaField build(final Annotation annotation, final Property<String> attrProperty) throws Exception {
		TTextAreaField tAnnotation = (TTextAreaField) annotation;
		final org.tedros.fx.control.TTextAreaField control = new org.tedros.fx.control.TTextAreaField();
		callParser(tAnnotation, control);
		control.textProperty().bindBidirectional(attrProperty);
		return control;
	}
}
