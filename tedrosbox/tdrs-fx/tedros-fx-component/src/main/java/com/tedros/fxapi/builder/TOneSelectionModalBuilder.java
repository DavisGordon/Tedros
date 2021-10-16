/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package com.tedros.fxapi.builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;

import com.tedros.fxapi.annotation.control.TOneSelectionModal;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.collections.TFXCollections;
import com.tedros.fxapi.control.TSelectionModal;
import com.tedros.fxapi.presenter.model.TModelView;

import javafx.beans.property.Property;
import javafx.collections.ListChangeListener;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public class TOneSelectionModalBuilder 
extends TBuilder
implements ITControlBuilder<com.tedros.fxapi.control.TSelectionModal, Property> {

	
	@SuppressWarnings({ "unchecked" })
	public TSelectionModal build(final Annotation annotation, final Property attrProperty) throws Exception {
		
		TOneSelectionModal tAnnotation = (TOneSelectionModal) annotation;
		TSelectionModal control = null;
			
		Object value = attrProperty.getValue();
		
		TModelView model = value == null 
				? null 
						: value instanceof TModelView 
						? (TModelView) value 
								: buildModel(tAnnotation, value);
						
		ITObservableList list = TFXCollections.iTObservableList();
		if(model!=null)
			list.add(model);

		ListChangeListener lcl = o -> {
			TModelView m = null;
			o.next();
			if(o.wasAdded()) {
				m = (TModelView) o.getAddedSubList().get(0);
				attrProperty.setValue(m.getModel());
			}
			if(o.wasRemoved()) {
				attrProperty.setValue(null);
			}
		};
		list.addListener(lcl);
		
		control = new TSelectionModal(list, false, tAnnotation.width(), tAnnotation.height(), tAnnotation.modalWidth(), tAnnotation.modalHeight());
		control.settModelViewClass(tAnnotation.modelViewClass());
		
		callParser(tAnnotation, control);
		return control;
	}

	private TModelView buildModel(TOneSelectionModal tAnnotation, Object value) {
		try {
			return tAnnotation.modelViewClass().getConstructor(value.getClass()).newInstance(value);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
