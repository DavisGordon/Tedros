package org.tedros.fx.annotation.parser;

import org.apache.commons.lang3.math.NumberUtils;
import org.tedros.fx.annotation.chart.TData;
import org.tedros.fx.annotation.chart.TSeries;
import org.tedros.fx.annotation.chart.TXYChart;
import org.tedros.fx.builder.TParamBuilder;
import org.tedros.fx.domain.TAxisType;
import org.tedros.fx.exception.TProcessException;
import org.tedros.fx.process.TChartProcess;
import org.tedros.server.controller.TParam;
import org.tedros.server.model.ITChartModel;
import org.tedros.server.result.TResult;

import javafx.concurrent.Worker.State;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;

@SuppressWarnings("rawtypes")
public class TXYChartParser extends TAnnotationParser<TXYChart, XYChart>{
	
	@Override
	public void parse(TXYChart ann, XYChart chart, String... byPass) throws Exception {
		
		if(!"".equals(ann.service())) {
			try {
				TChartProcess pss = new TChartProcess(ann.service());
				if(ann.paramsBuilder()!=TParamBuilder.class) {
					TParamBuilder pb = ann.paramsBuilder().newInstance();
					pb.setComponentDescriptor(super.getComponentDescriptor());
					TParam[] params = (TParam[]) pb.build();
					pss.process(params);
				}else
					pss.process();
				pss.stateProperty().addListener((a,o,n)->{
					if(n.equals(State.SUCCEEDED)) {
						TResult<? extends ITChartModel> res = pss.getValue();
						ITChartModel<String, Long> model = res.getValue();
						addData(ann, chart, model);
					}
				});
				pss.startProcess();
			} catch (TProcessException e) {
				e.printStackTrace();
			}
		}else if(ann.data().length>0) {
			for(TSeries tSerie : ann.data()){
				for(TData tData : tSerie.data())
						tAddData(ann, chart, tSerie.name(), tData.x(), tData.y());
				
			}
		}
		super.parse(ann, chart, "service", "paramsBuilder", "data");
		
	}

	private void addData(TXYChart ann, XYChart chart, ITChartModel<String, Long> model) {
		model.getSeries().forEach(s->{
			s.getDatas().forEach(d->{
				tAddData(ann, chart, s.getName(), d.getX(), d.getY());
			});
		});
	}
	
	@SuppressWarnings("unchecked")
	private void tAddData(TXYChart ann, XYChart chart, String name, Object xValue, Object yValue){
		
		tAddSeries(chart, name);
		
		for(Object obj :chart.getData()){
			if(obj instanceof XYChart.Series) {
				XYChart.Series serie = (Series) obj;
				
				if(serie.getName().equals(name)){
					final XYChart.Data data = (Data) 
							new XYChart.Data<>(tConvertValue(ann.xAxis().axisType(), xValue), 
									tConvertValue(ann.yAxis().axisType(), yValue));
					serie.getData().add(data);
					return;
				}
			}
		}	
	}
	
	private void tAddSeries(XYChart chart, String name){
		for(Object obj : chart.getData()){
			if(obj instanceof XYChart.Series) {
				XYChart.Series serie = (Series) obj;
				if(serie.getName().equals(name))
					return;
			}else
				return;
		}
		XYChart.Series series = new XYChart.Series<>();
		series.setName(name);
		chart.getData().add(series);
	}
	
	private Object tConvertValue(TAxisType type, Object xValue){
		return (type.equals(TAxisType.NUMBER)) ? NumberUtils.createNumber(String.valueOf(xValue)) : String.valueOf(xValue);
	}
	
	
}
