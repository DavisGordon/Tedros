package com.tedros.core.control;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Tedros box resize bar
 * */
public class TedrosBoxResizeBar extends Region{
    
	public TedrosBoxResizeBar(final Stage stage, final double stageMinimumWidthParam, final double stageMinimumHeightParam){
        setId("t-window-resize-button");
        setPrefSize(11D, 11D);
        setOnMousePressed(new EventHandler<MouseEvent>() {
        	@Override
            public void handle(MouseEvent e)
            {
                dragOffsetX = (stage.getX() + stage.getWidth()) - e.getScreenX();
                dragOffsetY = (stage.getY() + stage.getHeight()) - e.getScreenY();
                e.consume();
            }             
        });
        
        setOnMouseDragged(new EventHandler<MouseEvent>() {
        	final double stageMinimumWidth = stageMinimumWidthParam;;
            final double stageMinimumHeight = stageMinimumHeightParam;
        	@SuppressWarnings("rawtypes")
			@Override
            public void handle(MouseEvent e){
                ObservableList screens = Screen.getScreensForRectangle(stage.getX(), stage.getY(), 1.0D, 1.0D);
                Screen screen;
                if(screens.size() > 0)
                    screen = (Screen)Screen.getScreensForRectangle(stage.getX(), stage.getY(), 1.0D, 1.0D).get(0);
                else
                    screen = (Screen)Screen.getScreensForRectangle(0.0D, 0.0D, 1.0D, 1.0D).get(0);
                Rectangle2D visualBounds = screen.getVisualBounds();
                double maxX = Math.min(visualBounds.getMaxX(), e.getScreenX() + dragOffsetX);
                double maxY = Math.min(visualBounds.getMaxY(), e.getScreenY() - dragOffsetY);
                stage.setWidth(Math.max(stageMinimumWidth, maxX - stage.getX()));
                stage.setHeight(Math.max(stageMinimumHeight, maxY - stage.getY()));
                e.consume();
            }            
            
           
        });
    }

    private double dragOffsetX;
    private double dragOffsetY;




}
