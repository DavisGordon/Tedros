package com.tedros.fxapi.layout;

import com.tedros.core.context.TedrosContext;
import com.tedros.core.module.TObjectRepository;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.WeakEventHandler;
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
	
	private StackPane sliderContent;
	private Timeline timeline;
	private Button exBtn;
	
	private SimpleBooleanProperty tMenuOpenedProperty;
	private TObjectRepository repo = new TObjectRepository();
	
	public TSliderMenu(Node content) {
		super(content);
	    build(280);
	}
	
	public TSliderMenu(Node content, double width) {
		super(content);
	    build(width);
	}


	/**
	 * 
	 */
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
	    sliderContent.setId("t-slider-menu");
	    
	    exBtn = new Button();
	    exBtn.setGraphic(imgV);
	    exBtn.setCache(true);
	    exBtn.setCacheShape(true);
	    exBtn.setDepthTest(DepthTest.ENABLE);
	    exBtn.setId("t-slider-menu-button");
	    

	    HBox slider = new HBox(sliderContent, exBtn);
	    slider.setAlignment(Pos.CENTER);
	    slider.setPrefWidth(Region.USE_COMPUTED_SIZE);
	    slider.setMaxWidth(Region.USE_PREF_SIZE);

	    // start out of view
	    slider.setTranslateX(-sliderContent.getPrefWidth());
	    StackPane.setAlignment(slider, Pos.CENTER_LEFT);

	    // animation for moving the slider
	    timeline = new Timeline(
	            new KeyFrame(Duration.ZERO, new KeyValue(slider.translateXProperty(), -sliderContent.getPrefWidth())),
	            new KeyFrame(Duration.millis(500), new KeyValue(slider.translateXProperty(), 0d))
	    );
	    
	    ChangeListener<Boolean> chl = (o, a, b) -> {
	    	setMenuAction(b);
	    };
	    this.repo.add("chl", chl);
	    
	    this.tMenuOpenedProperty = new SimpleBooleanProperty(false);
	    this.tMenuOpenedProperty.addListener(new WeakChangeListener<>(chl));

	    EventHandler<ActionEvent> eh = evt -> {
	        tMenuOpenedProperty.setValue(!tMenuOpenedProperty.getValue());
	    };
	    this.repo.add("eh", eh);
	    
	    exBtn.setOnAction(new WeakEventHandler<>(eh));

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
		EventHandler<MouseEvent> mev = ev -> {
			this.tMenuOpenedProperty.setValue(false);
		};
		this.repo.add("mev", mev);
		node.setOnMouseExited(new WeakEventHandler<>(mev));
		this.sliderContent.getChildren().add(0, node);
	}
	
	public void tClearMenuContent() {
		if(this.sliderContent.getChildren().size()==1) {
			this.sliderContent.getChildren().clear();
			this.repo.remove("mev");
		}
		
	}
	
	public void tInvalidate() {
		this.repo.clear();
	}
	
	public void settExpandButtonVisible(boolean visible) {
		this.exBtn.setVisible(visible);
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
	}
	

}
