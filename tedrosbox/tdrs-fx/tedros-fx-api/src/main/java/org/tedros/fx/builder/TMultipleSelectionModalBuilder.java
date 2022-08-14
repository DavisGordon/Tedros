/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package org.tedros.fx.builder;

import java.lang.annotation.Annotation;

import org.tedros.fx.annotation.control.TMultipleSelectionModal;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.control.TSelectionModal;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public class TMultipleSelectionModalBuilder 
extends TBuilder
implements ITControlBuilder<org.tedros.fx.control.TSelectionModal, ITObservableList> {

	
	public TSelectionModal build(final Annotation annotation, final ITObservableList attrProperty) throws Exception {
		TMultipleSelectionModal tAnnotation = (TMultipleSelectionModal) annotation;
		TSelectionModal control = new TSelectionModal( attrProperty, true, tAnnotation.width(), tAnnotation.height(), tAnnotation.modalWidth(), tAnnotation.modalHeight());
		control.settModelViewClass(tAnnotation.modelViewClass());
		callParser(tAnnotation, control);
		return control;
	}
}
