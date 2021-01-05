package com.tedros.fxapi.presenter.entity.behavior;

import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.domain.TViewMode;
import com.tedros.fxapi.form.ITModelForm;
import com.tedros.fxapi.form.TFormBuilder;
import com.tedros.fxapi.form.TReaderFormBuilder;
import com.tedros.fxapi.modal.TMessageBox;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.dynamic.behavior.TDynaViewCrudBaseBehavior;
import com.tedros.fxapi.presenter.dynamic.decorator.TDynaViewCrudBaseDecorator;
import com.tedros.fxapi.presenter.entity.decorator.TDetailCrudViewDecorator;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.fxapi.presenter.model.TModelView;
import com.tedros.fxapi.util.TEntityListViewCallback;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.util.Callback;

@SuppressWarnings({ "rawtypes" })
public class TDetailCrudViewBehavior<M extends TEntityModelView, E extends ITEntity>
extends com.tedros.fxapi.presenter.dynamic.behavior.TDynaViewCrudBaseBehavior<M, E> {
	
	private TDetailCrudViewDecorator<M> decorator;

	public void load() {
		super.setSkipConfigBreadcrumb(true);
		super.load();
		this.decorator = (TDetailCrudViewDecorator<M>) getPresenter().getDecorator();
		initialize();
	}
	
	public void initialize() {
		try{
			
			configColapseButton();
			configNewButton();
			configDeleteButton();
			if(this.decorator.gettEditButton()!=null)
				configEditButton();
			
			configModesRadio();
			
			configListView();
			configListViewCallBack();
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
		processModelView(null);
	}
	
	@SuppressWarnings("unchecked")
	private void configListViewCallBack() {
		TBehavior tBehavior = getPresenter().getPresenterAnnotation().behavior();
		try {
			Callback<ListView<M>, ListCell<M>> callBack = (Callback<ListView<M>, ListCell<M>>) ((tBehavior!=null) 
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
		
		super.getListenerRepository().add("listviewselecteditemviewCL", chl);
		
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
	public void setForm(ITModelForm form) {
		final TDynaPresenter presenter = (TDynaPresenter) getModulePresenter();
		if( ((TDynaViewCrudBaseDecorator) presenter.getDecorator()).gettBreadcrumbForm()!=null){
			removeBreadcrumbFormChangeListener();
			super.setForm(form);
			addBreadcrumbFormChangeListener();
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
		presenter.getBehavior().setForm(form);
	}

	public void setDisableModelActionButtons(boolean flag) {
		if(decorator.gettCancelButton()!=null)
			decorator.gettCancelButton().setDisable(flag);
		if(decorator.gettEditButton()!=null)
			decorator.gettEditButton().setDisable(flag);
		if(decorator.gettDeleteButton()!=null)
			decorator.gettDeleteButton().setDisable(flag);
	}
	
	@Override
	public void removeBreadcrumbFormChangeListener() {
		final TDynaPresenter presenter = getModulePresenter();
		final TDynaViewCrudBaseBehavior behavior = (TDynaViewCrudBaseBehavior)presenter.getBehavior();
		behavior.removeBreadcrumbFormChangeListener();
	}
	
	@Override
	public void addBreadcrumbFormChangeListener() {
		final TDynaPresenter presenter = getModulePresenter();
		final TDynaViewCrudBaseBehavior behavior = (TDynaViewCrudBaseBehavior)presenter.getBehavior();
		behavior.addBreadcrumbFormChangeListener();
	}
	
}
