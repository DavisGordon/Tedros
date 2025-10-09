package org.tedros.fx.annotation.chart;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.fx.annotation.parser.TXYChartParser;
import org.tedros.fx.annotation.parser.engine.TAnnotationParser;
import org.tedros.fx.builder.TParamBuilder;

import javafx.scene.chart.XYChart;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface TXYChart {

	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TXYChartParser.class}
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TAnnotationParser<TXYChart, XYChart>>[] parser() default {TXYChartParser.class};
	
	/**
	 * The X axis, by default it is along the bottom of the plot
	 */
	public TAxis xAxis();
	
	/**
	 * The Y axis, by default it is along the left of the plot
	 */
	public TAxis yAxis();
	

	/**
	 * The jndi name of the ejb chart controller.
	 * It must be of the type ITEjbChartController.
	 * 
	 * @return the jndi name
	 */
	public String service() default "";
	
	/**
	 * Optional, service params builder class.
	 * @return class 
	 */
	public Class<? extends TParamBuilder> paramsBuilder() default TParamBuilder.class;
	
	/**
	* <pre>
	* {@link XYChart} Class
	* 
	*  Optional, sets the value of the property data. 
	*  Property description: 
	*  XYCharts data
	* </pre>
	**/
	public TSerie[] data() default {};
	
	/**
	* <pre>
	* {@link XYChart} Class
	* 
	*  Sets the value of the property verticalGridLinesVisible. 
	*  Property description: 
	*  True if vertical grid lines should be drawn
	* </pre>
	**/
	public boolean verticalGridLinesVisible() default false;

	/**
	* <pre>
	* {@link XYChart} Class
	* 
	*  Sets the value of the property horizontalGridLinesVisible.
	*  Property description: 
	*  True if horizontal grid lines should be drawn
	* </pre>
	**/
	public boolean horizontalGridLinesVisible() default false;

	/**
	* <pre>
	* {@link XYChart} Class
	* 
	*  Sets the value of the property alternativeColumnFillVisible. 
	*  Property description: 
	*  If true then alternative vertical columns will have fills
	* </pre>
	**/
	public boolean alternativeColumnFillVisible() default false;

	/**
	* <pre>
	* {@link XYChart} Class
	* 
	*  Sets the value of the property alternativeRowFillVisible. 
	*  Property description: 
	*  If true then alternative horizontal rows will have fills
	* </pre>
	**/
	public boolean alternativeRowFillVisible() default false;

	/**
	* <pre>
	* {@link XYChart} Class
	* 
	*  Sets the value of the property verticalZeroLineVisible. 
	*  Property description: 
	*  If this is true and the vertical axis has both positive 
	*  and negative values then a additional axis line will be 
	*  drawn at the zero point Default value: true
	* </pre>
	**/
	public boolean verticalZeroLineVisible() default true;

	/**
	* <pre>
	* {@link XYChart} Class
	* 
	*  Sets the value of the property horizontalZeroLineVisible. 
	*  Property description: 
	*  If this is true and the horizontal axis has both positive and 
	*  negative values then a additional axis line will be drawn at 
	*  the zero point Default value: true
	* </pre>
	**/
	public boolean horizontalZeroLineVisible() default true;


}
