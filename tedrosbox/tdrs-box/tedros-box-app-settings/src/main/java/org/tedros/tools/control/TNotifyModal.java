/**
 * 
 */
package org.tedros.tools.control;

import org.tedros.app.component.ITComponent;
import org.tedros.core.TLanguage;
import org.tedros.core.context.TedrosAppManager;
import org.tedros.core.control.PopOver;
import org.tedros.core.module.TObjectRepository;
import org.tedros.core.notify.model.TNotify;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.control.TButton;
import org.tedros.fx.presenter.dynamic.view.TDynaView;
import org.tedros.fx.presenter.model.TModelView;
import org.tedros.fx.util.TEntityListViewCallback;
import org.tedros.server.model.ITModel;
import org.tedros.tools.module.notify.TNotifyModule;
import org.tedros.tools.module.notify.model.TNotifyMV;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.WeakEventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Callback;

/**
 * Open a notify modal 
 * 
 * @author Davis Gordon
 *
 */
@Deprecated
@SuppressWarnings("rawtypes")
public class TNotifyModal extends VBox implements ITComponent {
	
	private ITObservableList<TNotifyMV> tSelectedItems;
	
	private ListView<TNotifyMV> tListView;
	
	private TButton tEditButton;
	
	private TDynaView view;

	private Class<? extends TModelView> tModelViewClass = TNotifyMV.class;
	private Class<? extends ITModel> tModelClass = TNotify.class;

	private double width;
	private double height;
	
	private TObjectRepository repo = new TObjectRepository();
	
	
	/**
	 * 
	 */
	public TNotifyModal(ITObservableList items, double width, double height,
			double modalWidth, double modalHeight) {
		init(items, width, height, modalWidth, modalHeight);
	}
	
	@SuppressWarnings("unchecked")
	private void init(ITObservableList items, double width, double height,
			double modalWidth, double modalHeight) {
		this.tSelectedItems = items;
		this.width = width;
		this.height = (height<50) ? 50 : height;
		
		boolean disable = !(this.tSelectedItems!=null && !this.tSelectedItems.isEmpty());
		TLanguage iEngine = TLanguage.getInstance(null);
		
		tEditButton = new TButton(iEngine.getString("#{tedros.fxapi.button.edit}"));
		
		this.buildListView();
		this.configSelectedListView();
		
		HBox box = new HBox();
		box.getChildren().addAll(tEditButton);
		
		VBox.setVgrow(tListView, Priority.ALWAYS);
		this.getChildren().addAll(tListView, box);
		
		//Open modal event
		EventHandler<ActionEvent> fev = e -> {
			StringBuilder pp = new StringBuilder();
			TNotify m = new TNotify();
			m.setSubject("kkkkkkk");
			
			TedrosAppManager.getInstance().loadInModule(TNotifyModule.class, new TNotifyMV(m));
			/*
			
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
			.getPresenter().getView().tShowModal(pane, false);*/
		};
		repo.add("fev", fev);
		tEditButton.setOnAction(new WeakEventHandler<>(fev));
		
	}

	/**
	 * 
	 */
	private void showRequiredPopMsg() {
		Label l = new Label(TLanguage.getInstance().getString("#{tedros.fxapi.validator.required}"));
		l.setFont(Font.font(11));
		PopOver p = new PopOver();
		p.setCloseButtonEnabled(true);
		p.setContentNode(l);
		p.getRoot().setPadding(new Insets(10));
		p.show(tListView);
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
		Callback<ListView<TNotifyMV>, ListCell<TNotifyMV>> callBack = (Callback<ListView<TNotifyMV>, ListCell<TNotifyMV>>) 
				 new TEntityListViewCallback();
		
		tListView.setCellFactory(callBack);
		
	}

	public void invalidate() {
		repo.clear();
	}
	
	/**
	 * @return the tListView
	 */
	public ListView<TNotifyMV> gettListView() {
		return tListView;
	}

	/**
	 * @return the tFindButton
	 */
	public TButton gettEditButton() {
		return tEditButton;
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
	public ITObservableList<TNotifyMV> gettSelectedItems() {
		return tSelectedItems;
	}

	/**
	 * @param tModelClass the tModelClass to set
	 */
	public void settModelClass(Class<? extends ITModel> tModelClass) {
		this.tModelClass = tModelClass;
	}

	@Override
	public String gettComponentId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void settComponentId(String id) {
		// TODO Auto-generated method stub
		
	}

	
}
