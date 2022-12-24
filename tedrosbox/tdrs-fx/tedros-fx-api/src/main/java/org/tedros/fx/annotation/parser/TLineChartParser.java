package org.tedros.fx.annotation.parser;

import org.tedros.fx.annotation.chart.TLineChartField;

import javafx.scene.chart.LineChart;


/**
 * <pre>
 * The {@link TLineChartField} annotation parser, this parser will read the values 
 * in the annotation and set them at the {@link LineChart} component.
 * </pre>
 * @author Davis Gordon
 * */
@SuppressWarnings("rawtypes")
public class TLineChartParser extends TAnnotationParser<TLineChartField, LineChart> {

	@Override
	public void parse(TLineChartField annotation, LineChart object, String... byPass) throws Exception {
		super.parse(annotation, object, "chartModelBuilder");
	}
	
}
