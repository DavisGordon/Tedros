package org.tedros.core.context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

/**
 * CategoryPage
 *
 */
public class CategoryPage extends Page {
	
	private Node module;

    public CategoryPage(String name, Page ... pages) {
        super(name);
        getChildren().addAll(Arrays.asList(pages));
    }
    
    public Node getModule() {
    	return module;
    }

	@Override 
	public Node createModule() {
        // split children
        List<InternalViewPage> directChildren = new ArrayList<InternalViewPage>();
        List<CategoryPage> categoryChildren = new ArrayList<CategoryPage>();
        for (Object child:getChildren()) {
            Page page = (Page)child;
            if (page instanceof InternalViewPage) {
                directChildren.add((InternalViewPage)page);
            } else if (page instanceof CategoryPage) {
                categoryChildren.add((CategoryPage)page);
            }
        }
        
        // create main column
        VBox appsBox = new VBox(2);        
        appsBox.getStyleClass().add("t-category-page");
        appsBox.setStyle("-fx-background-color: transparent;");
        // create header
        Label header = new Label(getName());
        header.setMaxWidth(Double.MAX_VALUE);
        header.setMinHeight(Control.USE_PREF_SIZE);
        header.getStyleClass().add("t-main-page-header");
        appsBox.getChildren().add(header);
        
        FlowPane flowApps = new FlowPane(10,10);
        
        flowApps.setOrientation(Orientation.HORIZONTAL);
        flowApps.setAlignment(Pos.TOP_LEFT);;
        
        // add direct children
        if(!directChildren.isEmpty()) {
        	TilePane directChildFlow = new TilePane(4,4);
            directChildFlow.setAlignment(Pos.TOP_LEFT);
            directChildFlow.setOrientation(Orientation.HORIZONTAL);
            directChildFlow.setPrefColumns(1);
            directChildFlow.getStyleClass().add("t-category-page-flow");
            for (InternalViewPage internalViewPage:directChildren)
                directChildFlow.getChildren().add(internalViewPage.createTile());
            appsBox.getChildren().add(directChildFlow);
        }
        
        // add sub sections
        for(CategoryPage categoryPage:categoryChildren) {
        	VBox vbx = new VBox(2){
                 // stretch to allways fill height of scrollpane
                 @Override protected double computePrefWidth(double width) {
                     return flowApps.getChildren().size() > 1
                    		 ? (getParent().getBoundsInLocal().getWidth() / 2)-50:
                    			 getParent().getBoundsInLocal().getWidth() - 50;
                     
                 }
             };
        	FlowPane.setMargin(vbx, new Insets(0,5,0,5));
            // create header
            Label categoryHeader = new Label(categoryPage.getName());
            categoryHeader.setMaxWidth(Double.MAX_VALUE);
            categoryHeader.setMinHeight(Control.USE_PREF_SIZE);
            categoryHeader.getStyleClass().add("t-category-header");
            vbx.getChildren().add(categoryHeader);
            // add direct children
            TilePane directChildFlow = new TilePane(4,4);
            directChildFlow.setPrefColumns(1);
            directChildFlow.getStyleClass().add("t-category-page-flow");
            directChildFlow.setStyle("-fx-background-color: transparent;");

            addAllCategoriesTiles(categoryPage, directChildFlow);
            
            vbx.getChildren().add(directChildFlow);
            flowApps.getChildren().add(vbx);
        }
        
        if(flowApps.getChildren().size()>0)
        	appsBox.getChildren().add(flowApps);
        
        HBox hb = new HBox();
        Region lr = new Region();
        Region rr = new Region();
        lr.setPrefWidth(40);
        rr.setPrefWidth(40);
        hb.getChildren().addAll(lr, appsBox, rr);
        HBox.setHgrow(appsBox, Priority.ALWAYS);
        // wrap in scroll pane
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.getStyleClass().add("noborder-scroll-pane");
        scrollPane.setStyle("-fx-background-color: transparent;");
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(hb);
        
        module = scrollPane;
        return module;
    }

    private void addAllCategoriesTiles(CategoryPage categoryPage, TilePane pane) {
        for (Object child:categoryPage.getChildren()) {
            Page page = (Page)child;
            if(page instanceof InternalViewPage) {
                pane.getChildren().add(((InternalViewPage)page).createTile());
            } else if(page instanceof CategoryPage) {
                addAllCategoriesTiles((CategoryPage)page,pane);
            }
        }
    }

}
