/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 */
package org.tedros.tools.control.builder;

import java.lang.annotation.Annotation;

import org.tedros.fx.builder.ITControlBuilder;
import org.tedros.fx.builder.TBuilder;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.tools.annotation.TNotifyModal;
import org.tedros.tools.module.notify.model.TNotifyMV;

import javafx.collections.ObservableList;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@Deprecated
@SuppressWarnings("rawtypes")
public class TNotifyModalBuilder 
extends TBuilder
implements ITControlBuilder<org.tedros.tools.control.TNotifyModal, ObservableList<TNotifyMV>> {

	public org.tedros.tools.control.TNotifyModal build(final Annotation annotation, final ObservableList<TNotifyMV> attrProperty) throws Exception {
		TNotifyModal tAnnotation = (TNotifyModal) annotation;
		org.tedros.tools.control.TNotifyModal control = 
				new org.tedros.tools.control.TNotifyModal( (ITObservableList) attrProperty, tAnnotation.width(), tAnnotation.height(), 
						tAnnotation.modalWidth(), tAnnotation.modalHeight());
		callParser(tAnnotation, control);
		return control;
	}
}
