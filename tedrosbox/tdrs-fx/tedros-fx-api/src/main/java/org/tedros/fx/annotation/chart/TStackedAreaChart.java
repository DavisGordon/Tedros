package org.tedros.fx.annotation.chart;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.api.parser.ITAnnotationParser;
import org.tedros.fx.annotation.parser.TStackedAreaChartParser;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.layout.TRegion;
import org.tedros.fx.builder.ITChartBuilder;
import org.tedros.fx.builder.TChartModelBuilder;
import org.tedros.fx.builder.TStackedAreaChartBuilder;

import javafx.scene.Node;
import javafx.scene.chart.Chart;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.layout.Region;


/**
 * <pre>
 * Construct a stacked area chart.
 * StackedAreaChart is a variation of AreaChart that 
 * displays trends of the contribution of each value. 
 * (over time e.g.) The areas are stacked so that each 
 * series adjoins but does not overlap the preceding series. 
 * This contrasts with the Area chart where each series 
 * overlays the preceding series. The cumulative nature of 
 * the StackedAreaChart gives an idea of the total Y data value 
 * at any given point along the X axis. Since data points 
 * across multiple series may not be common, StackedAreaChart 
 * interpolates values along the line joining the data points 
 * whenever necessary.
 * 
 * The data can be provided in three different ways:
 * 
 * 1. Using a server service that implements ITEjbChartController.
 *
 * <b>@</b>TStackedAreaChart(xyChart = <b>@</b>TXYChart(service=TProfileChartController.JNDI_NAME, 
 *	xAxis = <b>@</b>TAxis(axisType = TAxisType.NUMBER, label = "Horizontal"), 
 *	yAxis = <b>@</b>TAxis(axisType = TAxisType.NUMBER, label = "Vertical")))
 * <b>@</b>TModelViewType(modelClass=TAuthorization.class)
 * private ITObservableList&lt;TAuthorization&gt; autorizations;
 * 
 * 2. Using a builder that extends TChartModelBuilder.
 * 
 * <b>@</b>TStackedAreaChart(chartModelBuilder=MyChartBuilder.class,
 *  xyChart = <b>@</b>TXYChart(
 *	xAxis = <b>@</b>TAxis(axisType = TAxisType.NUMBER, label = "Horizontal"), 
 *	yAxis = <b>@</b>TAxis(axisType = TAxisType.NUMBER, label = "Vertical")))
 *  <b>@</b>TModelViewType(modelClass=TAuthorization.class)
 * private ITObservableList&lt;TAuthorization&gt; autorizations;
 * 
 * 3. Configuring static data.
 * 
 * <b>@</b>TStackedAreaChart(xyChart = <b>@</b>TXYChart(
 *   data= {<b>@</b>TSerie(name = "Teste 1", data= {<b>@</b>TData(x="10", y="20")}),
 *	 <b>@</b>TSerie(name = "Teste 2", data= {<b>@</b>TData(x="20", y="5")})},
 *	xAxis = <b>@</b>TAxis(axisType = TAxisType.NUMBER, label = "Horizontal"), 
 *	yAxis = <b>@</b>TAxis(axisType = TAxisType.NUMBER, label = "Vertical")))
 * <b>@</b>TModelViewType(modelClass=TAuthorization.class)
 * private ITObservableList&lt;TAuthorization&gt; autorizations;
 * </pre>
 * @author Davis Gordon
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TStackedAreaChart {
	
	/**
	 *<pre>
	 * The builder of type {@link ITChartBuilder} for this component.
	 * 
	 * Default value: {@link TStackedAreaChartBuilder}
	 *</pre> 
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITChartBuilder> builder() default TStackedAreaChartBuilder.class;
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TStackedAreaChartParser.class}
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITAnnotationParser>[] parser() default {TStackedAreaChartParser.class};
	
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
	* {@link StackedAreaChart} Class
	* 
	*  Sets the value of the property createSymbols. 
	*  Property description: 
	*  When true, CSS styleable symbols are created 
	*  for any data items that don't have a symbol 
	*  node specified. Since: JavaFX 8.0
	* </pre>
	**/
	public boolean createSymbols() default false;

}
