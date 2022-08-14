/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package org.tedros.fx.builder;

import java.lang.annotation.Annotation;

import org.tedros.core.ITModule;
import org.tedros.fx.annotation.control.TDetailListField;
import org.tedros.fx.collections.ITObservableList;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public class TDetailListFieldlBuilder 
extends TBuilder
implements ITControlBuilder<org.tedros.fx.control.TDetailListField, ITObservableList> {

	
	@SuppressWarnings({ "unchecked" })
	public org.tedros.fx.control. TDetailListField build(final Annotation annotation, final ITObservableList attrProperty) throws Exception {
		
		TDetailListField tAnnotation = (TDetailListField) annotation;
		
		ITModule module = getComponentDescriptor().getForm().gettPresenter().getModule();
		
		org.tedros.fx.control.TDetailListField control = 
				new org.tedros.fx.control.TDetailListField(module, tAnnotation.entityModelViewClass(), tAnnotation.entityClass(), attrProperty);
		callParser(tAnnotation, control);
		return control;
	}

	
}
