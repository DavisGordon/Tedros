/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 03/12/2013
 */
package com.tedros.fxapi.control;

import com.tedros.app.component.ITComponent;

import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.ListView;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public abstract class TRequiredListView<T> extends ListView<T> implements ITField, ITComponent {

	private String t_componentId;
	private TRequiredFieldHelper helper;
	
	public TRequiredListView() {
		this.helper = new TRequiredFieldHelper(this, tValueProperty(), true);
	}
	
	@Override
	@SuppressWarnings({ "unchecked", "hiding" })
	public <T extends Observable> T tValueProperty() {
		return (T) super.getSelectionModel().getSelectedItems();
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
