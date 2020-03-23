package com.tedros.fxapi.control.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.tedros.core.TInternationalizationEngine;
import com.tedros.core.model.ITModelView;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TValidator;
import com.tedros.fxapi.annotation.form.TDetailView;
import com.tedros.fxapi.descriptor.TFieldDescriptor;
import com.tedros.fxapi.domain.TZeroValidation;
import com.tedros.fxapi.util.TReflectionUtil;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;

@SuppressWarnings("rawtypes")
public final class TControlValidator<E extends ITModelView> {

	private static final String GET = "get";
	private static final String REQUIRED = "required";
	private static final String ZEROVALIDATION = "zeroValidation";
	
	private List<TValidatorResult<E>> list;
	private TInternationalizationEngine iEngine;
	
	public TControlValidator() {
		iEngine = TInternationalizationEngine.getInstance(null);
	}
	
	public TControlValidator(List<E> modelsView) throws Exception {
		iEngine = TInternationalizationEngine.getInstance(null);
		validateModels(modelsView);
	}
	
	public List<TValidatorResult<E>> getResult() throws Exception{
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<TValidatorResult<E>> validate(final E... modelsView) throws Exception{
		validateModels(Arrays.asList(modelsView));
		return list;
	}
	
	public List<TValidatorResult<E>> validate(final List<E> modelsView) throws Exception{
		validateModels(modelsView);
		return list;
	}

	@SuppressWarnings("unchecked")
	private void validateModels(final List<E> modelsView) throws Exception {
		if(list==null);
			list = new ArrayList<>(0);
		for (E tModelView : modelsView) {
			List<TFieldDescriptor> fiels = TReflectionUtil.getFieldDescriptorList(tModelView);
			TValidatorResult<E> result = new TValidatorResult(tModelView);
			for (TFieldDescriptor tFieldDescriptor : fiels){
				if(TReflectionUtil.isIgnoreField(tFieldDescriptor))
					continue;
				validateField(result, tFieldDescriptor, tModelView);
			}
			if(!result.isRequirementAccomplished())
				list.add(result);	
		}
	}
	
	@SuppressWarnings({"unchecked"})
	private void validateField(final TValidatorResult<E> result, final TFieldDescriptor tFieldDescriptor, final E modelView) throws Exception {
	
		final String fieldName = tFieldDescriptor.getFieldName();
		final Method propertyGetMethod = modelView.getClass().getMethod(GET+StringUtils.capitalize(fieldName));
		
		String label = "";
		
		for (final Annotation annotation : (List<Annotation>) tFieldDescriptor.getAnnotations()) {
			if (annotation instanceof TLabel){
				TLabel tAnnotation = (TLabel) annotation;
				label = tAnnotation.text();
				continue;
			}
			
			Object value = propertyGetMethod.invoke(modelView);
			
			validateRequiredField(label, value, result, annotation);
			
			if(annotation instanceof TValidator){
				TValidator tAnnotation = (TValidator) annotation;
				com.tedros.fxapi.control.validator.TValidator validator = tAnnotation.validatorClass().getConstructor(String.class, Object.class).newInstance(label, value);
				if(tAnnotation.associatedFieldsName().length>0){
					for(String fName : tAnnotation.associatedFieldsName()){
						final Method associatedFieldGetMethod = modelView.getClass().getMethod(GET+StringUtils.capitalize(fName));
						Object associatedValue = associatedFieldGetMethod.invoke(modelView);
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
				
				Object object = propertyGetMethod.invoke(modelView);
				if(object instanceof ObservableList)
					lst = ((ObservableList) propertyGetMethod.invoke(modelView));
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
					lst = ((ObservableList) propertyGetMethod.invoke(modelView));
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

	private void validateRequiredField(final String fieldLabel, final Object valueObject, final TValidatorResult<E> result, final Annotation annotation) throws IllegalAccessException,
			InvocationTargetException {
		
		
		Boolean isRequired = TReflectionUtil.getValue(annotation, REQUIRED);
		if(isRequired!=null && isRequired){
			
			if(valueObject==null){
				fieldRequiredMessage(fieldLabel, result);
				return;
			}
			
			if(valueObject instanceof ObservableList){
				final ObservableList attrProperty = (ObservableList) valueObject;
				if(attrProperty==null || attrProperty.size()==0)
					selectAnOptionMessage(fieldLabel, result);
			}else if(valueObject instanceof ObservableSet){
				final ObservableSet attrProperty = (ObservableSet) valueObject;
				if(attrProperty==null || attrProperty.size()==0)
					selectAnOptionMessage(fieldLabel, result);
			}else if(valueObject instanceof ObservableMap){
				final ObservableMap attrProperty = (ObservableMap) valueObject;
				if(attrProperty==null || attrProperty.size()==0)
					selectAnOptionMessage(fieldLabel, result);
			}else{
				
				final Property attrProperty = (Property) valueObject;
				Object propertyValue = attrProperty.getValue();
				if(propertyValue==null){
					fieldRequiredMessage(fieldLabel, result);
					return;
				}
				
				if(propertyValue instanceof Number){
					Double number = ((Number)propertyValue).doubleValue();
					TZeroValidation zeroValidation = TReflectionUtil.getValue(annotation, ZEROVALIDATION);
					if(zeroValidation!=null && zeroValidation.equals(TZeroValidation.MINOR_THAN_ZERO) && number>=0)
						minorThanZeroMessage(fieldLabel, result);
					else if( zeroValidation==null || (zeroValidation!=null && zeroValidation.equals(TZeroValidation.GREATHER_THAN_ZERO) && number<=0))
						greatherThanZeroMessage(fieldLabel, result);
				} else if(propertyValue instanceof String && StringUtils.isBlank((String)propertyValue) ){
					fieldRequiredMessage(fieldLabel, result);	
				} else if(propertyValue instanceof Boolean && !((Boolean)propertyValue) ){
					checkRequiredMessage(fieldLabel, result);
				}		
			}
		}
	}

	private void fieldRequiredMessage(final String fieldName, final TValidatorResult<E> result) {
		result.addResult(fieldName, iEngine.getString("#{tedros.fxapi.validator.required}"), true);
	}
	
	private void minorThanZeroMessage(final String fieldName, final TValidatorResult<E> result) {
		result.addResult(fieldName, iEngine.getString("#{tedros.fxapi.validator.minorThanZero}"), true);
	}
	
	private void greatherThanZeroMessage(final String fieldName, final TValidatorResult<E> result) {
		result.addResult(fieldName, iEngine.getString("#{tedros.fxapi.validator.greatherThanZero}"), true);
	}
	
	private void selectAnOptionMessage(final String fieldName, final TValidatorResult<E> result) {
		result.addResult(fieldName, iEngine.getString("#{tedros.fxapi.validator.selectAnOption}"), true);
	}
	
	private void checkRequiredMessage(final String fieldName, final TValidatorResult<E> result) {
		result.addResult(fieldName, iEngine.getString("#{tedros.fxapi.validator.checkRequired}"), true);
	}
	
}
