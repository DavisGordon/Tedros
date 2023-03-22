/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package org.tedros.fx.builder;

import java.lang.annotation.Annotation;
import java.util.Arrays;

import org.tedros.fx.annotation.control.TAutoCompleteTextField;

import javafx.beans.property.Property;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class TAutoCompleteTextFieldBuilder 
extends TBuilder
implements ITControlBuilder<org.tedros.fx.control.TAutoCompleteTextField, Property<String>> {

	public org.tedros.fx.control.TAutoCompleteTextField build(final Annotation annotation, final Property<String> attrProperty) throws Exception {
		TAutoCompleteTextField tAnn = (TAutoCompleteTextField) annotation;
		final org.tedros.fx.control.TAutoCompleteTextField control 
		= new org.tedros.fx.control.TAutoCompleteTextField();
		control.getEntries().addAll(Arrays.asList(tAnn.entries()));
		callParser(tAnn, control);
		control.textProperty().bindBidirectional(attrProperty);
		return control;
	}
	
}
