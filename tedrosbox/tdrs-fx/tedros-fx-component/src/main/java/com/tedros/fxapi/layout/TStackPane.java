package com.tedros.fxapi.layout;

import com.tedros.fxapi.control.TScrollPane;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class TStackPane extends StackPane {
	
	
	public TStackPane() {
	
	}
	
	public TStackPane(Node node, boolean defaultPadding) {
		setContent(node);
		if(defaultPadding)
			setDefaultPadding();
	}
	
	
	public TStackPane(Node node, double top, double right, double bottom, double left, Pos pos) {
		setContent(node);
		setCustomPadding(top, right, bottom, left);
	}
	
	final public void setContent(Node node) {
		getChildren().add(node);
	}
	
	final public void setContentWithDefaultPadding(Node node){
		setContent(node);
		setDefaultPadding();
	}
	
	final public void setContentWithCustomPadding(Node node, double top, double right, double bottom, double left, Pos pos){
		setContent(node);
		setCustomPadding(top, right, bottom, left);
	}
		
	
	final public void setContentWithTScrollPane(Node node){
		setContent(new TScrollPane(node));
	}
		
	private void setDefaultPadding() {
		setPadding(new Insets(10));
	}
	
	private void setCustomPadding(double top, double right, double bottom, double left) {
		setPadding(new Insets(top, right, bottom, left));
	}
	
}
