package org.tedros.core.ux;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.tedros.core.ITedrosBox;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.style.TThemeUtil;
import org.tedros.util.TLoggerUtil;

import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.DepthTest;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Factory to create new windows (Stages) for views.
 * Allows detaching a view from the main window to a separate stage.
 * 
 * @author Tedros AI
 */
public class TWindowFactory {

    /**
     * Detaches the given view from its current parent and opens it in a new Stage.
     * 
     * @param view  The view (Node) to be detached and moved.
     * @param title The title for the new window.
     */
    public static void detachView(Node view, String title) {
        if (view == null) {
            return;
        }

        // 1. Remove from current parent
        if (view.getParent() != null) {
            if (view.getParent() instanceof Pane) {
                ((Pane) view.getParent()).getChildren().remove(view);
            } else if (view.getParent() instanceof ScrollPane) {
                ((ScrollPane) view.getParent()).setContent(null);
            } else {
                // Try generic approach if possible, or log warning
                // For now assuming Pane or ScrollPane as per TedrosBox structure
            }
        }

        // 2. Create new Stage
        Stage newStage = new Stage();
        if (title != null) {
            newStage.setTitle(title);
        }

        // 3. Set Icons
        // Try to use the same icon as the main app
        try {
            newStage.getIcons().add(new Image(TedrosContext.getImageInputStream("icon-tedros.png")));
        } catch (Exception e) {
            // Ignore if icon not found
        }
        
        // create page area
        Pane pageArea = new Pane() {
            @Override protected void layoutChildren() {
                for (Node child:this.getChildren()) {
                    child.resizeRelocate(0, 0, this.getWidth(), this.getHeight());
                }
            }
        };
        pageArea.setId("t-app-area");
        pageArea.setStyle("-fx-background-color: transparent;");
        pageArea.getChildren().add(view);

        // 4. Create Root for new Scene
        StackPane layerPane = new StackPane();
        layerPane.setDepthTest(DepthTest.DISABLE);    
        layerPane.setStyle("-fx-background-color: transparent;");
        layerPane.getStyleClass().add("application");        
        layerPane.setId("t-tedros-color");
        
        BorderPane mainPane = new BorderPane();
        mainPane.setCenter(pageArea);
        mainPane.setMinWidth(300);
        mainPane.setStyle("-fx-effect: dropshadow( three-pass-box , #000000 , 9, 0.1 , 0 , 4); "
        		+ "-fx-text-fill: #FFFFFF; -fx-background-color: transparent;");
        
        layerPane.getChildren().add(mainPane);
        
        // 5. Create Scene
        boolean is3dSupported = Platform.isSupported(ConditionalFeature.SCENE3D);
        Scene newScene = new Scene(layerPane, 800, 600, is3dSupported); // Default size
        
        if(is3dSupported)
        	newScene.setCamera(new PerspectiveCamera());
        
        // 6. Copy Stylesheets from Main App
        ITedrosBox app = TedrosContext.getApplication();
        if (app != null && app.getStage() != null && app.getStage().getScene() != null) {
            ObservableList<String> mainStyles = app.getStage().getScene().getStylesheets();
            newScene.getStylesheets().addAll(mainStyles);
        }
        
        newStage.setScene(newScene);
        newStage.show();
        
        reloadStyle(layerPane, newScene);
    }
    
    public static void reloadStyle(StackPane root, Scene scene){
    	
    	final String customStyleCssUrl = TThemeUtil.getStyleURL().toExternalForm();
    	final String backgroundCssUrl = TThemeUtil.getBackgroundURL().toExternalForm();
    	
    	removeCss(scene, customStyleCssUrl);
    	File basckGroundCss = new File(TThemeUtil.getBackgroundCssFilePath());
		try {
			TLoggerUtil.info(TWindowFactory.class, FileUtils.readFileToString(basckGroundCss, Charset.defaultCharset()));
		} catch (IOException e) {
			TLoggerUtil.error(TWindowFactory.class, e.getMessage(), e);
		}
    	if(!basckGroundCss.exists()){
			removeCss(scene, backgroundCssUrl);
		}else{
			removeCss(scene, backgroundCssUrl);
			scene.getStylesheets().add(backgroundCssUrl);
		}
		scene.getStylesheets().add(customStyleCssUrl);
    	com.sun.javafx.css.StyleManager.getInstance().addUserAgentStylesheet(scene, customStyleCssUrl);//reloadStylesheets(scene);
    	root.setId("t-tedros-color");
    }
    
    private static void removeCss(Scene scene, String url) {
		if(scene.getStylesheets().contains(url))
			scene.getStylesheets().remove(url);
	}
}
