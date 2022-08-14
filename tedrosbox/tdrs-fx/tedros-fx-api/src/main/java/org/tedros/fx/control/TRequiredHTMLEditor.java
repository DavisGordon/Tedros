/**
 * 
 */
package org.tedros.fx.control;

import org.tedros.app.component.ITComponent;

import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;

/**
 * @author Davis Gordon
 *
 */
public abstract class TRequiredHTMLEditor extends VBox implements ITRequirable, ITComponent{

	private String t_componentId;
	private TRequiredFieldHelper helper;
	private SimpleObjectProperty<Node> tRequiredNodeProperty = new SimpleObjectProperty<>();
	
	public TRequiredHTMLEditor() {
		this.helper = new TRequiredFieldHelper(this, true);
	}
	
	@Override
	@SuppressWarnings({ "unchecked" })
	public <T extends Observable> T tValueProperty() {
		return (T) tHTMLProperty();
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

	public abstract SimpleStringProperty tHTMLProperty();
	
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

	/**
	 * @return the tRequiredNodeProperty
	 */
	public SimpleObjectProperty<Node> tRequiredNodeProperty() {
		return tRequiredNodeProperty;
	}
}
