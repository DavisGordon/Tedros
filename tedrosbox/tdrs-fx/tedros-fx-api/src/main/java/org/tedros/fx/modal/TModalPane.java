package org.tedros.fx.modal;

import java.util.function.Consumer;

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
		setOnMouseClicked(e -> {
                e.consume();
                hideModal();
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
			setOnMouseClicked(closeAction::accept);
		else
			setOnMouseClicked(null);
		getChildren().clear();
		getChildren().add(node);
		StackPane.setMargin(node, new Insets(20));
		StackPane.setAlignment(node, Pos.CENTER);
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
	}
	
	
}
