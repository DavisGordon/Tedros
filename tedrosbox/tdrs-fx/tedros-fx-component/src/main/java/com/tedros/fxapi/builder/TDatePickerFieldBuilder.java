/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package com.tedros.fxapi.builder;

import java.lang.annotation.Annotation;
import java.util.Date;

import javafx.beans.property.Property;

import com.tedros.fxapi.annotation.control.TDatePickerField;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class TDatePickerFieldBuilder 
extends TBuilder
implements ITControlBuilder<com.tedros.fxapi.control.TDatePickerField, Property<Date>> {
	
	private static TDatePickerFieldBuilder instance;	
	
	private TDatePickerFieldBuilder(){
		
	}
	/**
	 * <pre>
	 * Return a {@link TDatePickerFieldBuilder} instance.
	 * </pre>
	 * */
	public static TDatePickerFieldBuilder getInstance(){
		if(instance==null)
			instance = new TDatePickerFieldBuilder();
		return instance;
	}
	
	public com.tedros.fxapi.control.TDatePickerField build(final Annotation annotation, final Property<Date> attrProperty) throws Exception {
		final TDatePickerField tAnnotation = (TDatePickerField) annotation;
		final com.tedros.fxapi.control.TDatePickerField control = new com.tedros.fxapi.control.TDatePickerField();
		callParser(tAnnotation, control);
		control.valueProperty().bindBidirectional(attrProperty);
		return control;
	}
}
