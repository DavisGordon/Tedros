/**
 * 
 */
package com.tedros.fxapi.control;

import com.tedros.app.component.ITComponent;

import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;

/**
 * @author Davis Gordon
 *
 */
public abstract class TRequiredHTMLEditor extends VBox implements ITField, ITComponent{

	private String t_componentId;
	private TRequiredFieldHelper helper;
	
	public TRequiredHTMLEditor() {
		this.helper = new TRequiredFieldHelper(gettHTMLEditor(), tValueProperty(), true);
	}
	
	@Override
	@SuppressWarnings({ "unchecked", "hiding" })
	public <T extends Observable> T tValueProperty() {
		return (T) gettHTMLProperty();
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
    
	public abstract HTMLEditor gettHTMLEditor();

	public abstract SimpleStringProperty gettHTMLProperty();
	
	@Override
	public void settFieldStyle(String style) {
		gettHTMLEditor().setStyle(style);
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
