/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 03/12/2013
 */
package com.tedros.fxapi.control;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import javafx.scene.effect.Effect;

import org.apache.commons.lang3.math.NumberUtils;

import com.tedros.app.component.ITComponent;
import com.tedros.fxapi.domain.TZeroValidation;
import com.tedros.fxapi.effect.TEffectUtil;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public abstract class TRequiredNumberField extends TextField implements ITField, ITComponent {

	private SimpleBooleanProperty requirementAccomplishedProperty;
    private Effect requiredEffect;
    private ChangeListener<String> requiredListener;
    private SimpleObjectProperty<TZeroValidation> zeroValidationProperty;
	private String t_componentId; 
    
	public void setZeroValidation(TZeroValidation zeroValidation){
    	
		if(this.zeroValidationProperty == null)
			this.zeroValidationProperty = new SimpleObjectProperty<>();
		
		this.zeroValidationProperty.addListener(new ChangeListener<TZeroValidation>() {
			@Override
			public void changed(ObservableValue<? extends TZeroValidation> arg0, TZeroValidation arg1, TZeroValidation new_value) {
				if(!new_value.equals(TZeroValidation.NONE)){
					getStyleClass().add("required");
		    		buildRequiredEffect();
		    		buildZeroValidationListener();
		    		buildRequirementAccomplishedProperty();
		    		textProperty().addListener(requiredListener);
					checkNumber(getText());
					
		    	}else{
		    		requirementAccomplishedProperty = null;
		    		removeEffect();
		    		getStyleClass().remove("required");
		    		if(requiredListener!=null)
		    			textProperty().removeListener(requiredListener);
		    	}
			}
		});
		
		this.zeroValidationProperty.set(zeroValidation);
    }
    
    private void buildRequiredEffect(){
		if(requiredEffect == null)
			requiredEffect = TEffectUtil.buildNotNullFieldFormEffect();
	}
	
	private void buildZeroValidationListener(){
		if(requiredListener == null)
			requiredListener = new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> arg0, String arg1, String new_value) {
					checkNumber(new_value);
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
	
	public SimpleObjectProperty<TZeroValidation> zeroValidationProperty() {
		return zeroValidationProperty;
	}
	
	public SimpleBooleanProperty requirementAccomplishedProperty() {
		return requirementAccomplishedProperty;
	}
	
	public boolean isRequirementAccomplished(){
		return (requirementAccomplishedProperty==null) ? true : requirementAccomplishedProperty.get() ; 
	}
	
	private void checkNumber(String new_value) {
		if(!NumberUtils.isNumber(new_value)){
			applyEffect();
			return;
		}
		
		Number number = NumberUtils.createNumber(new_value);
		if(zeroValidationProperty.getValue().equals(TZeroValidation.GREATHER_THAN_ZERO)){
			if(number.doubleValue()<=0.0)
				applyEffect();
			else
				removeEffect();
		}
		
		if(zeroValidationProperty.getValue().equals(TZeroValidation.MINOR_THAN_ZERO)){
			if(number.doubleValue()>=0.0)
				applyEffect();
			else
				removeEffect();
		}
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
