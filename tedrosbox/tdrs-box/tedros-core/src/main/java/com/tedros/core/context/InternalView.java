package com.tedros.core.context;

import java.util.List;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Config the view layout
 * */
public class InternalView extends Pane {
    
    private boolean isFixedSize;
    
    /**
     * Create resizable View
     */
    public InternalView() {
    	isFixedSize = false;
        VBox.setVgrow(this, Priority.ALWAYS);
        setMaxWidth(Double.MAX_VALUE);
        setMaxHeight(Double.MAX_VALUE);
    }

    /**
     * Create fixed size view
     * 
     */
    public InternalView(double width, double height) {
    	isFixedSize = true;
        setMaxSize(width, height);
        setPrefSize(width, height);
        setMaxSize(width, height);
    }
    
    @Override 
    protected void layoutChildren() {
        if (isFixedSize) {
            super.layoutChildren();
        } else {
            List<Node> managed = getManagedChildren();
            double width = getWidth();
            ///System.out.println("width = " + width);
            double height = getHeight();
            ///System.out.println("height = " + height);
            double top = getInsets().getTop();
            double right = getInsets().getRight();
            double left = getInsets().getLeft();
            double bottom = getInsets().getBottom();
            for (int i = 0; i < managed.size(); i++) {
                Node child = managed.get(i);
                layoutInArea(child, left, top,
                               width - left - right, height - top - bottom,
                               0, Insets.EMPTY, true, true, HPos.CENTER, VPos.CENTER);
            }
        }
    }    
}
