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

import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Slider;
import javafx.util.StringConverter;

import com.tedros.fxapi.annotation.TAnnotationDefaultValue;
import com.tedros.fxapi.annotation.parser.TAnnotationParser;
import com.tedros.fxapi.annotation.parser.TRequiredSliderParser;
import com.tedros.fxapi.annotation.parser.TSliderParser;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.builder.ITControlBuilder;
import com.tedros.fxapi.builder.NullDoubleStringConverter;
import com.tedros.fxapi.builder.TSliderFieldBuilder;
import com.tedros.fxapi.domain.TDefaultValues;
import com.tedros.fxapi.domain.TZeroValidation;

/**
 * Build a {@link com.tedros.fxapi.control.TSlider} control.
 *
 * @author Davis Gordon
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TSliderField  {
	
	/**
	 *<pre>
	 * The builder of type {@link ITControlBuilder} for this component.
	 * 
	 *  Default value: {@link TSliderFieldBuilder}
	 *</pre> 
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITControlBuilder> builder() default TSliderFieldBuilder.class;
	
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TRequiredSliderParser.class, TSliderParser.class}
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TAnnotationParser>[] parser() default {TRequiredSliderParser.class, TSliderParser.class};
	
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
	* {@link Slider} Class
	* 
	*  Sets the value of the property max. 
	*  
	*  Property description: 
	*  
	*  The maximum value represented by this Slider. 
	*  This must be a value greater than min.
	* </pre>
	**/
	public double max();

	/**
	* <pre>
	* {@link Slider} Class
	* 
	*  Sets the value of the property min. 
	*  
	*  Property description: 
	*  
	*  The minimum value represented by this Slider. 
	*  This must be a value less than max.
	* </pre>
	**/
	public double min();

	/**
	* <pre>
	* {@link Slider} Class
	* 
	*  Sets the value of the property value. 
	*  
	*  Property description: 
	*  
	*  The current value represented by this Slider. 
	*  This value must always be between min and max, inclusive. 
	*  If it is ever out of bounds either due to min or max changing 
	*  or due to itself being changed, then it will be clamped to always remain valid.
	* </pre>
	**/
	public double value() default 0;

	/**
	* <pre>
	* {@link Slider} Class
	* 
	*  Sets the value of the property valueChanging. 
	*  
	*  Property description: 
	*  
	*  When true, indicates the current value of this Slider is changing. 
	*  It provides notification that the value is changing. 
	*  Once the value is computed, it is reset back to false.
	* </pre>
	**/
	public boolean valueChanging() default false;

	/**
	* <pre>
	* {@link Slider} Class
	* 
	*  Sets the value of the property orientation. 
	*  
	*  Property description: 
	*  
	*  The orientation of the Slider can either be horizontal or vertical.
	* </pre>
	**/
	public Orientation orientation() default Orientation.HORIZONTAL;

	/**
	* <pre>
	* {@link Slider} Class
	* 
	*  Sets the value of the property showTickLabels. 
	*  
	*  Property description: 
	*  
	*  Indicates that the labels for tick marks should be shown. 
	*  Typically a Skin implementation will only show labels if 
	*  showTickMarks is also true.
	* </pre>
	**/
	public boolean showTickLabels() default false;

	/**
	* <pre>
	* {@link Slider} Class
	* 
	*  Sets the value of the property showTickMarks. 
	*  
	*  Property description: 
	*  
	*  Specifies whether the Skin implementation should show tick marks.
	* </pre>
	**/
	public boolean showTickMarks() default false;

	/**
	* <pre>
	* {@link Slider} Class
	* 
	*  Sets the value of the property majorTickUnit. 
	*  
	*  Property description: 
	*  
	*  The unit distance between major tick marks. 
	*  
	*  For example, if the min is 0 and the max is 100 and the majorTickUnit is 25, 
	*  then there would be 5 tick marks: one at position 0, one at position 25, 
	*  one at position 50, one at position 75, and a final one at position 100. 
	*  This value should be positive and should be a value less than the span. 
	*  Out of range values are essentially the same as disabling tick marks.
	* </pre>
	**/
	public double majorTickUnit() default TAnnotationDefaultValue.DEFAULT_DOUBLE_VALUE_IDENTIFICATION;

	/**
	* <pre>
	* {@link Slider} Class
	* 
	*  Sets the value of the property minorTickCount. 
	*  
	*  Property description: 
	*  
	*  The number of minor ticks to place between any two major ticks. 
	*  This number should be positive or zero. 
	*  Out of range values will disable disable minor ticks, as will a value of zero.
	* </pre>
	**/
	public int minorTickCount() default TAnnotationDefaultValue.DEFAULT_INT_VALUE_IDENTIFICATION;

	/**
	* <pre>
	* {@link Slider} Class
	* 
	*  Sets the value of the property snapToTicks. 
	*  
	*  Property description: 
	*  
	*  Indicates whether the value of the Slider should always be aligned with the tick marks. 
	*  This is honored even if the tick marks are not shown.
	* </pre>
	**/
	public boolean snapToTicks() default false;

	/**
	* <pre>
	* {@link Slider} Class
	* 
	*  Sets the value of the property labelFormatter. 
	*  
	*  Property description: 
	*  
	*  A function for formatting the label for a major tick. 
	*  The number representing the major tick will be passed to the function. 
	*  If this function is not specified, then a default function will be used by the Skin implementation.
	* </pre>
	**/
	public Class<? extends StringConverter<java.lang.Double>> labelFormatter() default NullDoubleStringConverter.class;

	/**
	* <pre>
	* {@link Slider} Class
	* 
	*  Sets the value of the property blockIncrement. 
	*  
	*  Property description: 
	*  
	*  The amount by which to adjust the slider if the track of the 
	*  slider is clicked. This is used when manipulating the slider 
	*  position using keys. If snapToTicks is true then the nearest 
	*  tick mark to the adjusted value will be used.
	* </pre>
	**/
	public double blockIncrement() default TAnnotationDefaultValue.DEFAULT_DOUBLE_VALUE_IDENTIFICATION;
	
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
