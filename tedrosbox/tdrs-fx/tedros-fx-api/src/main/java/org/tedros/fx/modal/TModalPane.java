package org.tedros.fx.modal;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class TModalPane extends StackPane {

	public TModalPane(final Pane pane) {
		initialize();
		pane.getChildren().add(this);	
	}
	
	private void initialize() {
		setId("t-modal-dimmer");
		setAlignment(Pos.CENTER);
		setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {
                t.consume();
                hideModal();
            }
        });
		setVisible(false);
	}
	
	/**
	 * Abre modal
	 * */
	public void showModal(Node message, boolean closeModalOnMouseClick) {
		setEventToCloseModal(closeModalOnMouseClick);
		getChildren().add(message);
        setOpacity(0);
        setVisible(true);
        setCache(true);
        Timeline tl = new Timeline();
        tl.getKeyFrames().add(
            new KeyFrame(Duration.millis(400), 
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent t) {
                        setCache(false);
                    }
                },
                new KeyValue(opacityProperty(),1, Interpolator.LINEAR)
        ));
        tl.play();
        setVisible(true);
	 }
	
	/**
	 * Fecha modal
	 * */
	public void hideModal() {
        setCache(true);
        Timeline tl = new Timeline();
        tl.getKeyFrames().add(
            new KeyFrame(Duration.millis(400), 
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent t) {
                        setCache(false);
                        setVisible(false);
                        getChildren().clear();
                    }
                },
                new KeyValue(opacityProperty(),0, Interpolator.LINEAR)
        ));
        tl.play();
	}
	
	private void setEventToCloseModal(boolean addEvent) {
		if(addEvent)
			setOnMouseClicked(new EventHandler<MouseEvent>() {
	            public void handle(MouseEvent t) {
	                t.consume();
	                hideModal();
	            }
	        });
		else
			setOnMouseClicked(null);
	}
	
}
