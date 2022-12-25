/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package org.tedros.fx.builder;

import java.lang.annotation.Annotation;

import org.tedros.fx.annotation.chart.TLineChart;
import org.tedros.fx.annotation.parser.TXYChartParser;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.domain.TAxisType;
import org.tedros.server.model.TChartModel;

import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;


/**
 * The LineChart builder
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public final class TLineChartBuilder  extends TBuilder
implements ITChartBuilder<LineChart>{
	
	@SuppressWarnings("unchecked")
	public LineChart build(final Annotation annotation, ITObservableList observable ) throws Exception {
		TLineChart ann = (TLineChart) annotation;
		
		Axis xAxis = ann.xyChart().xAxis().axisType().equals(TAxisType.NUMBER)
				? new NumberAxis()
						: new CategoryAxis();
		Axis yAxis = ann.xyChart().yAxis().axisType().equals(TAxisType.NUMBER)
						? new NumberAxis()
								: new CategoryAxis();
						
		super.callParser(ann.xyChart().xAxis(), xAxis);
		super.callParser(ann.xyChart().yAxis(), yAxis);
		
		LineChart chart = new LineChart(xAxis, yAxis);
		
		if(ann.chartModelBuilder()!=TChartModelBuilder.class) {
			TChartModelBuilder mb = ann.chartModelBuilder().newInstance();
			mb.setComponentDescriptor(super.getComponentDescriptor());
			mb.setObservableList(observable);
			TChartModel model = (TChartModel) mb.build();
			TXYChartParser.addData(ann.xyChart(), chart, model);
		}
		super.callParser(ann, chart);
		return chart;
	}
	
	
	
}
