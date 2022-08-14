/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package org.tedros.fx.builder;

import java.lang.annotation.Annotation;

import org.tedros.fx.annotation.control.TSliderField;
import org.tedros.fx.control.TSlider;

import javafx.beans.property.Property;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public final class TSliderFieldBuilder 
extends TBuilder
implements ITControlBuilder<TSlider, Property> {

	@SuppressWarnings({"unchecked"})
	public org.tedros.fx.control.TSlider build(final Annotation annotation, final Property attrProperty) throws Exception {
		final TSliderField tAnnotation = (TSliderField) annotation;				
		final TSlider control = new TSlider();
		callParser(tAnnotation, control);
		control.valueProperty().bindBidirectional(attrProperty);
		return control;
	}
	
	
}
