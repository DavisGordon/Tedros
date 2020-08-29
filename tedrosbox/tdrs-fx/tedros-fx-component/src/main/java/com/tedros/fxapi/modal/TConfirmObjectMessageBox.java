package com.tedros.fxapi.modal;

import java.io.IOException;

import com.tedros.core.TInternationalizationEngine;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class TConfirmObjectMessageBox<T> extends TMessageBox {
	 	 
	private SimpleObjectProperty<T> confirm;
	
	public TConfirmObjectMessageBox(String text, T object) {
		try{
			
			TInternationalizationEngine iEngine = TInternationalizationEngine.getInstance(null);
			
			tAddMessage(iEngine.getString(text));
			confirm = new SimpleObjectProperty<>();
			
			Button yesBtn = new Button();
			yesBtn.setId("t-last-button");
			yesBtn.setText(iEngine.getString("#{tedros.fxapi.button.yes}"));
					
			
			Button noBtn = new Button();
			noBtn.setId("t-last-button");
			noBtn.setText(iEngine.getString("#{tedros.fxapi.button.no}"));
					
			HBox box = new HBox();
			box.getChildren().addAll(yesBtn, noBtn);
			box.setSpacing(10);
			box.setAlignment(Pos.CENTER);
			gettMessageVBox().getChildren().addAll(box);
			
			gettMessageVBox().setAlignment(Pos.CENTER);
			
			yesBtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					confirm.set(object);
				}
			});
			
			noBtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					confirm.set(null);
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

	public ReadOnlyObjectProperty<T> gettConfirmProperty() {
		return confirm;
	}
	
}
