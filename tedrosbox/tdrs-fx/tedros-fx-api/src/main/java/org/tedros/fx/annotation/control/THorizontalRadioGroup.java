package org.tedros.fx.annotation.control;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.api.parser.ITAnnotationParser;
import org.tedros.fx.annotation.TAnnotationDefaultValue;
import org.tedros.fx.annotation.parser.THBoxParser;
import org.tedros.fx.annotation.parser.THorizontalRadioGroupParser;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.control.TInsets;
import org.tedros.fx.annotation.scene.layout.TRegion;
import org.tedros.fx.builder.ITControlBuilder;
import org.tedros.fx.builder.THorizontalRadioGroupBuilder;

import javafx.beans.property.Property;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

/**
 * <pre>
 * Build a {@link org.tedros.fx.control.THorizontalRadioGroup} component;
 * </pre>
 *
 * @author Davis Gordon
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface THorizontalRadioGroup {
	
	/**
	 *<pre>
	 * The builder of type {@link ITControlBuilder} for this component.
	 * 
	 *  Default value: {@link THorizontalRadioGroupBuilder}
	 *</pre> 
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITControlBuilder<org.tedros.fx.control.THorizontalRadioGroup, Property>> builder() 
			default THorizontalRadioGroupBuilder.class;
	
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {THorizontalRadioGroupParser.class, THBoxParser.class}
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITAnnotationParser>[] parser() 
	default {THorizontalRadioGroupParser.class, THBoxParser.class};
	
	
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
	* {@link HBox} Class
	* 
	*  Sets the horizontal grow priority for the child when contained by an hbox. 
	*  If set, the hbox will use the priority to allocate additional space if the 
	*  hbox is resized larger than it's preferred width. If multiple hbox children 
	*  have the same horizontal grow priority, then the extra space will be split 
	*  evening between them. If no horizontal grow priority is set on a child, the 
	*  hbox will never allocate it additional horizontal space if available. 
	*  Setting the value to null will remove the constraint. 
	* </pre>
	**/
	public Priority hgrow() default Priority.ALWAYS;

	/**
	* <pre>
	* {@link HBox} Class
	* 
	*  Sets the margin for the child when contained by an hbox. 
	*  If set, the hbox will layout the child with the margin space around it. 
	*  Setting the value to null will remove the constraint. 
	* </pre>
	**/
	public TInsets margin() default @TInsets;

	/**
	* <pre>
	* {@link HBox} Class
	* 
	*  Sets the value of the property spacing. 
	*  
	*  Property description: 
	*  
	*  The amount of horizontal space between each child in the hbox.
	* </pre>
	**/
	public double spacing() default TAnnotationDefaultValue.DEFAULT_DOUBLE_VALUE_IDENTIFICATION;

	/**
	* <pre>
	* {@link HBox} Class
	* 
	*  Sets the value of the property alignment. 
	*  
	*  Property description: 
	*  
	*  The overall alignment of children within the hbox's width and height. 
	*  If the vertical alignment value is BASELINE, then children will always 
	*  be resized to their preferred heights and the fillHeight property will 
	*  be ignored.
	* </pre>
	**/
	public Pos alignment() default Pos.CENTER_LEFT;

	/**
	* <pre>
	* {@link HBox} Class
	* 
	*  Sets the value of the property fillHeight. 
	*  
	*  Property description: 
	*  
	*  Whether or not resizable children will be resized to fill the full height 
	*  of the hbox or be kept to their preferred height and aligned according to 
	*  the alignment vpos value. Note that if the hbox vertical alignment is set 
	*  to BASELINE, then this property will be ignored and children will be resized 
	*  to their preferred heights.
	* </pre>
	**/
	public boolean fillHeight() default false;
	

	/**
	 * <pre>
	 * {@link org.tedros.fx.control.TVerticalRadioGroup} Class
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
	default @TConverter(parse = false, type = org.tedros.fx.form.TConverter.class);
	
	
	/**
	 * <pre>
	 * {@link org.tedros.fx.control.THorizontalRadioGroup} Class
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
	 * {@link org.tedros.fx.control.THorizontalRadioGroup} Class
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
