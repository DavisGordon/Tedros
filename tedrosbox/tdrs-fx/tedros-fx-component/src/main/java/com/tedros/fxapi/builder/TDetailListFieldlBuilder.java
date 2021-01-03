/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package com.tedros.fxapi.builder;

import java.lang.annotation.Annotation;

import com.tedros.core.ITModule;
import com.tedros.fxapi.annotation.control.TDetailListField;
import com.tedros.fxapi.collections.ITObservableList;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public class TDetailListFieldlBuilder 
extends TBuilder
implements ITControlBuilder<com.tedros.fxapi.control.TDetailListField, ITObservableList> {

	
	@SuppressWarnings({ "unchecked" })
	public com.tedros.fxapi.control. TDetailListField build(final Annotation annotation, final ITObservableList attrProperty) throws Exception {
		
		TDetailListField tAnnotation = (TDetailListField) annotation;
		
		ITModule module = getComponentDescriptor().getForm().gettPresenter().getModule();
		
		com.tedros.fxapi.control.TDetailListField control = 
				new com.tedros.fxapi.control.TDetailListField(module, tAnnotation.entityModelViewClass(), tAnnotation.entityClass(), attrProperty);
		callParser(tAnnotation, control);
		return control;
	}

	
}
