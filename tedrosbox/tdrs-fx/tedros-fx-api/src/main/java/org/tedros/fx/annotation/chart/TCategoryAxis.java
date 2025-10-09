package org.tedros.fx.annotation.chart;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.fx.annotation.parser.TCategoryAxisParser;
import org.tedros.fx.annotation.parser.engine.TAnnotationParser;

import javafx.scene.chart.CategoryAxis;

/**
 * A axis implementation that will works on string 
 * categories where each value as a unique category(tick mark) 
 * along the axis.
 * @author Davis Gordon
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface TCategoryAxis {
	
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TCategoryAxisParser.class}
	 * </pre>
	 * */
	public Class<? extends TAnnotationParser<TCategoryAxis, CategoryAxis>>[] parser() default {TCategoryAxisParser.class};
	
	/**
	 * <pre>
	 * Force the parser execution 
	 * </pre>
	 * */
	public boolean parse();
	
	/**
	* <pre>
	* {@link CategoryAxis} Class
	* 
	*  Sets the value of the property startMargin. 
	*  Property description: 
	*  The margin between the axis start and the first tick-mark
	* </pre>
	**/
	public double startMargin() default 12D;

	/**
	* <pre>
	* {@link CategoryAxis} Class
	* 
	*  Sets the value of the property endMargin. 
	*  Property description: 
	*  The margin between the last tick mark and the axis end
	* </pre>
	**/
	public double endMargin() default 12D;

	/**
	* <pre>
	* {@link CategoryAxis} Class
	* 
	*  Sets the value of the property gapStartAndEnd. 
	*  Property description: 
	*  If this is true then half the space between 
	*  ticks is left at the start and end
	* </pre>
	**/
	public boolean gapStartAndEnd() default false;

	/**
	* <pre>
	* {@link CategoryAxis} Class
	* 
	*  The ordered list of categories plotted on this axis. 
	*  This is set automatically based on the charts data if 
	*  autoRanging is true. If the application sets the categories 
	*  then auto ranging is turned off. If there is an attempt to 
	*  add duplicate entry into this list, an IllegalArgumentException is thrown.
	* </pre>
	**/
	public String[] categories() default {};
	
}
