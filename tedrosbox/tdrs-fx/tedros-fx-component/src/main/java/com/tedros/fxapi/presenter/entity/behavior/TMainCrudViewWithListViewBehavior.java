package com.tedros.fxapi.presenter.entity.behavior;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.ejb.base.result.TResult;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.control.action.TPresenterAction;
import com.tedros.fxapi.domain.TViewMode;
import com.tedros.fxapi.modal.TMessageBox;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.dynamic.view.TDynaView;
import com.tedros.fxapi.presenter.entity.decorator.TMainCrudViewWithListViewDecorator;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.fxapi.presenter.model.TModelView;
import com.tedros.fxapi.process.TEntityProcess;
import com.tedros.fxapi.util.TEntityListViewCallback;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker.State;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

@SuppressWarnings({ "rawtypes" })
public class TMainCrudViewWithListViewBehavior<M extends TEntityModelView, E extends ITEntity>
extends com.tedros.fxapi.presenter.dynamic.behavior.TDynaViewCrudBaseBehavior<M, E> {
	
	private TMainCrudViewWithListViewDecorator<M> decorator;
		
	@Override
	public void load() {
		super.load();
		this.decorator = (TMainCrudViewWithListViewDecorator<M>) getPresenter().getDecorator();
		initialize();
	}
		
	@SuppressWarnings("unchecked")
	public void initialize() {
		try{
			
			if(this.decorator.isShowBreadcrumBar())
				configBreadcrumbForm();
			
			setCancelAction(new TPresenterAction<TDynaPresenter<M>>() {

				@Override
				public boolean runBefore(TDynaPresenter<M> presenter) {
					return true;
				}

				@Override
				public void runAfter(TDynaPresenter<M> presenter) {
					final ListView<M> listView = decorator.gettListView();
					listView.getSelectionModel().clearSelection();
					setDisableModelActionButtons(true);
					colapseAction();
				}
			});
			
			configColapseButton();
			configNewButton();
			configSaveButton();
			configDeleteButton();
			configListView();
			configListViewCallBack();
			configModesRadio();
			configCancelButton();
			setDisableModelActionButtons(true);
			this.decorator.showScreenSaver();
			
			// processo para listagem dos models
			final TEntityProcess<E> process  = (TEntityProcess<E>) createEntityProcess();
			if(process!=null){
				process.list();
				process.stateProperty().addListener(new ChangeListener<State>() {
					
					public void changed(ObservableValue<? extends State> arg0,
							State arg1, State arg2) {
						
							if(arg2.equals(State.SUCCEEDED)){
								List<?> resultados = process.getValue();
								if(resultados.isEmpty())
									return;
								TResult result = (TResult<?>) resultados.get(0);
								if(result.getValue()!=null && result.getValue() instanceof List<?>){
									ObservableList<M> models = getModels();
									if(models==null)
										models = FXCollections.observableArrayList();
									for(E e : (List<E>) result.getValue()){
										try {
											M model = (M) getModelViewClass().getConstructor(getEntityClass()).newInstance(e);
											models.add(model);
										} catch (InstantiationException
												| IllegalAccessException
												| IllegalArgumentException
												| InvocationTargetException
												| NoSuchMethodException
												| SecurityException e1) 
										{
											e1.printStackTrace();
										}
									}
									setModelViewList(models);
									loadListView();
								}
							}	
					}
				});
				process.startProcess();
			}else{
				System.err.println("\nWARNING: Cannot create a process for the "+getModelViewClass().getSimpleName()+", check the @TCrudForm(processClass,serviceName) properties");
				loadListView();
			}
		
		}catch(Throwable e){
			getView().tShowModal(new TMessageBox(e), true);
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Perform this action when a mode radio change listener is triggered.
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public void changeModeAction() {
		final TPresenterAction changeModeAction =  getChangeModeAction();
		final TDynaPresenter<M> presenter = getPresenter();
		if(changeModeAction==null || (changeModeAction!=null && changeModeAction.runBefore(presenter))){
			if(getModelView()!=null){
				if(decorator.isShowBreadcrumBar())
					decorator.gettBreadcrumbForm().tEntryListProperty().clear();
				showForm(null);
			}
		}
		
		if(changeModeAction!=null)
			changeModeAction.runAfter(presenter);
		
	}
	
	@SuppressWarnings("unchecked")
	private void loadListView() {
		final ObservableList<M> models = getModels();
		final ListView<M> listView = this.decorator.gettListView();
		listView.setItems((ObservableList<M>) (models==null ? FXCollections.observableArrayList() : models));
		listView.setEditable(true);
		listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
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
	
	protected void configListView() {
		this.decorator.gettListView()
		.getSelectionModel()
		.selectedItemProperty()
		.addListener(
				new ChangeListener<M>() {
	                public void changed(final ObservableValue<? extends M> ov, final M old_val, final M new_val) {
	                	if(isUserAuthorized(TAuthorizationType.EDIT))
	                		setViewMode(TViewMode.EDIT);
	                	else if(isUserAuthorized(TAuthorizationType.READ))
	                		setViewMode(TViewMode.READER);
	                	
	                	if(decorator.isShowBreadcrumBar())
	                		decorator.gettBreadcrumbForm().tEntryListProperty().clear();
	                	
	                	selectedItemAction(new_val);
	                	decorator.hideListContent();
	                	setDisableModelActionButtons(false);
	            }	
	        });
		
		this.decorator.gettListView().getSelectionModel().selectedItemProperty().addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable arg0) {
				setDisableModelActionButtons(true);
			}
		});
	}
	
	public void remove() {
		final ListView<M> listView = this.decorator.gettListView();
		final int selectedIndex = listView.getSelectionModel().getSelectedIndex();
		listView.getItems().remove(selectedIndex);
		listView.getSelectionModel().clearSelection();
		setDisableModelActionButtons(true);
	}
		
	public void colapseAction() {
		final StackPane pane = (StackPane) ((TDynaView) getView()).gettContentLayout().getLeft();
		if(!pane.isVisible())
			this.decorator.showListContent();
		else
			this.decorator.hideListContent();
	}
	
	public void setNewEntity(M model) {
		final ListView<M> list = this.decorator.gettListView();
		list.getItems().add(model);
		list.selectionModelProperty().get().select(list.getItems().size()-1);
	}
	
	public void editEntity(TModelView model) {
		
	}
	
	public void setDisableModelActionButtons(boolean flag) {
		if(decorator.gettCancelButton()!=null)
			decorator.gettCancelButton().setDisable(flag);
		if(decorator.gettSaveButton()!=null && isUserAuthorized(TAuthorizationType.SAVE))
			decorator.gettSaveButton().setDisable(flag);
		if(decorator.gettDeleteButton()!=null && isUserAuthorized(TAuthorizationType.DELETE))
			decorator.gettDeleteButton().setDisable(flag);
	}
		
}
