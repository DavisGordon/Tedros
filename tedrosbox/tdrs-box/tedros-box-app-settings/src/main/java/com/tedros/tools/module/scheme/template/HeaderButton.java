package com.tedros.tools.module.scheme.template;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * 
 * */
public class HeaderButton extends HBox{
  
	public HeaderButton()
    {
		super(4D);
        setMaxHeight(17);
        Button closeBtn = new Button();
        closeBtn.setId("t-window-close");
        Button minBtn = new Button();
        minBtn.setId("t-window-min");
        Button maxBtn = new Button();
        maxBtn.setId("t-window-max");
        HBox.setHgrow(closeBtn, Priority.ALWAYS);
        HBox.setHgrow(minBtn, Priority.ALWAYS);
        HBox.setHgrow(maxBtn, Priority.ALWAYS);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(8, 8, 2, 8));
        getStyleClass().add("t-panel-color");
		setStyle("-fx-background-radius: 6; -fx-background-insets: 1;"); 
		
        getChildren().addAll(minBtn, maxBtn, closeBtn);
    }

}
