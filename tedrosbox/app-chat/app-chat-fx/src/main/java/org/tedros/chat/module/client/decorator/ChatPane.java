/**
 * 
 */
package org.tedros.chat.module.client.decorator;

import org.tedros.core.TLanguage;
import org.tedros.fx.TFxKey;
import org.tedros.fx.control.TButton;
import org.tedros.fx.control.TTextAreaField;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToolBar;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * @author Davis Gordon
 *
 */
public class ChatPane extends BorderPane {
	

	private TTextAreaField msgArea;
	private TButton sendBtn;
	private TButton fileBtn;
	private ScrollPane sp;
	private GridPane gp;
	private TLanguage iEngine;
	/**
	 * 
	 */
	public ChatPane() {
		load();
	}
	
	private void load() {
		iEngine = TLanguage.getInstance();
		
		gp = new GridPane();
		gp.setHgap(10);
		gp.setVgap(8);
		
		sp = new ScrollPane();
		sp.setStyle("-fx-background-color: transparent");
		sp.setFitToHeight(true);
		sp.setFitToWidth(true);
		sp.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		sp.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		sp.setPadding(new Insets(10));
		sp.setContent(gp);
		
		this.msgArea = new TTextAreaField();
		this.msgArea.setWrapText(true);
		this.msgArea.setPrefHeight(100);
		this.msgArea.setMinHeight(100);
		this.msgArea.setMaxHeight(100);
		this.msgArea.setStyle("-fx-background-radius: 0; -fx-border-radius: 0;");
		this.sendBtn = new TButton(iEngine.getString(TFxKey.BUTTON_SEND));
		this.fileBtn = new TButton(iEngine.getString(TFxKey.BUTTON_SELECT_FILE));
		
		ToolBar tb = new ToolBar();
		tb.getStyleClass().add("t-panel-background-color");
		tb.getItems().addAll(sendBtn, fileBtn);
		

		VBox vb = new VBox(5);
		VBox.setVgrow(msgArea, Priority.ALWAYS);
		vb.getChildren().addAll(msgArea, tb);
		
		setCenter(sp);
		setBottom(vb);
	}
	
}
