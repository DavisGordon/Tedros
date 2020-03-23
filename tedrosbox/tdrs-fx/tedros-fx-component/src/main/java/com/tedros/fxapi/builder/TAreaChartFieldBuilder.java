/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package com.tedros.fxapi.builder;

import java.lang.annotation.Annotation;

import javafx.geometry.Side;

import com.tedros.fxapi.annotation.chart.TAreaChartField;
import com.tedros.fxapi.annotation.chart.TData;
import com.tedros.fxapi.annotation.chart.TSeries;
import com.tedros.fxapi.form.TAxisPropertiesConfig;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public final class TAreaChartFieldBuilder implements ITChartBuilder<com.tedros.fxapi.chart.TAreaChartField>{

	private static TAreaChartFieldBuilder instance;
	
	private TAreaChartFieldBuilder(){
		
	}
	
	public static TAreaChartFieldBuilder getInstance(){
		if(instance==null)
			instance = new TAreaChartFieldBuilder();
		return instance;
	}
	
	
	public com.tedros.fxapi.chart.TAreaChartField build(final Annotation annotation, com.tedros.fxapi.chart.TAreaChartField chartField) throws Exception {
		TAreaChartField tAnnotation = (TAreaChartField) annotation;
		chartField = new com.tedros.fxapi.chart.TAreaChartField<>(tAnnotation.xAxis().axisType().getValue(), tAnnotation.yAxis().axisType().getValue());
		setProperties(tAnnotation, chartField);
		return chartField;
	}
		
	@SuppressWarnings({"unchecked"})
	public static void setProperties(final TAreaChartField tAnnotation, com.tedros.fxapi.chart.TAreaChartField chartField) {
		
		chartField.gettChart().setTitle(tAnnotation.title());
		TAxisPropertiesConfig.setAxisProperties(tAnnotation.xAxis(), chartField.gettXAxis(), tAnnotation.xAxis().side());
		TAxisPropertiesConfig.setAxisProperties(tAnnotation.yAxis(), chartField.gettYAxis(), 
				(tAnnotation.xAxis().side()==Side.BOTTOM && tAnnotation.yAxis().side()==Side.BOTTOM 
				? Side.LEFT 
						: tAnnotation.yAxis().side()));
		for(TSeries tSerie : tAnnotation.series()){
			for(TData tData : tSerie.data()){
				chartField.tAddData(tSerie.name(), tData.x(), tData.y());
			}
		}
	}
	
}
