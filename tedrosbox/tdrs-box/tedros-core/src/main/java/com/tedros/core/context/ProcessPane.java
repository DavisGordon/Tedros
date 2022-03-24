package com.tedros.core.context;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import com.tedros.app.process.ITProcess;
import com.tedros.core.TLanguage;

public class ProcessPane extends BorderPane {
	
	private VBox processVBox;
	private ScrollPane processLayer;
	private TLanguage iEngine = TLanguage.getInstance(null);
	
	public ProcessPane() {
		
		setId("t-process-pane");
		setPrefSize(500, 400);
		setMaxSize(500, 400);
		setMinSize(500, 400);		
		processLayer = new ScrollPane();
		processVBox = new VBox();
		processVBox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		processLayer.setContent(processVBox);
		processLayer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		setCenter(processLayer);
	}
	
	@SuppressWarnings("rawtypes")
	public void load(){
		for(Class classz : TedrosProcess.moduleProcessMap.keySet()){
			ITProcess process = TedrosProcess.moduleProcessMap.get(classz);
			ProcessItem item = new ProcessItem(process);
			if(!processVBox.getChildren().contains(item)){		
				processVBox.getChildren().add(item);
			}else
				item = null;
		}
	}
	
	private class ProcessItem extends Pane{
		
		public Label processName;
		public Label processStatus;
		private HBox content;
		
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public ProcessItem(ITProcess process) {
			setId(process.getProcessId());
			setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			
			getStyleClass().add("t-process-item");
			
			process.stateProperty().addListener(new ChangeListener<State>() {
				@Override
				public void changed(ObservableValue<? extends State> arg0, State arg1, State state) {
					if(state.equals(State.READY))
						processStatus.setText(iEngine.getString("#{tedros.process.state.ready}"));
					if(state.equals(State.RUNNING))
						processStatus.setText(iEngine.getString("#{tedros.process.state.running}"));
					if(state.equals(State.FAILED))
						processStatus.setText(iEngine.getString("#{tedros.process.state.failed}"));
					if(state.equals(State.CANCELLED))
						processStatus.setText(iEngine.getString("#{tedros.process.state.cancelled}"));
					if(state.equals(State.SCHEDULED))
						processStatus.setText(iEngine.getString("#{tedros.process.state.scheduled}"));
					if(state.equals(State.SUCCEEDED))
						processStatus.setText(iEngine.getString("#{tedros.process.state.succeeded}"));
				}
			});
			
			content = new HBox();
			processName = new Label(process.getProcessName());
			processStatus = new Label();
			
			processName.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			processName.setStyle("-fx-background-color: #3b342a; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; ");
			
			HBox.setMargin(processName, new Insets(4, 4, 4, 4));
			HBox.setHgrow(processName, Priority.ALWAYS);
			
			//HBox.setMargin(processStatus, new Insets(4, 4, 4, 0));
			HBox.setHgrow(processStatus, Priority.ALWAYS);
			content.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			content.getChildren().addAll(processName, processStatus);
			getChildren().add(content);
		}
		
		@Override
		public boolean equals(Object obj) {
			return this.getId().equals(((Pane)obj).getId());
		}
		
	}
	
	

}

