package org.tedros.fx.annotation.parser;

import org.tedros.fx.annotation.chart.TStackedBarChart;
import org.tedros.fx.annotation.parser.engine.TAnnotationParser;

import javafx.scene.chart.StackedBarChart;


/**
 * <pre>
 * The {@link TStackedBarChart} annotation parser, this parser will read the values 
 * in the annotation and set them at the {@link StackedBarChart} component.
 * </pre>
 * @author Davis Gordon
 * */
@SuppressWarnings("rawtypes")
public class TStackedBarChartParser extends TAnnotationParser<TStackedBarChart, StackedBarChart> {

	@Override
	public void parse(TStackedBarChart annotation, StackedBarChart object, String... byPass) throws Exception {
		super.parse(annotation, object, "chartModelBuilder");
	}
	
}
