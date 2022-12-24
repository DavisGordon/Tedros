package org.tedros.fx.annotation.parser;

import org.tedros.fx.annotation.chart.TBubbleChartField;

import javafx.scene.chart.BubbleChart;


/**
 * <pre>
 * The {@link TBubbleChartField} annotation parser, this parser will read the values 
 * in the annotation and set them at the {@link BubbleChart} component.
 * </pre>
 * @author Davis Gordon
 * */
@SuppressWarnings("rawtypes")
public class TBubbleChartParser extends TAnnotationParser<TBubbleChartField, BubbleChart> {

	@Override
	public void parse(TBubbleChartField annotation, BubbleChart object, String... byPass) throws Exception {
		super.parse(annotation, object, "chartModelBuilder");
	}
	
}
