package org.tedros;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.tedros.core.ITModule;
import org.tedros.core.TLanguage;
import org.tedros.core.context.Pages;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.context.TedroxBoxHeaderButton;
import org.tedros.core.control.PopOver;
import org.tedros.core.control.TedrosBoxResizeBar;
import org.tedros.core.style.TThemeUtil;
import org.tedros.fx.control.TLabel;
import org.tedros.fx.modal.TConfirmMessageBox;
import org.tedros.fx.modal.TModalPane;
import org.tedros.util.TLoggerUtil;
import org.tedros.util.TedrosFolder;

import javafx.animation.FadeTransition;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.DepthTest;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToolBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

/**
 * Tedros main class
 * 
 * @author Davis Gordon
 * */
public class TWindow  {
	
	private static final String FX_BACKGROUND_COLOR_TRANSPARENT = "-fx-background-color: transparent;";
	private Stage stage;
    private Scene scene;
    private BorderPane root;
    private TedrosBox system;
    
	private Pane pageArea;
    protected Pages pages;
    private ToolBar toolBar;
    
    private ImageView imgLogo; 
    private StackPane logoPane;
    private BorderPane mainPane;
    private StackPane layerPane;
    private TedrosBoxResizeBar windowResizeButton;
    
    private double mouseDragOffsetX;
    private double mouseDragOffsetY;
    private TModalPane modalMessage;
    
    private Label appName;
    
    private FadeTransition logoEffect;
    private ChangeListener<Number> effectChl;
    
    public TWindow(TedrosBox system, Node view){
    	this.system = system;
    	mouseDragOffsetX = 0.0D;
        mouseDragOffsetY = 0.0D;
        stage = new Stage();
        init(view);
        showView(view);
    }
    
    public Node getView(){
		return pageArea.getChildren().isEmpty() ? null : pageArea.getChildren().get(0);
	}
	
    public Stage getStage(){
    	return stage;
    }    
    
    public double getXStage(){
    	return getStage().getX();
    }
    
    public double getYStage(){
    	return getStage().getY();
    }
    
	public void close() {
        pageArea.getChildren().clear();
    	mainPane.setTop(null);
		stage.close();
    }
    
    public void reloadStyle(){
    	
    	final String customStyleCssUrl = TThemeUtil.getStyleURL().toExternalForm();
    	final String backgroundCssUrl = TThemeUtil.getBackgroundURL().toExternalForm();
    	
    	removeCss(customStyleCssUrl);
    	File basckGroundCss = new File(TThemeUtil.getBackgroundCssFilePath());
		try {
			TLoggerUtil.info(TWindow.class, FileUtils.readFileToString(basckGroundCss, Charset.defaultCharset()));
		} catch (IOException e) {
			TLoggerUtil.error(TWindow.class, e.getMessage(), e);
		}
    	if(!basckGroundCss.exists()){
			removeCss(backgroundCssUrl);
		}else{
			removeCss(backgroundCssUrl);
			scene.getStylesheets().add(backgroundCssUrl);
		}
		scene.getStylesheets().add(customStyleCssUrl);
    	com.sun.javafx.css.StyleManager.getInstance().addUserAgentStylesheet(scene, customStyleCssUrl);
    	root.setId("t-tedros-color");
    }

    
	private void removeCss(String url) {
		if(scene.getStylesheets().contains(url))
			scene.getStylesheets().remove(url);
	}
    
	private void init(Node view) {
    	
    	getStage().setTitle("Tedros");
    	getStage().initStyle(StageStyle.UNDECORATED);
    	getStage().getIcons().add(new Image(TedrosContext.getImageInputStream("icon-tedros.png")));
    	// create root stack pane that we use to be able to overlay proxy dialog
        layerPane = new StackPane();
        
        // create window resize button
        windowResizeButton = new TedrosBoxResizeBar(getStage(), 800, 600);
        // create root
        root = new BorderPane() {
            @Override protected void layoutChildren() {
                super.layoutChildren();
                windowResizeButton.autosize();
                windowResizeButton.setLayoutX(getWidth() - windowResizeButton.getLayoutBounds().getWidth());
                windowResizeButton.setLayoutY(getHeight() - windowResizeButton.getLayoutBounds().getHeight());
            }
        };
       
        layerPane.setDepthTest(DepthTest.DISABLE);
        layerPane.getChildren().add(root);
        // create scene
        boolean is3dSupported = Platform.isSupported(ConditionalFeature.SCENE3D);
        scene = new Scene(layerPane, 1020, 600, is3dSupported);
        if(is3dSupported)
            scene.setCamera(new PerspectiveCamera());
        
        final String defaultStyleCssUrl = TedrosContext.getExternalURLFile(TedrosFolder.CSS_CASPIAN_FOLDER, "caspian.css").toExternalForm();
        final String defaultStyleCssUrl2 = TedrosContext.getExternalURLFile(TedrosFolder.CSS_CASPIAN_FOLDER, "caspian-no-transparency.css").toExternalForm();
        final String defaultStyleCssUrl3 = TedrosContext.getExternalURLFile(TedrosFolder.CSS_CASPIAN_FOLDER, "highcontrast.css").toExternalForm();

        final String customStyleCssUrl = TThemeUtil.getStyleURL().toExternalForm();
    	final String immutableStylesCssUrl = TedrosContext.getExternalURLFile(TedrosFolder.CONF_FOLDER, "immutable-styles.css").toExternalForm();

    	scene.getStylesheets().addAll(immutableStylesCssUrl, customStyleCssUrl, defaultStyleCssUrl2, defaultStyleCssUrl3);
    	scene.setUserAgentStylesheet(defaultStyleCssUrl);
    	
    	File backgroundCss = new File(TThemeUtil.getBackgroundCssFilePath());
		if(backgroundCss.exists())
			scene.getStylesheets().addAll(TThemeUtil.getBackgroundURL().toExternalForm());
		
		root.getStyleClass().add("application");
		root.setId("t-tedros-color");
        
		// create logo pane
		logoPane = new StackPane();
        // create main toolbar
        toolBar = new ToolBar();
        toolBar.setId("t-main-toolbar");        
        toolBar.getItems().add(logoPane);
        
        showDefaultLogo();
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        toolBar.getItems().add(spacer);
        toolBar.setPrefHeight(50);
        toolBar.setMinHeight(50);
        toolBar.setMaxHeight(50);
        GridPane.setConstraints(toolBar, 0, 0);
        // add close min max
        final TedroxBoxHeaderButton windowButtons = new TedroxBoxHeaderButton(getStage(), false);
        toolBar.getItems().add(windowButtons);
        // add window header double clicking
        toolBar.setOnMouseClicked(e -> {
        	if (e.getClickCount() == 2) 
        		windowButtons.toogleMaximized();
        });
        // add window dragging
        toolBar.setOnMousePressed(e -> {
        	mouseDragOffsetX = e.getSceneX();
        	mouseDragOffsetY = e.getSceneY();
        });
        toolBar.setOnMouseDragged(e->  {
            if(!windowButtons.isMaximized()) {
            	getStage().setX(e.getScreenX()-mouseDragOffsetX);
            	getStage().setY(e.getScreenY()-mouseDragOffsetY);
            }
        });
                           
        // create page area
        pageArea = new Pane() {
            @Override protected void layoutChildren() {
                for (Node child:pageArea.getChildren()) {
                    child.resizeRelocate(0, 0, pageArea.getWidth(), pageArea.getHeight());
                }
            }
        };
        
        pageArea.getChildren().addListener((Change<? extends Node> c)->{
        	if(c.getList().isEmpty()) {
				system.windows.remove(this);
			}
        });
        
        pageArea.setId("t-app-area");
        pageArea.setStyle(FX_BACKGROUND_COLOR_TRANSPARENT);
        // create main pane
        mainPane = new BorderPane();
        mainPane.setCenter(pageArea);
        mainPane.setMinWidth(300);
        mainPane.setStyle("-fx-effect: dropshadow( three-pass-box , #000000 , 9, 0.1 , 0 , 4); "
        		+ "-fx-text-fill: #FFFFFF; -fx-background-color: transparent;");
        
        root.setTop(toolBar);
        root.setCenter(mainPane);
        root.setStyle(FX_BACKGROUND_COLOR_TRANSPARENT);
        // add window resize button so its on top
        windowResizeButton.setManaged(false);
       
        root.getChildren().addAll(windowResizeButton);
        
        // sets a modal pane for messages and nodes
        modalMessage = new TModalPane(layerPane);  
        
        // listen for style changes
        TedrosContext.reloadStyleProperty()
        .addListener((a,o,n)-> {
        	if(imgLogo!=null && logoEffect!=null && effectChl!=null) {
	        	imgLogo.opacityProperty().removeListener(effectChl);
	 			logoEffect.play();
        	}
	 		reloadStyle();
	 		if(imgLogo!=null && effectChl!=null) {	
	 			imgLogo.opacityProperty().addListener(effectChl);
        	}
 		});
        
        
        getStage().setOnCloseRequest(e->{
        	if(view instanceof ScrollPane sp && sp.getContent() instanceof ITModule itModule) {
        		close(e, itModule);
        	}else     	
        	if(view instanceof ITModule itModule) {
        		close(e, itModule);
			}
        });
        
        getStage().setScene(scene);
        getStage().show();
        
    }

	private void close(WindowEvent e, ITModule itModule) {
		e.consume();
		String msg = itModule.tCanStop();
		if(msg==null) {
			system.windows.remove(this);
		}else {
			TConfirmMessageBox confirm = new TConfirmMessageBox(msg);
			confirm.tConfirmProperty().addListener((a, o, n) -> {    	    			
				if(n.intValue()==1) {
					system.windows.remove(this);
				}else {
					modalMessage.hideModal();
				}
			});
			
			modalMessage.showModal(confirm);
		}
	}

	private void showDefaultLogo() {
		String logoFileName =  TedrosFolder.IMAGES_FOLDER.getFullPath()+File.separator+"logo-tedros-small.png";
        String brand = "Tedros";
        double brandLeftMargin = 55;
        showLogo(logoFileName, brand, brandLeftMargin);
	}

	private void showLogo(String imagePath, String brand, Double brandLeftMargin) {
		
		int size = logoPane.getChildren().size();
		for(int x=0; x<size; x++)
			logoPane.getChildren().remove(0);
		
		imgLogo = null;
		logoEffect = null;
		effectChl = null;
		
		createLogoImageView(imagePath);
		
		//add Logo and app name
        DropShadow nameLogoEffect = buildLogoEffect();
        
        if(imgLogo!=null) {
	        imgLogo.setEffect(nameLogoEffect);
	        
	        HBox h = new HBox();
	        h.setAlignment(Pos.CENTER_LEFT);
	        HBox.setMargin(imgLogo, new Insets(8,0,0,8));
	        h.getChildren().addAll(imgLogo);
	        
	        logoPane.getChildren().add(h);
	        
	        logoEffect = new FadeTransition(Duration.millis(2000), imgLogo);
	        logoEffect.setFromValue(1.0);
	        logoEffect.setToValue(0.3);
	        logoEffect.setCycleCount(FadeTransition.INDEFINITE);
	        logoEffect.setAutoReverse(true);
	        effectChl = (a,o,n)->{
				if(n.intValue()==1)
					logoEffect.stop();
			};
        }
        
		if(appName==null) {
			appName = new Label();
			appName.setEffect(nameLogoEffect);
	        appName.setCache(true);
	        appName.setId("t-app-name");        
	        appName.setCursor(Cursor.HAND);        
	        appName.setOnMouseClicked(e -> {
	        	String tt = TLanguage.getInstance(null).getFormatedString("#{tedros.tooltip}", TedrosRelease.version);
	        	TLabel l = new TLabel(tt);
	        	l.setFont(Font.font(11));
	        	PopOver p = new PopOver();
	        	p.setCloseButtonEnabled(true);
	        	p.setContentNode(l);
	        	p.getRoot().setPadding(new Insets(20,20,20,20));
	        	p.show(appName);
	        });
		}
		
		appName.setText(brand==null ? "" : brand);
		if(brandLeftMargin!=null)
			StackPane.setMargin(appName, new Insets(0,0,0,brandLeftMargin));
		
        logoPane.getChildren().add(appName);
	}

	private void createLogoImageView(String path) {
		if(path!=null) {
			imgLogo = new ImageView();
	        try(InputStream is = new FileInputStream(new File(path))) {
	        	Image logo = new Image(is);
	        	imgLogo.setImage(logo);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	private DropShadow buildLogoEffect() {
		DropShadow nameLogoEffect = new DropShadow();
        nameLogoEffect.setOffsetY(3.0f);
        nameLogoEffect.setColor(Color.BLACK);
		return nameLogoEffect;
	}    
    
	private void showView(Node view){
           
        if(view instanceof ITModule){
        	ScrollPane scrollPane = new ScrollPane();
            scrollPane.setContent(view);
            scrollPane.setFitToWidth(true);
            scrollPane.setFitToHeight(true);
            scrollPane.setMinWidth(725);
            scrollPane.getStyleClass().add("noborder-scroll-pane");
            view = scrollPane;
        }else {
        	view.setStyle(FX_BACKGROUND_COLOR_TRANSPARENT);
        }
        pageArea.getChildren().setAll(view); 
    }
    
  
}

