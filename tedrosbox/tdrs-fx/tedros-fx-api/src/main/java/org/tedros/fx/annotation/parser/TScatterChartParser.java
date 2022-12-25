package org.tedros.fx.annotation.parser;

import org.tedros.fx.annotation.chart.TScatterChart;

import javafx.scene.chart.ScatterChart;


/**
 * <pre>
 * The {@link TScatterChart} annotation parser, this parser will read the values 
 * in the annotation and set them at the {@link ScatterChart} component.
 * </pre>
 * @author Davis Gordon
 * */
@SuppressWarnings("rawtypes")
public class TScatterChartParser extends TAnnotationParser<TScatterChart, ScatterChart> {

	@Override
	public void parse(TScatterChart annotation, ScatterChart object, String... byPass) throws Exception {
		super.parse(annotation, object, "chartModelBuilder");
	}
	
}
