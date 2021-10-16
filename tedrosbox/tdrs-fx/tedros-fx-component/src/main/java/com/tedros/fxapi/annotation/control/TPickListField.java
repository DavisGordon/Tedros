package com.tedros.fxapi.annotation.control;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tedros.fxapi.annotation.parser.TAnnotationParser;
import com.tedros.fxapi.annotation.parser.TStackPaneParser;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TInsets;
import com.tedros.fxapi.annotation.scene.layout.TRegion;
import com.tedros.fxapi.builder.ITControlBuilder;
import com.tedros.fxapi.builder.TPickListFieldBuilder;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * Build a {@link com.tedros.fxapi.control.TPickListField} control.
 * 
 * @author Davis Gordon
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TPickListField {

	/**
	 *<pre>
	 * The builder of type {@link ITControlBuilder} for this component.
	 * 
	 *  Default value: {@link TPickListFieldBuilder}
	 *</pre> 
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITControlBuilder> builder() default TPickListFieldBuilder.class;
	
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TStackPaneParser.class}
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TAnnotationParser>[] parser() default {TStackPaneParser.class};
	
	/**
	 * <pre>
	 * The {@link Node} properties.
	 * </pre>
	 * */
	public TNode node() default @TNode(parse = false);
	
	/**
	 * <pre>
	 * The {@link Region} properties.
	 * </pre>
	 * */
	public TRegion region() default @TRegion(parse = false);
	
	/**
	 * <pre>
	 * {@link TPickListField} Class
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
	 * {@link TPickListField} Class
	 * 
	 * Sets the ListView width
	 * 
	 * </pre>
	 * */
	public double width() default -1;
	
	/**
	 * <pre>
	 * {@link TPickListField} Class
	 * 
	 * Sets the ListView height
	 * 
	 * </pre>
	 * */
	public double height() default 150;
	
	/**
	 * <pre>
	 * {@link TPickListField} Class
	 * 
	 * Sets the value of the property sourceLabel.
	 * 
	 * Property description:
	 * 
	 * Set a label to the source list
	 * 
	 * Default value: Empty string.
	 * </pre>
	 * */
	public String sourceLabel() default "";
	
	/**
	 * <pre>
	 * {@link TPickListField} Class
	 * 
	 * Sets the value of the property selectedLabel.
	 * 
	 * Property description:
	 * 
	 * Set a label to the selected list
	 * 
	 * Default value: Empty string.
	 * </pre>
	 * */
	public String selectedLabel() default "";
	
	/**
	 * <pre>
	 * {@link TPickListField} Class
	 * 
	 * Sets the value of the property optionsList.
	 * 
	 * Property description:
	 * 
	 * Defines the process to fill the options at source list
	 * 
	 * Default value: Empty string.
	 * </pre>
	 * */
	public TOptionsList optionsList();
	
	/**
	* <pre>
	* {@link StackPane} Class
	* 
	*  Sets the margin for the child when contained by a stackpane. 
	*  If set, the stackpane will layout the child with the margin space around it.
	* </pre>
	**/
	public TInsets margin() default @TInsets;

	/**
	* <pre>
	* {@link StackPane} Class
	* 
	*  Sets the value of the property alignment. 
	*  
	*  Property description: 
	*  
	*  The default alignment of children within the stackpane's width and height. 
	*  This may be overridden on individual children by setting the child's alignment constraint.
	* </pre>
	**/
	public Pos alignment() default Pos.CENTER_LEFT;
}
