package com.tedros.core.context;

import com.tedros.core.TInternationalizationEngine;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * 
 * */
public class TedroxBoxHeaderButton extends HBox{

	private Stage stage;
    private Rectangle2D backupWindowBounds;
    private boolean maximized;
	    
	public TedroxBoxHeaderButton(final Stage stage)
    {
		super(4D);
		
		TInternationalizationEngine iEngine = TInternationalizationEngine.getInstance(null);
        
        setMaxHeight(17);
        backupWindowBounds = null;
        maximized = false;
        this.stage = stage;
        Button closeBtn = new Button();
        closeBtn.setId("t-window-close");
        closeBtn.setTooltip(new Tooltip(iEngine.getString("#{tedros.close}")));
        closeBtn.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
            public void handle(ActionEvent actionEvent){    
        		TedrosProcess.stopAllServices();
                Platform.exit();
            }            
        });
        Button minBtn = new Button();
        minBtn.setId("t-window-min");
        minBtn.setTooltip(new Tooltip(iEngine.getString("#{tedros.minimize}")));
        minBtn.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
            public void handle(ActionEvent actionEvent){
                stage.setIconified(true);
            }       
        });
        Button maxBtn = new Button();
        maxBtn.setId("t-window-max");
        maxBtn.setTooltip(new Tooltip(iEngine.getString("#{tedros.maximize}")));
        maxBtn.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent actionEvent){
                toogleMaximized();
            }        
        });
        HBox.setHgrow(closeBtn, Priority.ALWAYS);
        HBox.setHgrow(minBtn, Priority.ALWAYS);
        HBox.setHgrow(maxBtn, Priority.ALWAYS);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(2));
        getChildren().addAll(minBtn, maxBtn, closeBtn);
        //getChildren().add(maxBtn);
    }

    public void toogleMaximized()
    {
        Screen screen = (Screen)Screen.getScreensForRectangle(stage.getX(), stage.getY(), 1.0D, 1.0D).get(0);
        if(maximized){
            maximized = false;
            if(backupWindowBounds != null){
                stage.setX(backupWindowBounds.getMinX());
                stage.setY(backupWindowBounds.getMinY());
                stage.setWidth(backupWindowBounds.getWidth());
                stage.setHeight(backupWindowBounds.getHeight());
            }        
        }else{
            maximized = true;
            backupWindowBounds = new Rectangle2D(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight());
            stage.setX(screen.getVisualBounds().getMinX());
            stage.setY(screen.getVisualBounds().getMinY());
            stage.setWidth(screen.getVisualBounds().getWidth());
            stage.setHeight(screen.getVisualBounds().getHeight());
        }
    }

    public boolean isMaximized(){
        return maximized;
    }    
}
