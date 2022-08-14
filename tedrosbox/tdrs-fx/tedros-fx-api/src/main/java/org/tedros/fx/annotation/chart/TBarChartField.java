package org.tedros.fx.annotation.chart;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.fx.annotation.TAnnotationDefaultValue;
import org.tedros.fx.builder.ITChartBuilder;
import org.tedros.fx.builder.TBarChartFieldBuilder;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TBarChartField {
	
	@SuppressWarnings("rawtypes")
	public Class<? extends ITChartBuilder> parser() default TBarChartFieldBuilder.class;
	
	public String title() default TAnnotationDefaultValue.TCHART_title;
	public TAxis xAxis();
	public TAxis yAxis();
	public TSeries[] series();
}
