package com.tedros.fxapi.form;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tedros.fxapi.annotation.control.TLabelPosition;
import com.tedros.fxapi.control.ITField;

/**
 * <pre>
 * A field box is a layout componet to arrange the control and his label.
 * 
 * Example:
 * Label label = (Label) fieldBox.gettLabel();
 * TTextField textField = (TTextField) fieldBox.gettControl();   
 * </pre>
 * 
 * @author Davis Gordon
 * */
public class TFieldBox extends StackPane implements ITField {

	private Node label;
	private Node control;
	private String controlFieldName;
	
	/**
	 * Initialize the field box
	 * */
	public TFieldBox(String controlFieldName, Node label, Node control, TLabelPosition position) {
		this.controlFieldName = controlFieldName;
		this.label = label;
		this.control = control;
		
		if(this.label==null){
			getChildren().add(control);
			return;
		}
		
		if(position ==  null)
			position = TLabelPosition.TOP;
		
		if(position == TLabelPosition.TOP || position == TLabelPosition.DEFAULT){
			try{
				VBox box = new VBox(3);
				VBox.setVgrow(this.control, Priority.ALWAYS);
				box.setAlignment(Pos.CENTER_LEFT);
				box.getChildren().addAll(this.label, this.control);
				getChildren().add(box);
			}catch(NullPointerException e){
				e.printStackTrace();
			}
		}
		
		if(position == TLabelPosition.BOTTOM){
			VBox box = new VBox(3);
			VBox.setVgrow(this.control, Priority.ALWAYS);
			box.setAlignment(Pos.CENTER_LEFT);
			box.getChildren().addAll(this.control, this.label);
			getChildren().add(box);
		}
		
		if(position == TLabelPosition.RIGHT){
			HBox box = new HBox(8);
			HBox.setHgrow(this.control, Priority.ALWAYS);
			box.setAlignment(Pos.CENTER_LEFT);
			box.getChildren().addAll(this.control, this.label);
			getChildren().add(box);
		}
		
		if(position == TLabelPosition.LEFT){
			HBox box = new HBox(8);
			HBox.setHgrow(this.control, Priority.ALWAYS);
			box.setAlignment(Pos.CENTER_LEFT);
			box.getChildren().addAll(this.label, this.control);
			getChildren().add(box);
		}
		
	}
	
	/**
	 * <pre>
	 * Return the {@link Node} that represents the label, in the most of case they return a {@link Label},
	 * but custom components can return other {@link Node}.
	 * 
	 * Example:
	 * Label label = (Label) fieldBox.gettLabel();
	 * </pre>
	 * 
	 * @return {@link Node}
	 * */
	public Node gettLabel() {
		return label;
	}

	/**
	 * <pre>
	 * Return the {@link Node} that represents the control.
	 * 
	 * Example:
	 * TTextField textField = (TTextField) fieldBox.gettControl();   
	 * </pre>
	 * 
	 * @return {@link Node}
	 * */
	public Node gettControl() {
		return control;
	}
		
	/**
	 * <pre>
	 * Return the field name in the model view which generates the control.   
	 * </pre>
	 * 
	 * @return {@link String}
	 * */
	public String gettControlFieldName() {
		return controlFieldName;
	}

	/**
	 * <pre>
	 * Set style in the control.
	 * </pre>
	 * */
	@Override
	public void settFieldStyle(String style) {
		if(control!=null){
			if(control instanceof ITField){
				((ITField)control).settFieldStyle(style);
			}
		}
	}
	
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, true);
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, true);
	}
	
	@Override
	public String toString() {
		
		StringBuilder str = new StringBuilder("TFieldBox[");
		if(controlFieldName!=null)
			str.append("FIELD: "+controlFieldName+", ");
		if(label!=null)
			str.append("LABEL: "+label.toString()+", ");
		if(control!=null)
			str.append("CONTROL: "+control.toString()+", ");
		str.append("];");
		return str.toString();
	}
}
