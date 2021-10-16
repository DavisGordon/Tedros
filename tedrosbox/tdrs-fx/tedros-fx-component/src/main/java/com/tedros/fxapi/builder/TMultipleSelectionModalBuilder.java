/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package com.tedros.fxapi.builder;

import java.lang.annotation.Annotation;

import com.tedros.fxapi.annotation.control.TMultipleSelectionModal;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.control.TSelectionModal;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public class TMultipleSelectionModalBuilder 
extends TBuilder
implements ITControlBuilder<com.tedros.fxapi.control.TSelectionModal, ITObservableList> {

	
	public TSelectionModal build(final Annotation annotation, final ITObservableList attrProperty) throws Exception {
		TMultipleSelectionModal tAnnotation = (TMultipleSelectionModal) annotation;
		TSelectionModal control = new TSelectionModal( attrProperty, true, tAnnotation.width(), tAnnotation.height(), tAnnotation.modalWidth(), tAnnotation.modalHeight());
		control.settModelViewClass(tAnnotation.modelViewClass());
		callParser(tAnnotation, control);
		return control;
	}
}
