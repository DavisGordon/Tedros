/**
 * 
 */
package org.tedros.fx.control;

import org.tedros.core.repository.TRepository;
import org.tedros.fx.effect.TEffectUtil;

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
	private String requiredStyle;
	private final Boolean addEffect;
	private SimpleBooleanProperty requirementAccomplishedProperty;
	private SimpleBooleanProperty requiredProperty; 
	
	private ITRequirable component;
	 
    private TRepository repo = new TRepository();
    
    public TRequiredFieldHelper(String requiredStyle, ITRequirable component, Boolean addEffect) {
		this.component = component;
		this.addEffect = addEffect;
		this.requiredStyle = requiredStyle;
		ChangeListener<Node> l = (a,o,n)->{
			buildNotNullListener();
			buildRequiredProperty();
		};
		repo.add("trnp", l);
		this.component.tRequiredNodeProperty().addListener(new WeakChangeListener<>(l));
		
		buildRequiredEffect();
	}
    
	public TRequiredFieldHelper(ITRequirable component, Boolean addEffect) {
		this.component = component;
		this.addEffect = addEffect;
		
		ChangeListener<Node> l = (a,o,n)->{
			if(n==null) 
				return;
			if(n instanceof Pane) {
				this.requiredStyle = REQUIRED_PANE_STYLE;
			}else if(n instanceof CheckBox) {
				this.requiredStyle = REQUIRED_CHECKBOX_STYLE;
			}else {
				this.requiredStyle = REQUIRED_DEFAULT_STYLE;
			}
			buildNotNullListener();
			buildRequiredProperty();
		};
		repo.add("trnp", l);
		this.component.tRequiredNodeProperty().addListener(new WeakChangeListener<>(l));
		
		buildRequiredEffect();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void buildRequiredProperty() {
		this.requiredProperty = new SimpleBooleanProperty();
		ChangeListener<Boolean> rpchl = (a,b,n)->{
			if(n){
				component.tRequiredNodeProperty().getValue().getStyleClass().add(requiredStyle);
				buildRequirementAccomplishedProperty();
				if(component.tValueProperty() instanceof ObservableValue)
					((ObservableValue)component.tValueProperty()).addListener((ChangeListener)repo.get(LISTENER_KEY));
				else
					((ObservableList)component.tValueProperty()).addListener((ListChangeListener)repo.get(LISTENER_KEY));
				validate();
	    	}else{
	    		requirementAccomplishedProperty = null;
	    		removeEffect();
	    		component.tRequiredNodeProperty().getValue().getStyleClass().remove(requiredStyle);
	    		if(component.tValueProperty() instanceof ObservableValue)
					((ObservableValue)component.tValueProperty()).removeListener((ChangeListener)repo.get(LISTENER_KEY));
				else
					((ObservableList)component.tValueProperty()).removeListener((ListChangeListener)repo.get(LISTENER_KEY));
				
	    	}
		};
		repo.add("rpchl", rpchl);
		this.requiredProperty.addListener(new WeakChangeListener<>(rpchl));
	}

	/**
	 * 
	 */
	@SuppressWarnings("rawtypes")
	private void validate() {
		if(component.tValueProperty() instanceof ObservableList) {
			if(TRequiredValidation.isRequired((ObservableList)component.tValueProperty()))
				applyEffect();
			else
				removeEffect();
		}else {
			if(TRequiredValidation.isRequired(((ObservableValue)component.tValueProperty()).getValue()))
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
		if(component.tValueProperty() instanceof ObservableList){
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
		component.tRequiredNodeProperty().getValue().getStyleClass().remove(this.requiredStyle+"-not-ok");
		component.tRequiredNodeProperty().getValue().getStyleClass().add(this.requiredStyle+"-ok");
		if(addEffect)
			component.tRequiredNodeProperty().getValue().setEffect(null);
	}

	private void applyEffect() {
		if(requirementAccomplishedProperty!=null)
			requirementAccomplishedProperty.set(false);
		component.tRequiredNodeProperty().getValue().getStyleClass().remove(this.requiredStyle+"-ok");
		component.tRequiredNodeProperty().getValue().getStyleClass().add(this.requiredStyle+"-not-ok");
		if(addEffect)
			component.tRequiredNodeProperty().getValue().setEffect(requiredEffect);
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
