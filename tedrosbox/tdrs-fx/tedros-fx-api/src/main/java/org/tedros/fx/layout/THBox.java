package org.tedros.fx.layout;

import javafx.scene.Node;
import javafx.scene.layout.HBox;

public class THBox extends HBox {
	
	public THBox() {
		
	}
	
	public THBox(double spaceBetweenContent) {
		super(spaceBetweenContent);
	}
	
	final public void setContent(Node node){
		getChildren().add(node);
	}
	
}
