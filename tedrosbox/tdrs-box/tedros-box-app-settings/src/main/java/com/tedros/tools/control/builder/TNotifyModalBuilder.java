/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 */
package com.tedros.tools.control.builder;

import java.lang.annotation.Annotation;

import com.tedros.fxapi.builder.ITControlBuilder;
import com.tedros.fxapi.builder.TBuilder;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.tools.annotation.TNotifyModal;
import com.tedros.tools.module.notify.model.TNotifyMV;

import javafx.collections.ObservableList;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public class TNotifyModalBuilder 
extends TBuilder
implements ITControlBuilder<com.tedros.tools.control.TNotifyModal, ObservableList<TNotifyMV>> {

	public com.tedros.tools.control.TNotifyModal build(final Annotation annotation, final ObservableList<TNotifyMV> attrProperty) throws Exception {
		TNotifyModal tAnnotation = (TNotifyModal) annotation;
		com.tedros.tools.control.TNotifyModal control = 
				new com.tedros.tools.control.TNotifyModal( (ITObservableList) attrProperty, tAnnotation.width(), tAnnotation.height(), 
						tAnnotation.modalWidth(), tAnnotation.modalHeight());
		callParser(tAnnotation, control);
		return control;
	}
}
