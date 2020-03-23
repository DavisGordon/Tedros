package com.tedros.fxapi.popup;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Popup;

import com.tedros.fxapi.control.TWindowButtons;

public abstract class TPopup extends Popup {

	private Content content; 
	private double contentWidth = 500;
	private double contentHeight = 400;
	
	public TPopup(String name) {
		setWidth(contentWidth);
		setHeight(contentHeight);
		this.content = new Content(this);
		this.content.setName(name);
	}
	
	public TPopup(String popupName, Node popupContent, double width, double height) {
		contentWidth = width;
		contentHeight = height;
		setWidth(width);
		setHeight(height);
		createContent(popupContent);
		this.content.setName(popupName);
	}
	
	public void close(){
		hide();
	}
	
	public void setPopupContent(Node popupContent){
		createContent(popupContent);
	}
	
	public void setPopupName(String popupName){
		if(null==content)
			return;
		this.content.setName(popupName);
	}
	
	public void setPopupContent(Node popupContent, double width, double height){
		contentWidth = width;
		contentHeight = height;
		setWidth(width);
		setHeight(height);
		createContent(popupContent);
	}
	
	private void createContent(Node popupContent) {
		content = new Content(this);
		//StackPane contentPane = new StackPane();
		//contentPane.getChildren().add(popupContent);
		//contentPane.getStyleClass().add("popup-content");
		content.getStyleClass().add("t-popup-content");
		//BorderPane.setMargin(contentPane, new Insets(0));
		content.setCenter(popupContent);
		getContent().add(content);
	}
	
	class Content extends BorderPane{
		
		private Popup popup;
		private ToolBar header;
		private Label popupName;
		private class Delta{
			public double x;
			public double y;
		}
		
		public void setName(String name){
			if(popupName==null)
				buildPopupNameNode();
			popupName.setText(name);
		}
		
		public Content(TPopup pop) {
			
			setId("t-popup");
			setPrefSize(contentWidth, contentHeight);
			setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
			setMinSize(USE_PREF_SIZE, USE_PREF_SIZE);
			autosize();			
			//setStyle("-fx-background-color: #FFFFFF; -fx-effect: dropshadow( two-pass-box , black, 10, 0.2 , 0 , 0 ); -fx-background-radius: 0 8 8 8; ");
			
			popup = pop; 
			header = new ToolBar();
			header.setId("t-popup-toolbar");
			final Delta dragDelta = new Delta();
			 // add window dragging
			header.setOnMousePressed(new EventHandler<MouseEvent>() {
	            @Override 
	            public void handle(MouseEvent event) {
	            	dragDelta.x = event.getSceneX();
	            	dragDelta.y = event.getSceneY();
	            }
	        });
			header.setOnMouseDragged(new EventHandler<MouseEvent>() {
	            @Override public void handle(MouseEvent event) {
	            	popup.setX(event.getScreenX()-dragDelta.x);
	            	popup.setY(event.getScreenY()-dragDelta.y);
	                
	            }
	        });
			
			
			buildPopupNameNode();
	        
	        HBox h = new HBox();
	        h.setAlignment(Pos.CENTER_LEFT);
	        //HBox.setMargin(popupName, new Insets(1, 0, 3, 3));
	        h.getChildren().addAll(popupName);
			
	        TWindowButtons twinBtn = new TWindowButtons(false, false, true);
	        twinBtn.getCloseActionProperty().set(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					popup.hide();
				}
			});
	        
	        Region spacer = new Region();
	        HBox.setHgrow(spacer, Priority.ALWAYS);
	        header.getItems().addAll(popupName,spacer,twinBtn);
	        BorderPane.setAlignment(header, Pos.TOP_RIGHT);
			setTop(header);
		}

		private void buildPopupNameNode() {
			popupName = new Label("Set the popup name!");
			popupName.setId("t-popup-label");
		}
		
	}
	
}
