package org.tedros.core;


import org.tedros.core.context.TedrosContext;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
/**
 * Opens a modal text message
 * 
 * @author Davis Gordon
 * */
public class ModalMessage extends VBox{
	
    /** Drag offsets for window dragging */
    
    
    public ModalMessage(String message) {
        
    	setId("ProxyDialog");
        
        setSpacing(10);
        setMaxSize(430, USE_PREF_SIZE);
        
        // block mouse clicks
        setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {
                t.consume();
            }
        });

        Text explanation = new Text(message);
        explanation.setWrappingWidth(400);

        BorderPane explPane = new BorderPane();
        VBox.setMargin(explPane, new Insets(5, 5, 5, 5));
        explPane.setCenter(explanation);
        BorderPane.setMargin(explanation, new Insets(5, 5, 5, 5));

        TLanguage iEngine = TLanguage.getInstance(null);
        
        // create title
        Label title = new Label("warning");//new Label(iEngine.getString("#{tedros.warning}"));
        title.setId("title");
        title.setMaxWidth(Double.MAX_VALUE);
        title.setAlignment(Pos.CENTER);
        getChildren().add(title);
        
        
        
        Button cancelBtn = new Button("close"/*iEngine.getString("#{tedros.close}")*/);
        cancelBtn.setId("cancelButton");
        cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
                TedrosContext.hideMessage();
            }
        });
        cancelBtn.setMinWidth(74);
        cancelBtn.setPrefWidth(74);
        HBox.setMargin(cancelBtn, new Insets(0, 8, 0, 0));
        
        HBox bottomBar = new HBox(0);
        bottomBar.setAlignment(Pos.BASELINE_RIGHT);
        bottomBar.getChildren().addAll(cancelBtn);
        VBox.setMargin(bottomBar, new Insets(20, 5, 5, 5));
        getChildren().addAll(explPane, bottomBar);
    }


    	
}
