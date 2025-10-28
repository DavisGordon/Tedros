package org.tedros.fx.annotation.parser;

import org.tedros.fx.annotation.chart.TStackedAreaChart;
import org.tedros.fx.annotation.parser.engine.TAnnotationParser;

import javafx.scene.chart.StackedAreaChart;


/**
 * <pre>
 * The {@link TStackedAreaChart} annotation parser, this parser will read the values 
 * in the annotation and set them at the {@link StackedAreaChart} component.
 * </pre>
 * @author Davis Gordon
 * */
@SuppressWarnings("rawtypes")
public class TStackedAreaChartParser extends TAnnotationParser<TStackedAreaChart, StackedAreaChart> {

	@Override
	public void parse(TStackedAreaChart annotation, StackedAreaChart object, String... byPass) throws Exception {
		super.parse(annotation, object, "chartModelBuilder");
	}
	
}
