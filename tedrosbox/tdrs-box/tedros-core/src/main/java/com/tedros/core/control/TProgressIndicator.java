/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 22/01/2014
 */
package com.tedros.core.control;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * Progress indicator
 *
 * @author Davis Gordon
 */
public class TProgressIndicator {

	private Region veil;
	private ProgressIndicator progressIndicator;
	
	public TProgressIndicator(final Pane pane) {
		initialize();
		if(pane instanceof StackPane){
			StackPane.setMargin(progressIndicator, new Insets(50));
			StackPane.setAlignment(progressIndicator, Pos.CENTER);
		}else if(pane instanceof BorderPane){
			BorderPane.setMargin(progressIndicator, new Insets(50));
			BorderPane.setAlignment(progressIndicator, Pos.CENTER);
		}
        pane.getChildren().addAll(veil, progressIndicator);
	}
	
	public void bind(final ReadOnlyBooleanProperty property){
		removeBind();
		veil.visibleProperty().bind(property);
		progressIndicator.visibleProperty().bind(property);
	}
	
	public void removeBind(){
		veil.visibleProperty().unbind();
		progressIndicator.visibleProperty().unbind();
	}
	
	private void initialize() {
		veil = new Region();
		veil.setVisible(false);
		veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4)");
		progressIndicator = new ProgressIndicator();
		progressIndicator.setVisible(false);
		progressIndicator.setMaxSize(50, 50);
        progressIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
	}
	
}
