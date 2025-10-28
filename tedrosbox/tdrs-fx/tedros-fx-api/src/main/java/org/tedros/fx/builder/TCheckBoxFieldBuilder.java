/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package org.tedros.fx.builder;

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
implements ITControlBuilder<org.tedros.fx.control.TCheckBoxField, Property<Boolean>> {
	
	public org.tedros.fx.control.TCheckBoxField build(final Annotation annotation, final Property<Boolean> attrProperty) throws Exception {
		final org.tedros.fx.control.TCheckBoxField control = new org.tedros.fx.control.TCheckBoxField();
		callParser(annotation, control);
		//TRequiredCheckBoxParser.getInstance().parse(annotation, null, control);
		control.selectedProperty().bindBidirectional(attrProperty);
		return control;
	}
	
}
