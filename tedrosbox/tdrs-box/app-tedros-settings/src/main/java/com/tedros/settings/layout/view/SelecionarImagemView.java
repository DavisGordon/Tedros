package com.tedros.settings.layout.view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class SelecionarImagemView extends BorderPane {

	@FXML protected Button selecioneBtn;
	@FXML protected StackPane imagePane;
    @FXML protected ImageView imageView;
    @FXML protected HBox imagens;
    @FXML protected CheckBox ativarCheckBox;
    @FXML protected CheckBox repetirCheckBox;
    
	
	public SelecionarImagemView() throws IOException {
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("selecionar_imagem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();	
        this.getStyleClass().add("t-content-background-color");
        this.ativarCheckBox.setVisible(false);
        new SelecionarImagemPresenter(this);
	}

	
	
}
