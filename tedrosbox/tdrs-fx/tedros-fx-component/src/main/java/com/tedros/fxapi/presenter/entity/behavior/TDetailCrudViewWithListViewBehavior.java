package com.tedros.fxapi.presenter.entity.behavior;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.control.action.TPresenterAction;
import com.tedros.fxapi.domain.TViewMode;
import com.tedros.fxapi.form.ITModelForm;
import com.tedros.fxapi.form.TFormBuilder;
import com.tedros.fxapi.form.TReaderFormBuilder;
import com.tedros.fxapi.modal.TMessageBox;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.dynamic.behavior.TDynaViewCrudBaseBehavior;
import com.tedros.fxapi.presenter.dynamic.decorator.TDynaViewCrudBaseDecorator;
import com.tedros.fxapi.presenter.dynamic.view.TDynaView;
import com.tedros.fxapi.presenter.entity.decorator.TDetailCrudViewWithListViewDecorator;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.fxapi.presenter.model.TModelView;
import com.tedros.fxapi.util.TEntityListViewCallback;

@SuppressWarnings({ "rawtypes" })
public class TDetailCrudViewWithListViewBehavior<M extends TEntityModelView, E extends ITEntity>
extends com.tedros.fxapi.presenter.dynamic.behavior.TDynaViewCrudBaseBehavior<M, E> {
	
	private TDetailCrudViewWithListViewDecorator<M> decorator;

	public void load() {
		super.load();
		this.decorator = (TDetailCrudViewWithListViewDecorator<M>) getPresenter().getDecorator();
		initialize();
	}
	
	public void initialize() {
		try{
			
			setSelectedItemAction(new TPresenterAction<TDynaPresenter<M>>() {
				@Override
				public boolean runBefore(TDynaPresenter<M> presenter) {
					setViewMode(TViewMode.READER);
					if(presenter.getBehavior().getModelView()!=null 
							&& !selectMode())
						setForm(buildForm(TViewMode.READER));
					return false;
				}

				@Override
				public void runAfter(TDynaPresenter<M> presenter) {
					
				}
			});
			
			
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
			
			this.decorator.gettListView().getItems().addAll(getModels());
			
			configColapseButton();
			configNewButton();
			configDeleteButton();
			configEditButton();
			configCancelButton();
			configModesRadio();
			
			configListView();
			configListViewCallBack();
			setDisableModelActionButtons(true);
			
			this.decorator.showScreenSaver();
			loadListView();
			
		}catch(Throwable e){
			getView().tShowModal(new TMessageBox(e), true);
			e.printStackTrace();
		}
	}
		
	public void startRemoveProcess(boolean removeFromDataBase) {
		super.startRemoveProcess(false);
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
			Callback<ListView<M>, ListCell<M>> callBack = (Callback<ListView<M>, ListCell<M>>) ((tBehavior!=null) ? tBehavior.listViewCallBack().newInstance() : new TEntityListViewCallback<M>());
			final ListView<M> listView = this.decorator.gettListView();
			listView.setCellFactory(callBack);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
		
	protected void configListView() {
		this.decorator.gettListView().getSelectionModel().selectedItemProperty().addListener(
				new ChangeListener<M>() {
	                public void changed(final ObservableValue<? extends M> ov, final M old_val, final M new_val) {
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
	}
	
	@Override
	public void setForm(ITModelForm form) {
		final TDynaPresenter presenter = (TDynaPresenter) getModulePresenter();
		if( ((TDynaViewCrudBaseDecorator) presenter.getDecorator()).gettBreadcrumbForm()!=null){
			removeFormListChangeListener();
			super.setForm(form);
			addFormListChangeListener();
		}else
			super.setForm(form);
	}
	
	/**
	 * Called by the editAction()
	 * */
	@Override
	public void editEntity(TModelView model) {
		if(this.decorator.isShowBreadcrumBar())
			showFormInMainPresenter(model);
		else
			showForm(TViewMode.EDIT);
	}
	
	private void showFormInMainPresenter(TModelView model) {
		
		final TDynaPresenter presenter = getModulePresenter();
		final TDynaViewCrudBaseBehavior behavior = (TDynaViewCrudBaseBehavior)presenter.getBehavior(); 
		
		ITModelForm form =  behavior.isReaderModeSelected() // check in the main behavior  
				? TReaderFormBuilder.create(model).build() 
						: TFormBuilder.create(model).presenter(getPresenter()).build();
		
		form.settPresenter(presenter);
		
		//behavior.removeFormListChangeListener();
		presenter.getBehavior().setForm(form);
		//behavior.addFormListChangeListener();
	}

	public void setDisableModelActionButtons(boolean flag) {
		if(decorator.gettCancelButton()!=null)
			decorator.gettCancelButton().setDisable(flag);
		if(decorator.gettEditButton()!=null)
			decorator.gettEditButton().setDisable(flag);
		if(decorator.gettDeleteButton()!=null)
			decorator.gettDeleteButton().setDisable(flag);
	}
	
	public void removeFormListChangeListener() {
		final TDynaPresenter presenter = getModulePresenter();
		final TDynaViewCrudBaseBehavior behavior = (TDynaViewCrudBaseBehavior)presenter.getBehavior();
		behavior.removeFormListChangeListener();
	}
	
	public void addFormListChangeListener() {
		final TDynaPresenter presenter = getModulePresenter();
		final TDynaViewCrudBaseBehavior behavior = (TDynaViewCrudBaseBehavior)presenter.getBehavior();
		behavior.addFormListChangeListener();
	}
	
}
