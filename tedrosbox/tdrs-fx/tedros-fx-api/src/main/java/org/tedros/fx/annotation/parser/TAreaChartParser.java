package org.tedros.fx.annotation.parser;

import org.tedros.fx.annotation.chart.TAreaChartField;

import javafx.scene.chart.AreaChart;


/**
 * <pre>
 * The {@link TAreaChartField} annotation parser, this parser will read the values 
 * in the annotation and set them at the {@link AreaChart} component.
 * </pre>
 * @author Davis Gordon
 * */
@SuppressWarnings("rawtypes")
public class TAreaChartParser extends TAnnotationParser<TAreaChartField, AreaChart> {

	@Override
	public void parse(TAreaChartField annotation, AreaChart object, String... byPass) throws Exception {
		super.parse(annotation, object, "chartModelBuilder");
	}
	
}
