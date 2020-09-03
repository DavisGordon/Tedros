/**
 * 
 */
package com.tedros.fxapi.presenter.paginator;

import com.tedros.core.module.TListenerRepository;
import com.tedros.fxapi.control.TButton;
import com.tedros.fxapi.control.TSlider;
import com.tedros.fxapi.control.TTextField;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.WeakEventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
public class TPaginator extends BorderPane {
	
	
	private TSlider slider;
	private TButton searchButton;
	private TTextField search = null;
	private ToolBar toolbar;
	
	private SimpleObjectProperty<TPagination> paginationProperty;
	
	private TListenerRepository repo;
	
	public TPaginator(boolean showSearch) {
		repo = new TListenerRepository();
		HBox h1 = new HBox();
		
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
			paginationProperty.setValue(buildPagination(1));
		};
		repo.addListener("slider", ehs);
		
		slider.valueProperty().addListener(new WeakChangeListener<>(ehs));
		
		if(showSearch) {
			searchButton = new TButton();
			searchButton.setText("Filtar");
			searchButton.setId("t-last-button");
			EventHandler<ActionEvent> eh = e ->{
				paginationProperty.setValue(buildPagination(1));
			};
			repo.addListener("searchbtn", eh);
			searchButton.setOnAction(new WeakEventHandler<>(eh));
			
			search = new TTextField();
			search.setMaxHeight(searchButton.getHeight());
			
			HBox.setHgrow(search, Priority.ALWAYS);
			h1.getChildren().addAll(search, searchButton);
			setTop(h1);
		}
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
	
	public void reload(long length) {
		repo.clear();
		toolbar.getItems().clear();
		// 100 / 50 = 2 
		double pag = slider.getValue();
		double total = (length > pag) ?
				length / pag
				: 1;
		for(int i = 1; i<=total; i++) {
			TButton p = buildItem(i);
			toolbar.getItems().add(p);
		}
		
		Node lastNode = null;
		for(Node node : toolbar.getItems()){
			if(!(node instanceof Button) && (lastNode!=null && lastNode instanceof Button) ){
				lastNode.setId("t-last-button");
			}
			lastNode = node;
		}
		if(lastNode instanceof Button)
			lastNode.setId("t-last-button");;
	}
	
	private TButton buildItem(int t) {
		TButton p1 = new TButton() ;
		//p1.setMaxSize(30, 15);
		p1.setId("t-button");
		p1.setText(String.valueOf(t));
		final double pag = slider.getValue();
		EventHandler<ActionEvent> eh = e ->{
			int i = (int) ((t-1 == 0) 
					? 1
						: (t-1) * pag);
			
			
			paginationProperty.setValue(buildPagination(i));
		};
		repo.addListener("btn"+t, eh);
		p1.setOnAction(new WeakEventHandler<>(eh));
		
		return p1;
	}
	
	private TPagination buildPagination(int start){
		return new TPagination(search!=null ? search.getText() : null, start, (int) slider.getValue());
	}
	
	public TPagination getPagination(){
		return buildPagination(1);
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
