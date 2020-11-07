/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package com.tedros.fxapi.builder;

import java.lang.annotation.Annotation;

import org.apache.commons.lang3.ArrayUtils;

import com.tedros.fxapi.annotation.control.TShowField.TField;
import com.tedros.fxapi.control.TShowField;
import com.tedros.fxapi.control.TShowFieldValue;
import com.tedros.fxapi.domain.TLayoutType;

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

	private static TShowFieldBuilder instance;
	
	private TShowFieldBuilder(){
		
	}
	
	public static TShowFieldBuilder getInstance(){
		if(instance==null)
			instance = new TShowFieldBuilder();
		return instance;
	}
	
	public TShowField build(final Annotation annotation, final Property attrProperty) throws Exception {
		com.tedros.fxapi.annotation.control.TShowField tAnnotation = 
				(com.tedros.fxapi.annotation.control.TShowField) annotation;
		
		TLayoutType layout = tAnnotation.layout();
		TField[] tfs = tAnnotation.fields();
		TShowFieldValue[] fields = tfs.length>0 
				? new TShowFieldValue[0] 
						: null;
		for(TField f : tfs) {
			TShowFieldValue v = new TShowFieldValue(f.name(), f.pattern(), f.label(), f.labelPosition());
			fields = ArrayUtils.add(fields, v);
		}
		
		TShowField control = new TShowField(layout, attrProperty, fields);
		
		callParser(tAnnotation, control);
		return control;
	}
}
