package org.tedros.fx.modal;

import org.tedros.fx.control.TButton;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.WeakEventHandler;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

public class TConfirmMessageBox extends TMessageBox {
	 	 
	private SimpleIntegerProperty confirm;
	
	public TConfirmMessageBox(String text) {
		super(text);
		try{	
			size += 50;
			confirm = new SimpleIntegerProperty(-1);
			
			TButton yesBtn = new TButton();
					yesBtn.setText(iEngine.getString("#{tedros.fxapi.button.yes}"));
			
			TButton noBtn =new TButton();
					noBtn.setText(iEngine.getString("#{tedros.fxapi.button.no}"));
			
			HBox box = new HBox();
			box.getChildren().addAll(yesBtn, noBtn);
			box.setAlignment(Pos.CENTER);
			super.messagesPane.getChildren().add(box);
			
			EventHandler<ActionEvent> yevh = ev ->{
				confirm.set(1);
			};
			repo.add("yesevh", yevh);
			yesBtn.setOnAction(new WeakEventHandler<>(yevh));
			EventHandler<ActionEvent> nevh = ev ->{
				confirm.set(0);
			};
			repo.add("noevh", nevh);
			noBtn.setOnAction(new WeakEventHandler<>(nevh));
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public ReadOnlyIntegerProperty tConfirmProperty() {
		return confirm;
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.modal.TMessageBox#tDispose()
	 */
	@Override
	public void tDispose() {
		super.tDispose();
		this.confirm = null;
	}
	
}
