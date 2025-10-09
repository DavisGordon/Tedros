package org.tedros.fx.annotation.parser;

import org.tedros.fx.annotation.chart.TLineChart;
import org.tedros.fx.annotation.parser.engine.TAnnotationParser;

import javafx.scene.chart.LineChart;


/**
 * <pre>
 * The {@link TLineChart} annotation parser, this parser will read the values 
 * in the annotation and set them at the {@link LineChart} component.
 * </pre>
 * @author Davis Gordon
 * */
@SuppressWarnings("rawtypes")
public class TLineChartParser extends TAnnotationParser<TLineChart, LineChart> {

	@Override
	public void parse(TLineChart annotation, LineChart object, String... byPass) throws Exception {
		super.parse(annotation, object, "chartModelBuilder");
	}
	
}
