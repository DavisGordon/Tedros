/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package org.tedros.fx.builder;

import java.lang.annotation.Annotation;

import org.tedros.fx.annotation.control.TNumberSpinnerField;

import javafx.beans.property.Property;


/**
 * A builder to {@link org.tedros.fx.control.TNumberSpinnerField}
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public final class TNumberSpinnerFieldBuilder
extends TBuilder
implements ITControlBuilder<org.tedros.fx.control.TNumberSpinnerField, Property> {


	@SuppressWarnings({"unchecked"})
	public org.tedros.fx.control.TNumberSpinnerField build(final Annotation annotation, final Property attrProperty) throws Exception {
		final TNumberSpinnerField tAnnotation = (TNumberSpinnerField) annotation;
		final org.tedros.fx.control.TNumberSpinnerField control = new org.tedros.fx.control.TNumberSpinnerField();
		callParser(tAnnotation, control);
		control.valueProperty().bindBidirectional(attrProperty);
		return control;
	}
	
	
}
