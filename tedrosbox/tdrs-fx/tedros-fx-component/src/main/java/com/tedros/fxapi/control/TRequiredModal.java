/**
 * 
 */
package com.tedros.fxapi.control;

import com.tedros.app.component.ITComponent;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.presenter.model.TModelView;

import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

/**
 * @author Davis Gordon
 *
 */
public abstract class TRequiredModal extends VBox implements ITField, ITComponent{

	private String t_componentId;
	private TRequiredFieldHelper helper;
    
    public TRequiredModal() {
		this.helper = new TRequiredFieldHelper(gettListView(), tValueProperty(), true);
	}
    
    @Override
	@SuppressWarnings({ "unchecked" })
	public <T extends Observable> T tValueProperty() {
		return (T) gettSelectedItems();
	}
    
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
	
    @SuppressWarnings("rawtypes")
	public abstract ListView<TModelView> gettListView();

	@SuppressWarnings("rawtypes")
	public abstract ITObservableList<TModelView> gettSelectedItems();
	
	
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
