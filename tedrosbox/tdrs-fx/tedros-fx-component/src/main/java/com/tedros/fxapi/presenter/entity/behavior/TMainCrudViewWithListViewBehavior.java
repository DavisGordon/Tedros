package com.tedros.fxapi.presenter.entity.behavior;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.ejb.base.result.TResult;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.control.action.TPresenterAction;
import com.tedros.fxapi.modal.TMessageBox;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.dynamic.view.TDynaView;
import com.tedros.fxapi.presenter.entity.decorator.TMainCrudViewWithListViewDecorator;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.fxapi.presenter.model.TModelView;
import com.tedros.fxapi.process.TEntityProcess;
import com.tedros.fxapi.util.TEntityListViewCallback;

import javafx.beans.InvalidationListener;
import javafx.beans.WeakInvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
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
					showListView();
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
			
			// processo para listagem dos models
			final TEntityProcess<E> process  = (TEntityProcess<E>) createEntityProcess();
			if(process!=null){
				
				ChangeListener<State> prcl = (arg0, arg1, arg2) -> {
					
					if(arg2.equals(State.SUCCEEDED)){
						
						List<?> resultados = process.getValue();
						
						if(!resultados.isEmpty()) {
						
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
				};
				
				super.getListenerRepository().addListener("processloadlistviewCL", prcl);
				
				process.list();
				process.stateProperty().addListener(new WeakChangeListener(prcl));
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
	
	
	@SuppressWarnings("unchecked")
	private void loadListView() {
		final ObservableList<M> models = getModels();
		final ListView<M> listView = this.decorator.gettListView();
		listView.setItems((ObservableList<M>) (models==null ? FXCollections.observableArrayList() : models));
		listView.setEditable(true);
		listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		super.getListenerRepository().removeListener("processloadlistviewCL");
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
	
	protected void configListView() {
		
		ChangeListener<M> chl = (a0, old_, new_) -> {
			if(new_==null) {
				setModelView(null);
				showListView();
			}else{
				selectedItemAction(new_);
				hideListView();
			}
		};
		
		super.getListenerRepository().addListener("listviewselecteditemviewCL", chl);
		
		this.decorator.gettListView()
		.getSelectionModel()
		.selectedItemProperty()
		.addListener(new WeakChangeListener<>(chl));
		
	}
	
	public void remove() {
		final ListView<M> listView = this.decorator.gettListView();
		int index = getModels().indexOf(getModelView());
		listView.getSelectionModel().clearSelection();
		super.remove(index);
	}
		
	public void colapseAction() {
		final StackPane pane = (StackPane) ((TDynaView) getView()).gettContentLayout().getLeft();
		if(!pane.isVisible())
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
	
	
		
}
