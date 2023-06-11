/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package org.tedros.fx.builder;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

import org.tedros.core.TLanguage;
import org.tedros.fx.annotation.control.TAutoCompleteTextField;
import org.tedros.fx.annotation.control.TAutoCompleteTextField.TEntry;

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
		
		TEntry eAnn = tAnn.entries();
		if(eAnn.values().length>0) {
			Arrays.asList(eAnn.values()).forEach(e->{
				control.getEntries().add(TLanguage.getInstance().getString(e));
			});
			
		}else if(eAnn.factory()!=NullStringListBuilder.class) {
			ITGenericBuilder<List<String>> builder = eAnn.factory().newInstance();
			builder.setComponentDescriptor(super.getComponentDescriptor());
			List<String> l = builder.build();
			control.getEntries().addAll(l);
		}
		
		callParser(tAnn, control);
		control.textProperty().bindBidirectional(attrProperty);
		return control;
	}
	
}
