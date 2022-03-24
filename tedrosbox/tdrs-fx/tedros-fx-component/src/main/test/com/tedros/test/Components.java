/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 30/01/2014
 */
package com.tedros.test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import com.tedros.util.TFileUtil;
import com.tedros.util.TUrlUtil;
import com.tedros.util.TedrosFolder;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.DepthTest;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class Components extends Application {
	
	
	@Override
	public void start(Stage stage) throws Exception {
		iniciarPalco(stage);
	}

	private void iniciarPalco(Stage stage) {
		StackPane layerPane = new StackPane();
        //stage.initStyle(StageStyle.TRANSPARENT);
        // create window resize button
        
        // create root
        BorderPane root = new BorderPane();
        root.getStyleClass().add("application");
        root.setId("t-tedros-color");
        
        layerPane.setDepthTest(DepthTest.DISABLE);
        layerPane.getChildren().add(root);
        
        Scene scene = new Scene(layerPane, 1020, 600, false);
        
        // add css 
        scene.getStylesheets().addAll(getExternalURLFile(TedrosFolder.CONF_FOLDER, "custom-styles.css").toExternalForm(),
        		getExternalURLFile(TedrosFolder.CONF_FOLDER, "buttons.css").toExternalForm());
        
        File backgroundCss = new File(TFileUtil.getTedrosFolderPath()+TedrosFolder.CONF_FOLDER.getFolder()+"background-image.css");
		if(backgroundCss.exists()){
			scene.getStylesheets().addAll(getExternalURLFile(TedrosFolder.CONF_FOLDER, "background-image.css").toExternalForm());
		}
		
		// create main toolbar
		ToolBar toolBar = new ToolBar();
        toolBar.setId("t-main-toolbar");
        
        //add appName
        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.BLACK);
        
        Label appName = new Label();
        appName.setEffect(ds);
        appName.setCache(true);
        appName.setText("Tedros Box");
        appName.setId("t-app-name");
        
        HBox h = new HBox();
        h.setAlignment(Pos.CENTER_LEFT);
        HBox.setMargin(appName, new Insets(0,15,0,0));
        h.getChildren().addAll(appName);
        
        HBox.setMargin(h, new Insets(0,0,0,40));
        toolBar.getItems().add(h);
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        toolBar.getItems().add(spacer);
        toolBar.setPrefHeight(50);
        toolBar.setMinHeight(50);
        toolBar.setMaxHeight(50);
        GridPane.setConstraints(toolBar, 0, 0);
        
        root.setTop(toolBar);
        
        Entidade entidade = new Entidade("abcdefgh", 2.2D, 22);
        EntidadeProperty eProp = new EntidadeProperty(entidade);
        
        TextField tF = new TextField();
        //tF.setEditable(true);
        tF.textProperty().bindBidirectional(eProp.getTexto());
        
        VBox vbox = new VBox(8);
        vbox.getChildren().add(tF);
        
        root.setCenter(vbox);
        
        stage.setScene(scene);
        stage.show();
	}
	
	public static URL getExternalURLFile(TedrosFolder tedrosFolderEnum, String fileName){
		try {
			String path = TFileUtil.getTedrosFolderPath()+tedrosFolderEnum.getFolder()+fileName;
			return TUrlUtil.getURL(path);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
