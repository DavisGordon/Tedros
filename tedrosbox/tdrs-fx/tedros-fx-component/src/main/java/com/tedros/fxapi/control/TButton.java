package com.tedros.fxapi.control;

import javafx.scene.control.Button;

public class TButton extends Button {

	public TButton() {
		setId("t-button");
	}
	
	public TButton(String text) {
		setText(text);
		setId("t-button");
	}
	
	public TButton(String id, String text) {
		setText(text);
		setId(id);
		getStyleClass().add("t-button");
	}
	
	
	
}
