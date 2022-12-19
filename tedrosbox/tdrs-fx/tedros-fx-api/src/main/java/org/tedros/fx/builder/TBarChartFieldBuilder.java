/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package org.tedros.fx.builder;

import java.lang.annotation.Annotation;

import org.tedros.fx.annotation.chart.TBarChartField;
import org.tedros.fx.annotation.chart.TData;
import org.tedros.fx.annotation.chart.TSeries;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.form.TAxisPropertiesConfig;

import javafx.geometry.Side;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public final class TBarChartFieldBuilder implements ITChartBuilder<org.tedros.fx.chart.TBarChartField>{
	
	
	public org.tedros.fx.chart.TBarChartField build(final Annotation annotation, ITObservableList observable) throws Exception {
		TBarChartField tAnnotation = (TBarChartField) annotation;
		org.tedros.fx.chart.TBarChartField chartField = new org.tedros.fx.chart.TBarChartField<>(tAnnotation.xAxis().axisType().getValue(), tAnnotation.yAxis().axisType().getValue());
		setProperties(tAnnotation, chartField);
		return chartField;
	}
		
	@SuppressWarnings({"unchecked"})
	private static void setProperties(final TBarChartField tAnnotation, org.tedros.fx.chart.TBarChartField chartField) {
		
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
