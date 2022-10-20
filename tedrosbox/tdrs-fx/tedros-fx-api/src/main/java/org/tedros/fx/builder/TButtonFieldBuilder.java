/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package org.tedros.fx.builder;

import java.lang.annotation.Annotation;

import org.tedros.fx.control.TButton;

import javafx.beans.property.Property;


/**
 * TButton builder
 *
 * @author Davis Gordon
 *
 */
public class TButtonFieldBuilder 
extends TBuilder 
implements ITControlBuilder<TButton, Property<String>> {
	
	public TButton build(final Annotation annotation, final Property<String> attrProperty) throws Exception {
		final TButton control = new TButton();
		//control.textProperty().bindBidirectional(attrProperty);
		callParser(annotation, control);
		return control;
	}
	
}
