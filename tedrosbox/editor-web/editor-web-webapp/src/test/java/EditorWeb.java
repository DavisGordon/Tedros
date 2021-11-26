import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class EditorWeb extends Application {

	@Override
	public void start(Stage s) throws Exception {

		StackPane pane = new StackPane();
		HTMLEditor e = new HTMLEditor();
		WebView wv = (WebView) e.lookup("WebView");
		wv.getEngine().load("file:C:/Users/Davis Gordon/git/Tedros/tedrosbox/editor-web/editor-web-webapp/src/main/webapp/story/edit.html");
		
		pane.getChildren().add(e);
		s.setScene(new Scene(pane));
		s.show();
		
		

	}

	public static void main(String[] args) {
		launch(args);
	}

}
