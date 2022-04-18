/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package com.tedros.fxapi.builder;

import java.lang.annotation.Annotation;

import com.tedros.fxapi.annotation.control.TEditEntityModal;
import com.tedros.fxapi.collections.ITObservableList;

import javafx.beans.Observable;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public class TEditEntityModalBuilder 
extends TBuilder
implements ITControlBuilder<com.tedros.fxapi.control.TEditEntityModal, Observable> {

	
	public com.tedros.fxapi.control.TEditEntityModal build(final Annotation annotation, final Observable attrProperty) throws Exception {
		TEditEntityModal tAnnotation = (TEditEntityModal) annotation;
		com.tedros.fxapi.control.TEditEntityModal control =
				new com.tedros.fxapi.control.TEditEntityModal( (ITObservableList) attrProperty, tAnnotation.width(), tAnnotation.height(), tAnnotation.modalWidth(), tAnnotation.modalHeight());
		control.settModelViewClass(tAnnotation.modelViewClass());
		control.settModelClass(tAnnotation.modelClass());
		callParser(tAnnotation, control);
		return control;
	}
}
