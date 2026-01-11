package org.tedros.manager;

import org.tedros.api.presenter.view.TDetachViewType;
import org.tedros.api.presenter.view.TViewState;
import org.tedros.chat.module.client.behaviour.ChatBehaviour;
import org.tedros.chat.module.client.decorator.ChatDecorator;
import org.tedros.chat.module.client.model.ChatMV;
import org.tedros.core.TLanguage;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.control.PopOver;
import org.tedros.core.control.PopOver.ArrowLocation;
import org.tedros.core.message.TMessage;
import org.tedros.core.message.TMessageType;
import org.tedros.fx.modal.TMessageBox;

import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.fx.presenter.dynamic.view.TDynaView;
import org.tedros.tools.ai.pane.TerosPane;
import org.tedros.tools.logged.user.TMainSettingsPane;
import org.tedros.tools.logged.user.TUserSettingsPane;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

public class TedrosPopOverManager {

    private static final double GAP = 15;

    private Popup userPopOver;
    private PopOver infoPopOver;
    private PopOver chatPopOver;
    private PopOver terosPopOver;
    private TDynaView<ChatMV> chatView;
    private Accordion settingsAcc;

    private Button userButton;
    private Button infoButton;
    private Button chatButton;
    private Button terosButton;
    private Label chatUnreadMsgsLabel;

    private ChangeListener<TViewState> chatViewStateChl;

    public TedrosPopOverManager(Button userButton, Button infoButton, Button chatButton, Button terosButton,
            Label chatUnreadMsgsLabel) {
        this.userButton = userButton;
        this.infoButton = infoButton;
        this.chatButton = chatButton;
        this.terosButton = terosButton;
        this.chatUnreadMsgsLabel = chatUnreadMsgsLabel;

        initChatListener();
    }

    private void initChatListener() {
        chatViewStateChl = (a, o, n) -> {
            if (n != null && n.equals(TViewState.READY)) {
                TDynaPresenter<ChatMV> p = chatView.gettPresenter();
                ChatBehaviour bhv = (ChatBehaviour) p.getBehavior();
                ChatDecorator dec = (ChatDecorator) p.getDecorator();
                dec.getHidePopOverButton().setOnAction(ev -> chatPopOver.hide());
                bhv.totalUnreadMessagesProperty()
                        .addListener((x, y, z) -> this.chatUnreadMsgsLabel.setText(String.valueOf(z)));

                this.chatUnreadMsgsLabel.setText(String.valueOf(bhv.totalUnreadMessagesProperty().getValue()));
                bhv.hidePopOverProperty().addListener((x, y, z) -> {
                    if (z)
                        chatPopOver.hide();
                    else if (!chatPopOver.isShowing())
                        chatPopOver.show(chatButton);
                });
            }
        };
    }

    public void showTerosPopOver() {
        if (terosPopOver == null) {
            terosPopOver = new PopOver();
            terosPopOver.setTitle("Teros");
            terosPopOver.setHeaderAlwaysVisible(false);
            terosPopOver.setAutoFix(true);
            terosPopOver.setCloseButtonEnabled(true);
            terosPopOver.setArrowLocation(ArrowLocation.TOP_LEFT);
            terosPopOver.setContentNode(new TerosPane());
        }
        if (terosPopOver.isShowing())
            terosPopOver.hide();
        else {
            Point2D point = terosButton.localToScreen(0, terosButton.getHeight());
            terosPopOver.show(terosButton, point.getX() + GAP,
                    point.getY() + GAP);
        }
    }

    public void showUserPopOver() {

        if (userPopOver == null) {

            String linhaSuave = "rgba(0, 255, 255, 0.4)";
            String brilhoSuave = "rgba(0, 255, 255, 0.25)";

            StackPane sp = new StackPane();
            sp.setStyle("-fx-background-color: transparent;" +
                    "-fx-border-color: " + linhaSuave + ";" +
                    "-fx-border-radius: 8px 8px 8px 8px;" +
                    "-fx-border-width: 1;" +
                    "-fx-effect: dropshadow(three-pass-box, " + brilhoSuave + ", 12, 0.0, 0, 0);");
            sp.getChildren().add(this.settingsAcc);
            StackPane.setMargin(sp, new Insets(4));

            userPopOver = new Popup();
            userPopOver.setAutoFix(true);
            userPopOver.setAutoHide(true);
            userPopOver.getContent().add(sp);

        }

        if (userPopOver.isShowing())
            userPopOver.hide();
        else {
            Point2D point = userButton.localToScreen(0, userButton.getHeight());
            userPopOver.show(userButton, point.getX(),
                    point.getY() + GAP);
        }
    }

    public void showInfoPopOver(Scene scene) {
        hideInfoPopOver();
        infoPopOver = null;
        double h = scene.getHeight() - 200;
        StackPane infoPane = new StackPane();
        infoPane.setMaxHeight(h - 80);
        infoPopOver = new PopOver();
        infoPopOver.setHeaderAlwaysVisible(true);
        infoPopOver.setAutoFix(true);
        infoPopOver.setCloseButtonEnabled(true);
        infoPopOver.setArrowLocation(ArrowLocation.TOP_LEFT);

        infoPopOver.setMaxHeight(h);
        VBox vb = new VBox(8);
        ScrollPane scroll = new ScrollPane();
        scroll.setFitToHeight(true);
        scroll.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
        scroll.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        scroll.setStyle("-fx-background-color: transparent");
        scroll.setPadding(new Insets(8));
        scroll.setContent(vb);
        infoPane.getChildren().add(scroll);
        StackPane.setMargin(scroll, new Insets(8));
        infoPopOver.setContentNode(infoPane);
        Platform.runLater(() -> {
            if (infoPopOver != null) {
                Point2D point = infoButton.localToScreen(0, infoButton.getHeight());
                infoPopOver.show(infoButton, point.getX() + GAP,
                        point.getY() + GAP);

                if (!TedrosContext.infoListProperty().isEmpty()) {
                    TedrosContext.infoListProperty().forEach(c -> {
                        ((TMessage) c).setLoaded(false);
                        vb.getChildren().add(TMessageBox.buildMessagePane((TMessage) c));
                    });
                } else
                    vb.getChildren().add(TMessageBox
                            .buildMessagePane(new TMessage(TMessageType.GENERIC, "...")));
            }
        });
    }

    public void showChatPopOver(Scene scene) {
        double h = scene.getHeight() - 200;
        StackPane infoPane = new StackPane();
        infoPane.setMaxHeight(h - 80);
        if (chatPopOver == null) {
            chatPopOver = new PopOver();
            chatPopOver.setHeaderAlwaysVisible(false);
            chatPopOver.setAutoFix(true);
            chatPopOver.setCloseButtonEnabled(true);
            chatPopOver.setAutoHide(false);
            chatPopOver.setArrowLocation(ArrowLocation.TOP_LEFT);
            chatPopOver.setCornerRadius(20);
            chatPopOver.setMaxHeight(h);

        } else {
            TDynaPresenter<ChatMV> p = chatView.gettPresenter();
            ChatBehaviour bhv = (ChatBehaviour) p.getBehavior();
            bhv.setHidePopOver(false);
        }
        StackPane sp = new StackPane();
        sp.getStyleClass().add("t-settings-header");
        sp.getChildren().add(chatView);
        chatPopOver.setContentNode(sp);

        if (chatPopOver.isShowing())
            chatPopOver.hide();
        else {
            Point2D point = chatButton.localToScreen(0, chatButton.getHeight());
            chatPopOver.show(chatButton, point.getX() + GAP,
                    point.getY() + GAP);
        }
    }

    public void hideAllPopOver() {
        hideUserPopOver();
        hideInfoPopOver();
        hideChatPopOver();
        hideTerosPopOver();
    }

    public void hideChatPopOver() {
        if (chatPopOver != null)
            chatPopOver.hide();
    }

    public void hideInfoPopOver() {
        if (infoPopOver != null)
            infoPopOver.hide();
    }

    public void hideUserPopOver() {
        if (userPopOver != null)
            userPopOver.hide();
    }

    public void hideTerosPopOver() {
        if (terosPopOver != null)
            terosPopOver.hide();
    }

    @SuppressWarnings({ "rawtypes" })
    public void buildSettingsPane() {
        TLanguage iEngine = TLanguage.getInstance(null);
        hideInfoPopOver();
        hideChatPopOver();
        if (chatView != null) {
            chatView.tStateProperty().removeListener(chatViewStateChl);
            chatView.gettPresenter().invalidate();
            chatView = null;
        }

        chatView = new TDynaView<>(ChatMV.class, TDetachViewType.NONE);
        chatView.tStateProperty().addListener(chatViewStateChl);
        chatView.sceneProperty().addListener((ob, o, n) -> {
            if (n != null)
                chatView.tLoad();
        });

        if (settingsAcc == null) {
            settingsAcc = new Accordion();
            settingsAcc.autosize();
            settingsAcc.getStyleClass().add("t-accordion-menu-action");
        } else {
            for (TitledPane t : settingsAcc.getPanes())
                ((TDynaPresenter) ((TDynaView) ((StackPane) t.getContent())
                        .getChildren().get(0))
                        .gettPresenter()).invalidate();

            settingsAcc.getPanes().clear();

        }

        TUserSettingsPane u = new TUserSettingsPane();
        u.setMinWidth(350);
        TitledPane t = new TitledPane();
        t.setText(iEngine.getString("#{tedros.setting.user}"));
        t.getStyleClass().add("first-titled-pane");
        t.setContent(u);

        TitledPane t2 = new TitledPane();
        t2.setText(iEngine.getString("#{tedros.setting.main}"));
        t2.getStyleClass().add("last-titled-pane");
        t2.setContent(new TMainSettingsPane());

        settingsAcc.setExpandedPane(t);
        settingsAcc.getPanes().addAll(t, t2);
    }

    public void disposeChatView() {
        if (chatView != null) {
            chatView.gettPresenter().invalidate();
            chatView = null;
        }
    }

    public TDynaView<ChatMV> getChatView() {
        return chatView;
    }
}
