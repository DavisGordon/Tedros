/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package org.tedros.fx.builder;

import java.lang.annotation.Annotation;

import org.apache.commons.lang3.ArrayUtils;
import org.tedros.fx.annotation.control.TShowField.TField;
import org.tedros.fx.control.TShowField;
import org.tedros.fx.control.TShowFieldValue;
import org.tedros.fx.domain.TLayoutType;

import javafx.beans.property.Property;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public class TShowFieldBuilder 
extends TBuilder
implements ITControlBuilder<TShowField, Property> {

	
	public TShowField build(final Annotation annotation, final Property attrProperty) throws Exception {
		org.tedros.fx.annotation.control.TShowField tAnnotation = 
				(org.tedros.fx.annotation.control.TShowField) annotation;
		
		TLayoutType layout = tAnnotation.layout();
		TField[] tfs = tAnnotation.fields();
		TShowFieldValue[] fields = tfs.length>0 
				? new TShowFieldValue[0] 
						: null;
		for(TField f : tfs) {
			TShowFieldValue v = new TShowFieldValue(f.name(), f.pattern(), f.label(), f.labelPosition());
			fields = ArrayUtils.add(fields, v);
		}
		
		TShowField control = new TShowField(layout, attrProperty, super.getComponentDescriptor(), fields);
		
		callParser(tAnnotation, control);
		return control;
	}
}
