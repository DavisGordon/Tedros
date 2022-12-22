/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package org.tedros.fx.builder;

import java.lang.annotation.Annotation;

import org.tedros.fx.annotation.chart.TAreaChartField;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.server.model.ITChartModel;
import org.tedros.server.model.TChartModel;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public final class TAreaChartFieldBuilder extends TBuilder 
implements ITChartBuilder<org.tedros.fx.chart.TAreaChartField>{

	@SuppressWarnings("unchecked")
	public org.tedros.fx.chart.TAreaChartField build(final Annotation annotation, ITObservableList observable ) throws Exception {
		TAreaChartField ann = (TAreaChartField) annotation;
		org.tedros.fx.chart.TAreaChartField chart = 
				new org.tedros.fx.chart.TAreaChartField<>(ann.xyChart().xAxis().axisType().getValue(), 
						ann.xyChart().yAxis().axisType().getValue());
		
		if(ann.chartModelBuilder()!=TChartModelBuilder.class) {
			TChartModelBuilder mb = ann.chartModelBuilder().newInstance();
			mb.setComponentDescriptor(super.getComponentDescriptor());
			mb.setObservableList(observable);
			TChartModel model = (TChartModel) mb.build();
			this.addData(chart, model);
		}
		super.callParser(ann, chart.gettChart());
		return chart;
	}

	@SuppressWarnings("unchecked")
	private void addData(org.tedros.fx.chart.TAreaChartField chart, ITChartModel<String, Long> model) {
		model.getSeries().forEach(s->{
			s.getDatas().forEach(d->{
				chart.tAddData(s.getName(), d.getX(), d.getY());
			});
		});
	}
}
