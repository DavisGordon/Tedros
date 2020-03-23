package com.tedros.fxapi.form;

import javafx.geometry.Side;
import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.paint.Color;

import com.tedros.fxapi.annotation.chart.TAxis;

public class TAxisPropertiesConfig {
	
	private TAxisPropertiesConfig(){
		
	}
	
	@SuppressWarnings("rawtypes")
	public static void setAxisProperties(final TAxis tAxis, final Axis axis, final Side side) {
		
		axis.setLabel(tAxis.label());
		
		if(axis instanceof NumberAxis){
			((NumberAxis) axis).setTickUnit(tAxis.tickUnit());
			((NumberAxis) axis).setMinorTickCount(tAxis.minorTickCount());
			((NumberAxis) axis).setMinorTickLength(tAxis.minorTickLength());
			((NumberAxis) axis).setMinorTickVisible(tAxis.minorTickVisible());
			((NumberAxis) axis).setForceZeroInRange(tAxis.forceZeroInRange());
		}
		
		if(axis instanceof CategoryAxis){
			if(tAxis.startMargin()!=-1) ((CategoryAxis) axis).setStartMargin(tAxis.startMargin());
			if(tAxis.endMargin()!=-1) ((CategoryAxis) axis).setEndMargin(tAxis.endMargin());
			((CategoryAxis) axis).setGapStartAndEnd(tAxis.gapStartAndEnd());
		}
		
		axis.setSide(side);
		axis.setAnimated(tAxis.animated());
		axis.setAutoRanging(tAxis.autoRanging());
		axis.setTickLabelFill(Color.valueOf(tAxis.tickLabelFill()));
		axis.setTickLabelGap(tAxis.tickLabelGap());
		axis.setTickLabelRotation(tAxis.tickLabelRotation());
		axis.setTickMarkVisible(tAxis.tickMarkVisible());
		axis.setTickLabelFont(TFontBuilder.build(tAxis.tickLabelFont()));
		axis.setTickLabelsVisible(tAxis.tickLabelsVisible());
		if(tAxis.tickLength()!=-1) axis.setTickLength(tAxis.tickLength());
	}

}
