package org.tedros.fx.annotation.parser;

import org.tedros.fx.annotation.chart.TChart;

import javafx.scene.chart.Chart;

public class TChartParser extends TAnnotationParser<TChart, Chart>{
	
	@Override
	public void parse(TChart ann, Chart obj, String... byPass) throws Exception {
		if(!"".equals(ann.title()))
			obj.setTitle(iEngine.getString(ann.title()));
		super.parse(ann, obj, "title");
	}
}
