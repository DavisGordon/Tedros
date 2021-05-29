/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package com.tedros.fxapi.builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.ejb.base.result.TResult;
import com.tedros.fxapi.annotation.control.TComboBoxField;
import com.tedros.fxapi.annotation.control.TOptionsList;
import com.tedros.fxapi.control.TItem;
import com.tedros.fxapi.domain.TOptionProcessType;
import com.tedros.fxapi.exception.TException;
import com.tedros.fxapi.presenter.model.TModelView;
import com.tedros.fxapi.process.TOptionsProcess;

import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
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
implements ITControlBuilder<com.tedros.fxapi.control.TComboBoxField, Property<Object>> {
	
	private static final String T_ITENTITY_WARNING = "\n\nError: To TComboBoxField field for ITEntity values is necessary set the optionsList propertie.\nEx: @TComboBoxField(optionsList=@TOptionsList(optionsProcessClass=GrupoOptionProcess.class, optionModelViewClass=GrupoModelView.class, entityClass=Grupo.class)\n\n";
	
	@SuppressWarnings({"unchecked"})
	public com.tedros.fxapi.control.TComboBoxField build(final Annotation annotation, final Property<Object> attrProperty) throws Exception {
	
		final TComboBoxField tAnnotation = (TComboBoxField) annotation;
		final com.tedros.fxapi.control.TComboBoxField control = new com.tedros.fxapi.control.TComboBoxField();
		control.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<? extends Object> arg0,
					Object arg1, Object new_value) {
				
				if(new_value instanceof TItem)
					attrProperty.setValue(((TItem)new_value).getValue());
				else{
					if(new_value instanceof TModelView)
						attrProperty.setValue(((TModelView)new_value).getModel());
					else
						attrProperty.setValue(new_value);
				}
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
        						setText(((TModelView)obj).getDisplayProperty().getValue());
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
							if(resultados!=null && resultados.size()>0){
								TResult result = resultados.get(0);
								if(result.getValue()!=null && result.getValue() instanceof List<?>){
									for(Object e : (List<Object>) result.getValue()){
										if(e instanceof ITEntity){
											if(mClass != TModelView.class){
												try {
													TModelView<?> model = mClass.getConstructor(eClass).newInstance(e);
													control.getItems().add(model);
												} catch (InstantiationException
														| IllegalAccessException
														| IllegalArgumentException
														| InvocationTargetException
														| NoSuchMethodException
														| SecurityException e1) 
												{
													e1.printStackTrace();
												}
											}else
												System.out.println(T_ITENTITY_WARNING);
										}
									}
									Object value = attrProperty.getValue();
									if(value instanceof ITEntity){
										if(mClass != TModelView.class){
											try {
												TModelView<?> model = mClass.getConstructor(eClass).newInstance(value);
												control.setValue(model);
											} catch (Exception e1){
												e1.printStackTrace();
											}
												
										}else
											System.out.println(T_ITENTITY_WARNING);
									}else
										control.setValue(attrProperty.getValue());
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
			
			if(attrProperty!=null)
				control.setValue(attrProperty.getValue());
		}else{
			if(attrProperty!=null)
				control.setValue(attrProperty.getValue());
		}
		
		return control;
	}
}
