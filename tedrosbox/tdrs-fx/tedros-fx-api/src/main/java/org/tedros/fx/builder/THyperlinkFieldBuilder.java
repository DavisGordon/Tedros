/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package org.tedros.fx.builder;

import java.lang.annotation.Annotation;

import org.tedros.fx.control.TText.TTextStyle;

import javafx.beans.property.Property;
import javafx.scene.control.Hyperlink;


/**
 * Hyperlink builder
 *
 * @author Davis Gordon
 *
 */
public class THyperlinkFieldBuilder 
extends TBuilder 
implements ITControlBuilder<Hyperlink, Property<String>> {
	
	public Hyperlink build(final Annotation annotation, final Property<String> attrProperty) throws Exception {
		final Hyperlink control = new Hyperlink();
		control.getStyleClass().add(TTextStyle.CUSTOM.getValue());
		control.textProperty().bindBidirectional(attrProperty);
		callParser(annotation, control);
		return control;
	}
	
}
