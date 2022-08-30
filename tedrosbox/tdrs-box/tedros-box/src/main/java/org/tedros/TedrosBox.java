package org.tedros;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.tedros.core.ITModule;
import org.tedros.core.ITedrosBox;
import org.tedros.core.TCoreKeys;
import org.tedros.core.TLanguage;
import org.tedros.core.TModule;
import org.tedros.core.context.Page;
import org.tedros.core.context.Pages;
import org.tedros.core.context.TModuleContext;
import org.tedros.core.context.TedrosAppManager;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.context.TedroxBoxHeaderButton;
import org.tedros.core.control.PopOver;
import org.tedros.core.control.PopOver.ArrowLocation;
import org.tedros.core.control.TedrosBoxBreadcrumbBar;
import org.tedros.core.control.TedrosBoxResizeBar;
import org.tedros.core.logging.TLoggerManager;
import org.tedros.core.style.TThemeUtil;
import org.tedros.fx.TFxKey;
import org.tedros.fx.control.TLabel;
import org.tedros.fx.layout.TSliderMenu;
import org.tedros.fx.modal.TConfirmMessageBox;
import org.tedros.fx.modal.TMessageBox;
import org.tedros.fx.modal.TModalPane;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.fx.presenter.dynamic.view.TDynaView;
import org.tedros.login.model.LoginModelView;
import org.tedros.tools.logged.user.TMainSettingsPane;
import org.tedros.tools.logged.user.TUserSettingsPane;
import org.tedros.util.TFileUtil;
import org.tedros.util.TZipUtil;
import org.tedros.util.TedrosFolder;

import javafx.animation.FadeTransition;
import javafx.application.Application;
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
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeView;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * Tedros main class
 * 
 * @author Davis Gordon
 * */
public class TedrosBox extends Application implements ITedrosBox  {
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	private static TedrosBox tedros;
	private Stage stage;
    private Scene scene;
    private BorderPane root;
    
	@SuppressWarnings("rawtypes")
	private TreeView menuTree;
    private Pane pageArea;
    protected Pages pages;
    private Page currentPage;
    private String currentPagePath;
    private ToolBar pageToolBar;
    private ToolBar toolBar;
    private PopOver infoPopOver;
    
    private BorderPane mainPane;
    private StackPane layerPane;
    private VBox leftMenuPane;
    private TSliderMenu innerPane;
    private TedrosBoxBreadcrumbBar breadcrumbBar;
    private TedrosBoxResizeBar windowResizeButton;
    
    private Stack<Page> history;
    private Stack<Page> forwardHistory;
    public boolean fromForwardOrBackButton;
    private boolean changingPage;
    private double mouseDragOffsetX;
    private double mouseDragOffsetY;
    private TModalPane modalMessage;
    private TModalPane tModalPane;
    private Accordion settingsAcc;
    private Label appName;
    
    private FadeTransition logoEffect;
    private ChangeListener<Number> effectChl;
    
    public TedrosBox(){
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
    		if(new File(outputFolder+"/.tedros"+"/tedrosbox__V"+TedrosRelease.version+".txt").exists())
    			return false;
    		TFileUtil.delete(folder);
    	}
    	folder.mkdir();
    	new File(outputFolder+"/.tedros/LOG").mkdir();
    	return true;
	}

	private void extractZip(String outputFolder) {
		try(InputStream zipFile = TedrosRelease.class.getResourceAsStream("TedrosBox.zip")){
			TZipUtil.unZip(zipFile, outputFolder);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    public Stage getStage(){
    	return stage;
    }

    public static TedrosBox getTedros(){
        return tedros;
    }
    
    public double getXStage(){
    	return getStage().getX();
    }
    
    public double getYStage(){
    	return getStage().getY();
    }
    
    @Override
    public void stop() throws Exception {
    	TedrosContext.serverLogout();
    	super.stop();
    }
        
    public void reloadStyle(){
    	
    	final String customStyleCssUrl = TThemeUtil.getStyleURL().toExternalForm();
    	final String backgroundCssUrl = TThemeUtil.getBackgroundURL().toExternalForm();
    	
    	removeCss(customStyleCssUrl);
    	File basckGroundCss = new File(TThemeUtil.getBackgroundCssFilePath());
		try {
			System.out.println(FileUtils.readFileToString(basckGroundCss, Charset.defaultCharset()));
		} catch (IOException e) {
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

    
	private void removeCss(String url) {
		if(scene.getStylesheets().contains(url))
			scene.getStylesheets().remove(url);
	}

    @SuppressWarnings({"rawtypes", "unchecked"})
	private void init(Stage primaryStage) {
    	TLoggerManager.setup();
		
    	stage = primaryStage;
    	TedrosContext.setApplication(this);
    	tedros = this;
    	getStage().setTitle("Tedros");
    	getStage().initStyle(StageStyle.UNDECORATED);
    	getStage().getIcons().add(new Image(TedrosContext.getImageInputStream("icon-tedros.png")));
    	// create root stack pane that we use to be able to overlay proxy dialog
        layerPane = new StackPane();
        
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
        
        // create main toolbar
        toolBar = new ToolBar();
        toolBar.setId("t-main-toolbar");
        
        //add appName
        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.BLACK);
        InputStream is = TedrosContext.getImageInputStream("logo-tedros-small.png");
        StackPane logoPane = new StackPane();
        Image logo = new Image(is);
        try {
			is.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        ImageView imgLogo = new ImageView();
        imgLogo.setImage(logo);
        imgLogo.setEffect(ds);
        
        appName = new Label();
        appName.setEffect(ds);
        appName.setCache(true);
        appName.setText("Tedros Box");
        appName.setId("t-app-name");
        
        HBox h = new HBox();
        h.setAlignment(Pos.CENTER_LEFT);
        HBox.setMargin(imgLogo, new Insets(8,0,0,8));
        h.getChildren().addAll(imgLogo);
        
        logoPane.getChildren().addAll(h, appName);
        StackPane.setMargin(appName, new Insets(0,0,0,55));
        toolBar.getItems().add(logoPane);
        logoEffect = new FadeTransition(Duration.millis(2000), imgLogo);
        logoEffect.setFromValue(1.0);
        logoEffect.setToValue(0.3);
        logoEffect.setCycleCount(FadeTransition.INDEFINITE);
        logoEffect.setAutoReverse(true);
        effectChl = (a,o,n)->{
			if(n.intValue()==1)
				logoEffect.stop();
		};
        
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
        
        menuTree = new TreeView();
        menuTree.setId("t-tedros-menu-tree");
        menuTree.setStyle("-fx-background-color: transparent;");
        menuTree.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        menuTree.setShowRoot(false);
        menuTree.setEditable(false);
        menuTree.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        menuTree.getSelectionModel().selectedItemProperty()
        .addListener((observable, oldValue, newValue) -> {
            if (!changingPage) {
                Page selectedPage = (Page)menuTree.getSelectionModel().getSelectedItem();
                if (selectedPage!=pages.getRoot())
                	TedrosContext.setPageProperty(selectedPage, true, false, true);
            }
        });
        
        leftMenuPane = new VBox();
        leftMenuPane.setStyle("-fx-effect: dropshadow( three-pass-box , #000000 , 9, 0.1 , 0 , 4); "
        		+ "-fx-text-fill: #FFFFFF; -fx-background-color: transparent;");
        leftMenuPane.getChildren().add(menuTree);
        VBox.setVgrow(menuTree, Priority.ALWAYS);
        // create page toolbar
        pageToolBar = new ToolBar();
        pageToolBar.setId("t-tedros-toolbar");
        pageToolBar.setMaxSize(Double.MAX_VALUE, Control.USE_PREF_SIZE);
       
        Button infoButton = new Button();
        infoButton.getStyleClass().addAll("info");
        infoButton.setOnAction(e->{
        	infoPopOver = new PopOver();
        	infoPopOver.setHeaderAlwaysVisible(true);
        	infoPopOver.setAutoFix(true);
        	infoPopOver.setCloseButtonEnabled(true);
        	infoPopOver.setArrowLocation(ArrowLocation.TOP_LEFT);
        	infoPopOver.show(infoButton);
        	infoPopOver.setContentNode(this.settingsAcc);
        });
        infoPopOver = null;
        final Button forwardButton = new Button("");
        forwardButton.getStyleClass().addAll("forward");
        forwardButton.setOnAction(e->{
        	this.forward();
        });
        
        final Button backButton = new Button();
        backButton.getStyleClass().addAll("back");
        backButton.setOnAction(e->{
        	this.back();
        });
        HBox btnBox = new HBox();
        btnBox.getChildren().addAll(infoButton, backButton, forwardButton);
        HBox.setMargin(infoButton, new Insets(0,10,0,0));
        HBox.setMargin(backButton, new Insets(0,10,0,0));
        HBox.setMargin(forwardButton, new Insets(0,10,0,0));
        
        pageToolBar.getItems().add(btnBox);
        
        // Inicio breadcrumbar
        breadcrumbBar = new TedrosBoxBreadcrumbBar();
        pageToolBar.getItems().add(breadcrumbBar);
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
        // create main pane
        mainPane = new BorderPane();
        BorderPane.setMargin(pageToolBar, new Insets(0,0,0,32));
       // mainPane.setTop(pageToolBar);
        mainPane.setCenter(pageArea);
        mainPane.setMinWidth(300);
        mainPane.setStyle("-fx-effect: dropshadow( three-pass-box , #000000 , 9, 0.1 , 0 , 4); "
        		+ "-fx-text-fill: #FFFFFF; -fx-background-color: transparent;");
        
        this.innerPane = new TSliderMenu(mainPane);
        innerPane.settMenuVisible(false);
        root.setTop(toolBar);
        root.setCenter(this.innerPane);
        root.setStyle("-fx-background-color: transparent;");
        // add window resize button so its on top
        windowResizeButton.setManaged(false);
       
        root.getChildren().addAll(windowResizeButton);
        
        // sets a modal pane for messages and nodes
        modalMessage = new TModalPane(layerPane);
        tModalPane = new TModalPane(innerPane);
        
        // configura listener para exibir view
        TedrosContext.pageProperty()
        .addListener((a, o, n)-> {
			goToPage(n, TedrosContext.isPageAddHistory(), 
					TedrosContext.isPageForce(), 
					TedrosContext.isPageSwapViews());
		});
        TedrosContext.pagePathProperty()
        .addListener((a,o,n)->{
			if(StringUtils.isNotBlank(n))
				goToPage(n, TedrosContext.isPageForce());
		});
        // configura listener para exibir mensagens
        TedrosContext.messageListProperty()
        .addListener((Change c)->{
			if(!c.getList().isEmpty()) {
				imgLogo.opacityProperty().removeListener(effectChl);
				logoEffect.play();
				//modalMessage = new TModalPane(layerPane);
				modalMessage.showModal(new TMessageBox(c.getList()), ev->{
					TedrosContext.messageListProperty().clear();
				});
			}else {
				if(modalMessage!=null) {
					modalMessage.hideModal();
					//layerPane.getChildren().remove(modalMessage);
					//modalMessage = null;
					imgLogo.opacityProperty().addListener(effectChl);
				}
			}
		});
        
        TedrosContext.showModalProperty()
        .addListener((a, o, newValue) -> {
			if(newValue && TedrosContext.getModal() != null) {
				imgLogo.opacityProperty().removeListener(effectChl);
				logoEffect.play();
				//tModalPane = new TModalPane(innerPane);
				tModalPane.showModal(TedrosContext.getModal());
			}else {
				if(tModalPane!=null) {
					tModalPane.hideModal();
					//innerPane.getChildren().remove(tModalPane);
					imgLogo.opacityProperty().addListener(effectChl);
				}
			}
		});
        
        TedrosContext.reloadStyleProperty()
        .addListener((a,o,n)-> {
        	imgLogo.opacityProperty().removeListener(effectChl);
 			logoEffect.play();
 			reloadStyle();
 			imgLogo.opacityProperty().addListener(effectChl);
 		});
        
        getStage().setScene(scene);
        getStage().show();
        windowButtons.toogleMaximized();
        TedrosContext.showModal(buildLogin());
        
    }
    
    
    public void logout() {
    	if(infoPopOver!=null)
    		infoPopOver.hide();
    	currentPage = null;
        currentPagePath = "";
        changingPage = false;
        pageArea.getChildren().clear();
        TedrosContext.setView(null);
        breadcrumbBar.setPath("");
    	innerPane.settMenuOpened(false);
    	innerPane.tClearMenuContent();
    	innerPane.settMenuVisible(false);
    	mainPane.setTop(null);
    	cleanHistory();
    }
    
    
	@SuppressWarnings("unchecked")
	public void buildApplicationMenu(){
		pages = new Pages();
    	menuTree.setRoot(pages.getRoot());
    	pages.parseModules();
    	if(this.currentPage!=null) {
    		Node n = currentPage.getModule();
    		if(n!=null && n instanceof ITModule)
    			((ITModule)n).tStop();
    		this.currentPage = null;
    		this.currentPagePath = null;
    	}
    		
        TedrosContext.setView(null);
        // goto initial page
        goToPage(pages.getModules());
        innerPane.settMenuContent(leftMenuPane);
    	innerPane.settMenuVisible(true);
    	mainPane.setTop(this.pageToolBar);
    	cleanHistory();
    }

	@SuppressWarnings("rawtypes")
	public void buildSettingsPane() {
		TLanguage iEngine = TLanguage.getInstance(null);
		
		if(settingsAcc!=null) {
			for(TitledPane t : settingsAcc.getPanes())
				((TDynaPresenter)((TDynaView)((StackPane)t.getContent())
						.getChildren().get(0))
						.gettPresenter()).invalidate();
			
			settingsAcc.getPanes().clear();
		}else {
			settingsAcc = new Accordion();
			settingsAcc.autosize();
			//settingsAcc.getStyleClass().add("t-accordion");
		}
		TUserSettingsPane u = new TUserSettingsPane();
		u.setMinWidth(350);
        TitledPane t = new TitledPane();
        t.setText(iEngine.getString("#{tedros.setting.user}"));
        t.setContent(u);
        
        TitledPane t2 = new TitledPane();
        t2.setText(iEngine.getString("#{tedros.setting.main}"));
        t2.setContent(new TMainSettingsPane());
       // settingsAcc.getStyleClass().add("t-settings-page");
        t.getStyleClass().add("t-settings-header");
        t2.getStyleClass().add("t-settings-header");
        settingsAcc.setExpandedPane(t);
        settingsAcc.getPanes().addAll(t, t2);
	}
    
    public Node buildLogin(){
    	
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
    }

    public void goToPage(String pagePath, boolean force){
    	TedrosContext.setPageProperty(pages.getPage(pagePath), true, force, true);
    }

    public void goToPage(Page page){
    	TedrosContext.setPageProperty(page, true, false, true);
    }

    @SuppressWarnings("unchecked")
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
	    	String msg = m.tCanStop();
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
	    		confirm.tConfirmProperty().addListener(chl);
	    		TedrosContext.showModal(confirm);
	    	}
    	}else
    		callPage(page, addHistory, force, swapViews);
    }
    
    @SuppressWarnings({ "unchecked" })
	private void callPage(Page page, boolean addHistory, boolean force, boolean swapViews){
        if(page == null)
            return;
        if(!force && page == currentPage)
            return;
        
        changingPage = true;
        if(swapViews){
        	
        	boolean created = false;
  
            Node view = page.getModule();
            if(view==null) {
            	view = page.createModule();
            	created = true;
            }
            
            if(view == null)
                view = new Region();
            if(force || view != TedrosContext.getView()){
                	
                Node content;
                if(view instanceof ITModule){
                	
                	if(created)
                		((ITModule)view).tStart();
                	
                	ScrollPane scrollPane = new ScrollPane();
                    scrollPane.setContent(view);
                    scrollPane.setFitToWidth(true);
                    scrollPane.setFitToHeight(true);
                    scrollPane.setMinWidth(725);
                    scrollPane.getStyleClass().add("noborder-scroll-pane");
                    content = scrollPane;
                }else {
                	content = view;
                	content.setStyle("-fx-background-color: transparent;");
                }
                pageArea.getChildren().setAll(content);
                TedrosContext.setView(view);
                addHistory(addHistory, page);
                
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

	/**
	 * @param addHistory
	 */
	private void addHistory(boolean addHistory, Page page) {
		if(addHistory && currentPage!=null 
				&& currentPage.getModule() instanceof ITModule){
			Page p =  currentPage;
		    history.push(p);
		    resizeHistory();
		    if(page.getModule() instanceof ITModule)
		    	cleanForward(page);
		}
	}

	/**
	 * 
	 */
	private void resizeHistory() {
		if(history.size()>=TedrosContext.getTotalPageHistory()) {
			Page rem = history.remove(0);
			Node n = rem.getModule();
			if(n instanceof ITModule)
				((ITModule)n).tStop();
		}
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
	        if(currentPage!=null 
	        		&& currentPage.getModule() instanceof ITModule)
	        	forwardHistory.push(currentPage);
	        TedrosContext.setPageProperty(prevPage,false, false, true);
	    }
	    fromForwardOrBackButton = false;
	    this.printHistory();
	}

	/**
	 * Go to next page in history if there is one
	 */
	public void forward() {
	    fromForwardOrBackButton = true;
	    if (!forwardHistory.isEmpty()) {
	        Page prevPage = (Page) forwardHistory.pop();
	        if(currentPage!=null 
	        		&& currentPage.getModule() instanceof ITModule) {
	        	history.push(currentPage);
	        	resizeHistory();
	        }
	        TedrosContext.setPageProperty(prevPage,false, false, true);
	    }
	    fromForwardOrBackButton = false;
	    this.printHistory();
	}

	private void printHistory() {
	    System.out.print("   HISTORY = ");
	    for (Object o :history) {
	    	Page page = (Page) o;
	        System.out.print(page.getName()+"->");
	    }
	    System.out.print("   ["+currentPage.getName()+"]");
	    for (Object o :forwardHistory) {
	    	Page page = (Page) o;
	        System.out.print(page.getName()+"->");
	    }
	    System.out.print("\n");
	}

	/**
	 * 
	 */
	private void cleanHistory() {
		history.stream().forEach(p->{
			Node n = p.getModule();
			if(n!=null && n instanceof ITModule) {
				((ITModule)n).tStop();
			}
		});
		history.clear();
		cleanForward(null);
	}

	/**
	 * 
	 */
	private void cleanForward(Page page) {
		forwardHistory.stream().forEach(p->{
			Node n = p.getModule();
			if(n!=null && n instanceof ITModule 
				&& (page==null || (page!=null && page!=p))) {
				((ITModule)n).tStop();
			}
		});
		forwardHistory.clear();
	}

    /**
     * Reload the current page
     */
    public void reload() {
    	TedrosContext.setPageProperty(currentPage, false, true, true);
    }
    
    /**
     * ###################
     * TedrosBox e start 
     * metodos principais
     * ###################
     * */    
    @Override 
    public void start(Stage primaryStage) throws Exception {
    	TLanguage.addResourceBundle(null, "TedrosLoginLabels", TedrosRelease.class.getClassLoader());
    	TLanguage.addResourceBundle(null, "TCoreLabels", TCoreKeys.class.getClassLoader());
		TLanguage.addResourceBundles(null, TFxKey.class.getClassLoader(), "TFx", "TUsual");
        init(primaryStage);
        primaryStage.show();
        
       // TedrosProcessManager.addProcess(TTomeeServerService.class);
    }
  
}

