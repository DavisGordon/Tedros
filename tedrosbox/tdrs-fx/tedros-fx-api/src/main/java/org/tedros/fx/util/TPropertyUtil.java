package org.tedros.fx.util;

import javafx.beans.property.Property;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;

import org.apache.commons.lang3.StringUtils;

import com.sun.javafx.collections.ObservableListWrapper;
import com.sun.javafx.collections.ObservableMapWrapper;
import com.sun.javafx.collections.ObservableSetWrapper;

public final class TPropertyUtil {

	private TPropertyUtil() {
	
	}
	
	public  static boolean isValueNull(Property<?> property){
		return (property==null || (property!=null && property.getValue()==null));
	}
	
	public  static boolean isValueNotNull(Property<?> property){
		return (property!=null && property.getValue()!=null);
	}
	
	public  static boolean isStringValueNotBlank(Property<String> property){
		return (property!=null && StringUtils.isNotBlank(property.getValue()));
	}
	
	public  static boolean isStringValueBlank(Property<String> property){
		return (property!=null && StringUtils.isBlank(property.getValue()));
	}
	
	public static boolean isCollectionObservableType(final Class<?> propertyClass) {
		return TReflectionUtil.isImplemented(propertyClass, 
				ObservableList.class, ObservableSet.class, ObservableMap.class, ObservableListWrapper.class, ObservableSetWrapper.class, ObservableMapWrapper.class);
		/*return propertyClass == ObservableList.class || propertyClass == ObservableSet.class || propertyClass == ObservableMap.class || 
				propertyClass == ObservableListWrapper.class || propertyClass == ObservableSetWrapper.class || propertyClass == ObservableMapWrapper.class ;*/
	}
	
	
	/**
	 * Retorna a {@link String} setado no {@link Property}, em caso de string nula retorna uma string vazia.
	 * 
	 * @param property
	 * @return {@link String}
	 * */
	public static String getValue(Property<String> property){
		return isStringValueNotBlank(property) ? property.getValue() : "";
	}
	
	
}
