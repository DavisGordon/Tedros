/**
 * 
 */
package com.tedros.fxapi.control;

import com.tedros.app.component.ITComponent;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.presenter.dynamic.view.TDynaView;
import com.tedros.fxapi.presenter.model.TModelView;

import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.scene.layout.StackPane;

/**
 * @author Davis Gordon
 *
 */
public abstract class TDetailFieldRequired extends StackPane implements ITField, ITComponent{

	private String t_componentId;
	private SimpleBooleanProperty requirementAccomplishedProperty;
	@SuppressWarnings("rawtypes")
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
			@SuppressWarnings("unchecked")
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean new_value) {
				if(new_value){
					gettView().getStyleClass().add("required-detail");
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
		    		gettView().getStyleClass().remove("required-detail");
		    		if(requiredListener!=null)
		    			gettSelectedItems().removeListener(requiredListener);
		    	}
			}
		});
		this.requiredProperty.set(required);
    }

	@SuppressWarnings("rawtypes")
	public abstract TDynaView gettView();

	@SuppressWarnings("rawtypes")
	public abstract ITObservableList<TModelView> gettSelectedItems();
   
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
		gettView().getStyleClass().remove("required-detail-not-ok");
		gettView().getStyleClass().add("required-detail-ok");
	}

	private void applyEffect() {
		if(requirementAccomplishedProperty!=null)
			requirementAccomplishedProperty.set(false);
		gettView().getStyleClass().remove("required-detail-ok");
		gettView().getStyleClass().add("required-detail-not-ok");
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
		gettView().setStyle(style);
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
