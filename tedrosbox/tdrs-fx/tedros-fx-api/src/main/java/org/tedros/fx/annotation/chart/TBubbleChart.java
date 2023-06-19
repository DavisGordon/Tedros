package org.tedros.fx.annotation.chart;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.api.parser.ITAnnotationParser;
import org.tedros.fx.annotation.parser.TBubbleChartParser;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.layout.TRegion;
import org.tedros.fx.builder.ITChartBuilder;
import org.tedros.fx.builder.TBubbleChartBuilder;
import org.tedros.fx.builder.TChartModelBuilder;

import javafx.scene.Node;
import javafx.scene.chart.Chart;
import javafx.scene.layout.Region;

/**
 * <pre>
 * Construct a bubble chart.
 * Chart type that plots bubbles for the data points in a series. 
 * The extra value property of Data is used to represent the radius 
 * of the bubble it should be a java.lang.Number.
 * 
 * The data can be provided in three different ways:
 * 
 * 1. Using a server service that implements ITEjbChartController.
 *
 * <b>@</b>TBubbleChart(xyChart = <b>@</b>TXYChart(
 *   service=BudgetChartController.JNDI_NAME, 
 *   xAxis = <b>@</b>TAxis(axisType = TAxisType.NUMBER, label = "Week"), 
 *   yAxis = <b>@</b>TAxis(axisType = TAxisType.NUMBER, label = "Budget", 
 *    numberAxis=<b>@</b>TNumberAxis(
 *      valueAxis=<b>@</b>TValueAxis(tickLabelFormatter=CurrencyFormatterBuilder.class, parse = true),
 *    parse = true))))
 * <b>@</b>TGenericType(modelClass=Budget.class)
 * private ITObservableList&lt;Budget&gt; budgets;
 * 
 * 2. Using a builder that extends TChartModelBuilder.
 * 
 * <b>@</b>TBubbleChart(chartModelBuilder=MyBudgetChartBuilder.class,
 *  xyChart = <b>@</b>TXYChart(
 *   xAxis = <b>@</b>TAxis(axisType = TAxisType.NUMBER, label = "Week"), 
 *   yAxis = <b>@</b>TAxis(axisType = TAxisType.NUMBER, label = "Budget", 
 *    numberAxis=<b>@</b>TNumberAxis(
 *     valueAxis=<b>@</b>TValueAxis(tickLabelFormatter=CurrencyFormatterBuilder.class, parse = true),
 *    parse = true))))
 *  <b>@</b>TGenericType(modelClass=Budget.class)
 * private ITObservableList&lt;Budget&gt; budgets;
 * 
 * 3. Configuring static data.
 * 
 * <b>@</b>TBubbleChart(xyChart = <b>@</b>TXYChart(
 *   data= {
 *    <b>@</b>TSerie(name = "Product 1", data= {
 *      <b>@</b>TData(x="2", y="20", extra="1.2"),
 *      <b>@</b>TData(x="3", y="45", extra="5")}),
 *    <b>@</b>TSerie(name = "Product 2", data= {
 *      <b>@</b>TData(x="2", y="5", extra="1")})},
 *   xAxis = <b>@</b>TAxis(axisType = TAxisType.NUMBER, label = "Week"), 
 *   yAxis = <b>@</b>TAxis(axisType = TAxisType.NUMBER, label = "Budget", 
 *    numberAxis=<b>@</b>TNumberAxis(
 *      valueAxis=<b>@</b>TValueAxis(tickLabelFormatter=CurrencyFormatterBuilder.class, parse = true),
 *    parse = true))))
 * <b>@</b>TGenericType(modelClass=Budget.class)
 * private ITObservableList&lt;Budget&gt; budgets;
 * </pre>
 * @author Davis Gordon
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TBubbleChart {
	
	/**
	 *<pre>
	 * The builder of type {@link ITChartBuilder} for this component.
	 * 
	 * Default value: {@link TBubbleChartBuilder}
	 *</pre> 
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITChartBuilder> builder() default TBubbleChartBuilder.class;
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TBubbleChartParser.class}
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITAnnotationParser>[] parser() default {TBubbleChartParser.class};
	
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
	
}
