package com.tedros.fxapi.layout;

import javafx.scene.Node;
import javafx.scene.layout.FlowPane;

public class TFlowPane extends FlowPane{
	
	public TFlowPane() {
		
	}
	
	public TFlowPane(double spaceBetweenColumns, double spaceBetweenRows) {
		setVgap(spaceBetweenRows);
		setHgap(spaceBetweenColumns);
	}
	
	
	public void setContent(Node node){
		getChildren().add(node);
	}

}
