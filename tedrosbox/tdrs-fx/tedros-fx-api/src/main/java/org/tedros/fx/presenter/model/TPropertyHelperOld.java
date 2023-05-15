/**
 * 
 */
package org.tedros.fx.presenter.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.tedros.fx.annotation.control.TModelViewType;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.collections.TFXCollections;
import org.tedros.fx.property.TSimpleFileProperty;
import org.tedros.fx.util.TReflectionUtil;
import org.tedros.server.model.ITFileBaseModel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;

/**
 * @author Davis Gordon
 *
 */
class TPropertyHelperOld<T extends TModelView> {
	
	final String name;;
	final Class type;
	final static String SET = "set";
	final static String GET = "get";
	
	Object value;
	T modelView;
	TModelViewType genericType;
	boolean buildListener = false;

	/**
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * 
	 */
	public TPropertyHelperOld(Field field, T modelView) throws Throwable {
		this.name = field.getName();
		this.type = field.getType();
		this.modelView = modelView;
		TModelViewType[] arr = field.getAnnotationsByType(TModelViewType.class);
		if(arr.length>0) 
			this.genericType = arr[0];
		
		if(isElegible()) {
			// recupera o metodo get do property
			final Method getMethod = modelView.getClass().getMethod(GET+StringUtils.capitalize(name));
			value = getMethod.invoke(modelView);
			initialize();
		}
	}
	
	void setValue(Object value)  throws Throwable {
		// recupera o metodo set do property
			final Method propertySetMethod = modelView.getClass().getMethod(SET+StringUtils.capitalize(name), type);
			
	}
	
	boolean isElegible() {
		return TCompatibleTypesHelper.isCompatible(type);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	void initialize() throws Throwable{
		// recupera o metodo set do property
		final Method setMethod = modelView.getClass().getMethod(SET+StringUtils.capitalize(name), type);
		if(value==null){
			buildListener = true;
			if(TReflectionUtil.isImplemented(type, ITObservableList.class)){
				value = (ITObservableList) TFXCollections.iTObservableList();
				setMethod.invoke(modelView, value);
			}else if(TReflectionUtil.isImplemented(type, ObservableList.class)){
				value = (ObservableList) FXCollections.observableArrayList();
				setMethod.invoke(modelView, value);
			}else if(type == ObservableSet.class){
				value = (ObservableSet)FXCollections.observableSet((Set)new HashSet());
				setMethod.invoke(modelView, value);
			}else if(type == ObservableMap.class ){
				value = (ObservableMap)FXCollections.observableMap((Map)new HashMap());
				setMethod.invoke(modelView, value);
			}else if(type == TSimpleFileProperty.class) {
				Class entityClass = null;
				if(this.genericType!=null)
					entityClass = this.genericType.modelClass();
					
				if(entityClass!=null) {
					value = type.getConstructor(ITFileBaseModel.class).newInstance(entityClass.newInstance());
					setMethod.invoke(modelView, value);
				}else {
					value = type.newInstance();
					setMethod.invoke(modelView, value);
				}
			}else{
				value = type.newInstance();
				setMethod.invoke(modelView, value);
			}
		}
	}

}
