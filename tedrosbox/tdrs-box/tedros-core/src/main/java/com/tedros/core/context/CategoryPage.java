package com.tedros.core.context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
        super.setExpanded(true);
        getChildren().addAll(Arrays.asList(pages));
    }
    
    public Node getModule() {
    	return module;
    }

    @SuppressWarnings("restriction")
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
        VBox main = new VBox(8) {
            // stretch to allways fill height of scrollpane
            @Override protected double computePrefHeight(double width) {
                return Math.max(
                        super.computePrefHeight(width),
                        getParent().getBoundsInLocal().getHeight()
                );
            }
        };
        
        main.getStyleClass().add("t-category-page");
        main.setStyle("-fx-background-color: transparent;");
        // create header
        Label header = new Label(getName());
        header.setMaxWidth(Double.MAX_VALUE);
        header.setMinHeight(Control.USE_PREF_SIZE);
        header.getStyleClass().add("t-main-page-header");
        main.getChildren().add(header);
        // add direct children
        if(!directChildren.isEmpty()) {
            TilePane directChildFlow = new TilePane(8,8);
            directChildFlow.setPrefColumns(1);
            directChildFlow.getStyleClass().add("t-category-page-flow");
            for (InternalViewPage internalViewPage:directChildren) {
                directChildFlow.getChildren().add(internalViewPage.createTile());
            }
            main.getChildren().add(directChildFlow);
        }
        // add sub sections
        for(CategoryPage categoryPage:categoryChildren) {
            // create header
            Label categoryHeader = new Label(categoryPage.getName());
            categoryHeader.setMaxWidth(Double.MAX_VALUE);
            categoryHeader.setMinHeight(Control.USE_PREF_SIZE);
            categoryHeader.getStyleClass().add("t-category-header");
            main.getChildren().add(categoryHeader);
            // add direct children
            TilePane directChildFlow = new TilePane(8,8);
            directChildFlow.setPrefColumns(1);
            directChildFlow.getStyleClass().add("t-category-page-flow");
            directChildFlow.setStyle("-fx-background-color: transparent;");

            addAllCategoriesTiles(categoryPage, directChildFlow);
            main.getChildren().add(directChildFlow);
        }
        HBox hb = new HBox();
        Region lr = new Region();
        Region rr = new Region();
        lr.setPrefWidth(80);
        rr.setPrefWidth(80);
        hb.getChildren().addAll(lr, main, rr);
        HBox.setHgrow(main, Priority.ALWAYS);
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
