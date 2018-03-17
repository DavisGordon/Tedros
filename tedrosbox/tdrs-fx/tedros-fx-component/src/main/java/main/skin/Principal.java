/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 30/04/2014
 */
package main.skin;



import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.SceneBuilder;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class Principal extends Application {

	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage stage) throws Exception {
		
		
		StackPane stpane = new StackPane();
		
		Scene scene = SceneBuilder.create()
				.height(400)
				.width(600)
				.root(stpane)
				.build();
		
		
		stage.setScene(scene);
		
		BorderPane bp = new BorderPane();
		
		Text txt = new Text("TESTE");
		TextField tx = new TextField("teste");
		
		tx.textProperty().bindBidirectional(txt.textProperty());
		
		
		
		
		
		bp.setTop(txt);
		
		bp.setBottom(tx);
		
		
		stpane.getChildren().add(bp);
		
		stage.show();

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);

	}

}
