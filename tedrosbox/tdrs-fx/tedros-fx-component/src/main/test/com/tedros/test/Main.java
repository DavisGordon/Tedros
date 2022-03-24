/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 09/11/2013
 */
package com.tedros.test;


import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import com.tedros.core.context.TedrosContext;
import com.tedros.fxapi.form.TDefaultForm;
import com.tedros.test.component.file.FileTestModel;
import com.tedros.test.component.file.FileTestModelView;
import com.tedros.util.TFileUtil;
import com.tedros.util.TUrlUtil;
import com.tedros.util.TedrosFolder;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.TimelineBuilder;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class Main extends Application {

	private Scene scene;
	private StackPane root;
	private StackPane modalDimmer;
	private BorderPane parede;
	private VBox prateleira;
	
	@Override
	public void start(Stage stage) throws Exception {
		
		//iniciarPalco(stage);
		root = new StackPane();
		scene = new Scene(root, 800, 300, false);
		
		root.setId("t-tedros-color");
        
        final String customStyleCssUrl = TedrosContext.getExternalURLFile(TedrosFolder.CONF_FOLDER, "custom-styles.css").toExternalForm();
    	final String immutableStylesCssUrl = TedrosContext.getExternalURLFile(TedrosFolder.CONF_FOLDER, "immutable-styles.css").toExternalForm();
    	 
    	scene.getStylesheets().addAll(customStyleCssUrl, immutableStylesCssUrl);
    
    	File backgroundCss = new File(TFileUtil.getTedrosFolderPath()+TedrosFolder.CONF_FOLDER.getFolder()+"background-image.css");
		if(backgroundCss.exists()){
			scene.getStylesheets().addAll(TedrosContext.getExternalURLFile(TedrosFolder.CONF_FOLDER, "background-image.css").toExternalForm());
		}
        
        
		
		/*ScrollPane scroll = new ScrollPane();
		scroll.setContent(prateleira);
		scroll.fitToHeightProperty();
		scroll.fitToWidthProperty();*/
		
		
		
		//prateleira.getChildren().add(TList)
		
		TDefaultForm<FileTestModelView> form = new TDefaultForm<FileTestModelView>(new FileTestModelView(new FileTestModel()));
		
		root.getChildren().add(form);
		stage.setScene(scene);
		stage.show();
		
//		prateleira.getChildren().add(form);
//		showModal(scroll, false);
		
		
	}
		


	
	
	/**
	 * Abre modal
	 * */
	public void showModal(Node message, boolean addEvent) {
		setEventToCloseModal(addEvent);
		modalDimmer.getChildren().add(message);
        modalDimmer.setOpacity(0);
        modalDimmer.setVisible(true);
        modalDimmer.setCache(true);
        TimelineBuilder.create().keyFrames(
            new KeyFrame(Duration.millis(400), 
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent t) {
                        modalDimmer.setCache(false);
                    }
                },
                new KeyValue(modalDimmer.opacityProperty(),1, Interpolator.LINEAR)
        )).build().play();
        modalDimmer.setVisible(true);
	 }
	
	/**
	 * Fecha modal
	 * */
	public void hideModal() {
        modalDimmer.setCache(true);
        TimelineBuilder.create().keyFrames(
            new KeyFrame(Duration.millis(400), 
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent t) {
                        modalDimmer.setCache(false);
                        modalDimmer.setVisible(false);
                        modalDimmer.getChildren().clear();
                    }
                },
                new KeyValue(modalDimmer.opacityProperty(),0, Interpolator.LINEAR)
        )).build().play();
	}
	
	public void setEventToCloseModal(boolean addEvent) {
		if(addEvent)
			modalDimmer.setOnMouseClicked(new EventHandler<MouseEvent>() {
	            public void handle(MouseEvent t) {
	                t.consume();
	                hideModal();
	            }
	        });
		else
			modalDimmer.setOnMouseClicked(null);
	}
	
	/**
	 * Inicia o Palco
	 * */
	public void iniciarPalco(Stage stage) {
		
		root = new StackPane();
		parede = new BorderPane();
		prateleira = new VBox(5);
		modalDimmer = new StackPane();
		
		root.setId("t-tedros-color");
		root.getStyleClass().add("application");
		root.setAlignment(Pos.CENTER_LEFT);
		
		prateleira.setPadding(new Insets(25));
		parede.setLeft(prateleira);
		
		modalDimmer.setId("t-modal-dimmer");
		modalDimmer.setAlignment(Pos.CENTER);
		modalDimmer.setVisible(false);
		root.getChildren().addAll(parede, modalDimmer);
		//scene = new Scene(root, 1920, 1080, false);
		scene = new Scene(root, 800, 300, false);
        
        final String customStyleCssUrl = TedrosContext.getExternalURLFile(TedrosFolder.CONF_FOLDER, "custom-styles.css").toExternalForm();
    	final String immutableStylesCssUrl = TedrosContext.getExternalURLFile(TedrosFolder.CONF_FOLDER, "immutable-styles.css").toExternalForm();
    	 
    	scene.getStylesheets().addAll(customStyleCssUrl, immutableStylesCssUrl);
    
    	File backgroundCss = new File(TFileUtil.getTedrosFolderPath()+TedrosFolder.CONF_FOLDER.getFolder()+"background-image.css");
		if(backgroundCss.exists()){
			scene.getStylesheets().addAll(TedrosContext.getExternalURLFile(TedrosFolder.CONF_FOLDER, "background-image.css").toExternalForm());
		}
        
        stage.setScene(scene);
	}

	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	public static URL getExternalURLFile(TedrosFolder TedrosFolder, String fileName){
		try {
			String path = TFileUtil.getTedrosFolderPath()+TedrosFolder.getFolder()+fileName;
			return TUrlUtil.getURL(path);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
