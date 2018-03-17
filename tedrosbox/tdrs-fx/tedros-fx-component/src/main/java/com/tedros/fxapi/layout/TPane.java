package com.tedros.fxapi.layout;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import com.tedros.fxapi.control.TScrollPane;

public class TPane extends Pane {

	public TPane() {
	
	}
	
	public TPane(boolean autosize) {
		if(autosize)
			autosize();
	}
	
	public TPane(double width, double height) {
		setPrefSize(width, height);
		if(width != Double.MAX_VALUE && height != Double.MAX_VALUE){
			setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
			setMinSize(USE_PREF_SIZE, USE_PREF_SIZE);
		}
	}
		
	final public void setContentWithDefaultPadding(Node node){
		setDefaultPadding();
		setContent(node);
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
	
	private void setContent(Node node) {
		getChildren().add(node);
	}
	
}
