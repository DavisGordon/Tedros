package org.tedros.api.form;

import javafx.beans.Observable;
import javafx.scene.Node;
import javafx.scene.control.Label;

public interface ITFieldBox {

	Observable tValueProperty();

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
	Node gettLabel();

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
	Node gettControl();

	/**
	 * <pre>
	 * Return the field name in the model view which generates the control.   
	 * </pre>
	 * 
	 * @return {@link String}
	 * */
	String gettControlFieldName();

	/**
	 * <pre>
	 * Set style in the control.
	 * </pre>
	 * */
	void settFieldStyle(String style);

}