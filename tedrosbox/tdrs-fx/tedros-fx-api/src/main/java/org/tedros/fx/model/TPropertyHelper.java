/**
 * 
 */
package org.tedros.fx.model;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
@SuppressWarnings("rawtypes")
class TPropertyHelper<T extends TModelView> {
	
	final Field field;;
	final String name;
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
	public TPropertyHelper(Field field, T modelView) throws Throwable {
		this.field = field;
		this.name = field.getName();
		this.type = field.getType();
		this.field.setAccessible(true);
		this.modelView = modelView;
		TModelViewType[] arr = field.getAnnotationsByType(TModelViewType.class);
		if(arr.length>0) 
			this.genericType = arr[0];
		
		if(isElegible()) {
			value = field.get(modelView);//getMethod.invoke(modelView);
			initialize();
		}
	}
	
	void setValue(Object value)  throws Throwable {
		this.field.set(modelView, value);
	}
	
	boolean isElegible() {
		return TCompatibleTypesHelper.isCompatible(type);
	}
	
	@SuppressWarnings({ "unchecked" })
	void initialize() throws Throwable{
		if(value==null){
			buildListener = true;
			if(TReflectionUtil.isImplemented(type, ITObservableList.class)){
				value = (ITObservableList) TFXCollections.iTObservableList();
				setValue(value);
			}else if(TReflectionUtil.isImplemented(type, ObservableList.class)){
				value = (ObservableList) FXCollections.observableArrayList();
				setValue(value);
			}else if(type == ObservableSet.class){
				value = (ObservableSet)FXCollections.observableSet((Set)new HashSet());
				setValue(value);
			}else if(type == ObservableMap.class ){
				value = (ObservableMap)FXCollections.observableMap((Map)new HashMap());
				setValue(value);
			}else if(type == TSimpleFileProperty.class) {
				Class entityClass = null;
				if(this.genericType!=null)
					entityClass = this.genericType.modelClass();
					
				if(entityClass!=null) {
					value = type.getConstructor(ITFileBaseModel.class).newInstance(entityClass.newInstance());
					setValue(value);
				}else {
					value = type.newInstance();
					setValue(value);
				}
			}else{
				value = type.newInstance();
				setValue(value);
			}
		}
	}

}
