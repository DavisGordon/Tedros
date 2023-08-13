package main;

import javafx.application.Application;
import javafx.concurrent.Worker.State;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

public class Test extends Application {

	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {	
		
		WebSetting setting = new WebSetting();
		
		Scene scene = new Scene(setting.pane, 1280, 720);
		stage.setScene(scene);
		stage.show();
	}
	
	public class WebSetting {
		StackPane modal;
		StackPane pane;
		WebView webView;
		
		public WebSetting() {
			webView = new WebView();
			WebEngine webEngine = webView.getEngine(); 
			pane = new StackPane();
			
			TextField txtField = new TextField();
			txtField.textProperty()
			.addListener((a,o,n)->writeMsg(n!=null && !"".equals(n) ? n : ""));
			Button btn = new Button("Call JS");
			btn.setOnAction(ev->{
				String js = "alert('test')";
				webEngine.executeScript(js);
				
			});
			HBox hb = new HBox(10);
			hb.getChildren().addAll(txtField, btn);
			HBox.setHgrow(txtField, Priority.ALWAYS);
			BorderPane layout = new BorderPane();
			BorderPane.setMargin(txtField, new Insets(10));
			layout.setTop(hb);
			layout.setCenter(webView);
			
			pane.getChildren().add(layout);       
			webEngine.setJavaScriptEnabled(true); 
			
			webEngine.getLoadWorker().stateProperty().addListener((a, b, n)->{
				if(State.SUCCEEDED==n) {
					// cria uma ponte (bridge) entre a pagina html e a classe java
					JSObject window = (JSObject) webEngine.executeScript("window");
					window.setMember("tedros", this);

			        webEngine.setOnAlert(event -> showAlert(event.getData()));
			        webEngine.setConfirmHandler(message -> showConfirm(message));
				}
			});
			
			webEngine.load("http://localhost:8081/tedros-webapp/vista2.html");              
			//webEngine.load("https://multigradoballestero.online/ingles/vista");              
			
		}
		
		public void writeMsg(String str) {
			webView.getEngine().executeScript("writeText('"+str+"')");
		}
		
		public void showMsg(String str) {
			if(modal==null) {
				modal = new StackPane();
				modal.setStyle("-fx-background-color: rgba(0,0,0,0.75);"); 
				modal.setOnMouseClicked(ev->modal.setVisible(false));
				pane.getChildren().add(modal);
			}
			
			Label txt = new Label(str);
			//txt.setFont(Font.font("Courier", FontWeight.EXTRA_BOLD, 40));
			txt.setStyle("-fx-font: Arial; "+ 
					"	-fx-font-size: 2.5em;" + 
					"	-fx-font-weight: bold;" + 
					"	-fx-font-smoothing-type:lcd;" + 
					"	-fx-text-fill: #ffffff;");
			modal.setVisible(true);
			modal.getChildren().clear();
			modal.getChildren().add(txt);
		}
	
		// metodo a ser chamado via javascriot
		public void log(String msg) {
			System.out.println(msg);
		}
		
		 private void showAlert(String message) {
		        Dialog<Void> alert = new Dialog<>();
		        alert.getDialogPane().setContentText(message);
		        alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
		        alert.showAndWait();
		    }

		    private boolean showConfirm(String message) {
		        Dialog<ButtonType> confirm = new Dialog<>();
		        confirm.getDialogPane().setContentText(message);
		        confirm.getDialogPane().getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
		        boolean result = confirm.showAndWait().filter(ButtonType.YES::equals).isPresent();

		        // for debugging:
		        System.out.println(result);

		        return result ;
		    }
	}
	
	
}