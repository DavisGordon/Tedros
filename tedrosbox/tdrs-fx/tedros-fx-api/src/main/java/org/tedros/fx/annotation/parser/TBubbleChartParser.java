package org.tedros.fx.annotation.parser;

import org.tedros.fx.annotation.chart.TBubbleChart;

import javafx.scene.chart.BubbleChart;


/**
 * <pre>
 * The {@link TBubbleChart} annotation parser, this parser will read the values 
 * in the annotation and set them at the {@link BubbleChart} component.
 * </pre>
 * @author Davis Gordon
 * */
@SuppressWarnings("rawtypes")
public class TBubbleChartParser extends TAnnotationParser<TBubbleChart, BubbleChart> {

	@Override
	public void parse(TBubbleChart annotation, BubbleChart object, String... byPass) throws Exception {
		super.parse(annotation, object, "chartModelBuilder");
	}
	
}
