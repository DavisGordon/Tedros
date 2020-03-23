package main;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.scene.web.WebView;

public class PocWebEngine {
	
	public PocWebEngine(Main main ) {
		
		final WebView wv = new WebView();
		
		wv.getEngine().setJavaScriptEnabled(true);
		wv.getEngine().getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {

			@Override
			public void changed(ObservableValue<? extends State> arg0,
					State arg1, State arg2) {
				
				if (wv.getEngine().getLoadWorker().getException() != null){
	                System.out.println(wv.getEngine().getLoadWorker().getException().toString());
	            }
				
			}

			
		});
		
		
		//main.prateleira.getChildren().addAll(wv, btn);
		
		wv.getEngine().executeScript("google.charts.load('current', {packages:['corechart']}); google.charts.setOnLoadCallback(drawChart);");
		
		//main.showModal(main.scroll, false);
	}

}
