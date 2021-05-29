/**
 * 
 */
package com.tedros.fxapi.control;

import org.apache.commons.lang3.StringUtils;

import com.tedros.app.component.ITComponent;
import com.tedros.fxapi.effect.TEffectUtil;

import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.effect.Effect;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;

/**
 * @author Davis Gordon
 *
 */
public abstract class TRequiredHTMLEditor extends VBox implements ITField, ITComponent{

	private String t_componentId;
	private SimpleBooleanProperty requirementAccomplishedProperty;
	private Effect requiredEffect;
    private ChangeListener<String> requiredListener;
    private SimpleBooleanProperty requiredProperty; 
    
    @Override
	public Observable tValueProperty() {
		return gettHTMLProperty();
	}
    
	public void setRequired(boolean required){
    	
		if(this.requiredProperty == null)
			this.requiredProperty = new SimpleBooleanProperty();
		
		this.requiredProperty.addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean new_value) {
				if(new_value){
					gettHTMLEditor().getStyleClass().add("required");
					buildRequiredEffect();
		    		buildNotNullListener();
		    		buildRequirementAccomplishedProperty();
		    		gettHTMLProperty().addListener(requiredListener);
					if(StringUtils.isBlank(gettHTMLProperty().getValue()))
						applyEffect();
					else
						removeEffect();
		    	}else{
		    		requirementAccomplishedProperty = null;
		    		removeEffect();
		    		gettHTMLEditor().getStyleClass().remove("required");
		    		if(requiredListener!=null)
		    			gettHTMLProperty().removeListener(requiredListener);
		    	}
			}
		});
		this.requiredProperty.set(required);
    }

	public abstract HTMLEditor gettHTMLEditor();

	public abstract SimpleStringProperty gettHTMLProperty();
	
	 private void buildRequiredEffect(){
			if(requiredEffect == null)
				requiredEffect = TEffectUtil.buildNotNullFieldFormEffect();
		}
   
	private void buildNotNullListener(){
		if(requiredListener == null)
			requiredListener = (ob, o, n)  -> {
				if(StringUtils.isBlank(n))
						applyEffect();
					else
						removeEffect();
			};
	}
	
	private void buildRequirementAccomplishedProperty(){
		if(requirementAccomplishedProperty == null)
			requirementAccomplishedProperty = new SimpleBooleanProperty();
	}
	
	private void removeEffect() {
		if(requirementAccomplishedProperty!=null)
			requirementAccomplishedProperty.set(true);
		gettHTMLEditor().setEffect(null);
		gettHTMLEditor().getStyleClass().remove("required-not-ok");
		gettHTMLEditor().getStyleClass().add("required-ok");
	}

	private void applyEffect() {
		if(requirementAccomplishedProperty!=null)
			requirementAccomplishedProperty.set(false);
		gettHTMLEditor().setEffect(requiredEffect);
		gettHTMLEditor().getStyleClass().remove("required-ok");
		gettHTMLEditor().getStyleClass().add("required-not-ok");
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
		gettHTMLEditor().setStyle(style);
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
