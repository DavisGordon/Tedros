/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package org.tedros.fx.builder;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import org.tedros.core.TLanguage;
import org.tedros.core.model.TModelViewUtil;
import org.tedros.fx.TFxKey;
import org.tedros.fx.annotation.control.TOptionsList;
import org.tedros.fx.annotation.control.TPickListField;
import org.tedros.fx.domain.TOptionProcessType;
import org.tedros.fx.presenter.model.TModelView;
import org.tedros.fx.process.TOptionsProcess;
import org.tedros.server.entity.ITEntity;
import org.tedros.server.result.TResult;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.WeakEventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;


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
			loadControl(tAnnotation, control);
			
			ContextMenu ctx = new ContextMenu();
			MenuItem reload = new MenuItem(TLanguage.getInstance().getString(TFxKey.BUTTON_RELOAD));
			EventHandler<ActionEvent> reloadEvent = ev->{
				try {
					loadControl(tAnnotation, control);
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
			String k = super.getComponentDescriptor().getFieldDescriptor()
					.getFieldName()+"_picklist_reload_event";
			
			super.getComponentDescriptor().getForm()
			.gettObjectRepository()
			.add(k, reloadEvent);
			
			reload.setOnAction(new WeakEventHandler<>(reloadEvent));
			ctx.getItems().add(reload);
			
			control.gettSourceListView().setContextMenu(ctx);
			
		}
		
		callParser(tAnnotation, control);
				
		return control;
	}

	/**
	 * @param tAnnotation
	 * @param control
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	private void loadControl(TPickListField tAnnotation, final org.tedros.fx.control.TPickListField control)
			throws InstantiationException, IllegalAccessException {
		TOptionsList optAnn = tAnnotation.optionsList();
		final Class<? extends TOptionsProcess> pClass = optAnn.optionsProcessClass();
		final Class<? extends TModelView> mClass = optAnn.optionModelViewClass();
		final Class<? extends ITEntity> eClass = optAnn.entityClass();
		final String serviceName = optAnn.serviceName();
		
		final TOptionsProcess process = pClass!=TOptionsProcess.class 
			? pClass.newInstance()
				: new TOptionsProcess(eClass, serviceName) {};
				
		process.stateProperty().addListener((a,o,n)-> {
			if(n.equals(State.SUCCEEDED)){
				List<TResult<Object>> resultados = (List<TResult<Object>>) process.getValue();
				for(TResult result : resultados) {
					if(result.getValue()!=null && result.getValue() instanceof List<?>){
						List list = new ArrayList<>(0);
						for(Object e : (List<Object>) result.getValue()){
							if(e instanceof ITEntity){
								if(mClass!=TModelView.class) {
									TModelView<?> model =(TModelView<?>) 
											new TModelViewUtil(mClass, eClass, (ITEntity) e)
											.convertToModelView();
									list.add(model);
								}else 
									list.add(e);
							}
						}
						control.settSourceList(FXCollections.observableArrayList(list));
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
	
}
