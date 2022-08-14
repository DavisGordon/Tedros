package org.tedros.fx.chart;

import org.apache.commons.lang3.math.NumberUtils;
import org.tedros.fx.util.TReflectionUtil;

import javafx.collections.ObservableList;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.layout.Pane;

public class TAreaChartField<XType, YType, XAxisType extends Axis<XType>, YAxisType extends Axis<YType>> extends Pane {
	
	private AreaChart<XType, YType> areaChart;
	private XAxisType xAxis;
	private YAxisType yAxis;
	
	public TAreaChartField(Class<XType> xClass, Class<YType> yClass ) {
		settXAxisType(xClass);
		settYAxisType(yClass);
		tCreateChart();
		setId("t-form");
	}
	
	public void tCreateChart(){
		if(xAxis!=null && yAxis!=null)
			areaChart = (AreaChart<XType, YType>) new AreaChart<>((XAxisType) xAxis, (YAxisType) yAxis);
		else
			throw new RuntimeException("ERROR: xAxisType and yAxisType canot be null!"); 
		
		getChildren().add(areaChart);
				
	}
	
	public void tAddSeries(String name){
		if(areaChart == null)
			throw new RuntimeException("ERROR: The chart canot be null!");
		
		for(XYChart.Series<XType, YType> serie : areaChart.getData()){
			if(serie.getName().equals(name))
				return;
		}
		
		XYChart.Series<XType, YType> series = new XYChart.Series<>();
        series.setName(name);
        areaChart.getData().add(series);
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
		if(areaChart == null)
			throw new RuntimeException("ERROR: The chart canot be null!");
		
		tAddSeries(name);
		
		for(XYChart.Series<XType, YType> serie : areaChart.getData()){
			if(serie.getName().equals(name)){
				final XYChart.Data<XType, YType> data = (Data<XType, YType>) new XYChart.Data<>(tConvertXValue(xValue), tConvertYValue(yValue));
				serie.getData().add(data);
				return;
			}	
		}	
	}
	
	public void tAddData(String seriesName, ObservableList<Data<XType, YType>> data){
		if(areaChart == null)
			throw new RuntimeException("ERROR: The chart canot be null!");
		
		for(XYChart.Series<XType, YType> serie : areaChart.getData()){
			if(serie.getName().equals(seriesName)){
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
	

	public final AreaChart<XType, YType> gettChart() {
		return areaChart;
	}

	public final XAxisType gettXAxis() {
		return xAxis;
	}

	public final YAxisType gettYAxis() {
		return yAxis;
	}
	
	
	
	
}
