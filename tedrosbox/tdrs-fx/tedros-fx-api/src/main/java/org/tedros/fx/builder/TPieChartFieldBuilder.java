/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package org.tedros.fx.builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;

import org.tedros.fx.annotation.chart.TPieChartField;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.process.TChartProcess;
import org.tedros.server.controller.TParam;
import org.tedros.server.model.ITChartModel;
import org.tedros.server.result.TResult;

import javafx.concurrent.Worker.State;
import javafx.scene.chart.PieChart;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public final class TPieChartFieldBuilder 
extends TBuilder
implements ITChartBuilder<PieChart>{
	
	
	@SuppressWarnings("unchecked")
	public PieChart build(final Annotation annotation, ITObservableList observable) throws Exception {
		TPieChartField tAnn = (TPieChartField) annotation;

		PieChart chart = new PieChart();
		if(!"".equals(tAnn.service())) {
			Class<? extends TChartProcess> prClss = tAnn.process();
			Constructor cnt = prClss.getConstructor(String.class);
			TChartProcess pss = (TChartProcess) cnt.newInstance(tAnn.service());
			if(tAnn.paramsBuilder()!=TGenericBuilder.class) {
				TGenericBuilder pb = tAnn.paramsBuilder().newInstance();
				pb.setComponentDescriptor(super.getComponentDescriptor());
				TParam[] params = (TParam[]) pb.build();
				pss.process(params);
			}else
				pss.process();
			pss.stateProperty().addListener((a,o,n)->{
				if(n.equals(State.SUCCEEDED)) {
					TResult<? extends ITChartModel> res = pss.getValue();
					ITChartModel<String, Long> model = res.getValue();
					model.getSeries().forEach(s->{
						s.getDatas().forEach(d->{
							chart.getData().add(new PieChart.Data(d.getX(), d.getY()));
						});
					});
				}
			});
			pss.startProcess();
		}
		chart.setTitle(tAnn.title());
		return chart;
	}
	
	
}
