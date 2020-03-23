/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package com.tedros.fxapi.builder;

import java.lang.annotation.Annotation;

import javafx.beans.property.Property;

import com.tedros.fxapi.annotation.control.TSliderField;
import com.tedros.fxapi.control.TSlider;


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
	
	private static TSliderFieldBuilder instance;
	
	private TSliderFieldBuilder(){
		
	}
	
	public static TSliderFieldBuilder getInstance(){
		if(instance==null)
			instance = new TSliderFieldBuilder();
		return instance;
	}
	
	@SuppressWarnings({"unchecked"})
	public com.tedros.fxapi.control.TSlider build(final Annotation annotation, final Property attrProperty) throws Exception {
		final TSliderField tAnnotation = (TSliderField) annotation;				
		final TSlider control = new TSlider();
		callParser(tAnnotation, control);
		control.valueProperty().bindBidirectional(attrProperty);
		return control;
	}
	
	
}
