/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 03/12/2013
 */
package org.tedros.fx.control;

import org.tedros.app.component.ITComponent;
import org.tedros.fx.domain.TZeroValidation;
import org.tedros.fx.effect.TEffectUtil;

import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;
import javafx.scene.effect.Effect;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public abstract class TRequiredSlider extends Slider implements ITField, ITComponent {

	private SimpleBooleanProperty requirementAccomplishedProperty;
    private Effect requiredEffect;
    private ChangeListener<Number> requiredListener;
    private SimpleObjectProperty<TZeroValidation> zeroValidationProperty;
	private String t_componentId; 
	
	@Override
	public Observable tValueProperty() {
		return valueProperty();
	}
    
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
		    		valueProperty().addListener(requiredListener);
					checkNumber(getValue());
		    	}else{
		    		requirementAccomplishedProperty = null;
		    		removeEffect();
		    		getStyleClass().remove("required");
		    		if(requiredListener!=null)
		    			valueProperty().removeListener(requiredListener);
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
			requiredListener = new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number new_value) {
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

	private void checkNumber(Number new_value) {
		if(zeroValidationProperty.getValue().equals(TZeroValidation.GREATHER_THAN_ZERO)){
			if(new_value.doubleValue()<=0.0)
				applyEffect();
			else
				removeEffect();
		}
		
		if(zeroValidationProperty.getValue().equals(TZeroValidation.MINOR_THAN_ZERO)){
			if(new_value.doubleValue()>=0.0)
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
