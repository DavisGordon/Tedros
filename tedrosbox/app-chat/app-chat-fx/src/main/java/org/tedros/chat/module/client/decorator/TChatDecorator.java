/**
 * 
 */
package org.tedros.chat.module.client.decorator;

import org.tedros.chat.module.client.model.TChatMV;
import org.tedros.core.security.model.TUser;
import org.tedros.fx.TFxKey;
import org.tedros.fx.control.TButton;
import org.tedros.fx.control.TTextAreaField;
import org.tedros.fx.presenter.dynamic.decorator.TDynaViewSimpleBaseDecorator;

import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * @author Davis Gordon
 *
 */
public class TChatDecorator extends TDynaViewSimpleBaseDecorator<TChatMV> {

	private GridPane msgPane;
	private VBox msgsVB;
	private ListView<TUser> usrLV;
	private TTextAreaField msgArea;
	private TButton sendBtn;
	private TButton fileBtn;
	
	@Override
	public void decorate() {
		
		this.usrLV = new ListView<>();
		super.addItemInTLeftContent(usrLV);
		this.usrLV.getStyleClass().add("t-panel-background-color");
		
		this.msgPane = new GridPane();
		this.msgPane.setHgap(10);
		this.msgPane.setVgap(8);
		
		
		//this.msgsVB = new VBox(20);
		//this.msgsVB.autosize();
		//StackPane sp = new StackPane(msgsVB);
		ScrollPane sp = new ScrollPane();
		sp.setStyle("-fx-background-color: transparent");
		sp.setFitToHeight(true);
		sp.setFitToWidth(true);
		sp.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		sp.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		sp.setContent(msgPane);
		sp.setPadding(new Insets(10));
		
		this.msgArea = new TTextAreaField();
		this.msgArea.setWrapText(true);
		this.msgArea.setPrefHeight(100);
		this.msgArea.setMinHeight(100);
		this.msgArea.setMaxHeight(100);
		this.msgArea.setStyle("-fx-background-radius: 0; -fx-border-radius: 0;");
		this.sendBtn = new TButton(super.iEngine.getString(TFxKey.BUTTON_SEND));
		this.fileBtn = new TButton(super.iEngine.getString(TFxKey.BUTTON_SELECT_FILE));
		
		ToolBar tb = new ToolBar();
		tb.getStyleClass().add("t-panel-background-color");
		tb.getItems().addAll(sendBtn, fileBtn);
		
		VBox vb = new VBox(5);
		VBox.setVgrow(msgArea, Priority.ALWAYS);
		vb.getChildren().addAll(sp, msgArea, tb);
		super.addItemInTCenterContent(vb);
		
		
	}

	/**
	 * @return the msgsVB
	 */
	public VBox getMsgsVB() {
		return msgsVB;
	}

	/**
	 * @return the usrLV
	 */
	public ListView<TUser> getUsrLV() {
		return usrLV;
	}

	/**
	 * @return the msgArea
	 */
	public TTextAreaField getMsgArea() {
		return msgArea;
	}

	/**
	 * @return the sendBtn
	 */
	public TButton getSendBtn() {
		return sendBtn;
	}

	/**
	 * @return the fileBtn
	 */
	public TButton getFileBtn() {
		return fileBtn;
	}

	/**
	 * @return the msgPane
	 */
	public GridPane getMsgPane() {
		return msgPane;
	}

}
