/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 21/11/2013
 */
package com.tedros.fxapi.annotation.control;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;

import com.tedros.fxapi.annotation.TAnnotationDefaultValue;
import com.tedros.fxapi.annotation.parser.ITAnnotationParser;
import com.tedros.fxapi.annotation.parser.TNumberSpinnerFieldParser;
import com.tedros.fxapi.annotation.parser.TRequiredNumeberFieldParser;
import com.tedros.fxapi.annotation.parser.TTNumberSpinnerFieldParser;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.builder.ITEventHandlerBuilder;
import com.tedros.fxapi.builder.ITFieldBuilder;
import com.tedros.fxapi.builder.ITGenericBuilder;
import com.tedros.fxapi.builder.NullActionEventBuilder;
import com.tedros.fxapi.builder.NullNumberStringConverterBuilder;
import com.tedros.fxapi.builder.TNumberSpinnerFieldBuilder;
import com.tedros.fxapi.domain.TDefaultValues;
import com.tedros.fxapi.domain.TZeroValidation;

/**
 * <pre>
 * Build a {@link com.tedros.fxapi.control.TNumberSpinnerField} component.
 * </pre>
 *
 * @author Davis Gordon
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TNumberSpinnerField {
	
	/**
	 *<pre>
	 * The builder of type {@link ITFieldBuilder} for this component.
	 * 
	 *  Default value: {@link TNumberSpinnerFieldBuilder}
	 *</pre> 
	 * */
	public Class<? extends ITFieldBuilder> builder() default TNumberSpinnerFieldBuilder.class;
	
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TRequiredNumeberFieldParser.class, TNumberSpinnerFieldParser.class, TTNumberSpinnerFieldParser.class}
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITAnnotationParser>[] parser() default {TRequiredNumeberFieldParser.class, TNumberSpinnerFieldParser.class, TTNumberSpinnerFieldParser.class};

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
     * Sets the value.
     *
     * @param value The value.
     * @see #valueProperty()
     */
    public double value() default 0;
    
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
	 * Sets the maxLength property.
	 * 
	 * Property description:
	 * 
	 * The max length for this input control.
	 * </pre>
	 * */
	public int maxLength() default TAnnotationDefaultValue.DEFAULT_INT_VALUE_IDENTIFICATION; 
	
    
    /**
     * Sets the max value.
     *
     * @param maxValue The max value.
     * @throws IllegalArgumentException If the max value is smaller than the min value.
     * @see #maxValueProperty()
     */
	public double maxValue();
	
	/**
     * Sets the min value.
     *
     * @param minValue The min value.
     * @throws IllegalArgumentException If the min value is greater than the max value.
     * @see #minValueProperty()
     */
	public double minValue() default 0;
	
	/**
     * Sets the step width.
     *
     * @param stepWidth The step width.
     * @see #stepWidthProperty()
     */
	public double stepWidth() default 1;
	
	
	/**
     * Sets the number format.
     *
     * @param numberStringConverter The number format.
     * @see #numberStringConverterProperty()
     */
    public Class<? extends ITGenericBuilder<NumberStringConverter>> numberStringConverter() default NullNumberStringConverterBuilder.class;
	
    /**
     * The horizontal alignment of the text field.
     *
     * @param hAlignment The alignment.
     * @see #hAlignmentProperty()
     */
	public HPos hAlignment() default HPos.LEFT;
	
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
	
	/**
	 * <pre>
	 * Sets the zeroValidation property.
	 * 
	 * Property description:
	 * 
	 * Validate if the value of this control are more or less than zero.
	 * </pre>
	 * */
	public TZeroValidation zeroValidation() default TZeroValidation.NONE;
}
