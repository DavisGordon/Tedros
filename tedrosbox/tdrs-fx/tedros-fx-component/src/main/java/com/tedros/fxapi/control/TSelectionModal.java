/**
 * 
 */
package com.tedros.fxapi.control;

import com.tedros.core.TInternationalizationEngine;
import com.tedros.core.TModule;
import com.tedros.core.context.TedrosAppManager;
import com.tedros.core.context.TedrosContext;
import com.tedros.core.module.TListenerRepository;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.presenter.dynamic.view.TDynaView;
import com.tedros.fxapi.presenter.model.TModelView;
import com.tedros.fxapi.util.TEntityListViewCallback;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.ListChangeListener;
import javafx.collections.WeakListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.WeakEventHandler;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/**
 * Open a modal to search and select items
 * 
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public class TSelectionModal extends TModalRequired {
	
	private ITObservableList<TModelView> tSelectedItems;
	
	private ListView<TModelView> tListView;
	
	private TButton tFindButton;
	private TButton tRemoveButton;
	private TButton tClearButton;
	
	private TDynaView view;

	private Class<? extends TModelView> tModelViewClass;

	private double width;
	private double height;
	
	private TListenerRepository repo = new TListenerRepository();
	
	/**
	 * 
	 */
	@SuppressWarnings({"unchecked" })
	public TSelectionModal(ITObservableList items,  boolean selectMultipleItem, double width, double height) {
		this.tSelectedItems = items;
		this.width = width;
		this.height = height;
		if(selectMultipleItem && height<100) {
			height = 150;
		}
		boolean disable = !(this.tSelectedItems!=null && !this.tSelectedItems.isEmpty());
		TInternationalizationEngine iEngine = TInternationalizationEngine.getInstance(null);
		
		tFindButton = new TButton(iEngine.getString("#{tedros.fxapi.button.search}"));
		tRemoveButton = new TButton(iEngine.getString("#{tedros.fxapi.button.delete}"));
		tClearButton = new TButton(iEngine.getString("#{tedros.fxapi.button.clean}"));
		tClearButton.setId("t-last-button");
		
		this.buildListView();
		this.configSelectedListView();
		
		HBox box = new HBox();
		box.getChildren().addAll(tFindButton, tRemoveButton, tClearButton);
		
		tRemoveButton.setDisable(true);
		tClearButton.setDisable(disable);
		VBox.setVgrow(tListView, Priority.ALWAYS);
		this.getChildren().addAll(tListView, box);
		
		//Open modal event
		EventHandler<ActionEvent> fev = e -> {
			StackPane pane = new StackPane();
			view = new TDynaView(tModelViewClass, tSelectedItems);
			pane.setMaxSize(950, 600);
			pane.getChildren().add(view);
			pane.setId("t-tedros-color");
			pane.setStyle("-fx-effect: dropshadow( three-pass-box , white , 4 , 0.4 , 0 , 0 );");
			view.tLoad();
			TedrosAppManager.getInstance()
			.getModuleContext((TModule)TedrosContext.getView()).getCurrentViewContext()
			.getPresenter().getView().tShowModal(pane, false);
		};
		repo.addListener("fev", fev);
		tFindButton.setOnAction(new WeakEventHandler<>(fev));
		
		//remove event
		EventHandler<ActionEvent> rev = e -> {
			int index = tListView.getSelectionModel().getSelectedIndex();
			tListView.getSelectionModel().clearSelection();
			tListView.getItems().remove(index);
		};
		repo.addListener("rev", rev);
		tRemoveButton.setOnAction(new WeakEventHandler<>(rev));
		
		//clear event
		EventHandler<ActionEvent> cev = e -> {
			tSelectedItems.clear();;
		};
		repo.addListener("cev", cev);
		tClearButton.setOnAction(new WeakEventHandler<>(cev));
	}
	
	private void buildListView() {
		tListView = new ListView<>();
		tListView.setCache(false);
		tListView.autosize();
		tListView.setMaxWidth(width);
		tListView.setMinWidth(width);
		tListView.setMaxHeight(height);
		tListView.setMinHeight(height);
		
    }
	
	@SuppressWarnings({ "unchecked" })
	public void configSelectedListView() {
		tListView.setItems(tSelectedItems);
		tListView.setEditable(true);
		tListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		Callback<ListView<TModelView>, ListCell<TModelView>> callBack = (Callback<ListView<TModelView>, ListCell<TModelView>>) 
				 new TEntityListViewCallback();
		
		tListView.setCellFactory(callBack);
		
		ListChangeListener<TModelView> icl = (o) -> {
			boolean disable = o.getList().isEmpty();
			tClearButton.setDisable(disable);
		};
		repo.addListener("icl", icl);
		tListView.getItems().addListener(new WeakListChangeListener(icl));
		
		ChangeListener<TModelView> selchl = (o, old, n) -> {
			tRemoveButton.setDisable(n==null);
		};
		repo.addListener("selchl", selchl);
		tListView.getSelectionModel().selectedItemProperty().addListener(new WeakChangeListener(selchl));
	}

	public void invalidate() {
		repo.clear();
	}
	
	/**
	 * @return the tListView
	 */
	public ListView<TModelView> gettListView() {
		return tListView;
	}

	/**
	 * @return the tFindButton
	 */
	public TButton gettFindButton() {
		return tFindButton;
	}

	/**
	 * @return the tRemoveButton
	 */
	public TButton gettRemoveButton() {
		return tRemoveButton;
	}

	/**
	 * @return the tClearButton
	 */
	public TButton gettClearButton() {
		return tClearButton;
	}

	/**
	 * @param tModelViewClass the tModelViewClass to set
	 */
	public void settModelViewClass(Class<? extends TModelView> tModelViewClass) {
		this.tModelViewClass = tModelViewClass;
	}

	/**
	 * @return the tSelectedItems
	 */
	public ITObservableList<TModelView> gettSelectedItems() {
		return tSelectedItems;
	}
	
	
}
