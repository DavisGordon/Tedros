/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 08/11/2013
 */
package org.tedros.fx.annotation.control;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.api.parser.ITAnnotationParser;
import org.tedros.fx.annotation.TAnnotationDefaultValue;
import org.tedros.fx.annotation.parser.TMaskFieldParser;
import org.tedros.fx.annotation.parser.TRequiredTextFieldParser;
import org.tedros.fx.annotation.parser.TTextFieldParser;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.builder.ITEventHandlerBuilder;
import org.tedros.fx.builder.ITFieldBuilder;
import org.tedros.fx.builder.NullActionEventBuilder;
import org.tedros.fx.builder.TMaskFieldBuilder;
import org.tedros.fx.control.TRequiredTextField;
import org.tedros.fx.domain.TDefaultValues;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;

/**
 * <pre>
 * Build a {@link org.tedros.fx.control.TMaskField} component.
 * </pre>
 *
 * @author Davis Gordon
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TMaskField  {
	
	/**
	 *<pre>
	 * The builder of type {@link ITFieldBuilder} for this component.
	 * 
	 *  Default value: {@link TMaskFieldBuilder}
	 *</pre> 
	 * */
	public Class<? extends ITFieldBuilder> builder() default TMaskFieldBuilder.class;
	
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TRequiredTextFieldParser.class, TTextFieldParser.class, TMaskFieldParser.class}
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITAnnotationParser>[] parser() default {TRequiredTextFieldParser.class, TTextFieldParser.class, TMaskFieldParser.class};
		
	/**
	 * <pre>
	 * The {@link Node} settings.
	 * </pre>
	 * */
	public TNode node() default @TNode(parse = false);
	
	/**
	 * <pre>
	 * The {@link Control} settings.
	 * 
	 * Default value: @TControl(prefWidth=250) 
	 * </pre>
	 * */
	public TControl control() default @TControl(prefWidth=TDefaultValues.LABEL_WIDTH, parse = true);
	
	/**
	 * <pre>
	 * The {@link TextInputControl} settings.
	 * </pre>
	 * */
	public TTextInputControl textInputControl() default @TTextInputControl(parse = false);
	
	/**
	* <pre>
	* {@link TextField} Class
	* 
	*  Sets the value of the property prefColumnCount. 
	*  
	*  Property description: 
	*  
	*  The preferred number of text columns. This is used for calculating the TextField's preferred width.
	* </pre>
	**/
	public int prefColumnCount() default TAnnotationDefaultValue.DEFAULT_INT_VALUE_IDENTIFICATION;

	/**
	* <pre>
	* {@link TextField} Class
	* 
	*  Sets the value of the property onAction. 
	*  
	*  Property description: 
	*  
	*  The action handler associated with this text field, or null if no action handler is assigned. 
	*  The action handler is normally called when the user types the ENTER key.
	* </pre>
	**/
	public Class<? extends ITEventHandlerBuilder<ActionEvent>> onAction() default NullActionEventBuilder.class;

	/**
	* <pre>
	* {@link TextField} Class
	* 
	*  Sets the value of the property alignment. 
	*  
	*  Property description: 
	*  
	*  Specifies how the text should be aligned when there is empty space within the TextField.
	* </pre>
	**/
	public Pos alignment() default Pos.CENTER_LEFT;
	
	/**
	 * <pre>
	 * {@link org.tedros.fx.control.TMaskField} Class
	 * 
	 * Sets the maxLength property.
	 * 
	 * Property description:
	 * 
	 * The max length for this input control.
	 * </pre>
	 * */
	public int maxLength() default TAnnotationDefaultValue.DEFAULT_INT_VALUE_IDENTIFICATION; 
	
	/**
	 * <pre>
	 * {@link TRequiredTextField} Class
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
	 * {@link org.tedros.fx.control.TMaskField} Class
	 * 
	 * Sets the value of the property mask.
	 * 
	 * Property description:
	 * 
	 * Apply a mask for this input control.
	 * 
	 * A = Only alpha characters [A - Z]
	 * # = Any character, alfa, numeric or symbol
	 * 9 = Only numeric characters [0-9]
	 * 
	 * Example:
	 * 
	 * 999.999.999-99 
	 * 99.999.999/9999-99
	 * AAA-9999 
	 * (99) 9999-99999
	 * [###] A999
	 * </pre>
	 * */
	public String mask(); 
	
}
