/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 03/12/2013
 */
package com.tedros.fxapi.control;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextArea;
import javafx.scene.effect.Effect;

import org.apache.commons.lang3.StringUtils;

import com.tedros.app.component.ITComponent;
import com.tedros.fxapi.effect.TEffectUtil;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public abstract class TRequiredTextArea extends TextArea implements ITField, ITComponent {

	private SimpleBooleanProperty requirementAccomplishedProperty;
    private Effect requiredEffect;
    private ChangeListener<String> requiredListener;
    private SimpleBooleanProperty requiredProperty;
	private String t_componentId; 
    
	public void setRequired(boolean required){
    	
		if(this.requiredProperty == null)
			this.requiredProperty = new SimpleBooleanProperty();
		
		this.requiredProperty.addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean new_value) {
				if(new_value){
					getStyleClass().add("required");
		    		buildRequiredEffect();
		    		buildNotNullListener();
		    		buildRequirementAccomplishedProperty();
		    		textProperty().addListener(requiredListener);
					if(StringUtils.isBlank(getText()))
						applyEffect();
					else
						removeEffect();
		    	}else{
		    		requirementAccomplishedProperty = null;
		    		removeEffect();
		    		getStyleClass().remove("required");
		    		if(requiredListener!=null)
		    			textProperty().removeListener(requiredListener);
		    	}
			}
		});
		
		this.requiredProperty.set(required);
    }
    
    private void buildRequiredEffect(){
		if(requiredEffect == null)
			requiredEffect = TEffectUtil.buildNotNullFieldFormEffect();
	}
	
	private void buildNotNullListener(){
		if(requiredListener == null)
			requiredListener = new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> arg0, String arg1, String new_value) {
					if(StringUtils.isBlank(new_value))
						applyEffect();
					else
						removeEffect();
				}
			};
	}
	
	private void buildRequirementAccomplishedProperty(){
		if(requirementAccomplishedProperty == null)
			requirementAccomplishedProperty = new SimpleBooleanProperty();
	}
	
	private void removeEffect() {
		if(requirementAccomplishedProperty!=null)
			requirementAccomplishedProperty.set(true);
		setEffect(null);
		getStyleClass().remove("required-not-ok");
		getStyleClass().add("required-ok");
	}

	private void applyEffect() {
		if(requirementAccomplishedProperty!=null)
			requirementAccomplishedProperty.set(false);
		setEffect(requiredEffect);
		getStyleClass().remove("required-ok");
		getStyleClass().add("required-not-ok");
	}
	
	public SimpleBooleanProperty requiredProperty() {
		return requiredProperty;
	}
	
	public SimpleBooleanProperty requirementAccomplishedProperty() {
		return requirementAccomplishedProperty;
	}
	
	public boolean isRequirementAccomplished(){
		return (requirementAccomplishedProperty==null) ? true : requirementAccomplishedProperty.get() ; 
	}
	
	@Override
	public void settFieldStyle(String style) {
		setStyle(style);
	}
	
	@Override
	public String gettComponentId() {
		return t_componentId;
	}
	
	@Override
	public void settComponentId(String id) {
		t_componentId = id;
	}
	
}
