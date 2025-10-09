package org.tedros.fx.annotation.parser;

import org.tedros.fx.annotation.chart.TPieChart;
import org.tedros.fx.annotation.parser.engine.TAnnotationParser;

import javafx.scene.chart.PieChart;


/**
 * <pre>
 * The {@link TPieChart} annotation parser, this parser will read the values 
 * in the annotation and set them at the {@link PieChart} component.
 * </pre>
 * @author Davis Gordon
 * */
public class TPieChartParser extends TAnnotationParser<TPieChart, PieChart> {

	@Override
	public void parse(TPieChart annotation, PieChart object, String... byPass) throws Exception {
		super.parse(annotation, object, "service", "data", "paramsBuilder", "chartModelBuilder");
	}
	
}
