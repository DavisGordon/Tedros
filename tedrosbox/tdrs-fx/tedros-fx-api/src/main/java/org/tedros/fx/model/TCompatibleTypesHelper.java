/**
 * 
 */
package org.tedros.fx.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.collections.TSimpleObservableList;
import org.tedros.fx.property.TSimpleFileProperty;
import org.tedros.fx.util.TReflectionUtil;
import org.tedros.server.entity.ITEntity;
import org.tedros.server.entity.ITFileEntity;
import org.tedros.server.entity.TEntity;
import org.tedros.server.model.ITFileBaseModel;
import org.tedros.server.model.ITFileModel;
import org.tedros.server.model.ITModel;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;

/**
 * @author davis.dun
 *
 */
class TCompatibleTypesHelper {
	
	@SuppressWarnings("rawtypes")
	static Map<Class, List<Class>> compatibleTypes;
	
	static {
		loadTypesCompatibility();
	}
	
	
	@SuppressWarnings("rawtypes")
	static boolean isTypesCompatible(final Class propertyFieldType, Class typeToVerify) {
		
		boolean compatible = compatibleTypes.get(propertyFieldType).contains(typeToVerify); 
		
		if(!compatible && propertyFieldType == SimpleObjectProperty.class)
			compatible = true;
		
		else if(!compatible && propertyFieldType == TSimpleFileProperty.class)
			compatible = isClassAFileBaseModel(typeToVerify);
		
		else if(!compatible && isClassAnEntity(typeToVerify))
			compatible = compatibleTypes.get(propertyFieldType).contains(TEntity.class);
		
		else if(!compatible && isClassAModel(typeToVerify))
			compatible = compatibleTypes.get(propertyFieldType).contains(ITModel.class);
		
		return compatible; 
	}
	
	@SuppressWarnings("rawtypes")
	static boolean isCompatible(final Class propertyFieldType) {
		return compatibleTypes.containsKey(propertyFieldType);
	}

	@SuppressWarnings("rawtypes")
	static boolean isClassAModel(Class clazz) {
		return TReflectionUtil.isImplemented(clazz, ITModel.class);
	}
	
	@SuppressWarnings("rawtypes")
	static boolean isClassAnEntity(Class clazz) {
		return TReflectionUtil.isImplemented(clazz, ITEntity.class);
	}
	
	@SuppressWarnings("rawtypes")
	static boolean isClassAFileBaseModel(Class clazz) {
		return TReflectionUtil.isImplemented(clazz, ITFileBaseModel.class);
	}

	@SuppressWarnings("rawtypes")
	static boolean isClassAFileModel(Class clazz) {
		return TReflectionUtil.isImplemented(clazz, ITFileModel.class);
	}

	@SuppressWarnings("rawtypes")
	static boolean isClassAFileEntity(Class clazz) {
		return TReflectionUtil.isImplemented(clazz, ITFileEntity.class);
	}
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	private static void loadTypesCompatibility(){
		
		compatibleTypes = new HashMap<>();
		//compatibleTypes.put(SimpleObjectProperty.class, (List) Arrays.asList(ITModel.class, TEntity.class, ITFileEntity.class, ITFileModel.class, ITEntity.class, Color.class, Object.class, Date.class, byte[].class, BigDecimal.class, BigInteger.class, File.class, Enum.class));
		compatibleTypes.put(SimpleObjectProperty.class, (List) Arrays.asList(Object.class));
		compatibleTypes.put(SimpleStringProperty.class, (List) Arrays.asList(String.class, Character.class, Long.class, Integer.class, Double.class, BigDecimal.class, BigInteger.class));
		compatibleTypes.put(SimpleDoubleProperty.class, (List) Arrays.asList(Double.class, BigDecimal.class));
		compatibleTypes.put(SimpleBooleanProperty.class, (List) Arrays.asList(Boolean.class, String.class));
		compatibleTypes.put(SimpleIntegerProperty.class, (List) Arrays.asList(Integer.class));
		compatibleTypes.put(SimpleLongProperty.class, (List) Arrays.asList(Long.class));
		compatibleTypes.put(SimpleFloatProperty.class, (List) Arrays.asList(Float.class));
		compatibleTypes.put(TSimpleFileProperty.class, (List) Arrays.asList(ITFileEntity.class, ITFileModel.class));
		
		compatibleTypes.put(ITObservableList.class, (List) Arrays.asList(List.class, ArrayList.class));
		compatibleTypes.put(TSimpleObservableList.class, (List) Arrays.asList(List.class, ArrayList.class));
		compatibleTypes.put(ObservableList.class, (List) Arrays.asList(List.class, ArrayList.class));		
		compatibleTypes.put(ObservableSet.class, (List) Arrays.asList(Set.class, HashSet.class));
		compatibleTypes.put(ObservableMap.class, (List) Arrays.asList(Map.class, HashMap.class));
		
		compatibleTypes.put(SimpleListProperty.class, (List) Arrays.asList(List.class, ArrayList.class));		
		compatibleTypes.put(SimpleSetProperty.class, (List) Arrays.asList(Set.class, HashSet.class));
		compatibleTypes.put(SimpleMapProperty.class, (List) Arrays.asList(Map.class, HashMap.class));
		
	}
	
}
