package org.tedros.fx.chart;

import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Pane;

public class TPieChartField extends Pane {
	
	private PieChart chart;
	
	public TPieChartField() {
		//setId("t-form");
		tCreateChart();
	}
	
	public TPieChartField(ObservableList<PieChart.Data> data) {
		//setId("t-form");
		tCreateChart();
		tAddData(data);
	}
	
	public void tCreateChart(){
		chart = new PieChart();
		getChildren().add(chart);
	}
	
	public void tAddData(String name, double value){
		if(chart == null)
			throw new RuntimeException("ERROR: The chart canot be null!");
		
		for(PieChart.Data data : chart.getData()){
			if(data.getName().equals(name)){
				data.setPieValue(value);
				return;
			}
		}
		chart.getData().add(new PieChart.Data(name, value));
	}
	
	public void tAddData(ObservableList<PieChart.Data> data){
		if(chart == null)
			throw new RuntimeException("ERROR: The chart canot be null!");
		chart.setData(data);	
	}
	

	public final PieChart gettChart() {
		return chart;
	}
	
}
