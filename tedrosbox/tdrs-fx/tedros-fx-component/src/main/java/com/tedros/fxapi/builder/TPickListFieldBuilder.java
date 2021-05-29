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
import com.tedros.fxapi.annotation.control.TOptionsList;
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
		
		if(tAnnotation.optionsList()!=null) {
			TOptionsList optAnn = tAnnotation.optionsList();
			final Class<? extends TOptionsProcess> pClass = optAnn.optionsProcessClass();
			final Class<? extends TModelView> mClass = optAnn.optionModelViewClass();
			final Class<? extends ITEntity> eClass = optAnn.entityClass();
			final String serviceName = optAnn.serviceName();
			
			final TOptionsProcess process = pClass!=TOptionsProcess.class 
					? pClass.newInstance()
					: new TOptionsProcess(eClass, serviceName) {
						
					};
			process.stateProperty().addListener(new ChangeListener<State>() {
				@Override
				public void changed(ObservableValue<? extends State> arg0,
						State arg1, State arg2) {
					
						if(arg2.equals(State.SUCCEEDED)){
							List<TResult<Object>> resultados = (List<TResult<Object>>) process.getValue();
							
							for(TResult result : resultados) {
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
				}
			});
			
			if(optAnn.optionProcessType() == TOptionProcessType.LIST_ALL){
				process.list();
			}else {
				Class<? extends ITGenericBuilder> bClass = optAnn.exampleEntityBuilder();
				if(bClass == NullEntityBuilder.class)
					throw new RuntimeException("The property exampleEntityBuilder is required for @TOptionsList"
							+ " when the property optionProcessType = TOptionProcessType.SEARCH. See field "
							+ super.getComponentDescriptor().getFieldDescriptor().getFieldName()
							+ " in "+super.getComponentDescriptor().getModelView().getClass().getSimpleName());
				
				ITGenericBuilder builder = bClass.newInstance();
				ITEntity example = (ITEntity) builder.build();
				process.search(example);
			}
			
			process.startProcess();
		}
		
		callParser(tAnnotation, control);
				
		return control;
	}
	
}
