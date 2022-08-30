package org.tedros.fx.control.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.tedros.api.descriptor.ITFieldDescriptor;
import org.tedros.core.TLanguage;
import org.tedros.core.model.ITModelView;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TModelViewType;
import org.tedros.fx.annotation.control.TValidator;
import org.tedros.fx.domain.TZeroValidation;
import org.tedros.fx.presenter.model.TModelView;
import org.tedros.fx.util.TReflectionUtil;
import org.tedros.server.entity.ITFileEntity;
import org.tedros.server.model.ITFileModel;

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
	private TLanguage iEngine;
	
	public TControlValidator() {
		iEngine = TLanguage.getInstance(null);
	}
	
	public TControlValidator(List<E> modelsView) throws Exception {
		iEngine = TLanguage.getInstance(null);
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
			if(tModelView==null)
				continue;
			List<ITFieldDescriptor> l = TReflectionUtil.getFieldDescriptorList(tModelView);
			TValidatorResult<E> result = new TValidatorResult(tModelView);
			for (ITFieldDescriptor fd : l){
				if(TReflectionUtil.isIgnoreField(fd))
					continue;
				validateField(result, fd, tModelView);
			}
			if(!result.isRequirementAccomplished())
				list.add(result);	
		}
	}
	
	@SuppressWarnings({"unchecked"})
	private void validateField(final TValidatorResult<E> result, final ITFieldDescriptor tFieldDescriptor, final E modelView) throws Exception {
		
		final String fieldName = tFieldDescriptor.getFieldName();
		Method propertyGetMethod = null;
		try {
			propertyGetMethod = modelView.getClass().getMethod(GET+StringUtils.capitalize(fieldName));
		}catch(Exception e) {
			return;
		}
		StringBuilder label = new StringBuilder("");
		List<Annotation> lAnn = (List<Annotation>) tFieldDescriptor.getAnnotations();
		lAnn.stream()
		.forEach(a->{
			if(a instanceof TLabel) {
				TLabel l = (TLabel) a;
				label.append(l.text());
			}
		});
		for (final Annotation annotation : (List<Annotation>) tFieldDescriptor.getAnnotations()) {
			
			if(annotation instanceof TValidator){
				
				Object value = propertyGetMethod.invoke(modelView);
				validateRequiredField(label.toString(), value, result, annotation);
				
				TValidator tAnnotation = (TValidator) annotation;
				org.tedros.fx.control.validator.TValidator validator = tAnnotation.validatorClass()
						.getConstructor(String.class, Object.class).newInstance(label, value);
				
				if(tAnnotation.associatedFieldsName().length>0){
					for(String fName : tAnnotation.associatedFieldsName()){
						if(StringUtils.isBlank(fName))
							continue;
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
			
			if (annotation instanceof TModelViewType && ((TModelViewType)annotation).modelViewClass()!=TModelView.class){
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
				if(lst==null)
					continue;
				results = (List<TValidatorResult>) validator.validate(lst);
				if(results.size()>0)
					for (TValidatorResult tValidatorResult : results) 
						list.add(tValidatorResult);
				
				continue;
			}
		
			Object value = propertyGetMethod.invoke(modelView);
			validateRequiredField(label.toString(), value, result, annotation);
		}
		
	}

	private void validateRequiredField(final String fieldLabel, final Object valueObject, final TValidatorResult<E> result, final Annotation annotation) throws IllegalAccessException,
			InvocationTargetException {
		
		TZeroValidation zeroValidation = TReflectionUtil.getValue(annotation, ZEROVALIDATION);
		Boolean isRequired = TReflectionUtil.getValue(annotation, REQUIRED);
		if((isRequired!=null && isRequired) || (zeroValidation!=null && !zeroValidation.equals(TZeroValidation.NONE))){
			
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
				
				if(propertyValue instanceof ITFileModel && 
						(((ITFileModel)propertyValue).getFileSize()==null 
						|| ((ITFileModel)propertyValue).getFileSize()==0)){
					fieldRequiredMessage(fieldLabel, result);
					return;
				}else if(propertyValue instanceof ITFileEntity && 
						(((ITFileEntity)propertyValue).getFileSize()==null 
						|| ((ITFileEntity)propertyValue).getFileSize()==0)){
					fieldRequiredMessage(fieldLabel, result);
					return;
				}else if(propertyValue instanceof Number){
					Double number = ((Number)propertyValue).doubleValue();
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
