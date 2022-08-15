package org.tedros.fx.layout;



import org.tedros.core.context.TedrosContext;
import org.tedros.core.repository.TRepository;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.event.EventHandler;
import javafx.event.WeakEventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.DepthTest;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class TSliderMenu extends StackPane {
	
	private HBox slider;
	private StackPane sliderContent;
	private Timeline timeline;
	private StackPane btnPane;
	
	private SimpleBooleanProperty tMenuOpenedProperty;
	private TRepository repo = new TRepository();

	public TSliderMenu(Node content) {
		super(content);
		StackPane.setMargin(content, new Insets(0,0,0,50));
	    build(280);
	}
	
	public TSliderMenu(Node content, double width) {
		super(content);
		StackPane.setMargin(content, new Insets(0,0,0,50));
	    build(width);
	}

	private void build(double width) {
		setStyle("-fx-background-color: transparent;");
	    
	    DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.BLACK);
        
        Image img = new Image(TedrosContext.getImageInputStream("open-menu.png"));
        ImageView imgV = new ImageView();
        imgV.setImage(img);
        imgV.setEffect(ds);
        
        
	    sliderContent = new StackPane();
	    sliderContent.setPrefWidth(width);
	    
	    Button btn = new Button();
	    btn.setGraphic(imgV);
	    btn.setCache(true);
	    btn.setCacheShape(true);
	    btn.setDepthTest(DepthTest.ENABLE);
	    btn.setId("t-slider-menu-button");
	    
	    btnPane  = new StackPane(btn);
	    
	    btnPane.setPrefWidth(20);
	    slider = new HBox(sliderContent, btnPane);
	    slider.setAlignment(Pos.CENTER);
	    slider.setPrefWidth(Region.USE_COMPUTED_SIZE);
	    slider.setMaxWidth(Region.USE_PREF_SIZE);
	    slider.setId("t-slider-menu");
	    // start out of view
	    slider.setTranslateX(-sliderContent.getPrefWidth());
	    StackPane.setAlignment(slider, Pos.CENTER_LEFT);
	    
	    // animation for moving the slider
	    timeline = new Timeline(
	            new KeyFrame(Duration.ZERO, new KeyValue(slider.translateXProperty(), -sliderContent.getPrefWidth())),
	            new KeyFrame(Duration.millis(500), new KeyValue(slider.translateXProperty(), 0d))
	    );
	    
	    ChangeListener<Duration> chd = (a,b,n)->{
	    	for(Node c : sliderContent.getChildren()) 
	    		c.setVisible(!n.equals(Duration.ZERO));
	    };
	    repo.add("chd", chd);
	    timeline.currentTimeProperty().addListener(new WeakChangeListener<>(chd));
	    
	    ChangeListener<Boolean> chl = (o, a, b) -> {
	    	setMenuAction(b);
	    };
	    this.repo.add("chl", chl);
	    
	    this.tMenuOpenedProperty = new SimpleBooleanProperty(false);
	    this.tMenuOpenedProperty.addListener(new WeakChangeListener<>(chl));

	    EventHandler<MouseEvent> eh = evt -> {
	    	this.tMenuOpenedProperty.setValue(true);
	    };
	    this.repo.add("eh", eh);
	    slider.setOnMouseEntered(new WeakEventHandler<>(eh));
	    
	    EventHandler<MouseEvent> mev = ev -> {
			this.tMenuOpenedProperty.setValue(false);
		};
		this.repo.add("mev", mev);
		slider.setOnMouseExited(new WeakEventHandler<>(mev));
	    getChildren().add(slider);
	}


	/**
	 * @param text
	 */
	private void setMenuAction(boolean open) {
		
		boolean playing = timeline.getStatus() == Animation.Status.RUNNING;
		if (open) {
		    timeline.setRate(1);
		    if (!playing) {
		        timeline.playFromStart();
		    }
		} else {
		    timeline.setRate(-1);
		    if (!playing) {
		        timeline.playFrom("end");
		    }
		}
	}

	public void settMenuContent(Node node){
		this.sliderContent.getChildren().clear();
		this.sliderContent.getChildren().add(node);
		this.settMenuVisible(this.tMenuOpenedProperty.getValue());
		node.setVisible(this.tMenuOpenedProperty.getValue());
	}
	
	public void tClearMenuContent() {
		if(this.sliderContent.getChildren().size()==1) {
			this.sliderContent.getChildren().clear();
			//this.repo.remove("mev");
		}
		
	}
	
	public void tInvalidate() {
		this.repo.clear();
	}
	
	public void settMenuVisible(boolean visible) {
		this.slider.setVisible(visible);
	}
	

	/**
	 * @return the tMenuOpenedProperty
	 */
	public ReadOnlyBooleanProperty tMenuOpenedProperty() {
		return tMenuOpenedProperty;
	}


	/**
	 * @param open the tMenuOpenedProperty to set
	 */
	public void settMenuOpened(boolean open) {
		this.tMenuOpenedProperty.setValue(open);
		this.settMenuVisible(open);
	}
	

}
