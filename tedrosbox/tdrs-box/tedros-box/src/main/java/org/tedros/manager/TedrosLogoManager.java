package org.tedros.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.tedros.TedrosRelease;
import org.tedros.core.TLanguage;
import org.tedros.core.control.PopOver;
import org.tedros.util.TedrosFolder;
import org.tedros.fx.control.TLabel;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class TedrosLogoManager {

    private ImageView imgLogo;
    private StackPane logoPane;
    private Label appName;
    private FadeTransition logoEffect;
    private ChangeListener<Number> effectChl;

    public TedrosLogoManager(StackPane logoPane) {
        this.logoPane = logoPane;
    }

    public void showDefaultLogo() {
        String logoFileName = TedrosFolder.IMAGES_FOLDER.getFullPath() + File.separator + "logo-tedros-small.png";
        String brand = "Tedros";
        double brandLeftMargin = 55;
        showLogo(logoFileName, brand, brandLeftMargin);
    }

    public void showLogo(String imagePath, String brand, Double brandLeftMargin) {
        int size = logoPane.getChildren().size();
        for (int x = 0; x < size; x++)
            logoPane.getChildren().remove(0);

        imgLogo = null;
        logoEffect = null;
        effectChl = null;

        createLogoImageView(imagePath);

        // add Logo and app name
        DropShadow nameLogoEffect = buildLogoEffect();

        if (imgLogo != null) {
            imgLogo.setEffect(nameLogoEffect);

            HBox h = new HBox();
            h.setAlignment(Pos.CENTER_LEFT);
            HBox.setMargin(imgLogo, new Insets(8, 0, 0, 8));
            h.getChildren().addAll(imgLogo);

            logoPane.getChildren().add(h);

            logoEffect = new FadeTransition(Duration.millis(2000), imgLogo);
            logoEffect.setFromValue(1.0);
            logoEffect.setToValue(0.3);
            logoEffect.setCycleCount(Animation.INDEFINITE);
            logoEffect.setAutoReverse(true);
            effectChl = (a, o, n) -> {
                if (n.intValue() == 1)
                    logoEffect.stop();
            };
        }

        if (appName == null) {
            appName = new Label();
            appName.setEffect(nameLogoEffect);
            appName.setCache(true);
            appName.setId("t-app-name");
            appName.setCursor(Cursor.HAND);
            appName.setOnMouseClicked(e -> {
                String tt = TLanguage.getInstance(null).getFormatedString("#{tedros.tooltip}",
                        TedrosRelease.version);
                TLabel l = new TLabel(tt);
                l.setFont(Font.font(11));
                PopOver p = new PopOver();
                p.setCloseButtonEnabled(true);
                p.setContentNode(l);
                p.getRoot().setPadding(new Insets(20, 20, 20, 20));
                p.show(appName);
            });
        }

        appName.setText(brand == null ? "" : brand);
        if (brandLeftMargin != null)
            StackPane.setMargin(appName, new Insets(0, 0, 0, brandLeftMargin));

        logoPane.getChildren().add(appName);
    }

    private void createLogoImageView(String path) {
        if (path != null) {
            imgLogo = new ImageView();
            try (InputStream is = new FileInputStream(new File(path))) {
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

    public void playEffect() {
        if (imgLogo != null && logoEffect != null && effectChl != null) {
            imgLogo.opacityProperty().removeListener(effectChl);
            logoEffect.play();
        }
    }

    public void stopEffect() {
        if (imgLogo != null && effectChl != null) {
            imgLogo.opacityProperty().addListener(effectChl);
        }
    }
}
