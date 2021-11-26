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
import javafx.scene.Node;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public abstract class TRequiredToggleGroup extends ToggleGroup implements ITField, ITComponent {

	private String t_componentId; 
	private TRequiredFieldHelper helper;
	
	public TRequiredToggleGroup() {//need to set box style
		this.helper = new TRequiredFieldHelper(getBox(), tValueProperty(), true);
	}
	
	@Override
	@SuppressWarnings({ "unchecked" })
	public <T extends Observable> T tValueProperty() {
		return (T) selectedToggleProperty();
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
    
    abstract Pane getBox();
    
	
	@Override
	public void settFieldStyle(String style) {
		if(getBox()!=null)
			for(Node node : getBox().getChildren())
				node.setStyle(style);
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
