/**
 * 
 */
package org.tedros.fx.control;

import org.tedros.app.component.ITComponent;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

/**
 * @author Davis Gordon
 *
 */
public abstract class TRequiredStackedComponent extends StackPane implements ITRequirable, ITComponent{

	private String t_componentId;
	private TRequiredFieldHelper helper;
	private SimpleObjectProperty<Node> tRequiredNodeProperty = new SimpleObjectProperty<>();
    
    public TRequiredStackedComponent() {
		this.helper = new TRequiredFieldHelper(this,  true);
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
