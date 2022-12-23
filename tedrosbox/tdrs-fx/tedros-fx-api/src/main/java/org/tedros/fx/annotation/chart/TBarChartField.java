package org.tedros.fx.annotation.chart;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.api.parser.ITAnnotationParser;
import org.tedros.fx.annotation.parser.TBarChartParser;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.layout.TRegion;
import org.tedros.fx.builder.ITChartBuilder;
import org.tedros.fx.builder.TBarChartFieldBuilder;
import org.tedros.fx.builder.TChartModelBuilder;

import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.Chart;
import javafx.scene.layout.Region;

/**
 * <pre>
 * Construct a bar chart.
 * A chart that plots bars indicating data values for a category. 
 * The bars can be vertical or horizontal depending on which axis is a category axis.
 * 
 * The data can be provided in three different ways:
 * 
 * 1. Using a server service that implements ITEjbChartController.
 *
 * <b>@</b>TBarChartField(xyChart = <b>@</b>TXYChart(service=TProfileChartController.JNDI_NAME, 
 *	xAxis = <b>@</b>TAxis(axisType = TAxisType.CATEGORY, label = "Horizontal"), 
 *	yAxis = <b>@</b>TAxis(axisType = TAxisType.NUMBER, label = "Vertical")))
 * <b>@</b>TModelViewType(modelClass=TAuthorization.class)
 * private ITObservableList&lt;TAuthorization&gt; autorizations;
 * 
 * 2. Using a builder that extends TChartModelBuilder.
 * 
 * <b>@</b>TBarChartField(chartModelBuilder=MyChartBuilder.class,
 *  xyChart = <b>@</b>TXYChart(
 *	xAxis = <b>@</b>TAxis(axisType = TAxisType.CATEGORY, label = "Horizontal"), 
 *	yAxis = <b>@</b>TAxis(axisType = TAxisType.NUMBER, label = "Vertical")))
 *  <b>@</b>TModelViewType(modelClass=TAuthorization.class)
 * private ITObservableList&lt;TAuthorization&gt; autorizations;
 * 
 * 3. Configuring static data.
 * 
 * <b>@</b>TBarChartField(xyChart = <b>@</b>TXYChart(
 *   data= {<b>@</b>TSerie(name = "Teste 1", data= {<b>@</b>TData(x="10", y="20")}),
 *	 <b>@</b>TSerie(name = "Teste 2", data= {<b>@</b>TData(x="20", y="5")})},
 *	xAxis = <b>@</b>TAxis(axisType = TAxisType.CATEGORY, label = "Horizontal"), 
 *	yAxis = <b>@</b>TAxis(axisType = TAxisType.NUMBER, label = "Vertical")))
 * <b>@</b>TModelViewType(modelClass=TAuthorization.class)
 * private ITObservableList&lt;TAuthorization&gt; autorizations;
 * </pre>
 * @author Davis Gordon
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TBarChartField {
	
	/**
	 *<pre>
	 * The builder of type {@link ITChartBuilder} for this component.
	 * 
	 * Default value: {@link TBarChartFieldBuilder}
	 *</pre> 
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITChartBuilder> builder() default TBarChartFieldBuilder.class;
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TBarChartParser.class}
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITAnnotationParser>[] parser() default {TBarChartParser.class};
	
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
	* {@link BarChart} Class
	* 
	*  Sets the value of the property barGap. 
	*  Property description: 
	*  The gap to leave between bars in the same category
	* </pre>
	**/
	public double barGap() default 2;

	/**
	* <pre>
	* {@link BarChart} Class
	* 
	*  Sets the value of the property categoryGap. 
	*  Property description: 
	*  The gap to leave between bars in separate categories
	* </pre>
	**/
	public double categoryGap() default 4;
}
