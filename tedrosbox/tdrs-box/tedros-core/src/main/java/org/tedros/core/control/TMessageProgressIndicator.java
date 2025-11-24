/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 22/01/2014
 */
package org.tedros.core.control;

import org.tedros.core.context.TedrosContext;

import javafx.animation.Animation.Status;
import javafx.animation.FadeTransition;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Progress indicator
 *
 * @author Davis Gordon
 */
public class TMessageProgressIndicator implements ITProgressIndicator {

	private Region veil;
	private ImageView progressIndicator;
	private FadeTransition ft;
	private Pane pane;
	private BorderPane borderPane;
	private VBox messageBox;
	private ScrollPane scrollPane;
	private boolean scrollFlag = false;
	
	public TMessageProgressIndicator() {
		
	}
	
	public TMessageProgressIndicator(final Pane pane) {
		initialize(pane);
	}
	
	public void initialize(Pane pane) {
		this.pane = pane;
		
		this.veil = new Region();
		this.veil.setVisible(false);
		
		String name = pane.getClass().getSimpleName();
		if(name.contains("Form") || name.contains("GroupView"))
			veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7); -fx-background-radius: 0 0 20 20;");
		else
			veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7); -fx-background-radius: 20 20 20 20;");
		
		this.progressIndicator = new ImageView();
		this.progressIndicator.setVisible(false);
		
        setLogo();
        
        this.ft = new FadeTransition(Duration.millis(2000), progressIndicator);
        this.ft.setFromValue(1.0);
        this.ft.setToValue(0.3);
        this.ft.setCycleCount(FadeTransition.INDEFINITE);
        this.ft.setAutoReverse(true);
        
        this.progressIndicator.visibleProperty().addListener((a,b,n)-> {
        	if(n)
				ft.play();
			else
				ft.stop();
        });
        
        this.messageBox = new VBox();
        this.messageBox.setAlignment(Pos.CENTER);
        this.messageBox.setFillWidth(true);
        this.messageBox.setSpacing(10);
        this.messageBox.setVisible(false);
        this.messageBox.setStyle(name);
        
        this.scrollPane = new ScrollPane();
        this.scrollPane.setStyle("-fx-padding: 10 30 10 30;");
        this.scrollPane.setFitToWidth(true);
        this.scrollPane.setMaxHeight(280);
        this.scrollPane.setMinHeight(280);
        this.scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        this.scrollPane.setVisible(false);
        //this.scrollPane.setContent(this.messageBox);
        this.scrollPane.sceneProperty().addListener((obs, o, n)->{
			if(n!=null) {
				this.scrollPane.autosize();
				this.scrollPane.setContent(this.messageBox);
				this.scrollPane.getStyleClass().add("tab-content-area");
				
			}
		});
        
        this.scrollPane.setVvalue(1.0);
        this.scrollPane.vvalueProperty().addListener((a,o,n)->{
			if(scrollFlag) {
				scrollPane.setVvalue(scrollPane.getVmax());
				if(n.doubleValue()==scrollPane.getVmax())
					scrollFlag = false;
			}
		});
        
		this.borderPane = new BorderPane();
		this.borderPane.setTop(this.progressIndicator);
		this.borderPane.setCenter(this.scrollPane);
        this.borderPane.setVisible(false);
        
        setMargin(50);
        this.pane.getChildren().addAll(veil, borderPane);
	}
	
	public void addMessage(StackPane messagePane){
		scrollFlag = true;
		messageBox.getChildren().add(messagePane);
	}

	/**
	 * @param pane
	 */
	public void setMargin(double val) {
		BorderPane.setMargin(progressIndicator, new Insets(val));
		BorderPane.setAlignment(progressIndicator, Pos.CENTER);
	}
	
	public void bind(final BooleanBinding bb){
		removeBind();
		
		veil.visibleProperty().bind(bb);
		progressIndicator.visibleProperty().bind(bb);
		
		messageBox.visibleProperty().bind(bb);
		scrollPane.visibleProperty().bind(bb);
		borderPane.visibleProperty().bind(bb);
		
		if(bb.get() && !ft.getStatus().equals(Status.RUNNING))
			ft.play();
	}
	
	public void bind(ReadOnlyBooleanProperty bb) {
		removeBind();
		
		veil.visibleProperty().bind(bb);
		progressIndicator.visibleProperty().bind(bb);
		
		messageBox.visibleProperty().bind(bb);
		scrollPane.visibleProperty().bind(bb);
		borderPane.visibleProperty().bind(bb);
		
		if(bb.get() && !ft.getStatus().equals(Status.RUNNING))
			ft.play();
		
	}
	
	public void removeBind(){
		veil.visibleProperty().unbind();
		progressIndicator.visibleProperty().unbind();
		messageBox.visibleProperty().unbind();
		scrollPane.visibleProperty().unbind();
		borderPane.visibleProperty().unbind();
	}
	
	public void setSmallLogo() {
		Image img = new Image(TedrosContext.getImageInputStream("logo-tedros-small.png"));
        progressIndicator.setImage(img);
	}
	
	public void setMediumLogo() {
		Image img = new Image(TedrosContext.getImageInputStream("logo-tedros-medium.png"));
        progressIndicator.setImage(img);
	}
	
	public void setLogo() {
		Image img = new Image(TedrosContext.getImageInputStream("logo-tedros.png"));
        progressIndicator.setImage(img);
	}
	
	
}
