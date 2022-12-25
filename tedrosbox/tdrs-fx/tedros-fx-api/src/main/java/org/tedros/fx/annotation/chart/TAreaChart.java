package org.tedros.fx.annotation.chart;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.api.parser.ITAnnotationParser;
import org.tedros.fx.annotation.parser.TAreaChartParser;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.layout.TRegion;
import org.tedros.fx.builder.ITChartBuilder;
import org.tedros.fx.builder.TAreaChartBuilder;
import org.tedros.fx.builder.TChartModelBuilder;

import javafx.scene.Node;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.Chart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Region;


/**
 * <pre>
 * Construct a area chart.
 * AreaChart - Plots the area between the line that connects 
 * the data points and the 0 line on the Y axis.
 * 
 * The data can be provided in three different ways:
 * 
 * 1. Using a server service that implements ITEjbChartController.
 *
 * <b>@</b>TAreaChart(xyChart = <b>@</b>TXYChart(
 *   service=TProfileChartController.JNDI_NAME, 
 *   xAxis = <b>@</b>TAxis(axisType = TAxisType.NUMBER, label = "Horizontal"), 
 *   yAxis = <b>@</b>TAxis(axisType = TAxisType.NUMBER, label = "Vertical")))
 * <b>@</b>TModelViewType(modelClass=TAuthorization.class)
 * private ITObservableList&lt;TAuthorization&gt; autorizations;
 * 
 * 2. Using a builder that extends TChartModelBuilder.
 * 
 * <b>@</b>TAreaChart(chartModelBuilder=MyChartBuilder.class,
 *  xyChart = <b>@</b>TXYChart(
 *   xAxis = <b>@</b>TAxis(axisType = TAxisType.NUMBER, label = "Horizontal"), 
 *   yAxis = <b>@</b>TAxis(axisType = TAxisType.NUMBER, label = "Vertical")))
 * <b>@</b>TModelViewType(modelClass=TAuthorization.class)
 * private ITObservableList&lt;TAuthorization&gt; autorizations;
 * 
 * 3. Configuring static data.
 * 
 * <b>@</b>TAreaChart(xyChart = <b>@</b>TXYChart(
 *   data= {
 *    <b>@</b>TSerie(name = "Teste 1", data= {<b>@</b>TData(x="10", y="20")}),
 *    <b>@</b>TSerie(name = "Teste 2", data= {<b>@</b>TData(x="20", y="5")})},
 *   xAxis = <b>@</b>TAxis(axisType = TAxisType.NUMBER, label = "Horizontal"), 
 *   yAxis = <b>@</b>TAxis(axisType = TAxisType.NUMBER, label = "Vertical")))
 * <b>@</b>TModelViewType(modelClass=TAuthorization.class)
 * private ITObservableList&lt;TAuthorization&gt; autorizations;
 * </pre>
 * @author Davis Gordon
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TAreaChart {
	
	/**
	 *<pre>
	 * The builder of type {@link ITChartBuilder} for this component.
	 * 
	 * Default value: {@link TAreaChartBuilder}
	 *</pre> 
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITChartBuilder> builder() default TAreaChartBuilder.class;
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TAreaChartParser.class}
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITAnnotationParser>[] parser() default {TAreaChartParser.class};
	
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
	 * The {@link Chart} settings.
	 * </pre>
	 * */
	public TChart chart() default @TChart(parse = false);
	
	/**
	 * <pre>
	 * The {@link TXYChart} settings.
	 * </pre>
	 * */
	public TXYChart xyChart();
	
	/**
	 * Optional, the TChartModel builder to plot the chart.
	 * @return class 
	 */
	@SuppressWarnings("rawtypes")
	public Class<? extends TChartModelBuilder> chartModelBuilder() default TChartModelBuilder.class;
	
	/**
	* <pre>
	* {@link AreaChart} Class
	* 
	*  Sets the value of the property createSymbols. 
	*  Property description: 
	*  When true, CSS styleable symbols are created for
	*  any data items that don't have a symbol node specified. 
	*  Since: JavaFX 8.0
	* </pre>
	**/
	public boolean createSymbols() default false;
	
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
