package org.tedros.fx.annotation.chart;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.fx.annotation.TAnnotationDefaultValue;
import org.tedros.fx.builder.ITChartBuilder;
import org.tedros.fx.builder.TGenericBuilder;
import org.tedros.fx.builder.TPieChartFieldBuilder;
import org.tedros.fx.process.TChartProcess;
import org.tedros.server.controller.TParam;

import javafx.geometry.Side;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TPieChartField {
	

	@SuppressWarnings("rawtypes")
	public Class<? extends ITChartBuilder> builder() default TPieChartFieldBuilder.class;
	
	/**
	 * The chart title
	 * */
	public String title() default TAnnotationDefaultValue.TCHART_title;
	
	/**
	 * The side of the chart where the title is displayed
	 * */		
	public Side titleSide() default Side.TOP;
	
	/**
	 * When true any data changes will be animated.
	 * */
	public boolean animated() default true;
	
	/**
	 * Optional, the chart process class to call the ejb service.
	 * @return class
	 */
	public Class<? extends TChartProcess> process() default TChartProcess.class;
	
	/**
	 * The jndi name of the ejb chart controller.
	 * It must be of the type ITEjbChartController.
	 * 
	 * @return the jndi name
	 */
	public String service() default "";
	
	/**
	 * Optional, service params builder class.
	 * This builder must return an array of {@link TParam}
	 * @return class 
	 */
	@SuppressWarnings("rawtypes")
	public Class<? extends TGenericBuilder> paramsBuilder() default TGenericBuilder.class;
	
	/**
	 * PieCharts data
	 * */
	public TPieData[] data() default {};
	
	/**
	 * The angle to start the first pie slice at
	 * */
	public double startAngle() default -1;
	
	
	/**
	 * When true we start placing slices clockwise from the startAngle
	 * */
	public boolean clockwise() default true;

	/**
	 * The length of the line from the outside of the pie to the slice labels.
	 * */
	public double labelLineLength() default -1;
	
	/**
	 * When true pie slice labels are drawn
	 * */
	public boolean labelsVisible() default true;
	
	/**
	 * Charts are sized outside in, user tells chart how much space it has and chart draws inside that. So minimum height is a constant 150.
	 * */
	public double computeMinHeight() default -1;
	
	/**
	 * Charts are sized outside in, user tells chart how much space it has and chart draws inside that. So minimum width is a constant 200.
	 * */
	public double computeMinWidth() default -1;
	
	/**
	 * Charts are sized outside in, user tells chart how much space it has and chart draws inside that. So preferred width is a constant 500.
	 * */
	public double computePrefWidth() default -1;
	
	/**
	 * Charts are sized outside in, user tells chart how much space it has and chart draws inside that. So preferred height is a constant 400.
	 * */
	public double computePrefHeight() default -1;
	 
	
}
