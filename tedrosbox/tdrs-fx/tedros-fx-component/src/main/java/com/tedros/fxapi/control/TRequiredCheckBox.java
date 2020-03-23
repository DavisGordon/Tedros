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
import javafx.scene.control.CheckBox;
import javafx.scene.effect.Effect;

import com.tedros.app.component.ITComponent;
import com.tedros.fxapi.effect.TEffectUtil;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public abstract class TRequiredCheckBox extends CheckBox implements ITField, ITComponent{

	private SimpleBooleanProperty requirementAccomplishedProperty;
    private Effect requiredEffect;
    private ChangeListener<Boolean> requiredListener;
    private SimpleBooleanProperty requiredProperty;
	private String t_componentId; 
    
    public TRequiredCheckBox() {
		
	}
	
	public TRequiredCheckBox(String text) {
		super(text);
	}
    
	public void setRequired(boolean required){
    	
		if(this.requiredProperty == null)
			this.requiredProperty = new SimpleBooleanProperty();
		
		this.requiredProperty.addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean new_value) {
				if(new_value){
					getStyleClass().add("box-required");
		    		buildRequiredEffect();
		    		buildNotNullListener();
		    		buildRequirementAccomplishedProperty();
		    		selectedProperty().addListener(requiredListener);
					validate(new_value);
		    	}else{
		    		getStyleClass().remove("box-required");
		    		requirementAccomplishedProperty = null;
		    		removeEffect();
		    		if(requiredListener!=null)
		    			selectedProperty().removeListener(requiredListener);
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
			requiredListener = new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
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
		getStyleClass().remove("box-required");
		getStyleClass().add("box-required-ok");
	}

	private void applyEffect() {
		if(requirementAccomplishedProperty!=null)
			requirementAccomplishedProperty.set(false);
		setEffect(requiredEffect);
		getStyleClass().remove("box-required-ok");
		getStyleClass().add("box-required");
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

	private void validate(Boolean arg2) {
		if(!arg2)
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
