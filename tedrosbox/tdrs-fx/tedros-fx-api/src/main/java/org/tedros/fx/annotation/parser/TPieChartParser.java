package org.tedros.fx.annotation.parser;

import org.tedros.fx.annotation.chart.TPieChartField;

import javafx.scene.chart.PieChart;


/**
 * <pre>
 * The {@link TPieChartField} annotation parser, this parser will read the values 
 * in the annotation and set them at the {@link PieChart} component.
 * </pre>
 * @author Davis Gordon
 * */
public class TPieChartParser extends TAnnotationParser<TPieChartField, PieChart> {

	@Override
	public void parse(TPieChartField annotation, PieChart object, String... byPass) throws Exception {
		super.parse(annotation, object, "service", "paramsBuilder", "chartModelBuilder");
	}
	
}
