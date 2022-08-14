package org.tedros.fx.layout;

import java.util.List;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class TAutoResizePane extends StackPane {

	@Override protected void layoutChildren() {
        
        List<Node> managed = getManagedChildren();
        double width = getWidth();
        //System.out.println("width = " + width);
        double height = getHeight();
        //System.out.println("height = " + height);
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
