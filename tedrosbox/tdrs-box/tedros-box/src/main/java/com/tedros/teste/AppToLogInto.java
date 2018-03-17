package com.tedros.teste;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import com.tedros.teste.LoginPreloader.CredentialsConsumer;

public class AppToLogInto extends Application implements CredentialsConsumer {
    String user = null;
    Label l = new Label("eeee");
    Stage stage = null;
    
    private void mayBeShow() {
        // Show the application if it has credentials and 
        // the application stage is ready
        if (user != null && stage != null) {
            Platform.runLater(new Runnable() {
                public void run() {
                    stage.show();
                }                
            });
        }
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.setScene(new Scene(l, 400, 400));
        mayBeShow();
    }
 
    public void setCredential(String user, String password) {
        this.user = user;
        l.setText("Hello "+user+"!");
        mayBeShow();
    }
    
    public static void main(String[] args) {
		launch(args);
	}
}

