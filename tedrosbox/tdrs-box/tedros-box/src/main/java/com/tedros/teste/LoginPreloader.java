package com.tedros.teste;

import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginPreloader extends Preloader {
	
    public static interface CredentialsConsumer {
        public void setCredential(String user, String password);
    }
    
    Stage stage = null;
    ProgressBar bar = null;
    CredentialsConsumer consumer = null;
    String username = null;
    String password = null;
    
    private Scene createLoginScene() {
        VBox vbox = new VBox();
 
        final TextField userNameBox = new TextField();
        userNameBox.setPromptText("name");
        vbox.getChildren().add(userNameBox);
        
        final PasswordField passwordBox = new PasswordField();
        passwordBox.setPromptText("password");
        vbox.getChildren().add(passwordBox);
        
        final Button button = new Button("Log in");
        button.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t) {
                // Save credentials
                username = userNameBox.getText();
                password = passwordBox.getText();
                
                // Do not allow any further edits
                userNameBox.setEditable(false);
                passwordBox.setEditable(false);
                button.setDisable(true);
                
                // Hide if app is ready
                mayBeHide();
            }
        });
        vbox.getChildren().add(button);
        
        bar = new ProgressBar(0);
        vbox.getChildren().add(bar);
        bar.setVisible(false);
        
        Scene sc = new Scene(vbox, 200, 200);
        return sc;
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.setScene(createLoginScene());
        stage.show();
    }
    
    @Override
    public void handleProgressNotification(ProgressNotification pn) {
        bar.setProgress(pn.getProgress());
        if (pn.getProgress() > 0 && pn.getProgress() < 1.0) {
            bar.setVisible(true);
        }
    }
 
    private void mayBeHide() {
        if (stage.isShowing() && username != null && consumer != null) {
            consumer.setCredential(username, password);
            Platform.runLater(new Runnable() {
                public void run() {
                   stage.hide();
                }
            });
        }
    }
    
    @Override
    public void handleStateChangeNotification(StateChangeNotification evt) {
        if (evt.getType() == StateChangeNotification.Type.BEFORE_START) {
            //application is loaded => hide progress bar
            bar.setVisible(false);
            
            consumer = (CredentialsConsumer) evt.getApplication();
            //hide preloader if credentials are entered
            mayBeHide();
        }
    }    
    
    public static void main(String[] args) {
		launch(args);
	}
}
