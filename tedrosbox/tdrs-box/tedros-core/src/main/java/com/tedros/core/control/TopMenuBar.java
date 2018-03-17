package com.tedros.core.control;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 * BreadcrumbBar
 */
public class TopMenuBar extends HBox {
    
    public TopMenuBar() {
        super(0);
        getStyleClass().setAll("top-menu-bar");
        setFillHeight(true);
        setAlignment(Pos.CENTER_LEFT);
    }
    
    public void addButton(Button button) {
    	button.setId("t-button");
    	getChildren().add(button);
        
    }
}
