package org.tedros.fx.presenter.view;

import java.io.IOException;
import java.net.URL;
import java.util.function.Consumer;

import org.tedros.api.presenter.ITPresenter;
import org.tedros.api.presenter.view.ITView;
import org.tedros.api.presenter.view.TDetachViewType;
import org.tedros.api.presenter.view.TViewState;
import org.tedros.core.TLanguage;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.control.ITProgressIndicator;
import org.tedros.core.control.TProgressIndicator;
import org.tedros.core.ux.ITWindow;
import org.tedros.fx.TFxKey;
import org.tedros.fx.modal.TModalPane;
import org.tedros.util.TLoggerUtil;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

@SuppressWarnings("rawtypes")
public abstract class TView<P extends ITPresenter>
		extends StackPane implements ITView<P> {

	private URL fxmlURL;
	private String tViewId;
	private final P presenter;
	private TModalPane modalPane;
	private ITProgressIndicator progressIndicator;
	private SimpleObjectProperty<TViewState> stateProperty = new SimpleObjectProperty<>(TViewState.CREATED);
	
	// Tooltip for hover information
	private Tooltip detachTooltip = new Tooltip(TLanguage.getInstance().getString(TFxKey.TOOLTIP_DETACH_VIEW));
	
	// Event handler for double-click to detach
	private EventHandler<MouseEvent> doubleClickEvent = e -> {
	    if (e.getClickCount() == 2) {
	        TedrosContext.detachView(this);
	    }
	};
	
	protected TView(P presenter) {
		this.presenter = presenter;
		initListener();
		tInitializePresenter();	
	}

	protected TView(P presenter, URL fxmlURL) {
		this.presenter = presenter;
		this.fxmlURL = fxmlURL;
		tLoadFXML();
		initListener();
		tInitializePresenter();
		
	}
	
	public abstract Node gettTargetNodeForDetachAction();

	private void initListener() {
		this.stateProperty.addListener((a ,o, n) -> {
			if (n == TViewState.READY) {
				buildViewActions();
			}
		});
		
		this.sceneProperty().addListener((a ,o, n) -> {
			if (n != null && n.getUserData() != null && n.getUserData().equals(ITWindow.SCENE_ID)) {
				gettTargetNodeForDetachAction().setOnMouseClicked(null);
				Tooltip.uninstall(gettTargetNodeForDetachAction(), detachTooltip);
			}
			
			if (n != null && n.getUserData() == null) {
				buildViewActions();
			}
		});
	}

	private void buildViewActions() {
		if(this.presenter.getDetachViewType().equals(TDetachViewType.AUTO)) {
			buildMouseClickedAction();
			if(this.getScene()!=null && this.getScene().getUserData() == null) 
			{
				TedrosContext.detachView(this);
			}
		}
			
		if(this.presenter.getDetachViewType().equals(TDetachViewType.MANUAL))
			buildMouseClickedAction();
	}

	private void buildMouseClickedAction() {
		gettTargetNodeForDetachAction().setOnMouseClicked(doubleClickEvent);
		detachTooltip.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
		detachTooltip.setShowDelay(Duration.millis(200)); // Optional: Delay before showing (200ms)
	    detachTooltip.setHideDelay(Duration.millis(500)); // Optional: Delay before hiding (500ms)
	    Tooltip.install(gettTargetNodeForDetachAction(), detachTooltip); // Install the tooltip on the component
	}

	@SuppressWarnings("unchecked")
	public void tInitializePresenter() {
		if (this.presenter == null)
			throw new IllegalStateException();

		this.presenter.setView(this);
		this.presenter.initialize();
	}

	@Override
	public void tLoad() {
		TLoggerUtil.splitDebugLine(getClass(), '~');
		TLoggerUtil.timeComplexity(getClass(), "Loading view: " + getClass().getSimpleName(),
				() -> gettPresenter().loadView());
	}

	public void tLoadFXML() {

		if (gettFxmlURL() == null)
			throw new IllegalArgumentException("ERROR: FXML not defined!");

		try {
			FXMLLoader fxmlLoader = new FXMLLoader(gettFxmlURL());
			fxmlLoader.setRoot(this);
			fxmlLoader.setController(this);
			fxmlLoader.load();
			TLoggerUtil.debug(getClass(), "FXML " + gettFxmlURL() + " loaded!");
		} catch (IOException e) {
			TLoggerUtil.error(e.getMessage(), e);
		}
	}

	public P gettPresenter() {
		return this.presenter;
	}

	/**
	 * Show the modal
	 */
	public void tShowModal(Node message, boolean closeModalOnMouseClick) {
		initializeModalPane();
		modalPane.showModal(message, closeModalOnMouseClick);
	}

	/**
	 * Show the modal
	 */
	public void tShowModal(Node message, boolean closeModalOnMouseClick, Consumer<Node> closeCallback) {
		initializeModalPane();
		modalPane.showModal(message, closeModalOnMouseClick, closeCallback);
	}

	public ReadOnlyBooleanProperty tModalVisibleProperty() {
		initializeModalPane();
		return modalPane.visibleProperty();
	}

	/**
	 * Close the modal
	 */
	public void tHideModal() {
		if (modalPane != null) {
			modalPane.hideModal();
		}
	}

	@Override
	public void settProgressIndicator(ITProgressIndicator progressIndicator) {
		this.progressIndicator = progressIndicator;
		this.progressIndicator.initialize(this);
	}

	public ITProgressIndicator gettProgressIndicator() {
		initializeProgressIndicator();
		return progressIndicator;
	}

	@Override
	public URL gettFxmlURL() {
		return fxmlURL;
	}

	@Override
	public void settFxmlURL(URL fxmlUrl) {
		this.fxmlURL = fxmlUrl;
	}

	public String gettViewId() {
		return tViewId;
	}

	public void settViewId(String id) {
		this.tViewId = id;

	}

	public TModalPane getModalPane() {
		return modalPane;
	}

	public void setModalPane(TModalPane modalPane) {
		this.modalPane = modalPane;
	}

	private void initializeModalPane() {
		if (modalPane == null) {
			modalPane = new TModalPane(this);
		}
	}

	private void initializeProgressIndicator() {
		if (progressIndicator == null)
			progressIndicator = new TProgressIndicator(this);
	}

	public void settState(TViewState state) {
		this.stateProperty.setValue(state);
	}

	public TViewState gettState() {
		return this.stateProperty.getValue();
	}

	/**
	 * @return the stateProperty
	 */
	public ReadOnlyObjectProperty<TViewState> tStateProperty() {
		return stateProperty;
	}

}
