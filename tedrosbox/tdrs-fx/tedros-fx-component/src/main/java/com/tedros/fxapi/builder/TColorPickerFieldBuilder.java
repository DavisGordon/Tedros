/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package com.tedros.fxapi.builder;

import java.lang.annotation.Annotation;

import javafx.beans.property.Property;
import javafx.scene.paint.Color;

import com.tedros.fxapi.annotation.control.TColorPickerField;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public final class TColorPickerFieldBuilder 
extends TBuilder
implements ITControlBuilder<com.tedros.fxapi.control.TColorPickerField, Property<Color>> {
	
	
	public com.tedros.fxapi.control.TColorPickerField build(final Annotation annotation, final Property<Color> attrProperty) throws Exception {
		final TColorPickerField tAnnotation = (TColorPickerField) annotation;
		final com.tedros.fxapi.control.TColorPickerField control = new com.tedros.fxapi.control.TColorPickerField();
		callParser(tAnnotation, control);
		control.valueProperty().bindBidirectional(attrProperty);return control;
	}
	
	
	
	
}
