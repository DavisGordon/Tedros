package org.tedros.fx.annotation.parser;

import org.tedros.fx.annotation.chart.TBarChart;
import org.tedros.fx.annotation.parser.engine.TAnnotationParser;

import javafx.scene.chart.BarChart;


/**
 * <pre>
 * The {@link TBarChart} annotation parser, this parser will read the values 
 * in the annotation and set them at the {@link BarChart} component.
 * </pre>
 * @author Davis Gordon
 * */
@SuppressWarnings("rawtypes")
public class TBarChartParser extends TAnnotationParser<TBarChart, BarChart> {

	@Override
	public void parse(TBarChart annotation, BarChart object, String... byPass) throws Exception {
		super.parse(annotation, object, "chartModelBuilder");
	}
	
}
