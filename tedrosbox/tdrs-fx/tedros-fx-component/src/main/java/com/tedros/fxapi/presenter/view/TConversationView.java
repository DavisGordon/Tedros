package com.tedros.fxapi.presenter.view;

import java.util.Arrays;
import java.util.List;

import com.tedros.fxapi.layout.TBreadcrumbView;
import com.tedros.fxapi.presenter.view.group.TGroupViewControl;
import com.tedros.fxapi.presenter.view.group.TViewItem;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.LabelBuilder;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.PaneBuilder;
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
    	
        Label titleLabel = LabelBuilder.create().text(title).id("t-title-label").build();
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox.setMargin(titleLabel, new Insets(0, 10, 0, 0));
        
        HBox titleBox = HBoxBuilder.create()
				.id("t-view-title-box")
				.fillHeight(true)
				.alignment(Pos.CENTER)
				.padding(new Insets(0, 0, 0, 10))
				.children(Arrays.asList(titleLabel,	
										spacer,
										PaneBuilder.create().id("t-view-title-box-space2-color").prefWidth(5).maxWidth(5).build(),
										new Region()))
				.build();
        
        HBox tHeaderBox = HBoxBuilder.create()
				.id("t-header-box")
				.fillHeight(true)
				.children(Arrays.asList(titleBox, toolBar))
				.build();
       
    	setTop(tHeaderBox);
    	setCenter(contentPane);
    }
    
    public abstract void initialize();

  }
