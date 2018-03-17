/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 09/11/2013
 */
package main;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.TimelineBuilder;
import javafx.application.Application;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import org.apache.commons.lang3.RandomStringUtils;

import com.tedros.util.TFileUtil;
import com.tedros.util.TUrlUtil;
import com.tedros.util.TedrosFolderEnum;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class Test extends Application {

	private Scene scene;
	private StackPane root;
	private BorderPane pane;

	
	@Override
	public void start(Stage stage) throws Exception {
		
		iniciarPalco(stage);
		stage.show();
		
		final EntidadeProperty e1 = new EntidadeProperty(new Entidade(RandomStringUtils.randomAlphanumeric(4), 2D, 1)); 
		final EntidadeProperty e2 = new EntidadeProperty(new Entidade(RandomStringUtils.randomAlphanumeric(4), 2D, 1));
		final EntidadeProperty e3 = new EntidadeProperty(new Entidade(RandomStringUtils.randomAlphanumeric(4), 2D, 1));
		final EntidadeProperty e4 = new EntidadeProperty(new Entidade(RandomStringUtils.randomAlphanumeric(4), 2D, 1));
		
		List<EntidadeProperty> list1 = new ArrayList<>();
		list1.add(e1);
		list1.add(e2);
		list1.add(e3);
		list1.add(e4);
		
		SimpleListProperty<EntidadeProperty> listProp1 = new SimpleListProperty<>(FXCollections.observableList(list1));
		
		listProp1.addListener(new ListChangeListener<EntidadeProperty>(){
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends EntidadeProperty> change) {
				
				if(change.next()){
					System.out.println("wasPermutated: " + change.wasPermutated());
					System.out.println("wasReplaced: " + change.wasReplaced());
					System.out.println("wasUpdated: " + change.wasUpdated());
				}else
					System.out.println("next false");
				
			}
			
		});
		
		TimelineBuilder.create().keyFrames(
	            new KeyFrame(Duration.millis(4000), 
	                new EventHandler<ActionEvent>() {
	                    public void handle(ActionEvent t) {
	                    	e3.reload(new Entidade(RandomStringUtils.randomAlphanumeric(4), 2D, 1));
	                    }
	                })
	        )
	        .cycleCount(Animation.INDEFINITE)
	        .build()
	        .play();
		
		
	}

	/**
	 * Inicia o Palco
	 * */
	public void iniciarPalco(Stage stage) {
		
		root = new StackPane();
		pane = new BorderPane();
		
		
		root.getChildren().addAll(pane);
		
		scene = new Scene(root, 800, 300, false);
        
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
