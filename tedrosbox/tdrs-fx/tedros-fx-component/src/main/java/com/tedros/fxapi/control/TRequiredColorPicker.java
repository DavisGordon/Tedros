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
import javafx.scene.control.ColorPicker;
import javafx.scene.effect.Effect;

import com.tedros.app.component.ITComponent;
import com.tedros.fxapi.effect.TEffectUtil;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public abstract class TRequiredColorPicker extends ColorPicker implements ITField, ITComponent {

	private SimpleBooleanProperty requirementAccomplishedProperty;
    private Effect requiredEffect;
    private ChangeListener<Object> requiredListener;
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
		    		valueProperty().addListener(requiredListener);
					validate(getValue());
		    	}else{
		    		requirementAccomplishedProperty = null;
		    		removeEffect();
		    		getStyleClass().remove("required");
		    		if(requiredListener!=null)
		    			valueProperty().removeListener(requiredListener);
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
			requiredListener = new ChangeListener<Object>() {
				@Override
				public void changed(ObservableValue<? extends Object> arg0, Object arg1, Object arg2) {
					validate(arg2);
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

	private void validate(Object new_value) {
		final Object item = (new_value instanceof TItem) ? ((TItem)new_value).getValue() : new_value;
		if(item==null)
			applyEffect();
		else
			removeEffect();
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
