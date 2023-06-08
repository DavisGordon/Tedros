/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 04/12/2013
 */
package org.tedros.fx.control;

import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class TVerticalRadioGroup extends VBox implements ITField{

	final private TVerticalRadioGroup box = this;
	
	private TRadioButtonGroup radioButtonGroup = new TRadioButtonGroup() {
		@Override
		public Pane getBox() {
			return box;
		}
	};
	
	public TVerticalRadioGroup() {
		applyRadioGroupStyleClass();
	}
	
	public TVerticalRadioGroup(ObservableList<RadioButton> radioButtons) {
		applyRadioGroupStyleClass();
		applyRadioButtonStyleClass(radioButtons);
		radioButtonGroup.addRadioButton(radioButtons);
	}
	
	@Override
	public Observable tValueProperty() {
		return selectedToggleProperty();
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
	
	public TRadioButtonGroup getTogleeGroup() {
		return this.radioButtonGroup;
	}
	
	public Toggle getSelectedToggle(){
		return radioButtonGroup.getSelectedToggle();
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
		super.setSpacing(8);
	}

	@Override
	public void settFieldStyle(String style) {
		radioButtonGroup.settFieldStyle(style);
	}
	
}
