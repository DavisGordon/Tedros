/**
 * 
 */
package com.tedros.fxapi.presenter.paginator;


import java.util.UUID;

import com.tedros.core.TInternationalizationEngine;
import com.tedros.core.module.TListenerRepository;
import com.tedros.fxapi.control.TButton;
import com.tedros.fxapi.control.TLabel;
import com.tedros.fxapi.control.TSlider;
import com.tedros.fxapi.control.TTextField;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.WeakEventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * @author Davis Gordon
 *
 */
public class TPaginator extends BorderPane {
	
	private int lastEnd;
	private String lastButton;
	
	private TSlider slider;
	private TButton searchButton;
	private TButton clearButton;
	private TTextField search = null;
	private ToolBar toolbar;
	private TLabel label;
	
	private SimpleObjectProperty<TPagination> paginationProperty;
	
	private TListenerRepository repo;
	
	public TPaginator(boolean showSearch) {
		
		setId("t-header-box");
		
		TInternationalizationEngine iEngine = TInternationalizationEngine.getInstance(null);
		
		repo = new TListenerRepository();
		label = new TLabel();
		label.setId("t-title-label");
		lastButton = null;
		
		slider = new TSlider();
		slider.setMax(250);
		slider.setMin(50);
		slider.setValue(50);
		slider.setShowTickLabels(true);
		slider.setShowTickMarks(true);
		slider.setMajorTickUnit(50);
		slider.setMinorTickCount(0);
		slider.setSnapToTicks(true);
		
		ChangeListener<Number> ehs = (a0, a1, a2) ->{
			if(!slider.isValueChanging())
				lastButton = null;
				paginationProperty.setValue(buildPagination(0));
		};
		repo.addListener("slider", ehs);
		slider.valueProperty().addListener(new WeakChangeListener<>(ehs));
		
		TLabel title = new TLabel(iEngine.getString("#{tedros.fxapi.label.current.page}") + ":") ;
		title.setId("t-title-label");
		title.setFont(Font.font(16));
		HBox pane = new HBox();
		pane.setId("t-view-title-box");
		HBox.setHgrow(label, Priority.ALWAYS);
		HBox.setMargin(label, new Insets(0, 5, 0, 15));
		pane.setAlignment(Pos.CENTER_LEFT);
		pane.getChildren().addAll(title, label);
		VBox box = new VBox();
		box.getChildren().add(pane);
		
		if(showSearch) {
			searchButton = new TButton();
			searchButton.setText(iEngine.getString("#{tedros.fxapi.button.search}"));
			searchButton.setId("t-button");
			EventHandler<ActionEvent> eh = e ->{
				lastButton = null;
				paginationProperty.setValue(buildPagination(0));
			};
			repo.addListener("searchbtn", eh);
			searchButton.setOnAction(new WeakEventHandler<>(eh));
			
			clearButton = new TButton();
			clearButton.setText(iEngine.getString("#{tedros.fxapi.button.clean}"));
			clearButton.setId("t-last-button");
			EventHandler<ActionEvent> eh1 = e ->{
				lastButton = null;
				search.setText("");
				paginationProperty.setValue(buildPagination(0));
			};
			repo.addListener("clearhbtn", eh1);
			clearButton.setOnAction(new WeakEventHandler<>(eh1));
			
			search = new TTextField();
			search.setMaxHeight(searchButton.getHeight());
			//search.setMaxWidth(100);
			
			HBox h1 = new HBox();
			h1.setId("t-view-toolbar");
			HBox.setHgrow(search, Priority.ALWAYS);
			h1.getChildren().addAll(search, searchButton, clearButton);
			
			//HBox.setMargin(h1, new Insets(10, 0, 10, 8));
			box.getChildren().add(h1);
			
		}
		BorderPane.setMargin(box, new Insets(10, 8, 10, 8) );
		BorderPane.setMargin(slider, new Insets(0, 15, 0, 15) );
		setTop(box);
		setCenter(slider);
		
		toolbar = new ToolBar();
		toolbar.setId("t-view-toolbar");
		
		ScrollPane sp = new ScrollPane();
		sp.setFitToHeight(true);
		sp.setFitToWidth(true);
		sp.setContent(toolbar);
		sp.setStyle("-fx-background-color: transparent;");
		setBottom(sp);
		
		paginationProperty = new SimpleObjectProperty<>();
	}
	
	
	
	public void reload(long totalRows) {
		removeBtnEvent();
		toolbar.getItems().clear();
		
		double pag = slider.getValue();
		double total = (totalRows > pag) ?
				totalRows / pag
				: 1;
		
		lastEnd = (int) (pag - 1);
		
		for(int i = 0; i<total; i++) {
			TButton p = buildItem(i, totalRows);
			toolbar.getItems().add(p);
		}
		Node firstNode = toolbar.getItems().get(0);
		
		label.setText(lastButton==null 
					? ((TButton)firstNode).getText()
							: lastButton
				);
		
		Node lastNode = toolbar.getItems().get(toolbar.getItems().size()-1);
		lastNode.setId("t-last-button");;
	}
	
	private TButton buildItem(int startAt, long totalRows) {
		
		final double pag = slider.getValue();
		final int i = (int) ((startAt == 0) 
					? startAt
						: startAt * pag);
		
		int begin = i==0 ? i : lastEnd+1; 
		int end = (begin+((int)pag-1));
		lastEnd = end;

		String l =   (begin==0 ? "1" : begin) + "-" + (end>totalRows ? totalRows : end);
		String eId = UUID.randomUUID().toString();
		final TButton p1 = new TButton() ;
		p1.setId("t-button");
		p1.setText(l);
		p1.setUserData(eId);
		EventHandler<ActionEvent> eh = e ->{
			lastButton = p1.getText();
			label.setText(lastButton);
			paginationProperty.setValue(buildPagination(i));
		};
		repo.addListener(eId, eh);
		p1.setOnAction(eh);
		
		return p1;
	}
	
	private void removeBtnEvent() {
		
		for(Node node : toolbar.getItems()) {
			TButton b = (TButton) node;
			EventHandler<ActionEvent> e =  repo.removeListener((String) b.getUserData());
			b.removeEventHandler(ActionEvent.ACTION, e);
		}
	}
	
	private TPagination buildPagination(int start){
		return new TPagination(search!=null ? search.getText() : null, start, (int) slider.getValue());
	}
	
	public TPagination getPagination(){
		return buildPagination(0);
	}


	/**
	 * @return the paginationProperty
	 */
	public ReadOnlyObjectProperty<TPagination> paginationProperty() {
		return paginationProperty;
	}
	
	public void invalidate() {
		repo.clear();
	}

}