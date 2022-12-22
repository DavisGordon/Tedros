package org.tedros.fx.annotation.chart;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.fx.annotation.parser.TAnnotationParser;
import org.tedros.fx.annotation.parser.TValueAxisParser;
import org.tedros.fx.builder.TStringConverterBuilder;

import javafx.scene.chart.ValueAxis;

/**
 * A axis who's data is defined as Numbers. 
 * It can also draw minor tick-marks between the major ones.
 * @author Davis Gordon
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface TValueAxis {
	
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TValueAxisParser.class}
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TAnnotationParser<TValueAxis, ValueAxis>>[] parser() default {TValueAxisParser.class};
	
	/**
	 * <pre>
	 * Force the parser execution 
	 * </pre>
	 * */
	public boolean parse();
	
	/**
	* <pre>
	* {@link ValueAxis} Class
	* 
	*  Sets the value of the property minorTickVisible. 
	*  Property description: 
	*  true if minor tick marks should be displayed
	* </pre>
	**/
	public boolean minorTickVisible() default false;

	/**
	* <pre>
	* {@link ValueAxis} Class
	* 
	*  Sets the value of the property upperBound. 
	*  Property description: 
	*  The value for the upper bound of this axis, ie max value. 
	*  This is automatically set if auto ranging is on.
	* </pre>
	**/
	public double upperBound() default Double.MAX_VALUE;

	/**
	* <pre>
	* {@link ValueAxis} Class
	* 
	*  Sets the value of the property lowerBound. 
	*  Property description: 
	*  The value for the lower bound of this axis, ie min value. 
	*  This is automatically set if auto ranging is on.
	* </pre>
	**/
	public double lowerBound() default Double.MIN_VALUE; 

	/**
	* <pre>
	* {@link ValueAxis} Class
	* 
	*  Sets the value of the property tickLabelFormatter. 
	*  Property description: 
	*  StringConverter used to format tick mark labels. 
	*  If null a default will be used
	* </pre>
	**/
	@SuppressWarnings("rawtypes")
	public Class<? extends TStringConverterBuilder> tickLabelFormatter() default TStringConverterBuilder.class;

	/**
	* <pre>
	* {@link ValueAxis} Class
	* 
	*  Sets the value of the property minorTickLength. 
	*  Property description: 
	*  The length of minor tick mark lines. 
	*  Set to 0 to not display minor tick marks.
	* </pre>
	**/
	public double minorTickLength() default 1;

	/**
	* <pre>
	* {@link ValueAxis} Class
	* 
	*  Sets the value of the property minorTickCount. 
	*  Property description: 
	*  The number of minor tick divisions to be displayed 
	*  between each major tick mark. 
	*  The number of actual minor tick marks will be one less than this.
	* </pre>
	**/
	public int minorTickCount() default 4;
}
