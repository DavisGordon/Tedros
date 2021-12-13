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
import java.util.UUID;

import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.ejb.base.result.TResult;
import com.tedros.fxapi.annotation.control.TListViewField;
import com.tedros.fxapi.annotation.control.TOptionsList;
import com.tedros.fxapi.annotation.control.TPickListField;
import com.tedros.fxapi.control.TListView;
import com.tedros.fxapi.domain.TOptionProcessType;
import com.tedros.fxapi.presenter.model.TModelView;
import com.tedros.fxapi.process.TOptionsProcess;

import javafx.beans.Observable;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker.State;
import javafx.scene.control.SelectionMode;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public final class TListViewFieldBuilder 
extends TBuilder
implements ITControlBuilder<TListView, Observable> {

	@SuppressWarnings("unchecked")
	public TListView build(final Annotation annotation, final Observable property) throws Exception {
		TListViewField tAnnotation = (TListViewField) annotation;
		final TListView control = new TListView();
		
		buildListeners(property, control);
		
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
									control.getItems().add(FXCollections.observableArrayList(list));
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

	/**
	 * @param property
	 * @param control
	 */
	@SuppressWarnings("unchecked")
	public void buildListeners(final Observable property, final TListView control) {
		if(property instanceof ObservableList) {
			control.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			ObservableList sel = (ObservableList) control.tValueProperty();
			ObservableList prop = (ObservableList) property;
			String fn = super.getComponentDescriptor().getFieldDescriptor().getFieldName();
			
			ListChangeListener sellchll = c -> {
				prop.removeListener((ListChangeListener)super.getComponentDescriptor().getModelView().getListenerRepository()
						.get(fn+"_tlistview"));
				prop.clear();
				prop.addAll(c.getList());
				prop.addListener((ListChangeListener)super.getComponentDescriptor().getModelView().getListenerRepository()
						.get(fn+"_tlistview"));
			};
			super.getComponentDescriptor().getForm().gettObjectRepository()
			.add(fn+"_tlistview", sellchll);
			sel.addListener(sellchll);
			
			ListChangeListener proplchll = c -> {
				sel.removeListener((ListChangeListener)super.getComponentDescriptor().getForm().gettObjectRepository()
						.get(fn+"_tlistview"));
				control.getSelectionModel().clearSelection();
				for(Object o : c.getList())
					control.getSelectionModel().select(o);
				sel.addListener((ListChangeListener)super.getComponentDescriptor().getForm().gettObjectRepository()
						.get(fn+"_tlistview"));
				
			};
			super.getComponentDescriptor().getModelView().getListenerRepository()
			.add(fn+"_tlistview", proplchll);
			prop.addListener(proplchll);
			
			ListChangeListener itemslchll = c -> {
				sel.removeListener((ListChangeListener)super.getComponentDescriptor().getForm().gettObjectRepository()
						.get(fn+"_tlistview"));
				control.getSelectionModel().clearSelection();
				for(Object o : prop)
					control.getSelectionModel().select(o);
				sel.addListener((ListChangeListener)super.getComponentDescriptor().getForm().gettObjectRepository()
						.get(fn+"_tlistview"));
				
			};
			super.getComponentDescriptor().getForm().gettObjectRepository()
			.add(fn+"_tlistview2", itemslchll);
			control.getItems().addListener(itemslchll);
			
		}else{
			control.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		}
	}
	
}
