package org.tedros.fx.modal;

import java.util.function.Consumer;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class TModalPane extends StackPane {

	private Consumer<Node> closeCallback;
	
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
	 * Open the modal
	 * */
	public void showModal(Node node) {
		this.showModal(node,  null);
	}
	
	/**
	 * Open the modal
	 * */
	public void showModal(Node node, boolean closeModalOnMouseClick) {
		if(closeModalOnMouseClick)
			this.showModal(node, ev->{
				ev.consume();
				this.hideModal();
			});
		else
			this.showModal(node);
	}
	
	/**
	 * Open the modal
	 * */
	public void showModal(Node node, boolean closeModalOnMouseClick, Consumer<Node> closeCallback) {
		if(closeModalOnMouseClick)
			this.showModal(node, ev->{
				ev.consume();
				closeCallback.accept(this.getChildren().get(0));
				this.hideModal();
			});
		else {
			this.closeCallback = closeCallback;
			this.showModal(node);
		}
	}
	/**
	 * Open the modal and consume the closeAction on the mouse clicked event
	 * this not hide the modal.
	 * */
	public void showModal(Node node, Consumer<MouseEvent> closeAction) {
		
		if(closeAction!=null)
			setOnMouseClicked(ev->{
				closeAction.accept(ev);
	        });
		else
			setOnMouseClicked(null);
		getChildren().clear();
		getChildren().add(node);
		StackPane.setMargin(node, new Insets(20));
		StackPane.setAlignment(node, Pos.CENTER);
        /*setOpacity(0);
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
        tl.play();*/
        setVisible(true);
	 }
	
	/**
	 * Close the modal and clean it 
	 * */
	public void hideModal() {
		setVisible(false);

    	if(closeCallback!=null)
    		closeCallback.accept(getChildren().get(0));
    	getChildren().clear();
       /* setCache(true);
        Timeline tl = new Timeline();
        tl.getKeyFrames().add(
            new KeyFrame(Duration.millis(400), 
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent t) {
                    	if(closeCallback!=null)
                    		closeCallback.accept(getChildren().get(0));
                        getChildren().clear();
                        setCache(false);
                        setVisible(false);
                    }
                },
                new KeyValue(opacityProperty(),0, Interpolator.LINEAR)
        ));
        tl.play();*/
	}
	
	
}
