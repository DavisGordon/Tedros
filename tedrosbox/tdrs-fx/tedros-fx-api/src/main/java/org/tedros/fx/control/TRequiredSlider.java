/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 03/12/2013
 */
package org.tedros.fx.control;

import org.tedros.app.component.ITComponent;
import org.tedros.fx.domain.TValidateNumber;
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
    private SimpleObjectProperty<TValidateNumber> tValidateProperty;
	private String t_componentId; 
	
	@SuppressWarnings("unchecked")
	@Override
	public Observable tValueProperty() {
		return valueProperty();
	}
    
	public void setValidate(TValidateNumber validate){
    	
		if(this.tValidateProperty == null)
			this.tValidateProperty = new SimpleObjectProperty<>();
		
		this.tValidateProperty.addListener(new ChangeListener<TValidateNumber>() {
			@Override
			public void changed(ObservableValue<? extends TValidateNumber> arg0, TValidateNumber arg1, TValidateNumber new_value) {
				if(!new_value.equals(TValidateNumber.NONE)){
					getStyleClass().add("required");
		    		buildRequiredEffect();
		    		buildValidateListener();
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
		
		this.tValidateProperty.set(validate);
    }
    
    private void buildRequiredEffect(){
		if(requiredEffect == null)
			requiredEffect = TEffectUtil.buildNotNullFieldFormEffect();
	}
	
	private void buildValidateListener(){
		if(requiredListener == null)
			requiredListener = (a,o,n)->checkNumber(n);
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
	
	public SimpleObjectProperty<TValidateNumber> tValidateProperty() {
		return tValidateProperty;
	}
	
	public SimpleBooleanProperty requirementAccomplishedProperty() {
		return requirementAccomplishedProperty;
	}
	
	public boolean isRequirementAccomplished(){
		return (requirementAccomplishedProperty==null) ? true : requirementAccomplishedProperty.get() ; 
	}

	private void checkNumber(Number new_value) {
		if(tValidateProperty.getValue().equals(TValidateNumber.GREATHER_THAN_ZERO)){
			if(new_value.doubleValue()<=0.0)
				applyEffect();
			else
				removeEffect();
		}
		
		if(tValidateProperty.getValue().equals(TValidateNumber.MINOR_THAN_ZERO)){
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
