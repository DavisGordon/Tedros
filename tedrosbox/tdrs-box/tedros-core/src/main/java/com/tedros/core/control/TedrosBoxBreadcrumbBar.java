package com.tedros.core.control;

import java.util.ArrayList;
import java.util.List;

import com.tedros.core.context.TedrosContext;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 * The TedrosBox BreadcrumbBar
 */
public class TedrosBoxBreadcrumbBar extends HBox {

    private String path;
    private String deliminator = "/";
    private List<Button> buttons = new ArrayList<Button>();

    public TedrosBoxBreadcrumbBar() {
        super(0);
        getStyleClass().setAll("breadcrumb-bar");
        setFillHeight(true);
        setAlignment(Pos.CENTER_LEFT);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
        String[] parts = path.split(deliminator);
        String pathSoFar = "";
        for (int i=0; i < Math.max(parts.length,buttons.size()); i++) {
            if (i<parts.length) {
                // we have a part for this index
                pathSoFar += (i==0) ? parts[i] : deliminator + parts[i];
                final String currentPath = pathSoFar;
                Button button = null;
                if (i<buttons.size()) {
                    // alread have a button
                    button = buttons.get(i);
                } else {
                    button = new Button(parts[i]);
                    button.setMaxHeight(Double.MAX_VALUE);
                    buttons.add(button);
                    getChildren().add(button);
                }
                button.setVisible(true) ;
                button.setText(parts[i]);
                button.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                       TedrosContext.setPagePathProperty(currentPath, true, true, true);
                    }
                });
                if (i == parts.length-1) {
                    if(i==0) {
                        button.getStyleClass().setAll("button","only-button");
                    } else {
                        button.getStyleClass().setAll("button","last-button");
                    }
                } else if (i==0) {
                    button.getStyleClass().setAll("button","first-button");
                } else {
                    button.getStyleClass().setAll("button","middle-button");
                }
            }else {
                buttons.get(i).setVisible(false);
            }
        }
    }
}
