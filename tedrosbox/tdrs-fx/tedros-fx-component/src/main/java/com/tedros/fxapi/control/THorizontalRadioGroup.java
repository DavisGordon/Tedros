/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 04/12/2013
 */
package com.tedros.fxapi.control;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class THorizontalRadioGroup extends HBox implements ITField{

	final private THorizontalRadioGroup box = this;
	
	private TRadioButtonGroup radioButtonGroup = new TRadioButtonGroup() {
		@Override
		Pane getBox() {
			return box;
		}		
	};
	
	public THorizontalRadioGroup() {
		applyRadioGroupStyleClass();
	}
	
	public THorizontalRadioGroup(ObservableList<RadioButton> radioButtons) {
		applyRadioGroupStyleClass();
		applyRadioButtonStyleClass(radioButtons);
		radioButtonGroup.addRadioButton(radioButtons);
	}
	
	public void addRadioButton(final RadioButton radioButton){
		applyRadioButtonStyleClass(radioButton);
		radioButtonGroup.addRadioButton(radioButton);
	}
	
	public void addRadioButton(final ObservableList<RadioButton> radioButtons){
		applyRadioButtonStyleClass(radioButtons);
		radioButtonGroup.addRadioButton(radioButtons);
	}
	
	public void setRequired(boolean required){
		radioButtonGroup.setRequired(required);
	}
	
	public SimpleBooleanProperty requiredProperty() {
		return radioButtonGroup.requiredProperty();
	}
	
	public SimpleBooleanProperty requirementAccomplishedProperty() {
		return radioButtonGroup.requirementAccomplishedProperty();
	}
	
	public boolean isRequirementAccomplished(){
		return radioButtonGroup.isRequirementAccomplished(); 
	}
	
	public ReadOnlyObjectProperty<Toggle> selectedToggleProperty(){
		return radioButtonGroup.selectedToggleProperty();
	}
	
	public Toggle getSelectedToggle(){
		return radioButtonGroup.getSelectedToggle();
	}
	
	public void settFieldStyle(String style){
		radioButtonGroup.settFieldStyle(style);
	}
	
	private void applyRadioButtonStyleClass(ObservableList<RadioButton> radioButtons) {
		for (RadioButton radioButton : radioButtons)
			applyRadioButtonStyleClass(radioButton);
	}

	private void applyRadioButtonStyleClass(RadioButton radioButton) {
		radioButton.setId("t-form-control-label");
	}
	
	private void applyRadioGroupStyleClass() {
		getStyleClass().add("box-input");
	}
	
}
