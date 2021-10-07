/**
 * 
 */
package com.tedros.fxapi.presenter.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tedros.ejb.base.entity.ITFileEntity;
import com.tedros.ejb.base.entity.TEntity;
import com.tedros.ejb.base.model.ITFileModel;
import com.tedros.ejb.base.model.ITModel;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.collections.TSimpleObservableList;
import com.tedros.fxapi.property.TSimpleFileEntityProperty;
import com.tedros.fxapi.property.TSimpleFileModelProperty;

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
class TCompatibleTypesHelper<M extends ITModel> {
	
	private TModelView<M> tModelView;
	
	@SuppressWarnings("rawtypes")
	protected Map<Class, List<Class>> compatibleTypes;
	
	protected TCompatibleTypesHelper(TModelView<M> tModelView) {
		this.tModelView = tModelView;
		loadTypesCompatibility();
	}
	
	@SuppressWarnings("rawtypes")
	protected boolean isTypesCompatible(final Class propertyFieldType, Class typeToVerify) {
		
		boolean compatible = compatibleTypes.get(propertyFieldType).contains(typeToVerify); 
		
		if(!compatible && propertyFieldType == SimpleObjectProperty.class)
			compatible = true;
		
		else if(!compatible && propertyFieldType == TSimpleFileEntityProperty.class)
			compatible = tModelView.isClassAFileEntity(typeToVerify);
		
		else if(!compatible && propertyFieldType == TSimpleFileModelProperty.class)
			compatible = tModelView.isClassAFileModel(typeToVerify);
		
		else if(!compatible && tModelView.isClassAnEntity(typeToVerify))
			compatible = compatibleTypes.get(propertyFieldType).contains(TEntity.class);
		
		else if(!compatible && tModelView.isClassAModel(typeToVerify))
			compatible = compatibleTypes.get(propertyFieldType).contains(ITModel.class);
		
		return compatible; 
	}
	
	@SuppressWarnings("rawtypes")
	protected boolean isCompatible(final Class propertyFieldType) {
		return compatibleTypes.containsKey(propertyFieldType);
	}
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	private void loadTypesCompatibility(){
		
		compatibleTypes = new HashMap<>();
		//compatibleTypes.put(SimpleObjectProperty.class, (List) Arrays.asList(ITModel.class, TEntity.class, ITFileEntity.class, ITFileModel.class, ITEntity.class, Color.class, Object.class, Date.class, byte[].class, BigDecimal.class, BigInteger.class, File.class, Enum.class));
		compatibleTypes.put(SimpleObjectProperty.class, (List) Arrays.asList(Object.class));
		compatibleTypes.put(SimpleStringProperty.class, (List) Arrays.asList(String.class, Character.class, Long.class, Integer.class, Double.class, BigDecimal.class, BigInteger.class));
		compatibleTypes.put(SimpleDoubleProperty.class, (List) Arrays.asList(Double.class, BigDecimal.class));
		compatibleTypes.put(SimpleBooleanProperty.class, (List) Arrays.asList(Boolean.class, String.class));
		compatibleTypes.put(SimpleIntegerProperty.class, (List) Arrays.asList(Integer.class));
		compatibleTypes.put(SimpleLongProperty.class, (List) Arrays.asList(Long.class));
		compatibleTypes.put(SimpleFloatProperty.class, (List) Arrays.asList(Float.class));
		compatibleTypes.put(TSimpleFileEntityProperty.class, (List) Arrays.asList(ITFileEntity.class));
		compatibleTypes.put(TSimpleFileModelProperty.class, (List) Arrays.asList(ITFileModel.class));
		
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
