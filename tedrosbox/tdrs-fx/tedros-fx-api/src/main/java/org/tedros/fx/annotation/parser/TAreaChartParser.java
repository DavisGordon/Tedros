package org.tedros.fx.annotation.parser;

import org.tedros.fx.annotation.chart.TAreaChart;

import javafx.scene.chart.AreaChart;


/**
 * <pre>
 * The {@link TAreaChart} annotation parser, this parser will read the values 
 * in the annotation and set them at the {@link AreaChart} component.
 * </pre>
 * @author Davis Gordon
 * */
@SuppressWarnings("rawtypes")
public class TAreaChartParser extends TAnnotationParser<TAreaChart, AreaChart> {

	@Override
	public void parse(TAreaChart annotation, AreaChart object, String... byPass) throws Exception {
		super.parse(annotation, object, "chartModelBuilder");
	}
	
}
