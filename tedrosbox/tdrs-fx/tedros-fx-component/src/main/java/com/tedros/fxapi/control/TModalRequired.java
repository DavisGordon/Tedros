/**
 * 
 */
package com.tedros.fxapi.control;

import com.tedros.app.component.ITComponent;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.effect.TEffectUtil;
import com.tedros.fxapi.presenter.model.TModelView;

import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.scene.control.ListView;
import javafx.scene.effect.Effect;
import javafx.scene.layout.VBox;

/**
 * @author Davis Gordon
 *
 */
public abstract class TModalRequired extends VBox implements ITField, ITComponent{

	private String t_componentId;
	private SimpleBooleanProperty requirementAccomplishedProperty;
	private Effect requiredEffect;
    private ListChangeListener requiredListener;
    private SimpleBooleanProperty requiredProperty; 
    
    @Override
	public Observable tValueProperty() {
		return gettSelectedItems();
	}
    
	public void setRequired(boolean required){
    	
		if(this.requiredProperty == null)
			this.requiredProperty = new SimpleBooleanProperty();
		
		this.requiredProperty.addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean new_value) {
				if(new_value){
					gettListView().getStyleClass().add("required");
					buildRequiredEffect();
		    		buildNotNullListener();
		    		buildRequirementAccomplishedProperty();
		    		gettSelectedItems().addListener(requiredListener);
					if(gettSelectedItems().isEmpty())
						applyEffect();
					else
						removeEffect();
		    	}else{
		    		requirementAccomplishedProperty = null;
		    		removeEffect();
		    		gettListView().getStyleClass().remove("required");
		    		if(requiredListener!=null)
		    			gettSelectedItems().removeListener(requiredListener);
		    	}
			}
		});
		this.requiredProperty.set(required);
    }

	public abstract ListView<TModelView> gettListView();

	public abstract ITObservableList<TModelView> gettSelectedItems();
	
	 private void buildRequiredEffect(){
			if(requiredEffect == null)
				requiredEffect = TEffectUtil.buildNotNullFieldFormEffect();
		}
   
	private void buildNotNullListener(){
		if(requiredListener == null)
			requiredListener = o -> {
				if(o.getList().isEmpty())
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
		gettListView().setEffect(null);
		gettListView().getStyleClass().remove("required-not-ok");
		gettListView().getStyleClass().add("required-ok");
	}

	private void applyEffect() {
		if(requirementAccomplishedProperty!=null)
			requirementAccomplishedProperty.set(false);
		gettListView().setEffect(requiredEffect);
		gettListView().getStyleClass().remove("required-ok");
		gettListView().getStyleClass().add("required-not-ok");
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
		gettListView().setStyle(style);
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
