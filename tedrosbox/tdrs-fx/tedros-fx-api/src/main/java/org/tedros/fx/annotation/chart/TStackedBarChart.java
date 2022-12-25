package org.tedros.fx.annotation.chart;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.api.parser.ITAnnotationParser;
import org.tedros.fx.annotation.parser.TStackedBarChartParser;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.layout.TRegion;
import org.tedros.fx.builder.ITChartBuilder;
import org.tedros.fx.builder.TStackedBarChartBuilder;
import org.tedros.fx.builder.TChartModelBuilder;

import javafx.scene.Node;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.Chart;
import javafx.scene.layout.Region;

/**
 * <pre>
 * Construct a stacked bar chart.
 * StackedBarChart is a variation of BarChart that plots bars 
 * indicating data values for a category. The bars can be vertical 
 * or horizontal depending on which axis is a category axis. 
 * The bar for each series is stacked on top of the previous series.
 * 
 * The data can be provided in three different ways:
 * 
 * 1. Using a server service that implements ITEjbChartController.
 *
 * <b>@</b>TStackedBarChart(xyChart = <b>@</b>TXYChart(
 *   service=TProfileChartController.JNDI_NAME, 
 *   xAxis = <b>@</b>TAxis(axisType = TAxisType.CATEGORY, label = "Actions"), 
 *   yAxis = <b>@</b>TAxis(axisType = TAxisType.NUMBER, label = "Total views")))
 * <b>@</b>TModelViewType(modelClass=TAuthorization.class)
 * private ITObservableList&lt;TAuthorization&gt; autorizations;
 * 
 * 2. Using a builder that extends TChartModelBuilder.
 * 
 * <b>@</b>TStackedBarChart(chartModelBuilder=MyChartBuilder.class,
 *  xyChart = <b>@</b>TXYChart(
 *   xAxis = <b>@</b>TAxis(axisType = TAxisType.CATEGORY, label = "Actions"), 
 *   yAxis = <b>@</b>TAxis(axisType = TAxisType.NUMBER, label = "Total views")))
 * <b>@</b>TModelViewType(modelClass=TAuthorization.class)
 * private ITObservableList&lt;TAuthorization&gt; autorizations;
 * 
 * 3. Configuring static data.
 * 
 * <b>@</b>TStackedBarChart(xyChart = <b>@</b>TXYChart(
 *   data= {
 *     <b>@</b>TSerie(name = "App 1", data= {<b>@</b>TData(x="Save", y="8")}),
 *     <b>@</b>TSerie(name = "App 2", data= {<b>@</b>TData(x="Delete", y="5")})},
 *   xAxis = <b>@</b>TAxis(axisType = TAxisType.CATEGORY, label = "Action"), 
 *   yAxis = <b>@</b>TAxis(axisType = TAxisType.NUMBER, label = "Total views")))
 * <b>@</b>TModelViewType(modelClass=TAuthorization.class)
 * private ITObservableList&lt;TAuthorization&gt; autorizations;
 * </pre>
 * @author Davis Gordon
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TStackedBarChart {
	
	/**
	 *<pre>
	 * The builder of type {@link ITChartBuilder} for this component.
	 * 
	 * Default value: {@link TStackedBarChartBuilder}
	 *</pre> 
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITChartBuilder> builder() default TStackedBarChartBuilder.class;
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TStackedBarChartParser.class}
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITAnnotationParser>[] parser() default {TStackedBarChartParser.class};
	
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
	* {@link StackedBarChart} Class
	* 
	*  Sets the value of the property categoryGap. 
	*  Property description: 
	*  The gap to leave between bars in separate categories
	* </pre>
	**/
	public double categoryGap() default 4;
}
