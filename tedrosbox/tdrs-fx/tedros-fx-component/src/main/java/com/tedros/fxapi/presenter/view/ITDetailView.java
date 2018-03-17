package com.tedros.fxapi.presenter.view;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public interface ITDetailView {

	public StackPane getLayout();

	public void setLayout(StackPane layout);

	public Button getNewBtn();

	public void setNewBtn(Button newBtn);

	public Button getRemoveBtn();

	public void setRemoveBtn(Button removeBtn);

	public Pane getMenuBox();

	public void setMenuBox(Pane menuBox);

	public ToolBar getMenuBar();

	public void setMenuBar(ToolBar menuBar);
	
	public void showModal(Node message, boolean closeModalOnMouseClick);
	
	public void hideModal();

}