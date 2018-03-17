package com.tedros.fxapi.layout;

import com.tedros.fxapi.control.TScrollPane;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

public class TBorderPane extends BorderPane {

	public TBorderPane() {
		
	}
	
	public enum TBorderPosition{
		TOP, RIGHT, BOTTOM, LEFT, CENTER;
	}
	
	final public void setContent(TBorderPosition tBorderPosition, Node node){
		setPosition(tBorderPosition, node);
	}
	
	final public void setContentWithMargin(TBorderPosition tBorderPosition, Node node){
		setPosition(tBorderPosition, node);
		setDefaultMargin(node);
	}
	
	final public void setContentWithMargin(TBorderPosition tBorderPosition, Node node, double margin){
		setPosition(tBorderPosition, node);
		setMargin(node, margin);
	}

	
	final public void setContentWithMargin(TBorderPosition tBorderPosition, Node node, double top, double right, double bottom, double left){
		setPosition(tBorderPosition, node);
		setCustomMargin(node, top, right, bottom, left);
	}
	// center margin and alignment
	final public void setContentWithMarginAndAlignment(TBorderPosition tBorderPosition, Node node, Pos pos){
		setPosition(tBorderPosition, node);
		setDefaultMargin(node);
		setCustomAlignment(node, pos);
	}
		
	final public void setContentWithMarginAndAlignment(TBorderPosition tBorderPosition, Node node, double margin){
		setPosition(tBorderPosition, node);
		setMargin(node, margin);
	}
	
	final public void setContentWithMarginAndAlignment(TBorderPosition tBorderPosition, Node node, double top, double right, double bottom, double left, Pos pos){
		setPosition(tBorderPosition, node);
		setCustomMargin(node, top, right, bottom, left);
		setCustomAlignment(node, pos);
	}
		
	
	final public void setContentWithTScrollPane(TBorderPosition tBorderPosition, Node node){
		setPosition(tBorderPosition, new TScrollPane(node));
	}
		
	private void setDefaultMargin(Node node) {
		BorderPane.setMargin(node, new Insets(10));
	}
	
	private void setMargin(Node node, double margin) {
		BorderPane.setMargin(node, new Insets(margin));
	}

	private void setCustomMargin(Node node, double top, double right,
			double bottom, double left) {
		BorderPane.setMargin(node, new Insets(top, right, bottom, left));
	}
	
	private void setCustomAlignment(Node node, Pos pos) {
		BorderPane.setAlignment(node, pos);	
	}
	
	private void setPosition(TBorderPosition tBorderPosition, Node node) {
		switch (tBorderPosition) {
		case CENTER:
			setCenter(node);
			break;
		case TOP:
			setTop(node);
			break;
		case RIGHT:
			setRight(node);
			break;
		case BOTTOM:
			setBottom(node);
			break;
		case LEFT:
			setLeft(node);
			break;
		}
	}
	
}
