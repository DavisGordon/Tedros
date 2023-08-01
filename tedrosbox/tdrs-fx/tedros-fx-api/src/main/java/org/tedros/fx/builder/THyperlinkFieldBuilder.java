/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package org.tedros.fx.builder;

import java.lang.annotation.Annotation;

import org.tedros.fx.control.THyperlink;

import javafx.beans.property.Property;


/**
 * Hyperlink builder
 *
 * @author Davis Gordon
 *
 */
public class THyperlinkFieldBuilder 
extends TBuilder 
implements ITControlBuilder<THyperlink, Property<String>> {
	
	public THyperlink build(final Annotation annotation, final Property<String> attrProperty) throws Exception {
		final THyperlink control = new THyperlink();
		control.textProperty().bindBidirectional(attrProperty);
		callParser(annotation, control);
		return control;
	}
	
}
