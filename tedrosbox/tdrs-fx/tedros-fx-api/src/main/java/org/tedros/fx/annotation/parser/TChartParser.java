package org.tedros.fx.annotation.parser;

import org.tedros.fx.annotation.chart.TChart;

import javafx.scene.chart.Chart;

/**
 * <pre>
 * The {@link TChart} annotation parser, this parser will read the values 
 * in the annotation and set them at the {@link Chart} component.
 * </pre>
 * @author Davis Gordon
 * */
public class TChartParser extends TAnnotationParser<TChart, Chart>{
	
	@Override
	public void parse(TChart ann, Chart obj, String... byPass) throws Exception {
		if(!"".equals(ann.title()))
			obj.setTitle(iEngine.getString(ann.title()));
		super.parse(ann, obj, "title");
	}
}
