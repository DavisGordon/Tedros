/**
 * 
 */
package org.tedros.fx.control;

import org.tedros.app.component.ITComponent;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.model.TModelView;

import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

/**
 * @author Davis Gordon
 *
 */
public abstract class TRequiredDetailField extends StackPane implements ITRequirable, ITComponent{

	private String t_componentId;
	private TRequiredFieldHelper helper;
	private SimpleObjectProperty<Node> tRequiredNodeProperty = new SimpleObjectProperty<>();
	
	public TRequiredDetailField() {
		this.helper = new TRequiredFieldHelper(this, false);
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

	/**
	 * @return the tRequiredNodeProperty
	 */
	public SimpleObjectProperty<Node> tRequiredNodeProperty() {
		return tRequiredNodeProperty;
	}
}
