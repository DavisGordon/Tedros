/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package org.tedros.fx.builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.tedros.fx.annotation.control.TOptionsList;
import org.tedros.fx.annotation.control.TPickListField;
import org.tedros.fx.domain.TOptionProcessType;
import org.tedros.fx.presenter.model.TModelView;
import org.tedros.fx.process.TOptionsProcess;
import org.tedros.server.entity.ITEntity;
import org.tedros.server.result.TResult;

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
implements ITControlBuilder<org.tedros.fx.control.TPickListField, ObservableList> {

	@SuppressWarnings("unchecked")
	public org.tedros.fx.control.TPickListField build(final Annotation annotation, final ObservableList attrProperty) throws Exception {
		TPickListField tAnnotation = (TPickListField) annotation;
		final org.tedros.fx.control.TPickListField control = 
				new org.tedros.fx.control.TPickListField<>(	tAnnotation.sourceLabel(), 
															tAnnotation.selectedLabel(), 
															null, 
															attrProperty, 
															tAnnotation.width(),
															tAnnotation.height(),
															tAnnotation.required(),
															tAnnotation.selectionMode());
		
		if(tAnnotation.optionsList().entityClass()!=ITEntity.class) {
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
												if(mClass!=TModelView.class) {
													TModelView<?> model = mClass.getConstructor(eClass).newInstance(e);
													list.add(model);
												}else 
													list.add(e);
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
									control.settSourceList(FXCollections.observableArrayList(list));
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
