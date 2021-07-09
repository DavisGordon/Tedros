package com.tedros.settings.layout.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import com.tedros.core.TInternationalizationEngine;
import com.tedros.core.context.TedrosContext;
import com.tedros.core.style.TStyleResourceName;
import com.tedros.settings.start.TConstant;
import com.tedros.util.TFileUtil;
import com.tedros.util.TedrosFolderEnum;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;

public class EditarFundoView extends StackPane {
	
	@FXML public Label viewTitleLabel;
    @FXML public Button aplicarBtn;
    @FXML private StackPane formSpace;
    final private SelecionarImagemView selecionarImagemView;
    final private SimpleStringProperty backgroundStyleProperty = new SimpleStringProperty();
    
    
    
	//public EditarFundoView(final TedrosTemplate template) throws Exception {
    public EditarFundoView() throws Exception {
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("customizar_fundo.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();
		
        this.selecionarImagemView = new SelecionarImagemView();
        
        this.viewTitleLabel.setText(TInternationalizationEngine.getInstance(TConstant.UUI).getString("#{label.background}"));
        
        final ScrollPane scroll = new ScrollPane();
        scroll.setStyle("-fx-background-color: transparent;");
        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);
        scroll.setContent(selecionarImagemView);
        aplicarBtn.setText(TInternationalizationEngine.getInstance(TConstant.UUI).getString("#{label.apply}"));
        aplicarBtn.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					File css = new File(TFileUtil.getTedrosFolderPath()+TedrosFolderEnum.CONF_FOLDER.getFolder()+"background-image.css");
					String propFilePath = TFileUtil.getTedrosFolderPath()+TedrosFolderEnum.CONF_FOLDER.getFolder()+TStyleResourceName.BACKGROUND_STYLE;
					String imageName = (String) selecionarImagemView.imageView.getUserData();
					String repeat = selecionarImagemView.repetirCheckBox.isSelected() ? "repeat" : "no-repeat";
					String ativar = selecionarImagemView.ativarCheckBox.isSelected() ? "true" : "false";
					FileOutputStream fos = new FileOutputStream(propFilePath);
					Properties prop = new Properties();
					prop.setProperty("image", imageName);
					prop.setProperty("repeat", repeat);
					prop.setProperty("ativar", ativar);
					prop.store(fos, "background styles");
					
					StringBuffer sbf = new StringBuffer("#t-tedros-color { -fx-background-size: cover; -fx-background-image: url(\"../IMAGES/FUNDO/"+imageName+"\"); -fx-background-repeat: "+repeat+"; }");
					if(css.exists()){
						css.delete();
						css.createNewFile();
					}
					TFileUtil.saveFile(sbf.toString(), css);
					TedrosContext.reloadStyle();
				} catch (FileNotFoundException  e1) {
					e1.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
        
        formSpace.getChildren().add(scroll);	
	}

	

}
