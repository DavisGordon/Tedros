package org.tedros.fx.annotation.chart;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.api.parser.ITAnnotationParser;
import org.tedros.fx.annotation.parser.TPieChartParser;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.layout.TRegion;
import org.tedros.fx.builder.ITChartBuilder;
import org.tedros.fx.builder.TChartModelBuilder;
import org.tedros.fx.builder.TParamBuilder;
import org.tedros.fx.builder.TPieChartFieldBuilder;

import javafx.scene.Node;
import javafx.scene.chart.Chart;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Region;

/**
 * <pre>
 *Construct a pie chart.
 *A chart can be plotted in three different ways:
 *1. Using a server service that implements ITEjbChartController.
 *
 * <b>@</b>TPieChartField(service=TProfileChartController.JNDI_NAME, title="My chart")
 * <b>@</b>TModelViewType(modelClass=TAuthorization.class)
 * private ITObservableList&lt;TAuthorization&gt; autorizations;
 * 
 * 2. Using a builder that extends TChartModelBuilder.
 * 
 * <b>@</b>TPieChartField(chartModelBuilder=MyChartBuilder.class, title="My chart")
 * <b>@</b>TModelViewType(modelClass=TAuthorization.class)
 * private ITObservableList&lt;TAuthorization&gt; autorizations;
 * 
 * 3. Configuring static data.
 * 
 * <b>@</b>TPieChartField(title="My chart", 
 * data={<b>@</b>TPieData(name = "App 1", value = 50), <b>@</b>TPieData(name = "App 2", value = 50)})
 * <b>@</b>TModelViewType(modelClass=TAuthorization.class)
 * private ITObservableList&lt;TAuthorization&gt; autorizations;
 * </pre>
 * @author Davis Gordon
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TPieChartField {
	
	/**
	 *<pre>
	 * The builder of type {@link ITChartBuilder} for this component.
	 * 
	 * Default value: {@link TPieChartFieldBuilder}
	 *</pre> 
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITChartBuilder> builder() default TPieChartFieldBuilder.class;
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TPieChartParser.class}
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITAnnotationParser>[] parser() default {TPieChartParser.class};
	
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
	 * Optional, the TChartModel builder to plot the chart.
	 * @return class 
	 */
	@SuppressWarnings("rawtypes")
	public Class<? extends TChartModelBuilder> chartModelBuilder() default TChartModelBuilder.class;
	
	/**
	* <pre>
	* {@link PieChart} Class
	* 
	*  Optional, sets the value of the property data. 
	*  Property description: PieCharts data
	* </pre>
	**/
	public TPieData[] data() default {};
	
	/**
	* <pre>
	* {@link PieChart} Class
	* 
	*  Sets the value of the property startAngle. 
	*  Property description: 
	*  The angle to start the first pie slice at
	* </pre>
	**/
	public double startAngle() default 90;

	/**
	* <pre>
	* {@link PieChart} Class
	* 
	*  Sets the value of the property clockwise. 
	*  Property description: 
	*  When true we start placing slices clockwise 
	*  from the startAngle
	* </pre>
	**/
	public boolean clockwise() default true;

	/**
	* <pre>
	* {@link PieChart} Class
	* 
	*  Sets the value of the property labelLineLength. 
	*  Property description: 
	*  The length of the line from the outside of the 
	*  pie to the slice labels.
	* </pre>
	**/
	public double labelLineLength() default 1L;

	/**
	* <pre>
	* {@link PieChart} Class
	* 
	*  Sets the value of the property labelsVisible. 
	*  Property description: 
	*  When true pie slice labels are drawn
	* </pre>
	**/
	public boolean labelsVisible() default true;
	
}
