/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package org.tedros.fx.builder;

import java.lang.annotation.Annotation;

import org.apache.commons.lang3.StringUtils;
import org.tedros.fx.annotation.control.THorizontalRadioGroup;
import org.tedros.fx.annotation.control.TRadioButton;
import org.tedros.fx.form.TConverter;

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


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings({ "rawtypes" })
public final class THorizontalRadioGroupBuilder extends TBuilder 
implements ITControlBuilder<org.tedros.fx.control.THorizontalRadioGroup, Property>{

	@SuppressWarnings({"unchecked"})
	public org.tedros.fx.control.THorizontalRadioGroup build(final Annotation annotation, final Property attrProperty) throws Exception {
		
		THorizontalRadioGroup tAnnotation = (THorizontalRadioGroup) annotation;
		
		final TConverter conv = 
				(tAnnotation.converter().parse() && tAnnotation.converter().type()!=TConverter.class) 
					? tAnnotation.converter().type().newInstance() 
					: null;
					
		if(conv!=null)
			conv.setComponentDescriptor(super.getComponentDescriptor());
				
				
		
		final org.tedros.fx.control.THorizontalRadioGroup control = new org.tedros.fx.control.THorizontalRadioGroup();
		control.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> arg0, Toggle arg1, Toggle n) {
				
				if(n == null){
					attrProperty.setValue(n);
					return;
				}
				
				if(conv!=null) {
					conv.setIn(n.getUserData());
					attrProperty.setValue(conv.getOut());
				}else{
				
				if(attrProperty instanceof SimpleStringProperty)
					attrProperty.setValue((String)n.getUserData());
				if(attrProperty instanceof SimpleDoubleProperty)
					attrProperty.setValue(Double.valueOf((String)n.getUserData()));
				if(attrProperty instanceof SimpleLongProperty)
					attrProperty.setValue(Long.valueOf((String)n.getUserData()));
				if(attrProperty instanceof SimpleIntegerProperty)
					attrProperty.setValue(Integer.valueOf((String)n.getUserData()));
				if(attrProperty instanceof SimpleBooleanProperty)
					attrProperty.setValue(Boolean.valueOf((String)n.getUserData()));
				if(attrProperty instanceof SimpleFloatProperty)
					attrProperty.setValue(Float.valueOf((String)n.getUserData()));
				}
			}
		});
		
		callParser(tAnnotation, control);
		
		for(TRadioButton tRb : tAnnotation.radioButtons()){
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
			if(attrProperty.getValue()!=null){
				if(conv!=null) {
					conv.setIn(tRb.userData());
					Object obj = conv.getOut();
					if(attrProperty.getValue().equals(obj))
						radioBtn.setSelected(true);
				}else
					if(attrProperty.getValue().toString().equals(tRb.userData()))
						radioBtn.setSelected(true);
				
			}else
				radioBtn.setSelected(tRb.selected());
			
			control.addRadioButton(radioBtn);
		}
		
		
		return control;
	}
}
