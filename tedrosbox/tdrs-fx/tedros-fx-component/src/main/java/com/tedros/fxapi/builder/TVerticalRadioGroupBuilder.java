/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package com.tedros.fxapi.builder;

import java.lang.annotation.Annotation;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.Tooltip;

import org.apache.commons.lang3.StringUtils;

import com.tedros.core.TInternationalizationEngine;
import com.tedros.fxapi.annotation.control.TRadioButtonField;
import com.tedros.fxapi.annotation.control.TVerticalRadioGroup;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public final class TVerticalRadioGroupBuilder 
extends TBuilder
implements ITControlBuilder<com.tedros.fxapi.control.TVerticalRadioGroup, Property>{

	private static TVerticalRadioGroupBuilder instance;
	
	private TVerticalRadioGroupBuilder(){
		
	}
	
	public static  TVerticalRadioGroupBuilder getInstance(){
		if(instance==null)
			instance = new TVerticalRadioGroupBuilder();
		return instance;	
	}
	
	@SuppressWarnings({"unchecked"})
	public com.tedros.fxapi.control.TVerticalRadioGroup build(final Annotation annotation, final Property attrProperty) throws Exception {
		TVerticalRadioGroup tAnnotation = (TVerticalRadioGroup) annotation;
		final com.tedros.fxapi.control.TVerticalRadioGroup control = new com.tedros.fxapi.control.TVerticalRadioGroup();
		control.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> arg0, Toggle arg1, Toggle new_toggle) {
				
				if(new_toggle == null){
					attrProperty.setValue(new_toggle);
					return;
				}
				
				if(attrProperty instanceof SimpleStringProperty)
					attrProperty.setValue((String)new_toggle.getUserData());
				if(attrProperty instanceof SimpleDoubleProperty)
					attrProperty.setValue(Double.valueOf((String)new_toggle.getUserData()));
				if(attrProperty instanceof SimpleLongProperty)
					attrProperty.setValue(Long.valueOf((String)new_toggle.getUserData()));
				if(attrProperty instanceof SimpleIntegerProperty)
					attrProperty.setValue(Integer.valueOf((String)new_toggle.getUserData()));
				if(attrProperty instanceof SimpleBooleanProperty)
					attrProperty.setValue(Boolean.valueOf((String)new_toggle.getUserData()));
				if(attrProperty instanceof SimpleFloatProperty)
					attrProperty.setValue(Float.valueOf((String)new_toggle.getUserData()));
				
			}
		});
		
		callParser(tAnnotation, control);
				
		for(TRadioButtonField tRb : tAnnotation.radioButtons()){
			final RadioButton radioBtn = new RadioButton(iEngine.getString(tRb.text()));
			if(tRb.alignment()!=null) radioBtn.setAlignment(tRb.alignment());
			if(tRb.width()>0) radioBtn.setPrefWidth(tRb.width());
			if(tRb.height()>0) radioBtn.setPrefHeight(tRb.height());
			if(tRb.opacity()>0) radioBtn.setOpacity(tRb.opacity());
			if(StringUtils.isNotBlank(tRb.style())) radioBtn.setStyle(tRb.style());
			if(tRb.styleClass()!=null && tRb.styleClass().length>0) radioBtn.getStyleClass().addAll(tRb.styleClass());
			if(StringUtils.isNotBlank(tRb.toolTip())) radioBtn.setTooltip(new Tooltip(tRb.toolTip()));
			
			radioBtn.setVisible(tRb.visible());
			radioBtn.setDisable(tRb.disable());
			radioBtn.setWrapText(tRb.wrapText());
			radioBtn.setUserData(tRb.userData());
			if(attrProperty.getValue()!=null && tRb.userData()!=null){
				if(attrProperty.getValue().toString().equals(tRb.userData()))
					radioBtn.setSelected(true);
			}else
				radioBtn.setSelected(tRb.selected());
			
			control.addRadioButton(radioBtn);
		}
		return control;
	}
}
