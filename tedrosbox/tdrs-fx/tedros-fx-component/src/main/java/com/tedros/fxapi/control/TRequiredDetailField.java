/**
 * 
 */
package com.tedros.fxapi.control;

import com.tedros.app.component.ITComponent;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.presenter.model.TModelView;

import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

/**
 * @author Davis Gordon
 *
 */
public abstract class TRequiredDetailField extends StackPane implements ITField, ITComponent{

	private String t_componentId;
	private TRequiredFieldHelper helper;
	
	public TRequiredDetailField() {
		this.helper = new TRequiredFieldHelper(gettComponent(), tValueProperty(), false);
	}
    
    @Override
	public Observable tValueProperty() {
		return gettSelectedItems();
	}
 
	public abstract Node gettComponent();

	@SuppressWarnings("rawtypes")
	public abstract ITObservableList<TModelView> gettSelectedItems();
    
	public void setRequired(boolean required){
    	this.helper.setRequired(required);
    }
	
	public SimpleBooleanProperty requiredProperty() {
		return helper.requiredProperty();
	}
	
	public SimpleBooleanProperty requirementAccomplishedProperty() {
		return helper.requirementAccomplishedProperty();
	}
	
	public boolean isRequirementAccomplished(){
		return helper.isRequirementAccomplished() ; 
	}
	
	@Override
	public void settFieldStyle(String style) {
		gettComponent().setStyle(style);
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
