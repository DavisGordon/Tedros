package com.tedros;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.tedros.core.ITModule;
import com.tedros.core.TInternationalizationEngine;
import com.tedros.core.TModule;
import com.tedros.core.context.Page;
import com.tedros.core.context.Pages;
import com.tedros.core.context.TModuleContext;
import com.tedros.core.context.TedrosAppManager;
import com.tedros.core.context.TedrosContext;
import com.tedros.core.context.TedroxBoxHeaderButton;
import com.tedros.core.control.TedrosBoxBreadcrumbBar;
import com.tedros.core.control.TedrosBoxResizeBar;
import com.tedros.core.logging.TLoggerManager;
import com.tedros.fxapi.modal.TConfirmMessageBox;
import com.tedros.fxapi.modal.TConfirmObjectMessageBox;
import com.tedros.fxapi.modal.TModalPane;
import com.tedros.fxapi.presenter.TPresenter;
import com.tedros.fxapi.presenter.dynamic.view.TDynaView;
import com.tedros.login.model.LoginModelView;
import com.tedros.util.TFileUtil;
import com.tedros.util.TZipUtil;
import com.tedros.util.TedrosFolderEnum;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.TimelineBuilder;
import javafx.application.Application;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.DepthTest;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeView;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * Tedros main class
 * 
 * @author Davis Gordon
 * */
public class Main extends Application {
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	private static Main tedros;
	//private Stage stage;
    private Scene scene;
    private BorderPane root;
    private ToolBar toolBar;
    private SplitPane splitPane;
    @SuppressWarnings("rawtypes")
	private TreeView menuTree;
    private Pane pageArea;
    protected Pages pages;
    private Page currentPage;
    private String currentPagePath;
    
    private TedrosBoxBreadcrumbBar breadcrumbBar;
    private Stack<Page> history;
    private Stack<Page> forwardHistory;
    private boolean changingPage;
    private double mouseDragOffsetX;
    private double mouseDragOffsetY;
    private TedrosBoxResizeBar windowResizeButton;
    public boolean fromForwardOrBackButton;
    private StackPane modalMessage;
    private TModalPane tModalPane;
    private ToolBar pageToolBar;
    
    private BorderPane leftSplitPane;
    
    private Label appName;

    private boolean expandedTollBar = true;
    
    
    public Main(){
    	LOGGER.setLevel(Level.ALL);
        history = new Stack<Page>();
        forwardHistory = new Stack<Page>();
        changingPage = false;
        mouseDragOffsetX = 0.0D;
        mouseDragOffsetY = 0.0D;
        fromForwardOrBackButton = false;
        try {
        	String outputFolder = System.getProperty("user.home");
        	boolean extract = checkAndBuildTedrosBoxFolder(outputFolder);
			TLoggerManager.setup();
			if(extract)
				extractZip(outputFolder);
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.severe(e.toString());
		}
    }
    
    private boolean checkAndBuildTedrosBoxFolder(String outputFolder) throws IOException{
		
		//create tedros directory if is not exists
    	File folder = new File(outputFolder+"/.tedros");
    	if(folder.exists()){ 
    		if(new File(outputFolder+"/.tedros"+"/tedrosbox__V8.0.txt").exists())
    			return false;
    		TFileUtil.delete(folder);
    	}
    	folder.mkdir();
    	new File(outputFolder+"/.tedros/LOG").mkdir();
    	return true;
	}

	private void extractZip(String outputFolder) {
		InputStream zipFile = getClass().getResourceAsStream("TedrosBox.zip");
    	TZipUtil.unZip(zipFile, outputFolder);
	}
    
    private Stage getStage(){
    	return TedrosContext.getStage();
    }

    public static Main getTedros(){
        return tedros;
    }
    
    public double getXStage(){
    	return getStage().getX();
    }
    
    public double getYStage(){
    	return getStage().getY();
    }
        
    public void reloadStyle(){
    	
    	final String customStyleCssUrl = TedrosContext.getExternalURLFile(TedrosFolderEnum.CONF_FOLDER, "custom-styles.css").toExternalForm();
    	final String backgroundCssUrl = TedrosContext.getExternalURLFile(TedrosFolderEnum.CONF_FOLDER, "background-image.css").toExternalForm();
    	
    	removeCss(customStyleCssUrl);
    	File basckGroundCss = new File(TFileUtil.getTedrosFolderPath()+TedrosFolderEnum.CONF_FOLDER.getFolder()+"background-image.css");
		try {
			System.out.println(FileUtils.readFileToString(basckGroundCss));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	if(!basckGroundCss.exists()){
			removeCss(backgroundCssUrl);
		}else{
			removeCss(backgroundCssUrl);
			scene.getStylesheets().add(backgroundCssUrl);
		}
		scene.getStylesheets().add(customStyleCssUrl);
    	com.sun.javafx.css.StyleManager.getInstance().addUserAgentStylesheet(scene, customStyleCssUrl);//reloadStylesheets(scene);
    	root.setId("t-tedros-color");
    }

	@SuppressWarnings("restriction")
	private void removeCss(String url) {
		if(scene.getStylesheets().contains(url))
			scene.getStylesheets().remove(url);
	}

    @SuppressWarnings({"rawtypes", "unchecked", "restriction" })
	private void init(Stage primaryStage) {
    	TedrosContext.setMain(this);
    	TedrosContext.setStage(primaryStage);
    	
    	tedros = this;
    	getStage().setTitle("Tedros");
    	
    	Image img = new Image(TedrosContext.getImageInputStream("icon-tedros.png"));
    	
    	getStage().getIcons().add(img);
    	// create root stack pane that we use to be able to overlay proxy dialog
        StackPane layerPane = new StackPane();
        
        getStage().initStyle(StageStyle.UNDECORATED);
        // create window resize button
        windowResizeButton = new TedrosBoxResizeBar(getStage(), 1020, 600);
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
        
        final String defaultStyleCssUrl = TedrosContext.getExternalURLFile(TedrosFolderEnum.CSS_CASPIAN_FOLDER, "caspian.css").toExternalForm();
        final String defaultStyleCssUrl2 = TedrosContext.getExternalURLFile(TedrosFolderEnum.CSS_CASPIAN_FOLDER, "caspian-no-transparency.css").toExternalForm();
        final String defaultStyleCssUrl3 = TedrosContext.getExternalURLFile(TedrosFolderEnum.CSS_CASPIAN_FOLDER, "highcontrast.css").toExternalForm();

        
        final String customStyleCssUrl = TedrosContext.getExternalURLFile(TedrosFolderEnum.CONF_FOLDER, "custom-styles.css").toExternalForm();
    	final String immutableStylesCssUrl = TedrosContext.getExternalURLFile(TedrosFolderEnum.CONF_FOLDER, "immutable-styles.css").toExternalForm();

    	scene.setUserAgentStylesheet(defaultStyleCssUrl);
    	scene.getStylesheets().addAll(customStyleCssUrl, immutableStylesCssUrl, defaultStyleCssUrl2, defaultStyleCssUrl3);

    	File backgroundCss = new File(TFileUtil.getTedrosFolderPath()+TedrosFolderEnum.CONF_FOLDER.getFolder()+"background-image.css");
		if(backgroundCss.exists())
			scene.getStylesheets().addAll(TedrosContext.getExternalURLFile(TedrosFolderEnum.CONF_FOLDER, "background-image.css").toExternalForm());
		
		root.getStyleClass().add("application");
		root.setId("t-tedros-color");
        // create modal dimmer, to dim screen when showing modal dialogs
        modalMessage = new StackPane();
        modalMessage.setId("t-modal-dimmer");
        modalMessage.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {
                t.consume();
                TedrosContext.hideMessage();
            }
        });
        modalMessage.setVisible(false);
        layerPane.getChildren().add(modalMessage);
        
        tModalPane = new TModalPane(layerPane);
         
        // create main toolbar
        toolBar = new ToolBar();
        toolBar.setId("t-main-toolbar");
        
        //add appName
        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.BLACK);
        
        appName = new Label();
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
        // add close min max
        final TedroxBoxHeaderButton windowButtons = new TedroxBoxHeaderButton(getStage());
        toolBar.getItems().add(windowButtons);
        // add window header double clicking
        toolBar.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) 
                    windowButtons.toogleMaximized();
            }
        });
        // add window dragging
        toolBar.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent event) {
                mouseDragOffsetX = event.getSceneX();
                mouseDragOffsetY = event.getSceneY();
            }
        });
        toolBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent event) {
                if(!windowButtons.isMaximized()) {
                	getStage().setX(event.getScreenX()-mouseDragOffsetX);
                	getStage().setY(event.getScreenY()-mouseDragOffsetY);
                }
            }
        });
        
        menuTree = new TreeView();
        menuTree.setId("t-tedros-menu-tree");
        menuTree.setStyle("-fx-background-color: transparent;");
        menuTree.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        //menuTree.setRoot(pages.getRoot());
        menuTree.setShowRoot(false);
        menuTree.setEditable(false);
        menuTree.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        menuTree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (!changingPage) {
                    Page selectedPage = (Page)menuTree.getSelectionModel().getSelectedItem();
                    if (selectedPage!=pages.getRoot())
                    	TedrosContext.setPageProperty(selectedPage, true, false, true);
                }
            }
        });
        
        // create left split pane
        leftSplitPane = new BorderPane();
        leftSplitPane.setStyle("-fx-background-color: transparent;");
        BorderPane leftMenuPane = new BorderPane();
        leftMenuPane.setStyle("-fx-effect: dropshadow( three-pass-box , #000000 , 9, 0.1 , 0 , 4); -fx-text-fill: #FFFFFF; -fx-background-color: transparent;");
        leftMenuPane.setCenter(menuTree);
        
        leftSplitPane.setCenter(leftMenuPane);
        // create page toolbar
        pageToolBar = new ToolBar();
        pageToolBar.setId("t-tedros-toolbar");
        //pageToolBar.setMinHeight(29);
        pageToolBar.setMaxSize(Double.MAX_VALUE, Control.USE_PREF_SIZE);
        
        final Button expandCollapseButton = new Button("<|>");
        expandCollapseButton.getStyleClass().setAll("button","expand-button");
        
        
        expandCollapseButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				colapseMenuBar();
			}
		});
        
        
        
        // Inicio breadcrumbar
        breadcrumbBar = new TedrosBoxBreadcrumbBar();
        //breadcrumbBar.getChildren().add(0, menuPane);
        breadcrumbBar.getChildren().add(0, expandCollapseButton);
        
        pageToolBar.getItems().addAll(breadcrumbBar);
        // Fim breadcrumbar 
                
        // create page area
        pageArea = new Pane() {
            @Override protected void layoutChildren() {
                for (Node child:pageArea.getChildren()) {
                    child.resizeRelocate(0, 0, pageArea.getWidth(), pageArea.getHeight());
                }
            }
        };
        pageArea.setId("t-app-area");
        pageArea.setStyle("-fx-background-color: transparent;");
        // create right split pane
        BorderPane rightSplitPane = new BorderPane();
        rightSplitPane.setTop(pageToolBar);
        rightSplitPane.setCenter(pageArea);
        splitPane = new SplitPane();
        splitPane.setId("t-tedros-splitpane");
        splitPane.setStyle("-fx-background-color: transparent;");
        splitPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        GridPane.setConstraints(splitPane, 0, 1);
           
        
        rightSplitPane.setMinWidth(300);
        rightSplitPane.setStyle("-fx-effect: dropshadow( three-pass-box , #000000 , 9, 0.1 , 0 , 4); -fx-text-fill: #FFFFFF; -fx-background-color: transparent;");
        
        splitPane.getItems().addAll(leftSplitPane, rightSplitPane);
        splitPane.setDividerPosition(0, 0.099);
        
        root.setTop(toolBar);
        root.setCenter(splitPane);
        root.setStyle("-fx-background-color: transparent;");
        // add window resize button so its on top
        windowResizeButton.setManaged(false);
       
        root.getChildren().addAll(windowResizeButton);
        
        // configura listener para exibir view
        TedrosContext.pageProperty().addListener(new ChangeListener<Page>() {
			@Override
			public void changed(ObservableValue<? extends Page> arg0, Page arg1, Page arg2) {
				goToPage(arg2, TedrosContext.isPageAddHistory(), TedrosContext.isPageForce(), TedrosContext.isPageSwapViews());
			}
		});
        TedrosContext.pagePathProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if(StringUtils.isNotBlank(arg2))
					goToPage(arg2, TedrosContext.isPageForce());
			}
		});
        // configura listener para exibir mensagens
        TedrosContext.showModalMessageProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0,
					Boolean arg1, Boolean arg2) {
				if(arg2)
					showModalMessage(TedrosContext.getMessage());
				else
					hideModalMessage();
			}
		});
        
        TedrosContext.showModalProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0,
					Boolean arg1, Boolean newValue) {
				
				if(newValue && TedrosContext.getModal() != null)
					tModalPane.showModal(TedrosContext.getModal(), false);
				else
					tModalPane.hideModal();
			}
		});
        
        TedrosContext.reloadStyleProperty().addListener(new ChangeListener<Boolean>() {
 			@Override
 			public void changed(ObservableValue<? extends Boolean> arg0,
 					Boolean arg1, Boolean arg2) {
 				reloadStyle();
 			}
 		});
        
        getStage().setScene(scene);
        getStage().show();
        windowButtons.toogleMaximized();
        TedrosContext.showContextInitializationErrorMessage();
        
        TedrosContext.showModal(buildLogin());
        
    }
    
	@SuppressWarnings("unchecked")
	public void buildApplicationMenu(){
    	pages = new Pages();
    	menuTree.setRoot(pages.getRoot());
    	pages.parseModules();
        // goto initial page
        goToPage(pages.getModules());
    }
    
    private Node buildLogin(){
    	
    	TModule module = new TModule() {
    		@Override
			public void tStart() {
    			TDynaView<LoginModelView> view = new TDynaView<LoginModelView>(LoginModelView.class);
    			tShowView(view);
			}
    		
			@Override
			public boolean tStop() {
				TModuleContext context = TedrosAppManager.getInstance().getModuleContext(this);
				boolean flag = true;
				if(context!=null){
					flag = context.stopModule();
					context = null;
				}
				
				return flag;
			}			
		};
		
		module.tStart();
		
    	return module;
    }
    
    public Pages getPages(){
        return pages;
    }
    
    public void goToPage(String pagePath){
       TedrosContext.setPageProperty(pages.getPage(pagePath), true, false, true);
    	//goToPage(pages.getPage(pagePath));
    }

    public void goToPage(String pagePath, boolean force){
    	TedrosContext.setPageProperty(pages.getPage(pagePath), true, force, true);
        //goToPage(pages.getPage(pagePath), true, force, true);
    }

    public void goToPage(Page page){
        //goToPage(page, true, false, true);
    	TedrosContext.setPageProperty(page, true, false, true);
    }

    private void goToPage(Page page, boolean addHistory, boolean force, boolean swapViews) {
    	if(page == null)
            return;
        if(!force && page == currentPage)
            return;
    	Node view = TedrosContext.getView();
    	ITModule m = null;
    	if(view != null && view instanceof ITModule)
    		m = (ITModule) view;
    	else if(view != null && view instanceof ScrollPane && ((ScrollPane)view).getContent() instanceof ITModule)
    		m = (ITModule) ((ScrollPane)view).getContent();
    	
    	if(m!=null) {
	    	String msg = m.canStop();
	    	if(msg==null) {
	    		callPage(page, addHistory, force, swapViews);
	    	}else {
	    		
	    		ChangeListener<Number> chl = (a0, a1, a2) -> {
	    			TedrosContext.hideModal();
	    			if(a2.equals(1)) {
	    				callPage(page, addHistory, force, swapViews);
	    			}else {
	    				menuTree.getSelectionModel().select(currentPage);
	    			}
	    		};
	    		
	    		TConfirmMessageBox confirm = new TConfirmMessageBox(msg);
	    		confirm.gettConfirmProperty().addListener(chl);
	    		TedrosContext.showModal(confirm);
	    	}
    	}else
    		callPage(page, addHistory, force, swapViews);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private void callPage(Page page, boolean addHistory, boolean force, boolean swapViews){
        if(page == null)
            return;
        if(!force && page == currentPage)
            return;
        changingPage = true;
        if(swapViews){
            Node view = page.createModule();
            if(view == null)
                view = new Region();
            if(force || view != TedrosContext.getView()){
            	boolean change = true;
                Iterator i = pageArea.getChildren().iterator();
                do{
                    if(!i.hasNext())
                        break;
                    Node child = (Node)i.next();
                    if(child instanceof ScrollPane){
                    	if(((ScrollPane) child).getContent() instanceof TModule)
                    		if(!((ITModule)((ScrollPane) child).getContent()).tStop()){
                    			change = false;
                    			break;
                    		}
                    }
                } while(true);
                
                if(!change) {
                	changingPage = false;
                	return;
                }
                	
                Node content;
                if(view instanceof ITModule){
                	
                	((ITModule)view).tStart();
                	
                	ScrollPane scrollPane = new ScrollPane();
                    scrollPane.setContent(view);
                    scrollPane.setFitToWidth(true);
                    scrollPane.setFitToHeight(true);
                    scrollPane.setMinWidth(725);
                    scrollPane.setStyle("-fx-background-color: transparent; -fx-padding: 20 20 20 20;");
                    content = scrollPane;
                    this.expandedTollBar = true;
                    colapseMenuBar();
                }else {
                	content = view;
                	content.setStyle("-fx-background-color: transparent;");
                	this.expandedTollBar = false;
                    colapseMenuBar();
                }
                pageArea.getChildren().setAll(content);
                TedrosContext.setView(view);
            }
        }
        
        currentPage = page;
        currentPagePath = page.getPath();
        
        for(Page p = page; p != null; p = (Page)p.getParent())
            p.setExpanded(true);

        menuTree.getSelectionModel().select(page);
        breadcrumbBar.setPath(currentPagePath);
        changingPage = false;
        System.gc();
        Runtime.getRuntime().runFinalization();
    }
       
    public void showModalMessage(Node message) {
        modalMessage.getChildren().add(message);
        modalMessage.setOpacity(0);
        modalMessage.setVisible(true);
        modalMessage.setCache(true);
        TimelineBuilder.create().keyFrames(
            new KeyFrame(Duration.millis(400), 
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent t) {
                        modalMessage.setCache(false);
                    }
                },
                new KeyValue(modalMessage.opacityProperty(),1, Interpolator.EASE_BOTH)
        )).build().play();
    }
    
    /**
     * Hide any modal message that is shown
     */
    public void hideModalMessage() {
        modalMessage.setCache(true);
        TimelineBuilder.create().keyFrames(
            new KeyFrame(Duration.millis(400), 
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent t) {
                        modalMessage.setCache(false);
                        modalMessage.setVisible(false);
                        modalMessage.getChildren().clear();
                    }
                },
                new KeyValue(modalMessage.opacityProperty(),0, Interpolator.EASE_BOTH)
        )).build().play();
    }
    
    /**
     * Check if current call stack was from back or forward button's action
     * 
     * @return True if current call was caused by action on back or forward button
     */
    public boolean isFromForwardOrBackButton() {
        return fromForwardOrBackButton;
    }
    
    /**
     * Got to previous page in history
     */
	public void back() {
        fromForwardOrBackButton = true;
        if (!history.isEmpty()) {      
            Page prevPage = (Page) history.pop();
            forwardHistory.push(currentPage);
            TedrosContext.setPageProperty(prevPage,false, false, true);
        }
        fromForwardOrBackButton = false;
    }

    /**
     * Go to next page in history if there is one
     */
	public void forward() {
        fromForwardOrBackButton = true;
        if (!forwardHistory.isEmpty()) {
            Page prevPage = (Page) forwardHistory.pop();
            history.push(currentPage);
            TedrosContext.setPageProperty(prevPage,false, false, true);
        }
        fromForwardOrBackButton = false;
    }
    
    /**
     * Reload the current page
     */
    public void reload() {
    	TedrosContext.setPageProperty(currentPage, false, true, true);
    }
    
    /**
     * ###################
     * Main e start 
     * metodos principais
     * ###################
     * */    
    @Override 
    public void start(Stage primaryStage) throws Exception {
    	TInternationalizationEngine.addResourceBundle(null, "TedrosLoginLabels", getClass().getClassLoader());
    	TInternationalizationEngine.addResourceBundle(null, "TCoreLabels", TedrosContext.class.getClassLoader());
		TInternationalizationEngine.addResourceBundle(null, "TLabels", TPresenter.class.getClassLoader());
        init(primaryStage);
        primaryStage.show();
        
       // TedrosProcessManager.addProcess(TTomeeServerService.class);
    }
    public void colapseMenuBar() {
		if(expandedTollBar){
			splitPane.setDividerPosition(0, 0.0038);
			expandedTollBar = false;
		}else{
			splitPane.setDividerPosition(0, 0.197);
			expandedTollBar = true;
		}
	}

	public static void main(String[] args) { 
    	launch(args); 
    }
	
}

