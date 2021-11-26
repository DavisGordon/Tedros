/**
 * 
 */
package com.tedros.fxapi.control;

import com.tedros.app.component.ITComponent;
import com.tedros.ejb.base.model.ITFileBaseModel;

import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

/**
 * @author Davis Gordon
 *
 */
public abstract class TRequiredFileField extends StackPane implements ITField, ITComponent{

	private String t_componentId;
	private TRequiredFieldHelper helper;
    
    public TRequiredFileField() {
		this.helper = new TRequiredFieldHelper(gettComponent(), tValueProperty(), false);
	}
	
    @Override
	@SuppressWarnings({ "unchecked"})
	public <T extends Observable> T tValueProperty() {
		return (T) gettSelectedProperty();
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
    
	public abstract Node gettComponent();

	public abstract SimpleObjectProperty<ITFileBaseModel> gettSelectedProperty();
   
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
