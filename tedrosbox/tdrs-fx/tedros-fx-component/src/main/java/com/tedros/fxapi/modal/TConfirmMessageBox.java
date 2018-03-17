package com.tedros.fxapi.modal;

import java.io.IOException;

import com.tedros.core.TInternationalizationEngine;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.layout.HBoxBuilder;

public class TConfirmMessageBox extends TMessageBox {
	 	 
	private SimpleIntegerProperty confirm;
	
	public TConfirmMessageBox(String text) {
		try{
			
			TInternationalizationEngine iEngine = TInternationalizationEngine.getInstance(null);
			
			tAddMessage(iEngine.getString(text));
			confirm = new SimpleIntegerProperty(-1);
			
			Button yesBtn = ButtonBuilder.create()
					.id("t-last-button")
					.text(iEngine.getString("#{tedros.fxapi.button.yes}"))
					.build();
			
			Button noBtn = ButtonBuilder.create()
					.id("t-last-button")
					.text(iEngine.getString("#{tedros.fxapi.button.no}"))
					.build();
			
			gettMessageVBox().getChildren().addAll(HBoxBuilder.create()
					.children(yesBtn, noBtn)
					.spacing(10)
					.alignment(Pos.CENTER)
					.build());
			
			gettMessageVBox().setAlignment(Pos.CENTER);
			
			yesBtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					confirm.set(1);
				}
			});
			
			noBtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					confirm.set(0);
				}
			});
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void loadFXML() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("message.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		fxmlLoader.load();
		//winBtn = new TWindowButtons(false, false, true);
	}

	public ReadOnlyIntegerProperty gettConfirmProperty() {
		return confirm;
	}
	
}
