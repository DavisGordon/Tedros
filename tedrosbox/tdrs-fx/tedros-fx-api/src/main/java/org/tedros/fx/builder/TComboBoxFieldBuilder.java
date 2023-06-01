/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package org.tedros.fx.builder;

import java.lang.annotation.Annotation;
import java.util.List;

import org.tedros.core.TLanguage;
import org.tedros.core.model.TModelViewUtil;
import org.tedros.fx.TFxKey;
import org.tedros.fx.annotation.control.TComboBoxField;
import org.tedros.fx.annotation.control.TOptionsList;
import org.tedros.fx.annotation.parser.TComboBoxParser;
import org.tedros.fx.control.TItem;
import org.tedros.fx.domain.TOptionProcessType;
import org.tedros.fx.presenter.model.TModelView;
import org.tedros.fx.process.TOptionsProcess;
import org.tedros.server.entity.ITEntity;
import org.tedros.server.result.TResult;

import javafx.beans.property.Property;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.WeakEventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.util.Callback;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public final class TComboBoxFieldBuilder 
extends TBuilder
implements ITControlBuilder<org.tedros.fx.control.TComboBoxField, Property<Object>> {
	
	@SuppressWarnings({"unchecked"})
	public org.tedros.fx.control.TComboBoxField build(final Annotation annotation, final Property<Object> attrProperty) throws Exception {
	
		final TComboBoxField tAnnotation = (TComboBoxField) annotation;
		final org.tedros.fx.control.TComboBoxField control = new org.tedros.fx.control.TComboBoxField();
		
		control.getSelectionModel().selectedItemProperty().addListener((a,o,n)-> {
			if(n instanceof TItem)
				attrProperty.setValue(((TItem)n).getValue());
			else{
				if(n instanceof TModelView)
					attrProperty.setValue(((TModelView)n).getModel());
				else
					attrProperty.setValue(n);
			}
		});	
								
		control.setCellFactory(new Callback<ListView<Object>,ListCell<Object>>(){
            @Override
            public ListCell<Object> call(ListView<Object> p) {
                final ListCell<Object> cell = new ListCell<Object>(){
                    @Override
                    protected void updateItem(Object obj, boolean bln) {
                        super.updateItem(obj, bln);
                        if(obj != null){
        					if(obj instanceof TModelView)
        						setText(((TModelView)obj).toStringProperty().getValue());
        					else
        						setText(obj.toString());
        				}else{
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        });
		
		callParser(tAnnotation, (ComboBox) control);
		
		if(tAnnotation.optionsList().entityClass()!=ITEntity.class) {
			
			loadControl(attrProperty, tAnnotation, control);
			
			ContextMenu ctx = new ContextMenu();
			MenuItem reload = new MenuItem(TLanguage.getInstance().getString(TFxKey.BUTTON_RELOAD));
			EventHandler<ActionEvent> reloadEvent = ev->{
				try {
					control.getItems().clear();
					TComboBoxParser.parseFirstItemText(tAnnotation, control);
					loadControl(attrProperty, tAnnotation, control);
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
			String k = super.getComponentDescriptor().getFieldDescriptor()
					.getFieldName()+"_combo_reload_event";
			
			super.getComponentDescriptor().getForm()
			.gettObjectRepository()
			.add(k, reloadEvent);
			
			reload.setOnAction(new WeakEventHandler<>(reloadEvent));
			ctx.getItems().add(reload);
			
			control.setContextMenu(ctx);
			
			if(attrProperty!=null)
				control.setValue(attrProperty.getValue());
		}else{
			if(attrProperty!=null)
				control.setValue(attrProperty.getValue());
		}
		
		return control;
	}

	/**
	 * @param attrProperty
	 * @param tAnnotation
	 * @param control
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	private void loadControl(final Property<Object> attrProperty, final TComboBoxField tAnnotation,
			final org.tedros.fx.control.TComboBoxField control) throws InstantiationException, IllegalAccessException {
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
				if(resultados!=null && resultados.size()>0){
					TResult result = resultados.get(0);
					if(result.getValue()!=null && result.getValue() instanceof List<?>){
						for(Object e : (List<Object>) result.getValue()){
							if(e instanceof ITEntity){
								if(mClass != TModelView.class){
									TModelView<?> model = (TModelView<?>) 
											new TModelViewUtil(mClass, eClass, (ITEntity) e)
											.convertToModelView();
									control.getItems().add(model);
								}else
									control.getItems().add(e);
							}
						}
						Object value = attrProperty.getValue();
						if(value instanceof ITEntity){
							if(mClass != TModelView.class){
								TModelView<?> model = (TModelView<?>) 
										new TModelViewUtil(mClass, eClass, (ITEntity) value)
										.convertToModelView();
								control.setValue(model);
							}else
								control.setValue(value);
						}else
							control.setValue(value);
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
