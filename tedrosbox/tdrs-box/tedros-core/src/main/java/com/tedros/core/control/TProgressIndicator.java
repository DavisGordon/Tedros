/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 22/01/2014
 */
package com.tedros.core.control;

import com.tedros.core.context.TedrosContext;

import javafx.animation.FadeTransition;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 * Progress indicator
 *
 * @author Davis Gordon
 */
public class TProgressIndicator {

	private Region veil;
	private ImageView progressIndicator;
	private FadeTransition ft;
	
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
		progressIndicator = new ImageView();
		progressIndicator.setVisible(false);
		//progressIndicator.setMaxSize(50, 50);
        
        Image img = new Image(TedrosContext.getImageInputStream("logo-tedros.png"));
        
        progressIndicator.setImage(img);
        
        ft = new FadeTransition(Duration.millis(1000), progressIndicator);
        ft.setFromValue(1.0);
        ft.setToValue(0.3);
        ft.setCycleCount(FadeTransition.INDEFINITE);
        ft.setAutoReverse(true);
        
        progressIndicator.visibleProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				if(arg2)
					ft.play();
				else
					ft.stop();
			}
        	
        });
    
        
	}
	
}
