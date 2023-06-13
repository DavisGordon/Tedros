/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package org.tedros.fx.builder;

import java.lang.annotation.Annotation;

import org.tedros.fx.annotation.control.TEditEntityModal;
import org.tedros.fx.collections.ITObservableList;

import javafx.beans.Observable;
import javafx.beans.property.SimpleObjectProperty;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public class TEditEntityModalBuilder 
extends TBuilder
implements ITControlBuilder<org.tedros.fx.control.TEditEntityModal, Observable> {

	
	public org.tedros.fx.control.TEditEntityModal build(final Annotation annotation, final Observable attrProperty) throws Exception {
		TEditEntityModal tAnnotation = (TEditEntityModal) annotation;
		org.tedros.fx.control.TEditEntityModal control =
				(attrProperty instanceof ITObservableList) 
					? new org.tedros.fx.control.TEditEntityModal( (ITObservableList) attrProperty, tAnnotation.width(), tAnnotation.height(), tAnnotation.modalWidth(), tAnnotation.modalHeight())
		: new org.tedros.fx.control.TEditEntityModal( (SimpleObjectProperty) attrProperty, tAnnotation.width(), tAnnotation.height(), tAnnotation.modalWidth(), tAnnotation.modalHeight());
		
		control.settModelViewClass(tAnnotation.modelView());
		control.settModelClass(tAnnotation.model());
		callParser(tAnnotation, control);
		return control;
	}
}
