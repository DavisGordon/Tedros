/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 03/12/2013
 */
package org.tedros.fx.control;

import org.tedros.app.component.ITComponent;

import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.TextField;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public abstract class TRequiredTextField extends TextField implements ITRequirable, ITComponent {

	
	private String t_componentId;
	private TRequiredFieldHelper helper;
	private SimpleObjectProperty<Node> tRequiredNodeProperty = new SimpleObjectProperty<>();
	
	public TRequiredTextField() {
		this.helper = new TRequiredFieldHelper(this, true);
	}
	
	@Override
	@SuppressWarnings({ "unchecked"})
	public <T extends Observable> T tValueProperty() {
		return (T) textProperty();
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
		return (helper.requirementAccomplishedProperty()==null) 
				? true 
						: helper.requirementAccomplishedProperty().get(); 
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

	/**
	 * @return the tRequiredNodeProperty
	 */
	public SimpleObjectProperty<Node> tRequiredNodeProperty() {
		return tRequiredNodeProperty;
	}
	
}
