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
import javafx.scene.Node;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.Effect;
import javafx.scene.layout.Pane;

import com.tedros.app.component.ITComponent;
import com.tedros.fxapi.effect.TEffectUtil;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings("restriction")
public abstract class TRequiredToggleGroup extends ToggleGroup implements ITField, ITComponent {

	private SimpleBooleanProperty requirementAccomplishedProperty;
    private Effect requiredEffect;
    private ChangeListener<Toggle> requiredListener;
    private SimpleBooleanProperty requiredProperty;
	private String t_componentId; 
    
    abstract Pane getBox();
    
	public void setRequired(boolean required){
    	
		if(this.requiredProperty == null)
			this.requiredProperty = new SimpleBooleanProperty();
		
		this.requiredProperty.addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean new_value) {
				if(new_value){
					getBox().getStyleClass().add("box-required");
					buildRequiredEffect();
		    		buildNotNullListener();
		    		buildRequirementAccomplishedProperty();
		    		selectedToggleProperty().addListener(requiredListener);
					validate(getSelectedToggle());
		    	}else{
		    		requirementAccomplishedProperty = null;
		    		removeEffect();
		    		getBox().getStyleClass().remove("box-required");
		    		if(requiredListener!=null)
		    			selectedToggleProperty().removeListener(requiredListener);
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
			requiredListener = new ChangeListener<Toggle>() {
				@Override
				public void changed(ObservableValue<? extends Toggle> arg0, Toggle arg1, Toggle arg2) {
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
		getBox().setEffect(null);
		getBox().getStyleClass().remove("box-required");
		getBox().getStyleClass().add("box-required-ok");
	}

	private void applyEffect() {
		if(requirementAccomplishedProperty!=null)
			requirementAccomplishedProperty.set(false);
		getBox().setEffect(requiredEffect);
		getBox().getStyleClass().remove("box-required-ok");
		getBox().getStyleClass().add("box-required");
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

	private void validate(Toggle toggle) {
		if(toggle==null)
			applyEffect();
		else
			removeEffect();			
	}
	
	@Override
	public void settFieldStyle(String style) {
		if(getBox()!=null)
			for(Node node : getBox().getChildren())
				node.setStyle(style);
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
