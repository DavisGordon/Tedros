/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package com.tedros.fxapi.builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;

import com.tedros.fxapi.annotation.control.TFindItemField;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.collections.TFXCollections;
import com.tedros.fxapi.control.TFindItem;
import com.tedros.fxapi.descriptor.TComponentDescriptor;
import com.tedros.fxapi.presenter.model.TModelView;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public class TFindFieldBuilder 
extends TBuilder
implements ITControlBuilder<com.tedros.fxapi.control.TFindItem, Property> {

	private static TFindFieldBuilder instance;
	
	private TFindFieldBuilder(){
		
	}
	
	public static TFindFieldBuilder getInstance(){
		if(instance==null)
			instance = new TFindFieldBuilder();
		return instance;
	}
	
	@SuppressWarnings({ "unchecked" })
	public com.tedros.fxapi.control.TFindItem build(final Annotation annotation, final Property attrProperty) throws Exception {
		TFindItemField tAnnotation = (TFindItemField) annotation;
		TFindItem control = null;
		TComponentDescriptor desc = super.getComponentDescriptor();
		
		if(attrProperty instanceof SimpleObjectProperty) {
			
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
			/*String uuid = UUID.randomUUID().toString();
			ITModelView main = desc.getModelView();
			main.addListener(uuid, lcl);*/
			list.addListener(lcl);
			
			/*ChangeListener chl = (o, old, n) -> {
				list.removeListener(lcl);
				if(n == null)
					list.clear();
				else {
					list.clear();
					list.add(buildModel(tAnnotation, n));
				}
				list.addListener(lcl);
			};
			((SimpleObjectProperty) attrProperty).addListener(chl);*/
			
			control = new TFindItem(list, false, tAnnotation.width(), tAnnotation.height());
			control.settModelViewClass(tAnnotation.modelViewClass());
		}
		
		
		//callParser(tAnnotation, control.getItemField());
		//control.textProperty().bindBidirectional(attrProperty);
		return control;
	}

	private TModelView buildModel(TFindItemField tAnnotation, Object value) {
		try {
			return tAnnotation.modelViewClass().getConstructor(value.getClass()).newInstance(value);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
