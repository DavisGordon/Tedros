package org.tedros;

import java.io.File;

import org.slf4j.Logger;
import org.tedros.api.presenter.view.TDetachViewType;
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
import org.tedros.fx.modal.TMessageBox;
import org.tedros.fx.modal.TModalPane;
import org.tedros.fx.presenter.dynamic.view.TDynaView;
import org.tedros.login.model.LoginMV;
import org.tedros.manager.TedrosLogoManager;
import org.tedros.manager.TedrosNavigationManager;
import org.tedros.manager.TedrosPopOverManager;
import org.tedros.util.TLoggerUtil;
import org.tedros.util.TedrosFolder;

import javafx.application.Application;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ListChangeListener.Change;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.DepthTest;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Tedros main class (Refactored)
 * 
 * @author Davis Gordon
 */
public class TedrosBox extends Application implements ITedrosBox {

	private static final String FX_BACKGROUND_COLOR_TRANSPARENT = "-fx-background-color: transparent;";

	private static Logger LOGGER = TLoggerUtil.getLogger(TedrosBox.class);

	private static TedrosBox tedros;
	private Stage stage;
	private Scene scene;
	private BorderPane root;

	@SuppressWarnings("rawtypes")
	private TreeView menuTree;
	private Pane pageArea;
	private ToolBar pageToolBar;
	private ToolBar toolBar;
	private Label chatUnreadMsgsLabel;

	private StackPane logoPane;
	private BorderPane mainPane;
	private StackPane layerPane;
	private VBox leftMenuPane;
	private TSliderMenu innerPane;
	private TedrosBoxBreadcrumbBar breadcrumbBar;
	private TedrosBoxResizeBar windowResizeButton;

	private double mouseDragOffsetX;
	private double mouseDragOffsetY;
	private TModalPane modalMessage;
	private TModalPane tModalPane;
	private Button userButton;
	private Button infoButton;
	private Button chatButton;
	private Button terosButton;

	private StringProperty historySize;
	private StringProperty forwardSize;

	// Managers
	private TedrosLogoManager logoManager;
	private TedrosPopOverManager popOverManager;
	private TedrosNavigationManager navManager;

	public TedrosBox() {
		mouseDragOffsetX = 0.0D;
		mouseDragOffsetY = 0.0D;
		TedrosBoxFolderHelper.checkTedrosFolder();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void init(Stage primaryStage) {
		TLoggerManager.setup();

		stage = primaryStage;
		TedrosContext.setApplication(this);
		TedrosContext.setViewBuilder(new TViewBuilder());
		setInstance(this);
		getStage().setTitle("Tedros");
		getStage().initStyle(StageStyle.UNDECORATED);
		getStage().getIcons().add(new Image(TedrosContext.getImageInputStream("icon-tedros.png")));
		// create root stack pane that we use to be able to overlay proxy dialog
		layerPane = new StackPane();

		// create window resize button
		windowResizeButton = new TedrosBoxResizeBar(getStage(), 1020, 600);
		// create root
		root = new BorderPane() {
			@Override
			protected void layoutChildren() {
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
		if (is3dSupported)
			scene.setCamera(new PerspectiveCamera());

		final String defaultStyleCssUrl = TedrosContext
				.getExternalURLFile(TedrosFolder.CSS_CASPIAN_FOLDER, "caspian.css").toExternalForm();
		final String defaultStyleCssUrl2 = TedrosContext
				.getExternalURLFile(TedrosFolder.CSS_CASPIAN_FOLDER, "caspian-no-transparency.css").toExternalForm();
		final String defaultStyleCssUrl3 = TedrosContext
				.getExternalURLFile(TedrosFolder.CSS_CASPIAN_FOLDER, "highcontrast.css").toExternalForm();

		final String customStyleCssUrl = TThemeUtil.getStyleURL().toExternalForm();
		final String immutableStylesCssUrl = TedrosContext
				.getExternalURLFile(TedrosFolder.CONF_FOLDER, "immutable-styles.css").toExternalForm();

		scene.getStylesheets().addAll(immutableStylesCssUrl, customStyleCssUrl, defaultStyleCssUrl2,
				defaultStyleCssUrl3);
		scene.setUserAgentStylesheet(defaultStyleCssUrl);

		File backgroundCss = new File(TThemeUtil.getBackgroundCssFilePath());
		if (backgroundCss.exists())
			scene.getStylesheets().addAll(TThemeUtil.getBackgroundURL().toExternalForm());

		root.getStyleClass().add("application");
		root.setId("t-tedros-color");

		// create logo pane
		logoPane = new StackPane();
		logoManager = new TedrosLogoManager(logoPane);

		// create main toolbar
		toolBar = new ToolBar();
		toolBar.setId("t-main-toolbar");
		toolBar.getItems().add(logoPane);

		logoManager.showDefaultLogo();

		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);
		toolBar.getItems().add(spacer);
		toolBar.setPrefHeight(50);
		toolBar.setMinHeight(50);
		toolBar.setMaxHeight(50);
		GridPane.setConstraints(toolBar, 0, 0);
		// add close min max
		final TedroxBoxHeaderButton windowButtons = new TedroxBoxHeaderButton(getStage(), true);
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
		toolBar.setOnMouseDragged(e -> {
			if (!windowButtons.isMaximized()) {
				getStage().setX(e.getScreenX() - mouseDragOffsetX);
				getStage().setY(e.getScreenY() - mouseDragOffsetY);
			}
		});

		menuTree = new TreeView();
		menuTree.setId("t-tedros-menu-tree");
		menuTree.setStyle(FX_BACKGROUND_COLOR_TRANSPARENT);
		menuTree.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		menuTree.setShowRoot(false);
		menuTree.setEditable(false);
		menuTree.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		// Listener delegated to Navigation Manager later, but need manager instance
		// first
		// It's circular if manager needs menuTree, so manager ctor takes menuTree.

		leftMenuPane = new VBox();
		leftMenuPane.setStyle("-fx-effect: dropshadow( three-pass-box , #000000 , 9, 0.1 , 0 , 4); "
				+ "-fx-text-fill: #FFFFFF; -fx-background-color: transparent;");
		leftMenuPane.getChildren().add(menuTree);
		VBox.setVgrow(menuTree, Priority.ALWAYS);
		// create page toolbar
		pageToolBar = new ToolBar();
		pageToolBar.setId("t-tedros-toolbar");
		pageToolBar.setMaxSize(Double.MAX_VALUE, Region.USE_PREF_SIZE);

		userButton = new Button();
		userButton.getStyleClass().addAll("user");
		userButton.setOnAction(e -> popOverManager.showUserPopOver());

		forwardSize = new SimpleStringProperty("0");
		final Button forwardButton = new Button("");
		forwardButton.getStyleClass().addAll("forward");
		forwardButton.setOnAction(e -> this.forward());
		forwardButton.setOnMouseEntered(ev -> {
			PopOver pvr = new PopOver();
			pvr.setHeaderAlwaysVisible(false);
			pvr.setAutoFix(true);
			pvr.setCloseButtonEnabled(false);
			pvr.setArrowLocation(ArrowLocation.TOP_LEFT);
			pvr.show(forwardButton);
			TLabel l = new TLabel();
			l.textProperty().bind(forwardSize);
			pvr.setContentNode(l);
			forwardButton.setUserData(pvr);
		});
		forwardButton.setOnMouseExited(ev -> {
			PopOver pvr = (PopOver) forwardButton.getUserData();
			TLabel l = (TLabel) pvr.getContentNode();
			l.textProperty().unbind();
			if (pvr != null)
				pvr.hide();
			forwardButton.setUserData(null);
		});
		historySize = new SimpleStringProperty("0");
		final Button backButton = new Button();
		backButton.getStyleClass().addAll("back");
		backButton.setOnAction(e -> this.back());
		backButton.setOnMouseEntered(ev -> {
			PopOver pvr = new PopOver();
			pvr.setAutoHide(true);
			pvr.setHeaderAlwaysVisible(false);
			pvr.setAutoFix(true);
			pvr.setCloseButtonEnabled(false);
			pvr.setArrowLocation(ArrowLocation.TOP_LEFT);
			pvr.show(backButton);
			TLabel l = new TLabel();
			l.textProperty().bind(historySize);
			pvr.setContentNode(l);
			backButton.setUserData(pvr);
		});
		backButton.setOnMouseExited(ev -> {
			PopOver pvr = (PopOver) backButton.getUserData();
			TLabel l = (TLabel) pvr.getContentNode();
			l.textProperty().unbind();
			if (pvr != null)
				pvr.hide();
			backButton.setUserData(null);
		});

		infoButton = new Button();
		infoButton.getStyleClass().addAll("info");
		infoButton.setOnAction(e -> popOverManager.showInfoPopOver(scene));

		terosButton = new Button();
		terosButton.getStyleClass().addAll("teros");
		terosButton.setOnAction(e -> popOverManager.showTerosPopOver());

		HBox chb = new HBox(5);
		chb.getStyleClass().addAll("box");

		chatButton = new Button();
		chatButton.getStyleClass().addAll("chat");
		chatButton.setOnAction(e -> popOverManager.showChatPopOver(scene));

		chatUnreadMsgsLabel = new Label("0");
		chatUnreadMsgsLabel.getStyleClass().addAll("boxTxt");
		chb.getChildren().addAll(chatButton, chatUnreadMsgsLabel);
		HBox.setMargin(chatButton, new Insets(2, 4, 2, 8));
		HBox.setMargin(chatUnreadMsgsLabel, new Insets(2, 8, 2, 4));

		// Init PopOver Manager
		popOverManager = new TedrosPopOverManager(userButton, infoButton, chatButton, terosButton, chatUnreadMsgsLabel);

		HBox btnBox = new HBox();
		btnBox.setAlignment(Pos.CENTER);
		btnBox.getChildren().addAll(userButton, infoButton, chb, terosButton, backButton, forwardButton);
		HBox.setMargin(userButton, new Insets(0, 10, 0, 0));
		HBox.setMargin(terosButton, new Insets(0, 10, 0, 0));
		HBox.setMargin(backButton, new Insets(0, 10, 0, 0));
		HBox.setMargin(forwardButton, new Insets(0, 10, 0, 0));
		HBox.setMargin(infoButton, new Insets(0, 10, 0, 0));
		HBox.setMargin(chb, new Insets(0, 10, 0, 0));

		pageToolBar.getItems().add(btnBox);

		// Inicio breadcrumbar
		breadcrumbBar = new TedrosBoxBreadcrumbBar();
		pageToolBar.getItems().add(breadcrumbBar);
		BorderPane.setMargin(pageToolBar, new Insets(0, 0, 0, 32));
		// Fim breadcrumbar

		// create page area
		pageArea = new Pane() {
			@Override
			protected void layoutChildren() {
				for (Node child : pageArea.getChildren()) {
					child.resizeRelocate(0, 0, pageArea.getWidth(), pageArea.getHeight());
				}
			}
		};
		pageArea.setId("t-app-area");
		pageArea.setStyle(FX_BACKGROUND_COLOR_TRANSPARENT);
		// create main pane
		mainPane = new BorderPane();

		mainPane.setCenter(pageArea);
		mainPane.setMinWidth(300);
		mainPane.setStyle("-fx-effect: dropshadow( three-pass-box , #000000 , 9, 0.1 , 0 , 4); "
				+ "-fx-text-fill: #FFFFFF; -fx-background-color: transparent;");

		this.innerPane = new TSliderMenu(mainPane);
		innerPane.settMenuVisible(false);
		root.setTop(toolBar);
		root.setCenter(this.innerPane);
		root.setStyle(FX_BACKGROUND_COLOR_TRANSPARENT);
		// add window resize button so its on top
		windowResizeButton.setManaged(false);

		root.getChildren().addAll(windowResizeButton);

		// sets a modal pane for messages and nodes
		modalMessage = new TModalPane(layerPane);
		tModalPane = new TModalPane(innerPane);

		// Init Navigation Manager
		navManager = new TedrosNavigationManager(menuTree, pageArea, breadcrumbBar, historySize, forwardSize);

		menuTree.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> {
					if (!navManager.isChangingPage()) {
						Page selectedPage = (Page) menuTree.getSelectionModel().getSelectedItem();
						if (selectedPage != navManager.getPages().getRoot())
							navManager.goToPage(selectedPage);
					}
				});

		// configura listener para exibir mensagens
		TedrosContext.messageListProperty()
				.addListener((Change c) -> {
					if (!c.getList().isEmpty()) {
						logoManager.playEffect(); // Use manager

						modalMessage.showModal(new TMessageBox(c.getList()),
								ev -> TedrosContext.messageListProperty().clear());

					} else {
						if (modalMessage != null) {
							modalMessage.hideModal();
							logoManager.stopEffect(); // Use manager
						}
					}
				});

		TedrosContext.infoListProperty()
				.addListener((Change c) -> popOverManager.showInfoPopOver(scene));

		TedrosContext.showModalProperty()
				.addListener((a, o, newValue) -> {
					if (newValue && TedrosContext.getModal() != null) {
						logoManager.playEffect(); // Use manager
						tModalPane.showModal(TedrosContext.getModal());
					} else {
						if (tModalPane != null) {
							tModalPane.hideModal();
							logoManager.stopEffect(); // Use manager
						}
					}
				});

		TedrosContext.reloadStyleProperty()
				.addListener((a, o, n) -> {
					logoManager.playEffect(); // Use manager
					TedrosBoxStyleHelper.reloadStyle(scene, root);
					logoManager.stopEffect(); // Use manager
				});

		getStage().setScene(scene);
		getStage().show();
		windowButtons.toogleMaximized();
		TedrosContext.showModal(buildLogin());
	}

	public void showDefaultLogo() {
		logoManager.showDefaultLogo();
	}
	
	@Override
	public void showLogo(String logoFileName, String brand, Double brandLeftMargin) {
		this.logoManager.showLogo(logoFileName, brand, brandLeftMargin);
	}

	@Override
	public void stop() throws Exception {
		super.stop();
		TedrosContext.exit();
	}

	public void logout() {
		popOverManager.hideAllPopOver();
		popOverManager.disposeChatView();

		TedrosContext.setView(null);
		breadcrumbBar.setPath("");
		innerPane.settMenuOpened(false);
		innerPane.tClearMenuContent();
		innerPane.settMenuVisible(false);
		mainPane.setTop(null);
		navManager.reset(); // Handle reset logic in manage
		TedrosContext.removeUserSession();
		showDefaultLogo();
	}

	@SuppressWarnings("unchecked")
	public void buildApplicationMenu() {
		Pages pages = new Pages();
		navManager.setPages(pages);
		menuTree.setRoot(pages.getRoot());
		pages.parseModules();
		if (navManager.getCurrentPage() != null) {
			Node n = navManager.getCurrentPage().getModule();
			if (n != null && n instanceof ITModule itModule)
				itModule.tStop();
			// navManager handle nulling current page?
			// doing it via reset/reload style or just accessing
		}
		TedrosContext.getWindows().clear();
		TedrosContext.setView(null);
		// goto initial page
		navManager.goToPage(pages.getModules());
		innerPane.settMenuContent(leftMenuPane);
		innerPane.settMenuVisible(true);
		mainPane.setTop(this.pageToolBar);
		navManager.clearPageHistory();
	}
	
	public void buildSettingsPane() {
		popOverManager.buildSettingsPane();
	}

	public Node buildLogin() {

		TModule module = new TModule() {
			@Override
			public void tStart() {
				TDynaView<LoginMV> view = new TDynaView<>(LoginMV.class, TDetachViewType.NONE);
				tShowView(view);
			}

			@Override
			public boolean tStop() {
				TModuleContext context = TedrosAppManager.getInstance().getModuleContext(this);
				boolean flag = true;
				if (context != null) {
					flag = context.stopModule();
					context = null;
				}
				return flag;
			}
		};

		module.tStart();

		return module;
	}

	public Pages getPages() {
		return navManager.getPages();
	}

	public void goToPage(String pagePath) {
		navManager.goToPage(pagePath);
	}

	public void goToPage(String pagePath, boolean force) {
		navManager.goToPage(pagePath, force);
	}

	public void goToPage(Page page) {
		navManager.goToPage(page);
	}

	@Override
	public void detachView(Node view) {
		navManager.detachView(view);
	}

	/**
	 * Check if current call stack was from back or forward button's action
	 * 
	 * @return True if current call was caused by action on back or forward button
	 */
	public boolean isFromForwardOrBackButton() {
		return navManager.isFromForwardOrBackButton();
	}

	/**
	 * Got to previous page in history
	 */
	public void back() {
		navManager.back();
	}

	/**
	 * Go to next page in history if there is one
	 */
	public void forward() {
		navManager.forward();
	}

	@Override
	public void clearPageHistory() {
		navManager.clearPageHistory();
	}

	private static void setInstance(TedrosBox tedrosBox) {
		TedrosBox.tedros = tedrosBox;
	}

	/**
	 * Reload the current page
	 */
	public void reload() {
		navManager.reload();
	}

	/**
	 * ###################
	 * TedrosBox start
	 * ###################
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		LOGGER.info("Starting TedrosBox Application...");
		TLanguage.addResourceBundle(null, "TedrosLoginLabels", TedrosRelease.class.getClassLoader());
		TLanguage.addResourceBundle(null, "TCoreLabels", TCoreKeys.class.getClassLoader());
		TLanguage.addResourceBundles(null, TFxKey.class.getClassLoader(), "TFx", "TUsual");
		init(primaryStage);
		primaryStage.show();
	}

	public Stage getStage() {
		return stage;
	}

	public static TedrosBox getTedros() {
		return tedros;
	}

	public double getXStage() {
		return getStage().getX();
	}

	public double getYStage() {
		return getStage().getY();
	}	
}
