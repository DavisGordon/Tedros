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

import org.tedros.fx.annotation.control.TListViewField;
import org.tedros.fx.annotation.control.TOptionsList;
import org.tedros.fx.control.TListView;
import org.tedros.fx.domain.TOptionProcessType;
import org.tedros.fx.presenter.model.TModelView;
import org.tedros.fx.process.TOptionsProcess;
import org.tedros.server.entity.ITEntity;
import org.tedros.server.result.TResult;

import javafx.beans.Observable;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
									control.getItems().addAll(list);
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
				prop.removeListener((ListChangeListener)super.getComponentDescriptor().getForm().gettObjectRepository()
						.get(fn+"_tlistview_prop"));
				prop.clear();
				prop.addAll(c.getList());
				prop.addListener((ListChangeListener)super.getComponentDescriptor().getForm().gettObjectRepository()
						.get(fn+"_tlistview_prop"));
			};
			super.getComponentDescriptor().getForm().gettObjectRepository()
			.add(fn+"_tlistview_sel", sellchll);
			sel.addListener(sellchll);
			
			ListChangeListener proplchll = c -> {
				sel.removeListener((ListChangeListener)super.getComponentDescriptor().getForm().gettObjectRepository()
						.get(fn+"_tlistview_sel"));
				control.getSelectionModel().clearSelection();
				for(Object o : c.getList())
					control.getSelectionModel().select(o);
				sel.addListener((ListChangeListener)super.getComponentDescriptor().getForm().gettObjectRepository()
						.get(fn+"_tlistview_sel"));
				
			};
			super.getComponentDescriptor().getForm().gettObjectRepository()
			.add(fn+"_tlistview_prop", proplchll);
			prop.addListener(proplchll);
			
			ListChangeListener itemslchll = c -> {
				sel.removeListener((ListChangeListener)super.getComponentDescriptor().getForm().gettObjectRepository()
						.get(fn+"_tlistview_sel"));
				control.getSelectionModel().clearSelection();
				for(Object o : prop)
					control.getSelectionModel().select(o);
				sel.addListener((ListChangeListener)super.getComponentDescriptor().getForm().gettObjectRepository()
						.get(fn+"_tlistview_sel"));
				
			};
			super.getComponentDescriptor().getForm().gettObjectRepository()
			.add(fn+"_tlistview2", itemslchll);
			control.getItems().addListener(itemslchll);
			
		}else{
			control.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
			ObservableList sel = (ObservableList) control.tValueProperty();
			Property prop = (Property) property;
			String fn = super.getComponentDescriptor().getFieldDescriptor().getFieldName();
			
			ListChangeListener sellchll = c -> {
				prop.removeListener((ChangeListener)super.getComponentDescriptor().getForm().gettObjectRepository()
						.get(fn+"_tlistview_prop"));
				prop.setValue(c.getList().isEmpty()?null:c.getList().get(0));
				prop.addListener((ChangeListener)super.getComponentDescriptor().getForm().gettObjectRepository()
						.get(fn+"_tlistview_prop"));
			};
			super.getComponentDescriptor().getForm().gettObjectRepository()
			.add(fn+"_tlistview_sel", sellchll);
			sel.addListener(sellchll);
			
			ChangeListener proplchll = (a,b,n) -> {
				sel.removeListener((ListChangeListener)super.getComponentDescriptor().getForm().gettObjectRepository()
						.get(fn+"_tlistview_sel"));
				control.getSelectionModel().clearSelection();
				control.getSelectionModel().select(n);
				sel.addListener((ListChangeListener)super.getComponentDescriptor().getForm().gettObjectRepository()
						.get(fn+"_tlistview_sel"));
				
			};
			super.getComponentDescriptor().getForm().gettObjectRepository()
			.add(fn+"_tlistview_prop", proplchll);
			prop.addListener(proplchll);
			
			ListChangeListener itemslchll = c -> {
				sel.removeListener((ListChangeListener)super.getComponentDescriptor().getForm().gettObjectRepository()
						.get(fn+"_tlistview_sel"));
				control.getSelectionModel().clearSelection();
				if(prop.getValue()!=null)
					control.getSelectionModel().select(prop.getValue());
				sel.addListener((ListChangeListener)super.getComponentDescriptor().getForm().gettObjectRepository()
						.get(fn+"_tlistview_sel"));
				
			};
			super.getComponentDescriptor().getForm().gettObjectRepository()
			.add(fn+"_tlistview2", itemslchll);
			control.getItems().addListener(itemslchll);
			
		}
	}
	
}
