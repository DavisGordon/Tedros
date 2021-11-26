/**
 * 
 */
package com.tedros.fxapi.control;

import com.tedros.core.module.TObjectRepository;
import com.tedros.fxapi.effect.TEffectUtil;

import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.effect.Effect;
import javafx.scene.layout.Pane;

/**
 * @author Davis Gordon
 *
 */
public class TRequiredFieldHelper {
	
	private static String LISTENER_KEY = "req-listenerKey";
	private static String REQUIRED_PANE_STYLE = "required-detail";
	private static String REQUIRED_DEFAULT_STYLE = "required";
	private static String REQUIRED_CHECKBOX_STYLE = "box-required";
	private Effect requiredEffect;
	private final String requiredStyle;
	private final Boolean addEffect;
	
	private final Node requiredNode;
	private final Observable observableValue;
	
	private SimpleBooleanProperty requirementAccomplishedProperty;
	private SimpleBooleanProperty requiredProperty; 
	 
    private TObjectRepository repo = new TObjectRepository();
    
	/**
	 * @param requiredNode
	 */
	public TRequiredFieldHelper(Node requiredNode, Observable observableValue, Boolean addEffect) {
		this.requiredNode = requiredNode;
		this.observableValue = observableValue;
		this.addEffect = addEffect;
		if(requiredNode instanceof Pane) {
			this.requiredStyle = REQUIRED_PANE_STYLE;
		}else if(requiredNode instanceof CheckBox) {
			this.requiredStyle = REQUIRED_CHECKBOX_STYLE;
		}else {
			this.requiredStyle = REQUIRED_DEFAULT_STYLE;
		}		
		buildRequiredEffect();
		buildNotNullListener();
		buildRequiredProperty();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void buildRequiredProperty() {
		this.requiredProperty = new SimpleBooleanProperty();
		ChangeListener<Boolean> rpchl = (a,b,n)->{
			if(n){
				this.requiredNode.getStyleClass().add(requiredStyle);
				buildRequirementAccomplishedProperty();
				if(observableValue instanceof ObservableValue)
					((ObservableValue)observableValue).addListener((ChangeListener)repo.get(LISTENER_KEY));
				else
					((ObservableList)observableValue).addListener((ListChangeListener)repo.get(LISTENER_KEY));
				validate();
	    	}else{
	    		requirementAccomplishedProperty = null;
	    		removeEffect();
	    		this.requiredNode.getStyleClass().remove(requiredStyle);
	    		if(observableValue instanceof ObservableValue)
					((ObservableValue)observableValue).removeListener((ChangeListener)repo.get(LISTENER_KEY));
				else
					((ObservableList)observableValue).removeListener((ListChangeListener)repo.get(LISTENER_KEY));
				
	    	}
		};
		repo.add("rpchl", rpchl);
		this.requiredProperty.addListener(new WeakChangeListener<>(rpchl));
	}

	/**
	 * 
	 */
	private void validate() {
		if(observableValue instanceof ObservableList) {
			if(TRequiredValidation.isRequired((ObservableList)observableValue))
				applyEffect();
			else
				removeEffect();
		}else {
			if(TRequiredValidation.isRequired(((ObservableValue)observableValue).getValue()))
				applyEffect();
			else
				removeEffect();
		}
	}

	public void setRequired(boolean required){
		this.requiredProperty.set(required);
    }
	
	private void buildRequiredEffect(){
		if(addEffect)
			requiredEffect = TEffectUtil.buildNotNullFieldFormEffect();
	}
	

	@SuppressWarnings("rawtypes")
	private void buildNotNullListener(){
		if(observableValue instanceof ObservableList){
			ListChangeListener x = c ->{
				if(TRequiredValidation.isRequired(c.getList()))
					applyEffect();
				else
					removeEffect();
			};
			this.repo.add(LISTENER_KEY, x);
		}else {
			ChangeListener x = (a,b,n) -> {
				if(TRequiredValidation.isRequired(n))
						applyEffect();
					else
						removeEffect();
			};
			this.repo.add(LISTENER_KEY, x);
		}
	}
	
	private void buildRequirementAccomplishedProperty(){
		if(requirementAccomplishedProperty == null)
			requirementAccomplishedProperty = new SimpleBooleanProperty();
	}
	
	private void removeEffect() {
		if(requirementAccomplishedProperty!=null)
			requirementAccomplishedProperty.set(true);
		this.requiredNode.getStyleClass().remove(this.requiredStyle+"-not-ok");
		this.requiredNode.getStyleClass().add(this.requiredStyle+"-ok");
		if(addEffect)
			this.requiredNode.setEffect(null);
	}

	private void applyEffect() {
		if(requirementAccomplishedProperty!=null)
			requirementAccomplishedProperty.set(false);
		this.requiredNode.getStyleClass().remove(this.requiredStyle+"-ok");
		this.requiredNode.getStyleClass().add(this.requiredStyle+"-not-ok");
		if(addEffect)
			this.requiredNode.setEffect(requiredEffect);
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
	
	public void dispose() {
		this.repo.clear();
	}
	
}
