package com.tedros.fxapi.layout;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.LabelBuilder;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import com.tedros.fxapi.domain.TLayoutType;

public class TFieldSet extends StackPane {
    
    private StackPane contentPane = new StackPane();
    private StackPane legendPane = new StackPane();
    private Pane contentBox;
    private Label legendLabel;
    private HBox legendBox; 
    
    public TFieldSet(TLayoutType layoutType) {
    	buildContentPane(layoutType);
	}
    
    public TFieldSet(TLayoutType layoutType, String legend){
    	buildContentPane(layoutType);
    	tAddLegend(legend);
    }
    
    public TFieldSet(TLayoutType layoutType, Node legend){
    	buildContentPane(layoutType);
    	tAddLegend(legend);
    }
    
    public void tAddLegend(String legend){
    	legendLabel = LabelBuilder.create().text(legend).id("t-fieldset-legend").build();
    	legendBox = HBoxBuilder.create().children(legendLabel).id("t-fieldset-legend-pane").build();
    	HBox.setMargin(legendLabel, new Insets(4,10,0,10));
    	legendBox.setPadding(new Insets(0,0,5,0));
    	configLegendBox(legendBox);
    	configureFieldSet();
    }
    
    public void tAddLegend(Node legend){
    	configLegendBox(legend);
    	configureFieldSet();
    }
    
    
    public void tAddContent(Node content){
    	contentBox.getChildren().add(content);
    }
    
    public void tAddAllContent(Node... content){
    	contentBox.getChildren().addAll(content);
    }
    
    public Pane gettContentBox(){
    	return contentBox;
    }
    
    public StackPane gettContentPane() {
		return contentPane;
	}
    
    public HBox gettLegendBox() {
		return legendBox;
	}
    
    public Label gettLegendLabel() {
		return legendLabel;
	}
    
	private void settBackGroundColor(String color){
	    super.setStyle("-fx-background-color:"+color+";");
	    contentPane.setStyle("-fx-background-color:"+color+";");
	    legendPane.setStyle("-fx-background-color:"+color+";");
    }
    
    
    // private methods
    
    private void buildContentPane(TLayoutType layoutType) {
		if(contentBox!=null)
			return;
		try {
			contentBox = layoutType==null ? new VBox() : layoutType.getValue().newInstance();
			contentBox.autosize();
			if(contentBox instanceof FlowPane){
				FlowPane pane = (FlowPane) contentBox;
				pane.setHgap(8);
				pane.setVgap(8);
				
			}
			
			contentBox.setId("t-fieldset-content-pane");
			contentPane.getChildren().add(contentBox);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
	}
    
    private void configLegendBox(Node node){
    	legendPane.getChildren().add(node);
    }
    
    private void configureFieldSet(){
    	super.setPadding(new Insets(10,0,0,0));
        super.setAlignment(Pos.TOP_LEFT);
        super.getStyleClass().add("fieldSetDefault");
        
        //contentBox.getStyleClass().add("fieldSet");
        contentPane.setId("t-fieldset");
        contentPane.setAlignment(Pos.TOP_LEFT);
        contentPane.setPadding(new Insets(20, 10, 10, 10));
        
        legendPane.setPadding(new Insets(0, 5, 0, 5));
        
        Group gp = new Group();
        gp.setTranslateX(20);
        gp.setTranslateY(-8);
        gp.getChildren().add(legendPane);
        
        super.getChildren().addAll(contentPane, gp);
        settBackGroundColor("transparent");
        
        /* Adding listeners for styles. */
        /*getStyleClass().addListener(new ListChangeListener<String>() {
                @Override
                public void onChanged(javafx.collections.ListChangeListener.Change<? extends String> paramChange) {
                        System.out.println("-->"+getStyleClass());
                        System.out.println(contentPane.getStyleClass());
                        contentPane.getStyleClass().clear();
                        contentPane.getStyleClass().addAll("fieldSet");
                        for (String clazz : getStyleClass()) {
                                if (!clazz.equals("fieldSetDefault")) {
                                        contentPane.getStyleClass().add(clazz);
                                }
                        }
                }
        });*/
    }
    
}
