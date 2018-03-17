/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 09/11/2013
 */
package com.tedros.test;


import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.TimelineBuilder;
import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import com.tedros.context.TedrosContext;
import com.tedros.fxapi.control.TBigDecimalField;
import com.tedros.fxapi.control.TBigIntegerField;
import com.tedros.fxapi.control.TDoubleField;
import com.tedros.fxapi.control.TIntegerField;
import com.tedros.fxapi.control.TLongField;
import com.tedros.fxapi.control.validator.TControlValidator;
import com.tedros.fxapi.control.validator.TFieldResult;
import com.tedros.fxapi.control.validator.TValidatorResult;
import com.tedros.fxapi.form.TDefaultForm;
import com.tedros.fxapi.form.THBoxForm;
import com.tedros.fxapi.form.TVBoxForm;
import com.tedros.fxapi.modal.TConfirmMessageBox;
import com.tedros.fxapi.modal.TMessageBox;
import com.tedros.fxapi.reader.TBigDecimalReader;
import com.tedros.fxapi.reader.TBigIntegerReader;
import com.tedros.fxapi.reader.TBooleanReader;
import com.tedros.fxapi.reader.TDoubleReader;
import com.tedros.fxapi.reader.TFloatReader;
import com.tedros.fxapi.reader.TIntegerReader;
import com.tedros.fxapi.reader.TLongReader;
import com.tedros.fxapi.reader.TStringReader;
import com.tedros.test.component.file.FileTestModel;
import com.tedros.test.component.file.FileTestModelView;
import com.tedros.util.TFileUtil;
import com.tedros.util.TUrlUtil;
import com.tedros.util.TedrosFolderEnum;

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
        
        final String customStyleCssUrl = TedrosContext.getExternalURLFile(TedrosFolderEnum.CONF_FOLDER, "custom-styles.css").toExternalForm();
    	final String immutableStylesCssUrl = TedrosContext.getExternalURLFile(TedrosFolderEnum.CONF_FOLDER, "immutable-styles.css").toExternalForm();
    	 
    	scene.getStylesheets().addAll(customStyleCssUrl, immutableStylesCssUrl);
    
    	File backgroundCss = new File(TFileUtil.getTedrosFolderPath()+TedrosFolderEnum.CONF_FOLDER.getFolder()+"background-image.css");
		if(backgroundCss.exists()){
			scene.getStylesheets().addAll(TedrosContext.getExternalURLFile(TedrosFolderEnum.CONF_FOLDER, "background-image.css").toExternalForm());
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
        
        final String customStyleCssUrl = TedrosContext.getExternalURLFile(TedrosFolderEnum.CONF_FOLDER, "custom-styles.css").toExternalForm();
    	final String immutableStylesCssUrl = TedrosContext.getExternalURLFile(TedrosFolderEnum.CONF_FOLDER, "immutable-styles.css").toExternalForm();
    	 
    	scene.getStylesheets().addAll(customStyleCssUrl, immutableStylesCssUrl);
    
    	File backgroundCss = new File(TFileUtil.getTedrosFolderPath()+TedrosFolderEnum.CONF_FOLDER.getFolder()+"background-image.css");
		if(backgroundCss.exists()){
			scene.getStylesheets().addAll(TedrosContext.getExternalURLFile(TedrosFolderEnum.CONF_FOLDER, "background-image.css").toExternalForm());
		}
        
        stage.setScene(scene);
	}

	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	public static URL getExternalURLFile(TedrosFolderEnum tedrosFolderEnum, String fileName){
		try {
			String path = TFileUtil.getTedrosFolderPath()+tedrosFolderEnum.getFolder()+fileName;
			return TUrlUtil.getURL(path);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
