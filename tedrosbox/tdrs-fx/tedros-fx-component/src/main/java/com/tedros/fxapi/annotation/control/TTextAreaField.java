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

import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputControl;

import com.tedros.fxapi.annotation.TAnnotationDefaultValue;
import com.tedros.fxapi.annotation.parser.ITAnnotationParser;
import com.tedros.fxapi.annotation.parser.TRequiredTextAreaParser;
import com.tedros.fxapi.annotation.parser.TTextAreaFieldParser;
import com.tedros.fxapi.annotation.parser.TTextAreaParser;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.builder.ITFieldBuilder;
import com.tedros.fxapi.builder.TLongFieldBuilder;
import com.tedros.fxapi.builder.TTextAreaFieldBuilder;
import com.tedros.fxapi.control.TRequiredTextArea;
import com.tedros.fxapi.domain.TDefaultValues;

/**
 * <pre>
 * Build a {@link com.tedros.fxapi.control.TTextAreaField} component.
 * 
 * Text input component that allows a user to enter multiple lines of plain text. 
 * Unlike in previous releases of JavaFX, support for single line input is not 
 * available as part of the TextArea control, however this is the sole-purpose of 
 * the TextField control. Additionally, if you want a form of rich-text editing, 
 * there is also the HTMLEditor control.
 * 
 * TextArea supports the notion of showing prompt text to the user when there is no 
 * text already in the TextArea (either via the user, or set programmatically). 
 * This is a useful way of informing the user as to what is expected in the text area, 
 * without having to resort to tooltips or on-screen labels.
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TTextAreaField  {
		
	/**
	 *<pre>
	 * The builder of type {@link ITFieldBuilder} for this component.
	 * 
	 *  Default value: {@link TLongFieldBuilder}
	 *</pre> 
	 * */
	public Class<? extends ITFieldBuilder> builder() default TTextAreaFieldBuilder.class;
	
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TRequiredTextAreaParser.class, TTextAreaFieldParser.class, TTextAreaParser.class}
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITAnnotationParser>[] parser() default {TRequiredTextAreaParser.class, TTextAreaFieldParser.class, TTextAreaParser.class};
	
	
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
	* {@link TextArea} Class
	* 
	*  Sets the value of the property wrapText. 
	*  
	*  Property description: 
	*  
	*  If a run of text exceeds the width of the TextArea, then this variable 
	*  indicates whether the text should wrap onto another line.
	* </pre>
	**/
	public boolean wrapText() default false;

	/**
	* <pre>
	* {@link TextArea} Class
	* 
	*  Sets the value of the property prefColumnCount. 
	*  
	*  Property description: 
	*  
	*  The preferred number of text columns. This is used for calculating the 
	*  TextArea's preferred width.
	* </pre>
	**/
	public int prefColumnCount() default TAnnotationDefaultValue.DEFAULT_INT_VALUE_IDENTIFICATION;

	/**
	* <pre>
	* {@link TextArea} Class
	* 
	*  Sets the value of the property prefRowCount. 
	*  
	*  Property description: 
	*  
	*  The preferred number of text rows. 
	*  This is used for calculating the TextArea's preferred height.
	* </pre>
	**/
	public int prefRowCount() default TAnnotationDefaultValue.DEFAULT_INT_VALUE_IDENTIFICATION;

	/**
	* <pre>
	* {@link TextArea} Class
	* 
	*  Sets the value of the property scrollTop. 
	*  
	*  Property description: 
	*  
	*  The number of pixels by which the content is vertically scrolled.
	* </pre>
	**/
	public double scrollTop() default TAnnotationDefaultValue.DEFAULT_DOUBLE_VALUE_IDENTIFICATION;

	/**
	* <pre>
	* {@link TextArea} Class
	* 
	*  Sets the value of the property scrollLeft. 
	*  
	*  Property description: 
	*  
	*  The number of pixels by which the content is horizontally scrolled.
	* </pre>
	**/
	public double scrollLeft() default TAnnotationDefaultValue.DEFAULT_DOUBLE_VALUE_IDENTIFICATION;
	
	/**
	 * <pre>
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
	 * {@link TRequiredTextArea} Class
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
