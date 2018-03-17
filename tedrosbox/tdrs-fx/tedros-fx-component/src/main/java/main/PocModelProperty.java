package main;

import org.apache.commons.lang3.RandomStringUtils;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import main.references.Model;
import main.references.ModelProperty;

public class PocModelProperty {
	
	private ModelProperty modelProperty;

	public PocModelProperty(VBox prateleira) {
		
		Button btn = new Button("Build");
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				
				if(modelProperty==null){
					modelProperty = new ModelProperty(new Model("Davis "+RandomStringUtils.randomAlphanumeric(2)));
					
					System.out.println("### MODEL PROPERTY CREATED = " + modelProperty);
				}else
					System.out.println("### MODEL PROPERTY ALREADY EXISTS = " + modelProperty);
				
				
				
				/*URL url = this.getClass().getClassLoader().getResource("main/teste4.html");
				File f;
				try {
					f = new File(url.toURI());
					StringBuffer sbf = new StringBuffer(FileUtils.readFileToString(f));
					
					wv.getEngine().loadContent(sbf.toString());	
					//wv.getEngine().load("http://canvasjs.com/editor/?id=http://canvasjs.com/example/gallery/overview/simple-column/");
				} catch (URISyntaxException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
			}
		});
		
		Button btn2 = new Button("Clear");
		btn2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if(modelProperty!=null)
					modelProperty.removeAllListener();
				modelProperty = null;
				System.out.println("### MODEL PROPERTY = " + modelProperty);
			}
		});
		
		
		
		prateleira.getChildren().addAll(btn, btn2);
		
	}
	
}
