package com.tedros.fxapi.annotation.control;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javafx.beans.property.Property;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import com.tedros.fxapi.annotation.TAnnotationDefaultValue;
import com.tedros.fxapi.annotation.parser.ITAnnotationParser;
import com.tedros.fxapi.annotation.parser.TVBoxParser;
import com.tedros.fxapi.annotation.parser.TVerticalRadioGroupParser;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TInsets;
import com.tedros.fxapi.annotation.scene.layout.TRegion;
import com.tedros.fxapi.builder.ITControlBuilder;
import com.tedros.fxapi.builder.TVerticalRadioGroupBuilder;

/**
 * <pre>
 * Build a {@link com.tedros.fxapi.control.TVerticalRadioGroup} component;
 * </pre>
 *
 * @author Davis Gordon
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TVerticalRadioGroup {
	
	/**
	 *<pre>
	 * The builder of type {@link ITControlBuilder} for this component.
	 * 
	 *  Default value: {@link TVerticalRadioGroupBuilder}
	 *</pre> 
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITControlBuilder<com.tedros.fxapi.control.TVerticalRadioGroup, Property>> builder() 
			default TVerticalRadioGroupBuilder.class;
	
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TVerticalRadioGroupParser.class, TVBoxParser.class}
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITAnnotationParser>[] parser() 
	default {TVerticalRadioGroupParser.class, TVBoxParser.class};
	
	
	/**
	 * <pre>
	 * The {@link Node} settings.
	 * </pre>
	 * */
	public TNode node() default @TNode(parse = false);
	
	/**
	 * <pre>
	 * The {@link Region} settings.
	 * </pre>
	 * */
	public TRegion region() default @TRegion(parse = false);
	
	/**
	* <pre>
	* {@link VBox} Class
	* 
	*  Sets the vertical grow priority for the child when contained by an vbox. 
	*  If set, the vbox will use the priority to allocate additional space if the 
	*  vbox is resized larger than it's preferred height. If multiple vbox children 
	*  have the same vertical grow priority, then the extra space will be split evenly 
	*  between them. If no vertical grow priority is set on a child, the vbox will never 
	*  allocate it additional vertical space if available. Setting the value to null 
	*  will remove the constraint. 
	* </pre>
	**/
	public Priority vgrow() default Priority.ALWAYS;

	/**
	* <pre>
	* {@link VBox} Class
	* 
	*  Sets the margin for the child when contained by a vbox. 
	*  If set, the vbox will layout the child so that it has the margin space around it. 
	*  Setting the value to null will remove the constraint. 
	* </pre>
	**/
	public TInsets margin() default @TInsets;

	/**
	* <pre>
	* {@link VBox} Class
	* 
	*  Sets the value of the property spacing. 
	*  
	*  Property description: 
	*  
	*  The amount of vertical space between each child in the vbox.
	* </pre>
	**/
	public double spacing() default TAnnotationDefaultValue.DEFAULT_DOUBLE_VALUE_IDENTIFICATION;

	/**
	* <pre>
	* {@link VBox} Class
	* 
	*  Sets the value of the property alignment. 
	*  
	*  Property description: 
	*  
	*  The overall alignment of children within the vbox's width and height.
	* </pre>
	**/
	public Pos alignment() default Pos.CENTER_LEFT;

	/**
	* <pre>
	* {@link VBox} Class
	* 
	*  Sets the value of the property fillWidth. 
	*  
	*  Property description: 
	*  
	*  Whether or not resizable children will be resized to fill the full 
	*  width of the vbox or be kept to their preferred width and aligned 
	*  according to the alignment hpos value.
	* </pre>
	**/
	public boolean fillWidth() default false;
	
	/**
	 * <pre>
	 * {@link com.tedros.fxapi.control.TVerticalRadioGroup} Class
	 * 
	 * A custom converter 
	 * 
	 * Property description:
	 * 
	 * Specify a converter between the radio button string value and the field Object value
	 * 
	 * </pre>
	 * */
	public TConverter converter() 
	default @TConverter(parse = false, type = com.tedros.fxapi.form.TConverter.class);
	
	
	/**
	 * <pre>
	 * {@link com.tedros.fxapi.control.TVerticalRadioGroup} Class
	 * 
	 * Sets the value of the property required.
	 * 
	 * Property description:
	 * 
	 * Determines with this control will be required.
	 * 
	 * Default value: false.
	 * </pre>
	 * */
	public boolean required() default false;
	
	/**
	 * <pre>
	 * {@link com.tedros.fxapi.control.TVerticalRadioGroup} Class
	 * 
	 * Sets the value of the property radioButtons.
	 * 
	 * Property description:
	 * 
	 * Add radio button for this control.
	 * 
	 * </pre>
	 * */
	public TRadioButton[] radioButtons();
	
	/**
	 * <pre>
	 * Sets the value for fieldStyle property.
	 * 
	 *  Property description:
	 *  
	 *  Defines the style for the inner field control.
	 * </pre>
	 * */
	public String fieldStyle() default "";
	
}
