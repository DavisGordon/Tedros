package com.tedros.fxapi.chart;

import javafx.collections.ObservableList;
import javafx.scene.chart.Axis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.layout.Pane;

import org.apache.commons.lang3.math.NumberUtils;

import com.tedros.fxapi.util.TReflectionUtil;

public class TBarChartField<XType, YType, XAxisType extends Axis<XType>, YAxisType extends Axis<YType>> extends Pane {
	
	private BarChart<XType, YType> chart;
	private XAxisType xAxis;
	private YAxisType yAxis;
	
	public TBarChartField(Class<XType> xClass, Class<YType> yClass ) {
		settXAxisType(xClass);
		settYAxisType(yClass);
		tCreateChart();
		setId("t-form");
	}
	
	public void tCreateChart(){
		if(xAxis!=null && yAxis!=null)
			chart = (BarChart<XType, YType>) new BarChart<>((XAxisType) xAxis, (YAxisType) yAxis);
		else
			throw new RuntimeException("ERROR: xAxisType and yAxisType canot be null!"); 
		
		getChildren().add(chart);
				
	}
	
	public void tAddSeries(String name){
		if(chart == null)
			throw new RuntimeException("ERROR: The chart canot be null!");
		
		for(XYChart.Series<XType, YType> serie : chart.getData()){
			if(serie.getName().equals(name))
				return;
		}
		
		XYChart.Series<XType, YType> series = new XYChart.Series<>();
        series.setName(name);
        chart.getData().add(series);
	}
	
	public void tAddSeries(String name, XType xValue, YType yValue ){
		tAddSeries(name);
		tAddData(name, xValue, yValue);
	}
	
	public void tAddSeries(String name, ObservableList<Data<XType, YType>> data){
		tAddSeries(name);
		tAddData(name, data);
	}
	
	private Object tConvertXValue(Object xValue){
		return (xAxis instanceof NumberAxis) ? NumberUtils.createNumber(String.valueOf(xValue)) : String.valueOf(xValue);
	}
	
	private Object tConvertYValue(Object yValue){
		return (yAxis instanceof NumberAxis) ? NumberUtils.createNumber(String.valueOf(yValue)) : String.valueOf(yValue);
	}
	
	@SuppressWarnings("unchecked")
	public void tAddData(String name, XType xValue, YType yValue){
		if(chart == null)
			throw new RuntimeException("ERROR: The chart canot be null!");
		
		tAddSeries(name);
		
		for(XYChart.Series<XType, YType> serie : chart.getData()){
			if(serie.getName().equals(name)){
				final XYChart.Data<XType, YType> data = (Data<XType, YType>) new XYChart.Data<>(tConvertXValue(xValue), tConvertYValue(yValue));
				serie.getData().add(data);
				return;
			}	
		}	
	}
	
	public void tAddData(String name, ObservableList<Data<XType, YType>> data){
		if(chart == null)
			throw new RuntimeException("ERROR: The chart canot be null!");
		
		for(XYChart.Series<XType, YType> serie : chart.getData()){
			if(serie.getName().equals(name)){
				serie.setData(data);
				return;
			}	
		}	
	}
	

	@SuppressWarnings("unchecked")
	private void settYAxisType(Class<YType> yClass) {
		if(TReflectionUtil.isTypeOf(yClass, Number.class))
			yAxis = (YAxisType) new NumberAxis();
		else
			yAxis = (YAxisType) new CategoryAxis();
	}

	@SuppressWarnings("unchecked")
	private void settXAxisType(Class<XType> xClass) {
		if(TReflectionUtil.isTypeOf(xClass, Number.class))
			xAxis = (XAxisType) new NumberAxis();
		else
			xAxis = (XAxisType) new CategoryAxis();
	}
	

	public final BarChart<XType, YType> gettChart() {
		return chart;
	}

	public final XAxisType gettXAxis() {
		return xAxis;
	}

	public final YAxisType gettYAxis() {
		return yAxis;
	}
	
	
	
	
}
