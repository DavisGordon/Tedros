package org.tedros.fx.modal;

import org.tedros.core.TLanguage;
import org.tedros.fx.control.TButton;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

public class TConfirmObjectMessageBox<T> extends TMessageBox {
	 	 
	private SimpleObjectProperty<T> confirm;
	
	public TConfirmObjectMessageBox(String text, T object) {
		try{
			
			TLanguage iEngine = TLanguage.getInstance(null);
			
			tAddMessage(iEngine.getString(text));
			confirm = new SimpleObjectProperty<>();
			
			TButton yesBtn = new TButton();
			yesBtn.setText(iEngine.getString("#{tedros.fxapi.button.yes}"));
					
			
			TButton noBtn = new TButton();
			noBtn.setText(iEngine.getString("#{tedros.fxapi.button.no}"));
					
			HBox box = new HBox();
			box.getChildren().addAll(yesBtn, noBtn);
			box.setAlignment(Pos.CENTER);
			gettMessageVBox().getChildren().addAll(box);
			
			gettMessageVBox().setAlignment(Pos.CENTER);
			
			yesBtn.setOnAction(e -> {
					confirm.set(object);
			});
			
			noBtn.setOnAction(e -> {
					confirm.set(null);
			});
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public ReadOnlyObjectProperty<T> gettConfirmProperty() {
		return confirm;
	}
	
}
