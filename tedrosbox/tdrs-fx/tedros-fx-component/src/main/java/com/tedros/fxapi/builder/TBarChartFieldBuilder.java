/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package com.tedros.fxapi.builder;

import java.lang.annotation.Annotation;

import javafx.geometry.Side;

import com.tedros.fxapi.annotation.chart.TBarChartField;
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
public final class TBarChartFieldBuilder implements ITChartBuilder<com.tedros.fxapi.chart.TBarChartField>{
	
	
	public com.tedros.fxapi.chart.TBarChartField build(final Annotation annotation, com.tedros.fxapi.chart.TBarChartField chartField) throws Exception {
		TBarChartField tAnnotation = (TBarChartField) annotation;
		chartField = new com.tedros.fxapi.chart.TBarChartField<>(tAnnotation.xAxis().axisType().getValue(), tAnnotation.yAxis().axisType().getValue());
		setProperties(tAnnotation, chartField);
		return chartField;
	}
		
	@SuppressWarnings({"unchecked"})
	private static void setProperties(final TBarChartField tAnnotation, com.tedros.fxapi.chart.TBarChartField chartField) {
		
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
