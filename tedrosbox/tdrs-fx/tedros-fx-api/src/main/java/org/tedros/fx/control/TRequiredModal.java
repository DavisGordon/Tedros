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
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

/**
 * @author Davis Gordon
 *
 */
public abstract class TRequiredModal extends VBox implements ITRequirable, ITComponent{

	private String t_componentId;
	private TRequiredFieldHelper helper;
	private SimpleObjectProperty<Node> tRequiredNodeProperty = new SimpleObjectProperty<>();
    
    public TRequiredModal() {
		this.helper = new TRequiredFieldHelper(this,  true);
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

	/**
	 * @return the tRequiredNodeProperty
	 */
	public SimpleObjectProperty<Node> tRequiredNodeProperty() {
		return tRequiredNodeProperty;
	}
}
