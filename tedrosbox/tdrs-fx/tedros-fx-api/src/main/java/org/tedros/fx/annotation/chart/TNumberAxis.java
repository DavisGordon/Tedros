package org.tedros.fx.annotation.chart;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.fx.annotation.parser.TNumberAxisParser;
import org.tedros.fx.annotation.parser.engine.TAnnotationParser;

import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ValueAxis;

/**
 * Setting the axis class that plots a range of numbers 
 * with major tick marks every "tickUnit". 
 * You can use any Number type with this axis, Long, Double, BigDecimal etc.
 * @author Davis Gordon
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface TNumberAxis {
	
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TNumberAxisParser.class}
	 * </pre>
	 * */
	public Class<? extends TAnnotationParser<TNumberAxis, NumberAxis>>[] parser() default {TNumberAxisParser.class};
	
	/**
	 * <pre>
	 * Force the parser execution 
	 * </pre>
	 * */
	public boolean parse();
	
	/**
	 * <pre>
	 * The {@link ValueAxis} settings.
	 * </pre>
	 * */
	public TValueAxis valueAxis() default @TValueAxis(parse = false);
	
	
	/**
	* <pre>
	* {@link NumberAxis} Class
	* 
	*  Sets the value of the property forceZeroInRange. 
	*  Property description: 
	*  When true zero is always included in the visible range. 
	*  This only has effect if auto-ranging is on.
	* </pre>
	**/
	public boolean forceZeroInRange() default true;

	/**
	* <pre>
	* {@link NumberAxis} Class
	* 
	*  Sets the value of the property tickUnit. 
	*  Property description: 
	*  The value between each major tick mark in data units. 
	*  This is automatically set if we are auto-ranging.
	* </pre>
	**/
	public double tickUnit() default 1;
}
