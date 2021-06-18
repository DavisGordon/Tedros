/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package com.tedros.fxapi.builder;

import java.lang.annotation.Annotation;

import javafx.beans.property.Property;
import javafx.scene.control.Hyperlink;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class THyperlinkFieldBuilder 
extends TBuilder 
implements ITControlBuilder<Hyperlink, Property<String>> {
	
	public Hyperlink build(final Annotation annotation, final Property<String> attrProperty) throws Exception {
		final Hyperlink control = new Hyperlink();
		control.setId("t-label");
		control.textProperty().bindBidirectional(attrProperty);
		callParser(annotation, control);
		return control;
	}
	
}
