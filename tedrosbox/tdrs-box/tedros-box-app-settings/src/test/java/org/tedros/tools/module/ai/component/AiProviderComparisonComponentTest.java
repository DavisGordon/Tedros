package org.tedros.tools.module.ai.component;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.tedros.tools.module.ai.component.AiProviderComparisonComponent;

public class AiProviderComparisonComponentTest extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            BorderPane root = new BorderPane();
            AiProviderComparisonComponent component = new AiProviderComparisonComponent();
            component.tInitializeComponent(null);
            root.setCenter(component);

            Scene scene = new Scene(root, 1200, 800);
            primaryStage.setScene(scene);
            primaryStage.setTitle("AI Provider Comparison Component Test");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
