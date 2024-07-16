package org.tedros.fx.presenter.modal.behavior;

import java.util.Arrays;

import org.tedros.core.TModule;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.context.TedrosAppManager;
import org.tedros.core.context.TedrosContext;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.control.action.TPresenterAction;
import org.tedros.fx.exception.TValidatorException;
import org.tedros.fx.modal.TMessageBox;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.fx.presenter.behavior.TActionState;
import org.tedros.fx.presenter.behavior.TActionType;
import org.tedros.fx.presenter.behavior.TProcessResult;
import org.tedros.fx.presenter.dynamic.behavior.TDynaViewCrudBaseBehavior;
import org.tedros.fx.presenter.modal.decorator.TEditModalDecorator;
import org.tedros.fx.util.TEntityListViewCallback;
import org.tedros.server.entity.ITEntity;

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

/**
 * The behavior of the open modal view to edit entity. 
 * This behavior can be applied on detail entities.
 * @author Davis Gordon
 *
 * @param <M>
 * @param <E>
 */
public class TEditModalBehavior<M extends TEntityModelView<E>, E extends ITEntity>
extends TDynaViewCrudBaseBehavior<M, E> {
	
	protected TEditModalDecorator<M> decorator;
	private boolean singleMode = false;
		
	@Override
	public void load() {
		super.load();
		this.decorator = (TEditModalDecorator<M>) getPresenter().getDecorator();
		initialize();
	}
	/**
	 * Initialize the behavior.
	 */
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
		else
			super.setViewStateAsReady();
	}
	
	/**
	 * Enable the single mode.
	 */
	public void setSingleMode() {
		this.singleMode = true;
		super.runNewActionAfterSave = false;
		this.colapseAction();
		if(!getModels().isEmpty() && super.getModelView()==null) {
			super.setModelView(getModels().get(0));
		}else {
			super.newAction();
		}
		if(decorator.gettDeleteButton()!=null)
			super.addAction(new TPresenterAction(TActionType.DELETE) {
	
				@Override
				public boolean runBefore() {
					return true;
				}
	
				@Override
				public void runAfter() {
					newAction();
				}
			});
		if(decorator.gettCancelButton()!=null)
			super.addAction(new TPresenterAction(TActionType.CANCEL) {
	
				@Override
				public boolean runBefore() {
					return true;
				}
	
				@Override
				public void runAfter() {
					if(!getModels().isEmpty())
						setModelView(getModels().get(0));
				}
				
			});
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
				LOGGER.error(e.getMessage(), e);
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
	/**
	 * Config the cancel action
	 */
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
							? tBehavior.listViewCallBack().getDeclaredConstructor().newInstance() 
									: new TEntityListViewCallback<M>());
			
			final ListView<M> listView = this.decorator.gettListView();
			listView.setCellFactory(callBack);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}	
	
	/**
	 * Config the ListView listener.
	 */
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
	/**
	 * Process the selected item on the ListView
	 * @param new_
	 */
	protected void processListViewSelectedItem(M new_) {
		if(new_==null) {
			setModelView(null);
			showListView();
		}else{
			selectedItemAction(new_);
			hideListView();
		}
	}
	
	/**
	 * Remove the selected item from the ListView
	 */
	public void remove() {
		final ListView<M> listView = this.decorator.gettListView();
		int index = getModels().indexOf(getModelView());
		listView.getSelectionModel().clearSelection();
		super.remove(index);
	}
		
	@Override
	public void colapseAction() {
		if(!this.decorator.isListContentVisible())
			showListView();
		else
			hideListView();
	}

	/**
	 * Hide the ListView panel
	 */
	public void hideListView() {
		this.decorator.hideListContent();
	}

	/**
	 * Show the ListView panel
	 */
	public void showListView() {
		if(!this.singleMode)
			this.decorator.showListContent();
	}
	
	@Override
	public boolean processNewEntityBeforeBuildForm(M model) {
		final ListView<M> list = this.decorator.gettListView();
		list.getItems().add(model);
		list.selectionModelProperty().get().select(list.getItems().size()-1);
		return false;
	}
		
}
