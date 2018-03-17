package com.tedros.fxapi.control;

import javafx.collections.ObservableList;
import javafx.scene.control.RadioButton;


public abstract class TRadioButtonGroup extends TRequiredToggleGroup {

	public void addRadioButton(final RadioButton radioButton){
		radioButton.setToggleGroup(this);
		getBox().getChildren().add(radioButton);
	}
	
	public void addRadioButton(final ObservableList<RadioButton> radioButtons){
		for (RadioButton radioButton : radioButtons)
			addRadioButton(radioButton);	
	}
	

}
