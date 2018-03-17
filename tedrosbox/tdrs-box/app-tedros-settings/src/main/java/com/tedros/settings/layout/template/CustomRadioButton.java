package com.tedros.settings.layout.template;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.StackPaneBuilder;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CircleBuilder;

/**
 * Custom button, to resemble like a radio button.
 * @author Sai.Dandem
 *
 */
public class CustomRadioButton extends Button{

	private SimpleBooleanProperty selected = new SimpleBooleanProperty();
	
	private StackPane greenButton = StackPaneBuilder.create().prefHeight(21).prefWidth(21).alignment(Pos.CENTER).build();
	private StackPane grayButton = StackPaneBuilder.create().prefHeight(21).prefWidth(21).alignment(Pos.CENTER).build();
	
	public CustomRadioButton(String text){
		super(text);
		getStyleClass().add("custom-radio-btn");
		buildGreenButton();
		buildGrayButton();
	
		setGraphic(grayButton);
		selected.addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				if(arg2){
					setGraphic(greenButton);
				}else{
					setGraphic(grayButton);
				}
			}
		});
		
	}

	public void setSelected(boolean selected){
		this.selected.set(selected);
	}
	
	public boolean getSelected(){
		return this.selected.get();
	}
	
	private void buildGreenButton() {
		Circle c1 = CircleBuilder.create().styleClass("radio-selected-c1").radius(10).build();
		Circle c2 = CircleBuilder.create().styleClass("radio-selected-c2").radius(8).build();
		Circle c3 = CircleBuilder.create().styleClass("radio-selected-c3").radius(5).build();
		greenButton.getChildren().addAll(c1,c2,c3);
	}
	
	private void buildGrayButton() {
		Circle c1 = CircleBuilder.create().styleClass("radio-unselected-c1").radius(10).build();
		Circle c2 = CircleBuilder.create().styleClass("radio-unselected-c2").radius(8).build();
		Circle c3 = CircleBuilder.create().styleClass("radio-unselected-c3").radius(5).build();
		grayButton.getChildren().addAll(c1,c2,c3);
	}
	
	
}
