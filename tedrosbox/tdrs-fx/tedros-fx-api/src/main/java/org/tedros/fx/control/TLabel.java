package org.tedros.fx.control;

import javafx.scene.control.Label;

public class TLabel extends Label {
	
	public TLabel() {
		setId("t-label");
	}
	
	public TLabel(String text) {
		super(text);
		setId("t-label");
	}

}
