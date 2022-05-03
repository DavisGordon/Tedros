package com.tedros.fxapi.presenter.modal.behavior;

import java.util.Arrays;

import com.tedros.core.TModule;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.context.TedrosAppManager;
import com.tedros.core.context.TedrosContext;
import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.control.action.TPresenterAction;
import com.tedros.fxapi.exception.TValidatorException;
import com.tedros.fxapi.modal.TMessageBox;
import com.tedros.fxapi.presenter.behavior.TActionState;
import com.tedros.fxapi.presenter.behavior.TActionType;
import com.tedros.fxapi.presenter.behavior.TProcessResult;
import com.tedros.fxapi.presenter.modal.decorator.TEditModalDecorator;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.fxapi.util.TEntityListViewCallback;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.util.Callback;

@SuppressWarnings({ "rawtypes" })
public class TEditModalBehavior<M extends TEntityModelView, E extends ITEntity>
extends com.tedros.fxapi.presenter.dynamic.behavior.TDynaViewCrudBaseBehavior<M, E> {
	
	protected TEditModalDecorator<M> decorator;
		
	@Override
	public void load() {
		super.load();
		this.decorator = (TEditModalDecorator<M>) getPresenter().getDecorator();
		initialize();
	}
		
	public void initialize() {
		
		configCancelAction();
		configColapseButton();
		configNewButton();
		configSaveButton();
		configDeleteButton();
		configImportButton();
		configPrintButton();
		
		configCancelButton();
		configCloseButton();
		
		configListViewChangeListener();
		configListViewCallBack();
		
		if(!isUserNotAuthorized(TAuthorizationType.VIEW_ACCESS))
			this.loadListView();
	}
	

	/**
	 * Config the close button 
	 * */
	public void configCloseButton() {
		final Button cleanButton = this.decorator.gettCloseButton();
			cleanButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					closeAction();
				}
			});
	}
	
	/**
	 * Perform this action when close button onAction is triggered.
	 * */
	@SuppressWarnings("unchecked")
	public void closeAction() {
		if(actionHelper.runBefore(TActionType.CLOSE)){
			try{
				
				//recupera a lista de models views
				final ObservableList<M> modelsViewsList = (ObservableList<M>) ((saveAllModels && getModels()!=null) 
						? getModels() 
								: getModelView()!=null 
								? FXCollections.observableList(Arrays.asList(getModelView())) 
										: null) ;
								
				if(modelsViewsList != null)
					validateModels(modelsViewsList);
				
				closeModal();
				
			}catch(TValidatorException e){
				getView().tShowModal(new TMessageBox(e), true);
				setActionState(new TActionState<>(TActionType.CLOSE, TProcessResult.FINISHED));
			} catch (Throwable e) {
				e.printStackTrace();
				getView().tShowModal(new TMessageBox(e), true);
				setActionState(new TActionState<>(TActionType.CLOSE, TProcessResult.ERROR));
			}
		}
		actionHelper.runAfter(TActionType.CLOSE);
	}
	

	private void closeModal() {
		super.invalidate();
		TedrosAppManager.getInstance()
		.getModuleContext((TModule)TedrosContext.getView()).getCurrentViewContext()
		.getPresenter().getView().tHideModal();
	}
	
	protected void configCancelAction() {
		addAction(new TPresenterAction(TActionType.CANCEL) {

			@Override
			public boolean runBefore() {
				return true;
			}

			@Override
			public void runAfter() {
				final ListView<M> listView = decorator.gettListView();
				listView.getSelectionModel().clearSelection();
				setDisableModelActionButtons(true);
				showListView();
			}
		});
	}
	
	
	@SuppressWarnings("unchecked")
	private void loadListView() {
		final ObservableList<M> models = getModels();
		final ListView<M> listView = this.decorator.gettListView();
		listView.setItems((ObservableList<M>) (models==null ? FXCollections.observableArrayList() : models));
		listView.setEditable(true);
		listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		super.getListenerRepository().remove("processloadlistviewCL");
		final M mv = getPresenter().getModelView();
		processModelView(mv);
		
	}
	
	
	@SuppressWarnings("unchecked")
	private void configListViewCallBack() {
		TBehavior tBehavior = getPresenter().getPresenterAnnotation().behavior();
		try {
			Callback<ListView<M>, ListCell<M>> callBack = (Callback<ListView<M>, ListCell<M>>) 
					((tBehavior!=null) 
							? tBehavior.listViewCallBack().newInstance() 
									: new TEntityListViewCallback<M>());
			
			final ListView<M> listView = this.decorator.gettListView();
			listView.setCellFactory(callBack);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	protected void configListViewChangeListener() {
		
		ChangeListener<M> chl = (a0, old_, new_) -> {
			processListViewSelectedItem(new_);
		};
		
		super.getListenerRepository().add("listviewselecteditemviewCL", chl);
		
		this.decorator.gettListView()
		.getSelectionModel()
		.selectedItemProperty()
		.addListener(new WeakChangeListener<>(chl));
		
	}

	protected void processListViewSelectedItem(M new_) {
		if(new_==null) {
			setModelView(null);
			showListView();
		}else{
			selectedItemAction(new_);
			hideListView();
		}
	}
	
	public void remove() {
		final ListView<M> listView = this.decorator.gettListView();
		int index = getModels().indexOf(getModelView());
		listView.getSelectionModel().clearSelection();
		super.remove(index);
	}
		
	public void colapseAction() {
		if(!this.decorator.isListContentVisible())
			showListView();
		else
			hideListView();
	}

	public void hideListView() {
		this.decorator.hideListContent();
	}

	public void showListView() {
		this.decorator.showListContent();
	}
	
	public boolean processNewEntityBeforeBuildForm(M model) {
		final ListView<M> list = this.decorator.gettListView();
		list.getItems().add(model);
		list.selectionModelProperty().get().select(list.getItems().size()-1);
		return false;
	}
	
	@Override
	public boolean invalidate() {
		return super.invalidate();
	}
		
}
