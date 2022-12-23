package org.tedros.fx.annotation.parser;

import org.tedros.fx.annotation.chart.TAxis;

import javafx.scene.chart.Axis;

@SuppressWarnings("rawtypes")
public class TAxisParser extends TAnnotationParser<TAxis, Axis>{
	
	@Override
	public void parse(TAxis annotation, Axis object, String... byPass) throws Exception {
		super.parse(annotation, object, "axisType");
	}
	
}
