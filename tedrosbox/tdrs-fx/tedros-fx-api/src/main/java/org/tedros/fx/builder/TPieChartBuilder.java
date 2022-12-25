/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package org.tedros.fx.builder;

import java.lang.annotation.Annotation;

import org.tedros.fx.annotation.chart.TPieChart;
import org.tedros.fx.annotation.chart.TPieData;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.exception.TProcessException;
import org.tedros.fx.process.TChartProcess;
import org.tedros.server.controller.TParam;
import org.tedros.server.model.ITChartModel;
import org.tedros.server.model.TChartModel;
import org.tedros.server.result.TResult;

import javafx.concurrent.Worker.State;
import javafx.scene.chart.PieChart;


/**
 * The PieChart builder.
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public final class TPieChartBuilder 
extends TBuilder
implements ITChartBuilder<PieChart>{
	
	
	@SuppressWarnings("unchecked")
	public PieChart build(final Annotation annotation, ITObservableList observable) throws Exception {
		TPieChart tAnn = (TPieChart) annotation;

		PieChart chart = new PieChart();
		if(!"".equals(tAnn.service())) {
			try {
				TChartProcess pss = new TChartProcess(tAnn.service());
				if(tAnn.paramsBuilder()!=TParamBuilder.class) {
					TParamBuilder pb = tAnn.paramsBuilder().newInstance();
					pb.setComponentDescriptor(super.getComponentDescriptor());
					TParam[] params = (TParam[]) pb.build();
					pss.process(params);
				}else
					pss.process();
				pss.stateProperty().addListener((a,o,n)->{
					if(n.equals(State.SUCCEEDED)) {
						TResult<? extends ITChartModel> res = pss.getValue();
						ITChartModel<String, Long> model = res.getValue();
						addData(chart, model);
					}
				});
				pss.startProcess();
			} catch (TProcessException e) {
				e.printStackTrace();
			}
		}else if(tAnn.chartModelBuilder()!=TChartModelBuilder.class) {
			TChartModelBuilder mb = tAnn.chartModelBuilder().newInstance();
			mb.setComponentDescriptor(super.getComponentDescriptor());
			mb.setObservableList(observable);
			TChartModel model = (TChartModel) mb.build();
			this.addData(chart, model);
		}else if(tAnn.data().length>0) {
			for(TPieData d : tAnn.data())
				chart.getData().add(new PieChart.Data(d.name(), d.value()));
		}
		super.callParser(tAnn, chart);
		return chart;
	}

	private void addData(PieChart chart, ITChartModel<String, Long> model) {
		model.getSeries().forEach(s->{
			s.getDatas().forEach(d->{
				chart.getData().add(new PieChart.Data(d.getX(), d.getY()));
			});
		});
	}
	
	
}
