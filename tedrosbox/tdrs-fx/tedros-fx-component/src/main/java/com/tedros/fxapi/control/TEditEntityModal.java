/**
 * 
 */
package com.tedros.fxapi.control;

import com.tedros.core.TLanguage;
import com.tedros.core.TModule;
import com.tedros.core.context.TedrosAppManager;
import com.tedros.core.context.TedrosContext;
import com.tedros.core.module.TObjectRepository;
import com.tedros.ejb.base.model.ITModel;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.presenter.dynamic.view.TDynaGroupView;
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
public class TEditEntityModal extends TRequiredModal {
	
	private ITObservableList<TModelView> tSelectedItems;
	
	private ListView<TModelView> tListView;
	
	private TButton tEditButton;
	private TButton tRemoveButton;
	private TButton tClearButton;
	
	private TDynaView view;

	private Class<? extends TModelView> tModelViewClass;
	private Class<? extends ITModel> tModelClass;

	private double width;
	private double height;
	
	private TObjectRepository repo = new TObjectRepository();
	
	/**
	 * 
	 */
	@SuppressWarnings({"unchecked" })
	public TEditEntityModal(ITObservableList items, double width, double height, double modalWidth, double modalHeight) {
		this.tSelectedItems = items;
		this.width = width;
		this.height = (height<100) ? 150 : height;
		
		boolean disable = !(this.tSelectedItems!=null && !this.tSelectedItems.isEmpty());
		TLanguage iEngine = TLanguage.getInstance(null);
		
		tEditButton = new TButton(iEngine.getString("#{tedros.fxapi.button.edit}"));
		tRemoveButton = new TButton(iEngine.getString("#{tedros.fxapi.button.delete}"));
		tClearButton = new TButton(iEngine.getString("#{tedros.fxapi.button.clean}"));
		tClearButton.setId("t-last-button");
		
		this.buildListView();
		this.configSelectedListView();
		
		HBox box = new HBox();
		box.getChildren().addAll(tEditButton, tRemoveButton, tClearButton);
		
		tRemoveButton.setDisable(true);
		tClearButton.setDisable(disable);
		VBox.setVgrow(tListView, Priority.ALWAYS);
		this.getChildren().addAll(tListView, box);
		
		//Open modal event
		EventHandler<ActionEvent> fev = e -> {
			StackPane pane = new StackPane();
			view = new TDynaGroupView(tModelViewClass, tModelClass, tSelectedItems);
			pane.setMaxSize(modalWidth, modalHeight);
			pane.getChildren().add(view);
			pane.setId("t-tedros-color");
			//pane.getStyleClass().add("t-panel-color");
			pane.setStyle("-fx-background-radius: 20 20 20 20;");
			view.tLoad();
			TedrosAppManager.getInstance()
			.getModuleContext((TModule)TedrosContext.getView()).getCurrentViewContext()
			.getPresenter().getView().tShowModal(pane, false);
		};
		repo.add("fev", fev);
		tEditButton.setOnAction(new WeakEventHandler<>(fev));
		
		//remove event
		EventHandler<ActionEvent> rev = e -> {
			int index = tListView.getSelectionModel().getSelectedIndex();
			tListView.getSelectionModel().clearSelection();
			tListView.getItems().remove(index);
		};
		repo.add("rev", rev);
		tRemoveButton.setOnAction(new WeakEventHandler<>(rev));
		
		//clear event
		EventHandler<ActionEvent> cev = e -> {
			tSelectedItems.clear();;
		};
		repo.add("cev", cev);
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
		super.tRequiredNodeProperty().setValue(tListView);
		
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
		repo.add("icl", icl);
		tListView.getItems().addListener(new WeakListChangeListener(icl));
		
		ChangeListener<TModelView> selchl = (o, old, n) -> {
			tRemoveButton.setDisable(n==null);
		};
		repo.add("selchl", selchl);
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
	public TButton gettEditButton() {
		return tEditButton;
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

	/**
	 * @param tModelClass the tModelClass to set
	 */
	public void settModelClass(Class<? extends ITModel> tModelClass) {
		this.tModelClass = tModelClass;
	}

	
}
