/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package com.tedros.fxapi.builder;

import java.lang.annotation.Annotation;

import javafx.beans.property.Property;

import com.tedros.fxapi.annotation.control.TNumberSpinnerField;


/**
 * A builder to {@link com.tedros.fxapi.control.TNumberSpinnerField}
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public final class TNumberSpinnerFieldBuilder
extends TBuilder
implements ITControlBuilder<com.tedros.fxapi.control.TNumberSpinnerField, Property> {

	private static TNumberSpinnerFieldBuilder instance;
	
	private TNumberSpinnerFieldBuilder(){
		
	}
	
	public static  TNumberSpinnerFieldBuilder getInstance(){
		if(instance==null)
			instance = new TNumberSpinnerFieldBuilder();
		return instance;	
	}

	@SuppressWarnings({"unchecked"})
	public com.tedros.fxapi.control.TNumberSpinnerField build(final Annotation annotation, final Property attrProperty) throws Exception {
		final TNumberSpinnerField tAnnotation = (TNumberSpinnerField) annotation;
		final com.tedros.fxapi.control.TNumberSpinnerField control = new com.tedros.fxapi.control.TNumberSpinnerField();
		callParser(tAnnotation, control);
		control.valueProperty().bindBidirectional(attrProperty);
		return control;
	}
	
	
}
