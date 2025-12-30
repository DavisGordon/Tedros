package org.tedros.fx.control;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.stage.Window;
import javafx.util.Duration;

public class TFadingTooltip extends Tooltip {
    private static final Duration FADE_DURATION = Duration.millis(1000); // Duração do fade (ajuste se quiser mais lento/rápido)

    public TFadingTooltip(String text) {
        super(text);
    }

    @Override
    public void show(Window owner) {
        // Define opacidade inicial como 0 antes de mostrar
        Node content = getScene().getRoot();
        content.setOpacity(0);
        
        super.show(owner);
        
        // Fade-in: de 0 para 1
        FadeTransition fadeIn = new FadeTransition(FADE_DURATION, content);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }

    @Override
    public void hide() {
        // Fade-out: de 1 para 0, e esconde após terminar
        Node content = getScene().getRoot();
        FadeTransition fadeOut = new FadeTransition(FADE_DURATION, content);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished(e -> super.hide());
        fadeOut.play();
    }
}
