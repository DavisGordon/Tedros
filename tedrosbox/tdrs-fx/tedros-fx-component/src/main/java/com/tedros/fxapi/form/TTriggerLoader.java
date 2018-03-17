package com.tedros.fxapi.form;

import java.lang.annotation.Annotation;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Toggle;

import com.tedros.core.model.ITModelView;
import com.tedros.fxapi.annotation.control.TTrigger;
import com.tedros.fxapi.descriptor.TFieldDescriptor;
import com.tedros.fxapi.domain.TViewMode;
import com.tedros.fxapi.exception.TErrorType;

public class TTriggerLoader<M extends ITModelView<?>, F extends ITModelForm<M>> {

	
	private final F form;
	
	public TTriggerLoader(F form) {
		this.form = form;
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	public void buildTriggers() throws Exception {
		
		for(final TFieldDescriptor tFieldDescriptor : this.form.gettFieldDescriptorList()){
		
			 
			final String fieldName = tFieldDescriptor.getFieldName();
			
			for (final Annotation annotation : (List<Annotation>) tFieldDescriptor.getAnnotations()) {
				if (!(annotation instanceof TTrigger))
					continue;
				
				TTrigger tAnnotation = (TTrigger) annotation;
				
				boolean buildTrigger = false;
				for(TViewMode mode : tAnnotation.mode()){
					if(this.form.gettMode().equals(mode))
						buildTrigger = true;
				}
				
				if(!buildTrigger)
					return;
				
				TFieldBox source = this.form.gettFieldBox(fieldName);
				TFieldBox target = (StringUtils.isNotBlank(tAnnotation.targetFieldName())) 
						? this.form.gettFieldBox(tAnnotation.targetFieldName()) 
								: null;
						
				if(source==null){
					throw new Exception("\n\nT_ERROR "
							+ "\nType: "+TErrorType.TRIGGER_BUILD
							+ "\nFIELD: "+this.form.gettModelView().getClass().getSimpleName() + "."+fieldName
							+ "\n\nThe source field not found, maybe the field are not visible for the selected mode\n\n");
				}
				
				final com.tedros.fxapi.control.trigger.TTrigger trigger = tAnnotation.triggerClass()
						.getConstructor(TFieldBox.class, TFieldBox.class).newInstance(source, target);
				
				trigger.setForm(this.form);
				
				for(String associatedFieldName : tAnnotation.associatedFieldBox())
					if(!associatedFieldName.trim().equals(""))
						trigger.addAssociatedField(this.form.gettFieldBox(associatedFieldName));
				
				final Node sourceControl = source.gettControl();
				
				if(sourceControl.getClass() == com.tedros.fxapi.control.TVerticalRadioGroup.class){
					com.tedros.fxapi.control.TVerticalRadioGroup control = (com.tedros.fxapi.control.TVerticalRadioGroup) sourceControl;
					control.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
						@Override
						public void changed(ObservableValue<? extends Toggle> arg0, Toggle arg1, Toggle new_toggle) {
							trigger.run();
						}
					});
				}
				
				if(sourceControl.getClass() == com.tedros.fxapi.control.THorizontalRadioGroup.class){
					final com.tedros.fxapi.control.THorizontalRadioGroup control = (com.tedros.fxapi.control.THorizontalRadioGroup) sourceControl;
					control.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
						@Override
						public void changed(ObservableValue<? extends Toggle> arg0, Toggle arg1, Toggle new_toggle) {
							trigger.run();
						}
					});
				}
				
				if(sourceControl.getClass() == com.tedros.fxapi.control.TPickListField.class){
					com.tedros.fxapi.control.TPickListField control = (com.tedros.fxapi.control.TPickListField) sourceControl;
					control.getSelectedList().addListener(new ListChangeListener() {
						@Override
						public void onChanged(Change arg0) {
							trigger.run();
						}
						
					});
				}
				
				if(sourceControl.getClass() == com.tedros.fxapi.control.TTextField.class){
					com.tedros.fxapi.control.TTextField control = (com.tedros.fxapi.control.TTextField) sourceControl;
					control.textProperty().addListener(new ChangeListener<String>() {
						@Override
						public void changed(ObservableValue<? extends String> arg0,
								String arg1, String arg2) {
							trigger.run();
						}
					});
				}
				
				if(sourceControl.getClass() == com.tedros.fxapi.control.TMaskField.class){
					com.tedros.fxapi.control.TMaskField control = (com.tedros.fxapi.control.TMaskField) sourceControl;
					control.textProperty().addListener(new ChangeListener<String>() {
						@Override
						public void changed(ObservableValue<? extends String> arg0,
								String arg1, String arg2) {
							trigger.run();
						}
					});
				}
				
				if(sourceControl.getClass() == com.tedros.fxapi.control.TBigIntegerField.class){
					com.tedros.fxapi.control.TBigIntegerField control = (com.tedros.fxapi.control.TBigIntegerField) sourceControl;
					control.textProperty().addListener(new ChangeListener<String>() {
						@Override
						public void changed(ObservableValue<? extends String> arg0,
								String arg1, String arg2) {
							trigger.run();
						}
					});
				}
				
				if(sourceControl.getClass() == com.tedros.fxapi.control.TBigDecimalField.class){
					com.tedros.fxapi.control.TBigDecimalField control = (com.tedros.fxapi.control.TBigDecimalField) sourceControl;
					control.textProperty().addListener(new ChangeListener<String>() {
						@Override
						public void changed(ObservableValue<? extends String> arg0,
								String arg1, String arg2) {
							trigger.run();
						}
					});
				}
				
				if(sourceControl.getClass() == com.tedros.fxapi.control.TDoubleField.class){
					com.tedros.fxapi.control.TDoubleField control = (com.tedros.fxapi.control.TDoubleField) sourceControl;
					control.textProperty().addListener(new ChangeListener<String>() {
						@Override
						public void changed(ObservableValue<? extends String> arg0,
								String arg1, String arg2) {
							trigger.run();
						}
					});
				}
				
				if(sourceControl.getClass() == com.tedros.fxapi.control.TLongField.class){
					com.tedros.fxapi.control.TLongField control = (com.tedros.fxapi.control.TLongField) sourceControl;
					control.textProperty().addListener(new ChangeListener<String>() {
						@Override
						public void changed(ObservableValue<? extends String> arg0,
								String arg1, String arg2) {
							trigger.run();
						}
					});
				}
				
				if(sourceControl.getClass() == com.tedros.fxapi.control.TIntegerField.class){
					com.tedros.fxapi.control.TIntegerField control = (com.tedros.fxapi.control.TIntegerField) sourceControl;
					control.textProperty().addListener(new ChangeListener<String>() {
						@Override
						public void changed(ObservableValue<? extends String> arg0,
								String arg1, String arg2) {
							trigger.run();
						}
					});
				}
				
				if(sourceControl.getClass() == com.tedros.fxapi.control.TTextAreaField.class){
					com.tedros.fxapi.control.TTextAreaField control = (com.tedros.fxapi.control.TTextAreaField) sourceControl;
					control.textProperty().addListener(new ChangeListener<String>() {
						@Override
						public void changed(ObservableValue<? extends String> arg0,
								String arg1, String arg2) {
							trigger.run();
						}
					});
				}
				
				if(sourceControl.getClass() == com.tedros.fxapi.control.TPasswordField.class){
					com.tedros.fxapi.control.TPasswordField control = (com.tedros.fxapi.control.TPasswordField) sourceControl;
					control.textProperty().addListener(new ChangeListener<String>() {
						@Override
						public void changed(ObservableValue<? extends String> arg0,
								String arg1, String arg2) {
							trigger.run();
						}
					});
				}
				
				
				if(sourceControl.getClass() == com.tedros.fxapi.control.TNumberSpinnerField.class){
					com.tedros.fxapi.control.TNumberSpinnerField control = (com.tedros.fxapi.control.TNumberSpinnerField) sourceControl;
					control.textProperty().addListener(new ChangeListener<String>() {
						@Override
						public void changed(ObservableValue<? extends String> arg0,
								String arg1, String arg2) {
							trigger.run();
						}
					});
				}
				
				if(sourceControl.getClass() == com.tedros.fxapi.control.TCheckBoxField.class){
					com.tedros.fxapi.control.TCheckBoxField control = (com.tedros.fxapi.control.TCheckBoxField) sourceControl;
					control.selectedProperty().addListener(new ChangeListener<Boolean>() {
						@Override
						public void changed(ObservableValue<? extends Boolean> arg0,
								Boolean arg1, Boolean arg2) {
							trigger.run();
						}
					});
				}
				
				if(sourceControl.getClass() == com.tedros.fxapi.control.TComboBoxField.class){
					com.tedros.fxapi.control.TComboBoxField control = (com.tedros.fxapi.control.TComboBoxField) sourceControl;
					control.valueProperty().addListener(new ChangeListener<Object>() {
						@Override
						public void changed(ObservableValue<? extends Object> arg0,
								Object arg1, Object arg2) {
							trigger.run();
						}
					});
				}
				
				if(sourceControl.getClass() == com.tedros.fxapi.control.TSlider.class){
					com.tedros.fxapi.control.TSlider control = (com.tedros.fxapi.control.TSlider) sourceControl;
					control.valueProperty().addListener(new ChangeListener<Object>() {
						@Override
						public void changed(ObservableValue<? extends Object> arg0,
								Object arg1, Object arg2) {
							trigger.run();
						}
					});
				}
				
				if(sourceControl.getClass() == com.tedros.fxapi.control.TDatePickerField.class){
					com.tedros.fxapi.control.TDatePickerField control = (com.tedros.fxapi.control.TDatePickerField) sourceControl;
					control.valueProperty().addListener(new ChangeListener<Object>() {
						@Override
						public void changed(ObservableValue<? extends Object> arg0,
								Object arg1, Object arg2) {
							trigger.run();
						}
					});
				}
				
				if(sourceControl.getClass() == com.tedros.fxapi.control.TColorPickerField.class){
					com.tedros.fxapi.control.TColorPickerField control = (com.tedros.fxapi.control.TColorPickerField) sourceControl;
					control.valueProperty().addListener(new ChangeListener<Object>() {
						@Override
						public void changed(ObservableValue<? extends Object> arg0,
								Object arg1, Object arg2) {
							trigger.run();
						}
					});
				}
				
				if(tAnnotation.runAfterFormBuild())
					trigger.run();
				
			}
		}
	}
	
}
