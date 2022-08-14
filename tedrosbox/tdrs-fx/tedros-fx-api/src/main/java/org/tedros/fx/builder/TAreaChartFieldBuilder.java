/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package org.tedros.fx.builder;

import java.lang.annotation.Annotation;

import org.tedros.fx.annotation.chart.TAreaChartField;
import org.tedros.fx.annotation.chart.TData;
import org.tedros.fx.annotation.chart.TSeries;
import org.tedros.fx.form.TAxisPropertiesConfig;

import javafx.geometry.Side;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public final class TAreaChartFieldBuilder implements ITChartBuilder<org.tedros.fx.chart.TAreaChartField>{

	
	
	public org.tedros.fx.chart.TAreaChartField build(final Annotation annotation, org.tedros.fx.chart.TAreaChartField chartField) throws Exception {
		TAreaChartField tAnnotation = (TAreaChartField) annotation;
		chartField = new org.tedros.fx.chart.TAreaChartField<>(tAnnotation.xAxis().axisType().getValue(), tAnnotation.yAxis().axisType().getValue());
		setProperties(tAnnotation, chartField);
		return chartField;
	}
		
	@SuppressWarnings({"unchecked"})
	public static void setProperties(final TAreaChartField tAnnotation, org.tedros.fx.chart.TAreaChartField chartField) {
		
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
