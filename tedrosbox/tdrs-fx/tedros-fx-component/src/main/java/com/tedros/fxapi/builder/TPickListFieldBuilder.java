/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package com.tedros.fxapi.builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.ejb.base.result.TResult;
import com.tedros.fxapi.annotation.control.TPickListField;
import com.tedros.fxapi.domain.TOptionProcessType;
import com.tedros.fxapi.presenter.model.TModelView;
import com.tedros.fxapi.process.TOptionsProcess;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker.State;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public final class TPickListFieldBuilder 
extends TBuilder
implements ITControlBuilder<com.tedros.fxapi.control.TPickListField, ObservableList> {

	@SuppressWarnings("unchecked")
	public com.tedros.fxapi.control.TPickListField build(final Annotation annotation, final ObservableList attrProperty) throws Exception {
		TPickListField tAnnotation = (TPickListField) annotation;
		final com.tedros.fxapi.control.TPickListField control = 
				new com.tedros.fxapi.control.TPickListField<>(	tAnnotation.sourceLabel(), 
															tAnnotation.selectedLabel(), 
															null, 
															attrProperty, 
															tAnnotation.required());
		
		if(tAnnotation.optionsList()!=null && tAnnotation.optionsList().optionProcessType() == TOptionProcessType.LIST_ALL){
			final Class<? extends TModelView> mClass = tAnnotation.optionsList().optionModelViewClass();
			final Class<? extends ITEntity> eClass = tAnnotation.optionsList().entityClass();
			final TOptionsProcess process = tAnnotation.optionsList().optionsProcessClass().newInstance();
			process.listAll();
			process.stateProperty().addListener(new ChangeListener<State>() {
				@Override
				public void changed(ObservableValue<? extends State> arg0,
						State arg1, State arg2) {
					
						if(arg2.equals(State.SUCCEEDED)){
							List<TResult<Object>> resultados = process.getValue();
							TResult result = resultados.get(0);
							if(result.getValue()!=null && result.getValue() instanceof List<?>){
								List list = new ArrayList<>(0);
								for(Object e : (List<Object>) result.getValue()){
									if(e instanceof ITEntity){
										try {
											TModelView<?> model = mClass.getConstructor(eClass).newInstance(e);
											list.add(model);
										} catch (InstantiationException
												| IllegalAccessException
												| IllegalArgumentException
												| InvocationTargetException
												| NoSuchMethodException
												| SecurityException e1) 
										{
											e1.printStackTrace();
										}
										
									}
								}
								control.setSourceList(FXCollections.observableArrayList(list));
							}
						}	
				}
			});
			process.startProcess();
		}
		
		callParser(tAnnotation, control);
				
		return control;
	}
	
}
