/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 08/11/2013
 */
package com.tedros.fxapi.annotation.control;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tedros.fxapi.annotation.parser.TAnnotationParser;
import com.tedros.fxapi.annotation.parser.TCheckBoxParser;
import com.tedros.fxapi.annotation.parser.TRequiredCheckBoxParser;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TButtonBase;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.annotation.scene.control.TLabeled;
import com.tedros.fxapi.builder.ITControlBuilder;
import com.tedros.fxapi.builder.TCheckBoxFieldBuilder;
import com.tedros.fxapi.control.TRequiredCheckBox;
import com.tedros.fxapi.domain.TDefaultValues;

import javafx.scene.Node;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.Labeled;

/**
 * <pre>
 * Build a {@link com.tedros.fxapi.control.TCheckBoxField} component.
 * 
 * A tri-state selection Control typically skinned as a box with a checkmark 
 * or tick mark when checked. A CheckBox control can be in one of three states:
 * 
 * checked: indeterminate == false, checked == true
 * unchecked: indeterminate == false, checked == false
 * undefined: indeterminate == true
 * 
 * If a CheckBox is checked, then it is also by definition defined. When checked 
 * the CheckBox is typically rendered with a "check" or "tick" mark. A CheckBox is 
 * in this state if selected is true and indeterminate is false.
 *  
 * A CheckBox is unchecked if selected is false and indeterminate is false.
 *  
 * A CheckBox is undefined if indeterminate is true, regardless of the state of 
 * selected. A typical rendering would be with a minus or dash, to indicate an 
 * undefined or indeterminate state of the CheckBox. This is convenient for constructing 
 * tri-state checkbox based trees, for example, where undefined check boxes typically mean 
 * "inherit settings from the parent".
 *  
 * The allowIndeterminate variable, if true, allows the user to cycle through the undefined 
 * state. If false, the CheckBox is not in the indeterminate state, and the user is allowed 
 * to change only the checked state.
 * 
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TCheckBoxField  {
	
	/**
	 *<pre>
	 * The builder of type {@link ITControlBuilder} for this component.
	 * 
	 *  Default value: {@link TCheckBoxFieldBuilder}
	 *</pre> 
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITControlBuilder> builder() default TCheckBoxFieldBuilder.class;
	
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TControlParser.class}
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TAnnotationParser>[] parser() default {TRequiredCheckBoxParser.class, TCheckBoxParser.class};
	
	/**
	 * <pre>
	 * The {@link Node} properties.
	 * </pre>
	 * */
	public TNode node() default @TNode(parse = false);
	
	/**
	 * <pre>
	 * The {@link Control} properties.
	 * 
	 * Default value: @TControl(prefWidth=250) 
	 * </pre>
	 * */
	public TControl control() default @TControl(prefWidth=TDefaultValues.LABEL_WIDTH, parse = true);
	
	/**
	 * <pre>
	 * The {@link Labeled} properties.
	 * </pre>
	 * */
	public TLabeled labeled();
	
	/**
	 * <pre>
	 * The {@link ButtonBase} properties.
	 * </pre>
	 * */
	public TButtonBase buttonBase() default @TButtonBase();
	
	/**
	* <pre>
	* {@link CheckBox} Class
	* 
	*  Sets the value of the property indeterminate. 
	*  
	*  Property description: 
	*  
	*  Determines whether the CheckBox is in the indeterminate state.
	*  
	*  Default value: false
	* </pre>
	**/
	public boolean indeterminate() default false;

	/**
	* <pre>
	* {@link CheckBox} Class
	* 
	*  Sets the value of the property selected. 
	*  
	*  Property description: 
	*  
	*  Indicates whether this CheckBox is checked.
	*  
	*  Default value: false
	* </pre>
	**/
	public boolean selected() default false;

	/**
	* <pre>
	* {@link CheckBox} Class
	* 
	*  Sets the value of the property allowIndeterminate. 
	*  
	*  Property description: 
	*  
	*  Determines whether the user toggling the CheckBox should cycle through 
	*  all three states: checked, unchecked, and undefined. 
	*  
	*  If true then all three states will be cycled through; if false then only 
	*  checked and unchecked will be cycled.
	*  
	*  Default value: false.
	* </pre>
	**/
	public boolean allowIndeterminate() default false;
	
	/**
	 * <pre>
	 * {@link TRequiredCheckBox} Class
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
	
}
