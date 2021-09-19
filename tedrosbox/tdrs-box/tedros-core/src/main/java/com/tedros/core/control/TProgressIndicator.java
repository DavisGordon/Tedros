/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 22/01/2014
 */
package com.tedros.core.control;

import com.tedros.core.context.TedrosContext;

import javafx.animation.Animation.Status;
import javafx.animation.FadeTransition;
import javafx.beans.binding.BooleanBinding;
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
	private Pane pane;
	public TProgressIndicator(final Pane pane) {
		initialize();
		this.pane = pane;
		setMargin(50);
        this.pane.getChildren().addAll(veil, progressIndicator);
	}

	/**
	 * @param pane
	 */
	public void setMargin(double val) {
		if(pane instanceof StackPane){
			StackPane.setMargin(progressIndicator, new Insets(val));
			StackPane.setAlignment(progressIndicator, Pos.CENTER);
		}else if(pane instanceof BorderPane){
			BorderPane.setMargin(progressIndicator, new Insets(val));
			BorderPane.setAlignment(progressIndicator, Pos.CENTER);
		}
	}
	
	public void bind(final BooleanBinding bb){
		removeBind();
		veil.visibleProperty().bind(bb);
		progressIndicator.visibleProperty().bind(bb);
		if(bb.get() && !ft.getStatus().equals(Status.RUNNING))
			ft.play();
	}
	
	public void bind(ReadOnlyBooleanProperty bb) {
		removeBind();
		veil.visibleProperty().bind(bb);
		progressIndicator.visibleProperty().bind(bb);
		if(bb.get() && !ft.getStatus().equals(Status.RUNNING))
			ft.play();
		
	}
	
	public void removeBind(){
		veil.visibleProperty().unbind();
		progressIndicator.visibleProperty().unbind();
	}
	
	public void setSmallLogo() {
		Image img = new Image(TedrosContext.getImageInputStream("logo-tedros-small.png"));
        progressIndicator.setImage(img);
	}
	
	public void setMediumLogo() {
		Image img = new Image(TedrosContext.getImageInputStream("logo-tedros-medium.png"));
        progressIndicator.setImage(img);
	}
	
	public void setLogo() {
		Image img = new Image(TedrosContext.getImageInputStream("logo-tedros.png"));
        progressIndicator.setImage(img);
	}
	
	private void initialize() {
		veil = new Region();
		veil.setVisible(false);
		veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4); -fx-background-radius: 20 20 20 20;");
		progressIndicator = new ImageView();
		progressIndicator.setVisible(false);
		//progressIndicator.setMaxSize(50, 50);
        
        setLogo();
        
        ft = new FadeTransition(Duration.millis(2000), progressIndicator);
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
