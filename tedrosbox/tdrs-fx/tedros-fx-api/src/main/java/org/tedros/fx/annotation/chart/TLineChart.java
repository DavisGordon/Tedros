package org.tedros.fx.annotation.chart;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.api.parser.ITAnnotationParser;
import org.tedros.fx.annotation.parser.TLineChartParser;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.layout.TRegion;
import org.tedros.fx.builder.ITChartBuilder;
import org.tedros.fx.builder.TChartModelBuilder;
import org.tedros.fx.builder.TLineChartBuilder;

import javafx.scene.Node;
import javafx.scene.chart.Chart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.LineChart.SortingPolicy;
import javafx.scene.layout.Region;

/**
 * <pre>
 * Construct a Line chart.
 * Line Chart plots a line connecting the data points in a series. 
 * The data points themselves can be represented by symbols optionally. 
 * Line charts are usually used to view data trends over time or category.
 * 
 * The data can be provided in three different ways:
 * 
 * 1. Using a server service that implements ITEjbChartController.
 *
 * <b>@</b>TLineChart(xyChart = <b>@</b>TXYChart(
 *   service=StockChartController.JNDI_NAME, 
 *   xAxis = <b>@</b>TAxis(axisType = TAxisType.CATEGORY, label = "Month"), 
 *   yAxis = <b>@</b>TAxis(axisType = TAxisType.NUMBER, label = "Stock")))
 * <b>@</b>TModelViewType(modelClass=Stock.class)
 * private ITObservableList&lt;Stock&gt; stocks;
 * 
 * 2. Using a builder that extends TChartModelBuilder.
 * 
 * <b>@</b>TLineChart(chartModelBuilder=MyStockChartBuilder.class,
 *  xyChart = <b>@</b>TXYChart(
 *    xAxis = <b>@</b>TAxis(axisType = TAxisType.CATEGORY, label = "Month"), 
 *    yAxis = <b>@</b>TAxis(axisType = TAxisType.NUMBER, label = "Stock")))
 * <b>@</b>TModelViewType(modelClass=Stock.class)
 * private ITObservableList&lt;Stock&gt; stocks;
 * 
 * 3. Configuring static data.
 * 
 * <b>@</b>TLineChart(xyChart = <b>@</b>TXYChart(
 *   data= {
 *     <b>@</b>TSerie(name = "Stock 1", data= {
 *       <b>@</b>TData(x="Jan", y="5"),
 *       <b>@</b>TData(x="Feb", y="20"),
 *       <b>@</b>TData(x="Mar", y="18")}),
 *     <b>@</b>TSerie(name = "Stock 2", data= {
 *       <b>@</b>TData(x="Jan", y="7"),
 *       <b>@</b>TData(x="Feb", y="12"),
 *       <b>@</b>TData(x="Mar", y="25")})},
 *   xAxis = <b>@</b>TAxis(axisType = TAxisType.CATEGORY, label = "Month"), 
 *   yAxis = <b>@</b>TAxis(axisType = TAxisType.NUMBER, label = "Stock")))
 * <b>@</b>TModelViewType(modelClass=Stock.class)
 * private ITObservableList&lt;Stock&gt; stocks;
 * </pre>
 * @author Davis Gordon
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TLineChart {
	
	/**
	 *<pre>
	 * The builder of type {@link ITChartBuilder} for this component.
	 * 
	 * Default value: {@link TLineChartBuilder}
	 *</pre> 
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITChartBuilder> builder() default TLineChartBuilder.class;
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TLineChartParser.class}
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITAnnotationParser>[] parser() default {TLineChartParser.class};
	
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
	* {@link LineChart} Class
	* 
	*  Sets the value of the property createSymbols. 
	*  Property description:
	*  When true, CSS styleable symbols are created for 
	*  any data items that don't have a symbol node specified.
	* </pre>
	**/
	public boolean createSymbols() default false;

	/**
	* <pre>
	* {@link LineChart} Class
	* 
	*  Sets the value of the property axisSortingPolicy. 
	*  Property description: 
	*  Indicates whether the data passed to LineChart should 
	*  be sorted by natural order of one of the axes. If this 
	*  is set to LineChart.SortingPolicy.NONE, the order in 
	*  XYChart.dataProperty() will be used. 
	*  Default value: SortingPolicy.X_AXIS 
	*  Since: JavaFX 8u40
	* </pre>
	**/
	public SortingPolicy axisSortingPolicy() default SortingPolicy.X_AXIS;

	
}
