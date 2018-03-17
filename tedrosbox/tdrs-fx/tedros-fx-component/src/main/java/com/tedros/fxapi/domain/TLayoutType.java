package com.tedros.fxapi.domain;

import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * <pre>
 * A layout type domain
 * 
 * HBOX sets a horizontal layout
 * VBOX sets a vertical layout
 * FLOWPANE sets a horizontal and vertical layout. 
 * </pre>
 * @author Davis Gordon
 * */
public enum TLayoutType {
	
	HBOX (HBox.class), 
	VBOX (VBox.class), 
	FLOWPANE (FlowPane.class);
	
	private Class<? extends Pane> value;
	
	private TLayoutType(Class<? extends Pane> type){
		value = type;
	}
	
	public Class<? extends Pane> getValue() {
		return value;
	}

}
