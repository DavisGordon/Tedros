/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 11/11/2013
 */
package com.tedros.fxapi.form;

import java.lang.annotation.Annotation;
import java.util.List;

import com.tedros.core.model.ITFilterModelView;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TTrigger;
import com.tedros.fxapi.annotation.parser.TAnnotationParser;
import com.tedros.fxapi.descriptor.TComponentDescriptor;
import com.tedros.fxapi.descriptor.TFieldDescriptor;
import com.tedros.fxapi.domain.TViewMode;
import com.tedros.fxapi.util.TReflectionUtil;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Toggle;
import javafx.scene.effect.Effect;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
class TFilterFormBuilder<F extends ITFilterModelView> {
	
	private final TComponentDescriptor descriptor;
	
	public TFilterFormBuilder(ITFilterForm<F> form, F filterView) {
		form.setId("t-form");
		descriptor = new TComponentDescriptor(form, filterView, TViewMode.EDIT);
		try{
			buildForm();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
			
	private void buildForm() throws Exception{
		//fieldsList = TReflectionUtil.getTedrosAnnotatedFields(this.filterView);
		for(final TFieldDescriptor f : descriptor.getFieldDescriptorList())
			buildControls(f);
		for(final TFieldDescriptor f : descriptor.getFieldDescriptorList())
			buildTriggers(f);
	}
	
	@SuppressWarnings({"unchecked"})
	public void buildTriggers(final TFieldDescriptor tFieldDescriptor) throws Exception {
	
		final String fieldName = tFieldDescriptor.getFieldName();
		
		for (final Annotation annotation : (List<Annotation>) tFieldDescriptor.getAnnotations()) {
			if (!(annotation instanceof TTrigger))
				continue;
			
			TTrigger tAnnotation = (TTrigger) annotation;
			TFieldBox source = descriptor.getForm().gettFieldBox(fieldName);
			TFieldBox target = descriptor.getForm().gettFieldBox(tAnnotation.targetFieldName());
			
			final com.tedros.fxapi.control.trigger.TTrigger trigger = tAnnotation.triggerClass()
					.getConstructor(TFieldBox.class, TFieldBox.class).newInstance(source, target);
			
			for(String associatedFieldName : tAnnotation.associatedFieldBox())
				if(!associatedFieldName.trim().equals(""))
					trigger.addAssociatedField(descriptor.getForm().gettFieldBox(associatedFieldName));
			
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
				com.tedros.fxapi.control.THorizontalRadioGroup control = (com.tedros.fxapi.control.THorizontalRadioGroup) sourceControl;
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
			
			if(tAnnotation.runAfterFormBuild())
				trigger.run();
			
		}
	}
	
	public void buildControls(final TFieldDescriptor tFieldDescriptor) throws Exception {
		
		descriptor.setMode(TViewMode.EDIT);
		descriptor.setFieldDescriptor(tFieldDescriptor);
		final List<Annotation> fieldEffects = TReflectionUtil.getEffectAnnotations(descriptor.getFieldAnnotationList());
		
		Node control = TComponentBuilder.getField(descriptor);
		
		if(control==null){
			System.err.println("WARNING: Control null to "+tFieldDescriptor.getFieldName());
			return;
		}
		
		if(control!=null && !fieldEffects.isEmpty()){
			for (final Annotation effect : fieldEffects) {
				Effect e = TComponentBuilder.getEffect(effect);
				if(e!=null)
					control.setEffect(e);
			}
		}

		for (Annotation annotation : descriptor.getFieldAnnotationList()) {
			if(annotation instanceof TLabel)
				continue;
			boolean builderMethod;
			boolean parserMethod;
			try{
				builderMethod = (annotation.getClass().getMethod("builder")!=null);
			}catch(NoSuchMethodException e){ builderMethod = false;}
			try{
				parserMethod = (annotation.getClass().getMethod("parser")!=null);
			}catch(NoSuchMethodException e){ parserMethod = false;}
			
			if(parserMethod && !builderMethod){
				TAnnotationParser.callParser(annotation, control, descriptor);
			}
		}
		
		((ITFilterForm)descriptor.getForm()).addFieldBox((TFieldBox) control);
	}
	
	protected TLabel getTLabel(final TFieldDescriptor tFieldDescriptor) {
		for(Annotation annotation : tFieldDescriptor.getAnnotations())
			if(annotation instanceof TLabel)
				return (TLabel) annotation;
		return null;
	}
		
	
	
	
	
}
