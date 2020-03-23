package com.tedros.fxapi.presenter.filter.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.tedros.core.model.ITModelView;
import com.tedros.fxapi.annotation.control.TBigDecimalField;
import com.tedros.fxapi.annotation.control.TBigIntegerField;
import com.tedros.fxapi.annotation.control.TCheckBoxField;
import com.tedros.fxapi.annotation.control.TComboBoxField;
import com.tedros.fxapi.annotation.control.TDatePickerField;
import com.tedros.fxapi.annotation.control.TDoubleField;
import com.tedros.fxapi.annotation.control.THorizontalRadioGroup;
import com.tedros.fxapi.annotation.control.TIntegerField;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TLongField;
import com.tedros.fxapi.annotation.control.TMaskField;
import com.tedros.fxapi.annotation.control.TNumberSpinnerField;
import com.tedros.fxapi.annotation.control.TPasswordField;
import com.tedros.fxapi.annotation.control.TPickListField;
import com.tedros.fxapi.annotation.control.TSliderField;
import com.tedros.fxapi.annotation.control.TTextAreaField;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.control.TValidator;
import com.tedros.fxapi.annotation.control.TVerticalRadioGroup;
import com.tedros.fxapi.annotation.form.TDetailView;
import com.tedros.fxapi.control.validator.TControlValidator;
import com.tedros.fxapi.control.validator.TFieldResult;

import com.tedros.fxapi.descriptor.TFieldDescriptor;
import com.tedros.fxapi.domain.TZeroValidation;
import com.tedros.fxapi.presenter.filter.TFilterModelView;
import com.tedros.fxapi.util.TReflectionUtil;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;

@SuppressWarnings("rawtypes")
public final class TFilterValidator<F extends TFilterModelView> {

	private List<TValidatorResult<F>> list;
	
	public TFilterValidator() {
	
	}
	
	public TFilterValidator(List<F> filterView) throws Exception {
		validateFilters(filterView);
	}
	
	public List<TValidatorResult<F>> getResult() throws Exception{
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<TValidatorResult<F>> validate(final F... filterView) throws Exception{
		validateFilters(Arrays.asList(filterView));
		return list;
	}
	
	public List<TValidatorResult<F>> validate(final List<F> filterView) throws Exception{
		validateFilters(filterView);
		return list;
	}

	@SuppressWarnings("unchecked")
	private void validateFilters(final List<F> filterView) throws Exception {
		if(list==null);
			list = new ArrayList<>(0);
		for (F tFilterView : filterView) {
			List<TFieldDescriptor> fiels = TReflectionUtil.getFieldDescriptorList(tFilterView);
			TValidatorResult<F> result = new TValidatorResult(tFilterView);
			for (TFieldDescriptor tFieldDescriptor : fiels)
				validateField(result, tFieldDescriptor, tFilterView);
			if(!result.isRequirementAccomplished())
				list.add(result);	
		}
	}
	
	@SuppressWarnings({"unchecked"})
	private void validateField(final TValidatorResult<F> result, final TFieldDescriptor tFieldDescriptor, final F filterView) throws Exception {
	
		final String fieldName = tFieldDescriptor.getFieldName();
		final Method propertyGetMethod = filterView.getClass().getMethod("get"+StringUtils.capitalize(fieldName));
		
		String label = "";
		
		for (final Annotation annotation : (List<Annotation>) tFieldDescriptor.getAnnotations()) {
			if (annotation instanceof TLabel){
				TLabel tAnnotation = (TLabel) annotation;
				label = tAnnotation.text();
				continue;
			}
			
			Object value = propertyGetMethod.invoke(filterView);
			
			validateRequiredField(label, value, result, annotation);
			
			if(annotation instanceof TValidator){
				TValidator tAnnotation = (TValidator) annotation;
				com.tedros.fxapi.control.validator.TValidator validator = tAnnotation.validatorClass().getConstructor(String.class, Object.class).newInstance(label, value);
				if(tAnnotation.associatedFieldsName().length>0){
					for(String fName : tAnnotation.associatedFieldsName()){
						final Method associatedFieldGetMethod = filterView.getClass().getMethod("get"+StringUtils.capitalize(fName));
						Object associatedValue = associatedFieldGetMethod.invoke(filterView);
						validator.addAssociatedFieldValue(fName, associatedValue);
					}
				}
				final TFieldResult TFieldResult = validator.validate();
				if(TFieldResult!=null)
					result.addResult(TFieldResult);
				continue;
			}
			
			if (annotation instanceof TDetailView){
				//final TDetailView tAnnotation = (TDetailView) annotation;
				final TControlValidator validator = new TControlValidator();
				List<ITModelView> lst = null;
				List<TValidatorResult> results = null;
				
				Object object = propertyGetMethod.invoke(filterView);
				if(object instanceof ObservableList)
					lst = ((ObservableList) propertyGetMethod.invoke(filterView));
				else if (object instanceof ObjectProperty) {
					ITModelView detailModelView = (ITModelView) ((ObjectProperty)object).getValue();  
					lst = Arrays.asList(detailModelView);
				}
				
				results = (List<TValidatorResult>) validator.validate(lst);
				if(results.size()>0)
					for (TValidatorResult tValidatorResult : results) 
						list.add(tValidatorResult);
				
				continue;
				
				/*TODO: TESTAR E ARRANCAR
				if(tAnnotation.propertyType() == ObservableList.class){
					lst = ((ObservableList) propertyGetMethod.invoke(filterView));
					results = (List<TValidatorResult>) validator.validate(lst);
					if(results.size()>0)
						for (TValidatorResult tValidatorResult : results) 
							list.add(tValidatorResult);
				}
				continue;
				*/
			}
		}
		
	}

	@SuppressWarnings("unchecked")
	private void validateRequiredField(final String fieldLabel, final Object valueObject, final TValidatorResult<F> result, final Annotation annotation) throws IllegalAccessException,
			InvocationTargetException {
		
		// *** TVerticalRadioGroup ***
		
		if (annotation instanceof TVerticalRadioGroup) {
			TVerticalRadioGroup tAnnotation = (TVerticalRadioGroup) annotation;
			final Property attrProperty = (Property) valueObject;
			if(tAnnotation.required() && (attrProperty==null || attrProperty.getValue()==null))
				notSelectedMessage(fieldLabel, result);
			
		}
		
		// *** THorizontalRadioGroup ***
		
		if (annotation instanceof THorizontalRadioGroup) {
			THorizontalRadioGroup tAnnotation = (THorizontalRadioGroup) annotation;
			final Property attrProperty = (Property) valueObject;
			if(tAnnotation.required() && (attrProperty==null || attrProperty.getValue()==null))
				notSelectedMessage(fieldLabel, result);
			
		}
		
		// *** TPickList ***
		
		if (annotation instanceof TPickListField) {
			TPickListField tAnnotation = (TPickListField) annotation;
			final ObservableList attrProperty = (ObservableList) valueObject;
			if(tAnnotation.required() && (attrProperty==null || attrProperty.size()==0))
				notSelectedMessage(fieldLabel, result);
			
		}
		
		// *** TTextField ***
		
		if (annotation instanceof TTextField) {
			TTextField tAnnotation = (TTextField) annotation;
			final Property<String> attrProperty = (Property) valueObject;
			if(tAnnotation.required() && (attrProperty==null || StringUtils.isBlank(attrProperty.getValue())))
				notFilledMessage(fieldLabel, result);
			
		}
		
		// *** TMaskField ***
		
		if (annotation instanceof TMaskField) {
			TMaskField tAnnotation = (TMaskField) annotation;
			final Property<String> attrProperty = (Property) valueObject;
			if(tAnnotation.required() && (attrProperty==null || StringUtils.isBlank(attrProperty.getValue())))
				notFilledMessage(fieldLabel, result);
			
		}
		
		// *** TBigIntegerField ***
		
		if (annotation instanceof TBigIntegerField) {
			final TBigIntegerField tAnnotation = (TBigIntegerField) annotation;
			final Property<BigInteger> attrProperty = (Property) valueObject;
			if(tAnnotation.zeroValidation().equals(TZeroValidation.MINOR_THAN_ZERO))
				if(attrProperty!=null && (attrProperty.getValue()==null || attrProperty.getValue().doubleValue()>=0.0))
					lessThanZeroMessage(fieldLabel, result);
			
			if(tAnnotation.zeroValidation().equals(TZeroValidation.GREATHER_THAN_ZERO))
				if(attrProperty!=null && (attrProperty.getValue()==null || attrProperty.getValue().doubleValue()<=0.0))
					moreThanZeroMessage(fieldLabel, result);
			
		}
		
		// *** TBigDecimalField ***
		
		if (annotation instanceof TBigDecimalField) {
			final TBigDecimalField tAnnotation = (TBigDecimalField) annotation;
			final Property<BigDecimal> attrProperty = (Property) valueObject;
			if(tAnnotation.zeroValidation().equals(TZeroValidation.MINOR_THAN_ZERO))
				if(attrProperty!=null && (attrProperty.getValue()==null ||  attrProperty.getValue().doubleValue() >= 0.0))
					lessThanZeroMessage(fieldLabel, result);
			
			if(tAnnotation.zeroValidation().equals(TZeroValidation.GREATHER_THAN_ZERO))
				if(attrProperty!=null && (attrProperty.getValue()==null || attrProperty.getValue().doubleValue() <= 0.0))
					moreThanZeroMessage(fieldLabel, result);
			
		}
		
		// *** TDoubleField ***
		
		if (annotation instanceof TDoubleField) {
			final TDoubleField tAnnotation = (TDoubleField) annotation;
			final Property<Double> attrProperty = (Property) valueObject;
			if(tAnnotation.zeroValidation().equals(TZeroValidation.MINOR_THAN_ZERO))
				if(attrProperty!=null && attrProperty.getValue()>=0.0)
					lessThanZeroMessage(fieldLabel, result);
			
			if(tAnnotation.zeroValidation().equals(TZeroValidation.GREATHER_THAN_ZERO))
				if(attrProperty!=null && attrProperty.getValue()<=0.0)
					moreThanZeroMessage(fieldLabel, result);
			
		}
		
		// *** TLongField ***
		
		if (annotation instanceof TLongField) {
			final TLongField tAnnotation = (TLongField) annotation;
			final Property<Long> attrProperty = (Property) valueObject;
			if(tAnnotation.zeroValidation().equals(TZeroValidation.MINOR_THAN_ZERO))
				if(attrProperty!=null && attrProperty.getValue()>=0)
					lessThanZeroMessage(fieldLabel, result);
			
			if(tAnnotation.zeroValidation().equals(TZeroValidation.GREATHER_THAN_ZERO))
				if(attrProperty!=null && attrProperty.getValue()<=0)
					moreThanZeroMessage(fieldLabel, result);
			
		}
		
		// *** TIntegerField ***
		
		if (annotation instanceof TIntegerField) {
			final TIntegerField tAnnotation = (TIntegerField) annotation;
			final Property<Integer> attrProperty = (Property) valueObject;
			if(tAnnotation.zeroValidation().equals(TZeroValidation.MINOR_THAN_ZERO))
				if(attrProperty!=null && attrProperty.getValue()>=0)
					lessThanZeroMessage(fieldLabel, result);
			
			if(tAnnotation.zeroValidation().equals(TZeroValidation.GREATHER_THAN_ZERO))
				if(attrProperty!=null && attrProperty.getValue()<=0)
					moreThanZeroMessage(fieldLabel, result);
			
		}
		
		// *** TTextArea ***
		
		if (annotation instanceof TTextAreaField) {
			final TTextAreaField tAnnotation = (TTextAreaField) annotation;
			final Property<String> attrProperty = (Property) valueObject;
			if(tAnnotation.required() && (attrProperty==null || StringUtils.isBlank(attrProperty.getValue())))
				notFilledMessage(fieldLabel, result);
			
		}
		
		// *** TPasswordField ***
		
		if (annotation instanceof TPasswordField) {
			final TPasswordField tAnnotation = (TPasswordField) annotation;
			final Property<String> attrProperty = (Property) valueObject;
			if(tAnnotation.required() && (attrProperty==null || StringUtils.isBlank(attrProperty.getValue())))
				notFilledMessage(fieldLabel, result);
			
		}
		
		// *** TNumberSpinner ***
		
		if (annotation instanceof TNumberSpinnerField) {
			final TNumberSpinnerField tAnnotation = (TNumberSpinnerField) annotation;
			final DoubleProperty attrProperty = (DoubleProperty) valueObject;
			if(tAnnotation.zeroValidation().equals(TZeroValidation.MINOR_THAN_ZERO))
				if(attrProperty!=null && attrProperty.getValue()>=0.0)
					lessThanZeroMessage(fieldLabel, result);
			
			if(tAnnotation.zeroValidation().equals(TZeroValidation.GREATHER_THAN_ZERO))
				if(attrProperty!=null && attrProperty.getValue()<=0.0)
					moreThanZeroMessage(fieldLabel, result);
			
		}
		
		// *** TCheckBox ***
		
		if (annotation instanceof TCheckBoxField) {
			TCheckBoxField tAnnotation = (TCheckBoxField) annotation;
			final Property<Boolean> attrProperty = (Property) valueObject;
			if(tAnnotation.required() && (attrProperty==null || !attrProperty.getValue()))
				needCheckMessage(fieldLabel, result);
			
		}
		
		// *** TComboBoxField ***
		
		if (annotation instanceof TComboBoxField) {
			final TComboBoxField tAnnotation = (TComboBoxField) annotation;
			final Property attrProperty = (Property) valueObject;
			if(tAnnotation.required() && (attrProperty==null || attrProperty.getValue()==null))
				notSelectedMessage(fieldLabel, result);
			
		}
		
		// *** TSlider ***
		
		if (annotation instanceof TSliderField) {
			final TSliderField tAnnotation = (TSliderField) annotation;				
			final DoubleProperty attrProperty = (DoubleProperty) valueObject;
			
			if(tAnnotation.zeroValidation().equals(TZeroValidation.MINOR_THAN_ZERO))
				if(attrProperty!=null && attrProperty.getValue()>=0.0)
					lessThanZeroMessage(fieldLabel, result);
			
			if(tAnnotation.zeroValidation().equals(TZeroValidation.GREATHER_THAN_ZERO))
				if(attrProperty!=null && attrProperty.getValue()<=0.0)
					moreThanZeroMessage(fieldLabel, result);
			
			
		}
		
		// *** TDatePicker ***
		
		if (annotation instanceof TDatePickerField) {
			final TDatePickerField tAnnotation = (TDatePickerField) annotation;
			final SimpleObjectProperty<Date> attrProperty = (SimpleObjectProperty<Date>) valueObject;
			if(tAnnotation.required() && (attrProperty==null || attrProperty.getValue()==null))
				notFilledMessage(fieldLabel, result);
		}
	}

	private void notFilledMessage(final String fieldName, final TValidatorResult<F> result) {
		result.addResult(fieldName, "Precisa ser preenchido", true);
	}
	
	private void lessThanZeroMessage(final String fieldName, final TValidatorResult<F> result) {
		result.addResult(fieldName, "Precisa ser menor que zero", true);
	}
	
	private void moreThanZeroMessage(final String fieldName, final TValidatorResult<F> result) {
		result.addResult(fieldName, "Precisa ser maior que zero", true);
	}
	
	private void notSelectedMessage(final String fieldName, final TValidatorResult<F> result) {
		result.addResult(fieldName, "Selecione uma op��o", true);
	}
	
	private void needCheckMessage(final String fieldName, final TValidatorResult<F> result) {
		result.addResult(fieldName, "Precisa ser marcado", true);
	}
	
	
	
	
}
