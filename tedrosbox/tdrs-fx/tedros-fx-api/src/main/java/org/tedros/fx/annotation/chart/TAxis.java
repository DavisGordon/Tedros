package org.tedros.fx.annotation.chart;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.fx.annotation.parser.TAxisParser;
import org.tedros.fx.annotation.parser.engine.TAnnotationParser;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.layout.TRegion;
import org.tedros.fx.builder.TFontBuilder;
import org.tedros.fx.builder.TPaintBuilder;
import org.tedros.fx.domain.TAxisType;

import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.Region;


/**
 * Setting the base class for all axes in JavaFX 
 * that represents an axis drawn on a chart area. 
 * It holds properties for axis auto ranging, 
 * ticks and labels along the axis.
 * Some examples of concrete subclasses include 
 * NumberAxis whose axis plots data in numbers 
 * and CategoryAxis whose values / ticks represent 
 * string categories along its axis.
 * @author Davis Gordon
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface TAxis {
	
	/**
	 * The axis type
	 * */
	public TAxisType axisType();
	
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TAxisParser.class}
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TAnnotationParser<TAxis, Axis>>[] parser() default {TAxisParser.class};
	

	/**
	 * <pre>
	 * The {@link NumberAxis} settings.
	 * </pre>
	 * */
	public TNumberAxis numberAxis() default @TNumberAxis(parse = false);
	
	/**
	 * <pre>
	 * The {@link CategoryAxis} settings.
	 * </pre>
	 * */
	public TCategoryAxis categoryAxis() default @TCategoryAxis(parse = false);
	

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
	* {@link Axis} Class
	* 
	*  Sets the value of the property side. Property description: The side of the plot which this axis is being drawn on
	* </pre>
	**/
	public Side side() default Side.LEFT;

	/**
	* <pre>
	* {@link Axis} Class
	* 
	*  Sets the value of the property label. 
	*  Property description: 
	*  The axis label
	* </pre>
	**/
	public String label();

	/**
	* <pre>
	* {@link Axis} Class
	* 
	*  Sets the value of the property tickMarkVisible. 
	*  Property description: 
	*  true if tick marks should be displayed
	* </pre>
	**/
	public boolean tickMarkVisible() default true;

	/**
	* <pre>
	* {@link Axis} Class
	* 
	*  Sets the value of the property tickLabelsVisible. 
	*  Property description: 
	*  true if tick mark labels should be displayed
	* </pre>
	**/
	public boolean tickLabelsVisible() default true;

	/**
	* <pre>
	* {@link Axis} Class
	* 
	*  Sets the value of the property tickLength. 
	*  Property description: 
	*  The length of tick mark lines
	* </pre>
	**/
	public double tickLength() default 10D;

	/**
	* <pre>
	* {@link Axis} Class
	* 
	*  Sets the value of the property autoRanging. 
	*  Property description: 
	*  This is true when the axis determines its range from the data automatically
	* </pre>
	**/
	public boolean autoRanging() default false;

	/**
	* <pre>
	* {@link Axis} Class
	* 
	*  Sets the value of the property tickLabelFont. 
	*  Property description: 
	*  The font for all tick labels
	* </pre>
	**/
	public Class<? extends TFontBuilder> tickLabelFont() default TFontBuilder.class;

	/**
	* <pre>
	* {@link Axis} Class
	* 
	*  Sets the value of the property tickLabelFill. 
	*  Property description: 
	*  The fill for all tick labels
	* </pre>
	**/
	public Class<? extends TPaintBuilder> tickLabelFill() default TPaintBuilder.class;

	/**
	* <pre>
	* {@link Axis} Class
	* 
	*  Sets the value of the property tickLabelGap. 
	*  Property description: 
	*  The gap between tick labels and the tick mark lines
	* </pre>
	**/
	public double tickLabelGap() default 22;

	/**
	* <pre>
	* {@link Axis} Class
	* 
	*  Sets the value of the property animated. 
	*  Property description: 
	*  When true any changes to the axis and its range will be animated.
	* </pre>
	**/
	public boolean animated() default false;

	/**
	* <pre>
	* {@link Axis} Class
	* 
	*  Sets the value of the property tickLabelRotation. 
	*  Property description: 
	*  Rotation in degrees of tick mark labels from their normal horizontal.
	* </pre>
	**/
	public double tickLabelRotation() default 30;
	
}
