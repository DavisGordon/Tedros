package org.tedros.fx.control.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.tedros.api.descriptor.ITFieldDescriptor;
import org.tedros.core.TLanguage;
import org.tedros.core.model.ITModelView;
import org.tedros.fx.TFxKey;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TGenericType;
import org.tedros.fx.annotation.control.TValidator;
import org.tedros.fx.domain.TValidateNumber;
import org.tedros.fx.exception.TException;
import org.tedros.fx.model.TModelView;
import org.tedros.fx.model.TModelViewBuilder;
import org.tedros.fx.util.TReflectionUtil;
import org.tedros.server.entity.ITFileEntity;
import org.tedros.server.model.ITFileModel;
import org.tedros.server.model.ITModel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;

@SuppressWarnings("rawtypes")
public final class TControlValidator<E extends ITModelView> {

	private static final String REQUIRED = "required";
	private static final String VALIDATE = "validate";
	
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
		Field field = tFieldDescriptor.getField();
		field.setAccessible(true);
		Object value = field.get(modelView);
		field.setAccessible(false);
		
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
				
				validateRequiredField(label.toString(), value, result, annotation);
				
				TValidator tAnnotation = (TValidator) annotation;
				org.tedros.fx.control.validator.TValidator validator = tAnnotation.validatorClass()
						.getConstructor(String.class, Object.class).newInstance(label.toString(), value);
				
				if(tAnnotation.associatedFieldsName().length>0){
					for(String fName : tAnnotation.associatedFieldsName()){
						if(StringUtils.isBlank(fName))
							continue;
						
						final Field associatedField = modelView.getClass().getField(fName);
						associatedField.setAccessible(true);
						Object associatedValue = associatedField.get(modelView);
						associatedField.setAccessible(false);
						validator.addAssociatedFieldValue(fName, associatedValue);
					}
				}
				final TFieldResult TFieldResult = validator.validate();
				if(TFieldResult!=null)
					result.addResult(TFieldResult);
				continue;
			}
			
			if (annotation instanceof TGenericType && ((TGenericType)annotation).modelView()!=TModelView.class){
				final TControlValidator validator = new TControlValidator();
				List<ITModelView> lst = null;
				List<TValidatorResult> results = null;
				
				if(value instanceof ObservableList)
					lst = (ObservableList) value;
				else if (value instanceof ObjectProperty) {
					Object object = ((ObjectProperty)value).getValue();
					if(object==null)
						continue;
					ITModelView detailModelView = null;
					try {
						detailModelView = (object instanceof ITModelView)
								? (ITModelView) object
										: TModelViewBuilder
										.create()
										.modelViewClass((Class<? extends ITModelView<?>>) ((TGenericType)annotation).modelView())
										.build((ITModel)object);
						lst = Arrays.asList(detailModelView);
					} catch (TException e) {
						e.printStackTrace();
						throw new RuntimeException(e);
					}
				}
				if(lst==null)
					continue;
				results = (List<TValidatorResult>) validator.validate(lst);
				if(results.size()>0)
					for (TValidatorResult tValidatorResult : results) 
						list.add(tValidatorResult);
				
				continue;
			}
		
			validateRequiredField(label.toString(), value, result, annotation);
		}
		
	}

	private void validateRequiredField(final String fieldLabel, final Object valueObject, final TValidatorResult<E> result, final Annotation annotation) throws IllegalAccessException,
			InvocationTargetException {
		
		TValidateNumber validate = TReflectionUtil.getValue(annotation, VALIDATE);
		Boolean isRequired = TReflectionUtil.getValue(annotation, REQUIRED);
		if((isRequired!=null && isRequired) || (validate!=null && !validate.equals(TValidateNumber.NONE))){
			
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
					if(validate!=null && validate.equals(TValidateNumber.MINOR_THAN_ZERO) && number>=0)
						minorThanZeroMessage(fieldLabel, result);
					else if( validate==null || (validate!=null && validate.equals(TValidateNumber.GREATHER_THAN_ZERO) && number<=0))
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
		result.addResult(fieldName, iEngine.getString(TFxKey.VALIDATOR_REQUIRED), true);
	}
	
	private void minorThanZeroMessage(final String fieldName, final TValidatorResult<E> result) {
		result.addResult(fieldName, iEngine.getString(TFxKey.VALIDATOR_MINORTHANZERO), true);
	}
	
	private void greatherThanZeroMessage(final String fieldName, final TValidatorResult<E> result) {
		result.addResult(fieldName, iEngine.getString(TFxKey.VALIDATOR_GREATHERTHANZERO), true);
	}
	
	private void selectAnOptionMessage(final String fieldName, final TValidatorResult<E> result) {
		result.addResult(fieldName, iEngine.getString(TFxKey.VALIDATOR_SELECTANOPTION), true);
	}
	
	private void checkRequiredMessage(final String fieldName, final TValidatorResult<E> result) {
		result.addResult(fieldName, iEngine.getString(TFxKey.VALIDATOR_CHECKREQUIRED), true);
	}
	
}
