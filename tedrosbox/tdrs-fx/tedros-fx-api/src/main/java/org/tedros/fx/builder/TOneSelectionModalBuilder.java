/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package org.tedros.fx.builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;

import org.tedros.fx.annotation.control.TOneSelectionModal;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.collections.TFXCollections;
import org.tedros.fx.control.TSelectionModal;
import org.tedros.fx.model.TModelView;

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
implements ITControlBuilder<org.tedros.fx.control.TSelectionModal, Property> {

	
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
