/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package org.tedros.fx.builder;

import java.lang.annotation.Annotation;
import java.util.Date;

import org.tedros.fx.annotation.control.TDatePickerField;

import javafx.beans.property.Property;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class TDatePickerFieldBuilder 
extends TBuilder
implements ITControlBuilder<org.tedros.fx.control.TDatePickerField, Property<Date>> {
	
	
	public org.tedros.fx.control.TDatePickerField build(final Annotation annotation, final Property<Date> attrProperty) throws Exception {
		final TDatePickerField tAnnotation = (TDatePickerField) annotation;
		final org.tedros.fx.control.TDatePickerField control = new org.tedros.fx.control.TDatePickerField();
		callParser(tAnnotation, control);
		control.valueProperty().bindBidirectional(attrProperty);
		return control;
	}
}
