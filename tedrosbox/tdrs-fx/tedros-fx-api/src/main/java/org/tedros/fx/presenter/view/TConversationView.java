package org.tedros.fx.presenter.view;

import java.util.Arrays;
import java.util.List;

import org.tedros.fx.layout.TBreadcrumbView;
import org.tedros.fx.presenter.view.group.TGroupViewControl;
import org.tedros.fx.presenter.view.group.TViewItem;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * BreadcrumbBar
 */
public abstract class TConversationView extends BorderPane {

	private TBreadcrumbView tBreadcrumbBar;
	private StackPane contentPane;
	private ToolBar toolBar;

    public TConversationView(String title, List<TViewItem> itemList, TGroupViewControl control) {
    	contentPane = new StackPane();
    	tBreadcrumbBar = new TBreadcrumbView(contentPane, control);
    	tBreadcrumbBar.setGroupItens(itemList);
    	
    	toolBar = new ToolBar();
        toolBar.setId("t-app-header-toolbar");
        //pageToolBar.setMinHeight(29);
        toolBar.setMaxSize(Double.MAX_VALUE, Control.USE_PREF_SIZE);
        toolBar.getItems().addAll(tBreadcrumbBar);
    	
        Label titleLabel = new Label();
        titleLabel.setText(title);
        titleLabel.setId("t-title-label");
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox.setMargin(titleLabel, new Insets(0, 10, 0, 0));
        
        Pane p = new Pane();
        p.setId("t-view-title-box-space2-color");
        p.prefWidth(5);
        p.maxWidth(5);
        
        HBox titleBox = new HBox();
        titleBox.setId("t-view-title-box");
        titleBox.setFillHeight(true);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(0, 0, 0, 10));
        titleBox.getChildren().addAll(Arrays.asList(titleLabel,spacer,p,new Region()));				
        
        HBox tHeaderBox = new HBox();
        tHeaderBox.setId("t-header-box");
        tHeaderBox.setFillHeight(true);
        tHeaderBox.getChildren().addAll(Arrays.asList(titleBox, toolBar));
		
    	setTop(tHeaderBox);
    	setCenter(contentPane);
    }
    
    public abstract void initialize();

  }
