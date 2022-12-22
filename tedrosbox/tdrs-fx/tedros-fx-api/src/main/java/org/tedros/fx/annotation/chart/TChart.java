package org.tedros.fx.annotation.chart;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.fx.annotation.parser.TAnnotationParser;
import org.tedros.fx.annotation.parser.TChartParser;

import javafx.geometry.Side;
import javafx.scene.chart.Chart;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface TChart {

	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TChartParser.class}
	 * </pre>
	 * */
	public Class<? extends TAnnotationParser<TChart, Chart>>[] parser() default {TChartParser.class};
	
	/**
	 * <pre>
	 * Force the parser execution 
	 * </pre>
	 * */
	public boolean parse();
	
	/**
	* <pre>
	* {@link Chart} Class
	* 
	*  Sets the value of the property title. 
	*  Property description: 
	*  The chart title
	* </pre>
	**/
	public String title() default "";

	/**
	* <pre>
	* {@link Chart} Class
	* 
	*  Sets the value of the property titleSide. 
	*  Property description: 
	*  The side of the chart where the title is displayed
	* </pre>
	**/
	public Side titleSide() default Side.TOP;

	/**
	* <pre>
	* {@link Chart} Class
	* 
	*  Sets the value of the property legendVisible. 
	*  Property description:
	*  When true the chart will display a legend 
	*  if the chart implementation supports a legend.
	* </pre>
	**/
	public boolean legendVisible() default false;

	/**
	* <pre>
	* {@link Chart} Class
	* 
	*  Sets the value of the property legendSide. 
	*  Property description: 
	*  The side of the chart where the legend should 
	*  be displayed Default value: Side.BOTTOM
	* </pre>
	**/
	public Side legendSide() default Side.BOTTOM;

	/**
	* <pre>
	* {@link Chart} Class
	* 
	*  Sets the value of the property animated.
	*  Property description: 
	*  When true any data changes will be animated.
	* </pre>
	**/
	public boolean animated() default false;


}
