/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package org.tedros.fx.builder;

import java.lang.annotation.Annotation;

import org.tedros.fx.annotation.control.TColorPickerField;

import javafx.beans.property.Property;
import javafx.scene.paint.Color;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public final class TColorPickerFieldBuilder 
extends TBuilder
implements ITControlBuilder<org.tedros.fx.control.TColorPickerField, Property<Color>> {
	
	
	public org.tedros.fx.control.TColorPickerField build(final Annotation annotation, final Property<Color> attrProperty) throws Exception {
		final TColorPickerField tAnnotation = (TColorPickerField) annotation;
		final org.tedros.fx.control.TColorPickerField control = new org.tedros.fx.control.TColorPickerField();
		callParser(tAnnotation, control);
		control.valueProperty().bindBidirectional(attrProperty);return control;
	}
	
	
	
	
}
