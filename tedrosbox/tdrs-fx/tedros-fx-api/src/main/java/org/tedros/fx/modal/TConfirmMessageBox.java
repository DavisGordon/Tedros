package org.tedros.fx.modal;

import org.tedros.core.TLanguage;
import org.tedros.fx.control.TButton;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

public class TConfirmMessageBox extends TMessageBox {
	 	 
	private SimpleIntegerProperty confirm;
	
	public TConfirmMessageBox(String text) {
		try{
			
			TLanguage iEngine = TLanguage.getInstance(null);
			
			tAddMessage(iEngine.getString(text));
			confirm = new SimpleIntegerProperty(-1);
			
			TButton yesBtn = new TButton();
					yesBtn.setText(iEngine.getString("#{tedros.fxapi.button.yes}"));
			
			TButton noBtn =new TButton();
					noBtn.setText(iEngine.getString("#{tedros.fxapi.button.no}"));
			
			HBox box = new HBox();
			box.getChildren().addAll(yesBtn, noBtn);
			//box.setSpacing(10);
			box.setAlignment(Pos.CENTER);
			
			super.messagesPane.add(box);
			
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
	
	public ReadOnlyIntegerProperty tConfirmProperty() {
		return confirm;
	}
	
}
