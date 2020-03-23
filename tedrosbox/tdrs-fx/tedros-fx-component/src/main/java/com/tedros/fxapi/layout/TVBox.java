package com.tedros.fxapi.layout;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class TVBox extends VBox {

	public TVBox() {
		
	}
	
	public TVBox(double spaceBetweenContent) {
		super(spaceBetweenContent);
	}
	
	final public void setContent(Node node){
		getChildren().add(node);
	}
	
}
