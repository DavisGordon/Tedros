package org.tedros.fx.presenter.entity.behavior;

import org.apache.commons.lang3.ArrayUtils;
import org.tedros.api.form.ITModelForm;
import org.tedros.api.presenter.view.TViewMode;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.control.action.TPresenterAction;
import org.tedros.fx.form.TFormBuilder;
import org.tedros.fx.form.TReaderFormBuilder;
import org.tedros.fx.modal.TMessageBox;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.fx.model.TModelView;
import org.tedros.fx.presenter.behavior.TActionType;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.fx.presenter.dynamic.behavior.TDynaViewCrudBaseBehavior;
import org.tedros.fx.presenter.dynamic.behavior.TDynaViewSimpleBaseBehavior;
import org.tedros.fx.presenter.dynamic.decorator.TDynaViewCrudBaseDecorator;
import org.tedros.fx.presenter.entity.decorator.TDetailCrudViewDecorator;
import org.tedros.fx.util.TEntityListViewCallback;
import org.tedros.server.entity.ITEntity;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.util.Callback;
/**
 * The behavior of the detail view. 
 * This behavior can be applied on detail entities.
 * @author Davis Gordon
 *
 * @param <M>
 * @param <E>
 */
@SuppressWarnings({ "rawtypes" })
public class TDetailCrudViewBehavior<M extends TEntityModelView<E>, E extends ITEntity>
extends TDynaViewCrudBaseBehavior<M, E> {
	
	private TDetailCrudViewDecorator<M> decorator;

	@Override
	public void load() {
		super.setSkipConfigBreadcrumb(true);
		super.load();
		this.decorator = (TDetailCrudViewDecorator<M>) getPresenter().getDecorator();
		initialize();
	}
	/**
	 * Initialize the behavior.
	 */
	public void initialize() {
		try{
			
			configColapseButton();
			configNewButton();
			configDeleteButton();
			configPrintButton();
			if(this.decorator.gettEditButton()!=null)
				configEditButton();
			
			configModesRadio();
			
			configListView();
			configListViewCallBack();
			loadListView();
			
		}catch(Throwable e){
			getView().tShowModal(new TMessageBox(e), true);
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	@Override
	public void addAction(TPresenterAction action) {
		boolean flag = false;
		if(action!=null && action.getTypes()!=null) { 
			for(TActionType a : new TActionType[] {TActionType.NEW, TActionType.DELETE, TActionType.PRINT, 
					TActionType.EDIT, TActionType.CHANGE_MODE, TActionType.SELECTED_ITEM})
				if(ArrayUtils.contains(action.getTypes(), a)) {
					flag = true;
					break;
				}	
		}else
			flag = true;
		if(flag) {
			super.addAction(action);
		}else {
			final TDynaPresenter presenter = getModulePresenter();
			final TDynaViewSimpleBaseBehavior behavior = (TDynaViewSimpleBaseBehavior) presenter.getBehavior(); 
			behavior.addAction(action);
		}
	}
		
	@Override
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
	protected void configListView() {
		
		ChangeListener<M> chl = (a, o, n) -> {
			this.processListViewSelectedItem(n);
		};
		
		super.getListenerRepository().add("listviewselecteditemviewCL", chl);
		
		this.decorator.gettListView()
		.getSelectionModel()
		.selectedItemProperty()
		.addListener(new WeakChangeListener<>(chl));
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
		this.decorator.showListContent();
	}

	@Override
	public boolean processNewEntityBeforeBuildForm(M model) {
		addInListView(model);
		return false;
	}

	/**
	 * @param model
	 */
	private void addInListView(M model) {
		final ListView<M> list = this.decorator.gettListView();
		list.getItems().add(model);
		list.selectionModelProperty().get().select(list.getItems().size()-1);
	}
	
	@Override
	public void setForm(ITModelForm form) {
		final TDynaPresenter presenter = (TDynaPresenter) getModulePresenter();
		if( presenter.getDecorator() instanceof TDynaViewCrudBaseDecorator 
				&& ((TDynaViewCrudBaseDecorator) presenter.getDecorator()).gettBreadcrumbForm()!=null){
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

	@Override
	public void setDisableModelActionButtons(boolean flag) {
		if(decorator.gettCancelButton()!=null)
			decorator.gettCancelButton().setDisable(flag);
		if(decorator.gettEditButton()!=null)
			decorator.gettEditButton().setDisable(flag);
		if(decorator.gettDeleteButton()!=null)
			decorator.gettDeleteButton().setDisable(flag);
		if(decorator.gettPrintButton()!=null)
			decorator.gettPrintButton().setDisable(flag);
	}
	
	@Override
	public void removeBreadcrumbFormChangeListener() {
		final TDynaPresenter presenter = getModulePresenter();
		if(presenter.getBehavior() instanceof TDynaViewCrudBaseBehavior) {
			final TDynaViewCrudBaseBehavior behavior = (TDynaViewCrudBaseBehavior)presenter.getBehavior();
			behavior.removeBreadcrumbFormChangeListener();
		}
	}
	
	@Override
	public void addBreadcrumbFormChangeListener() {
		final TDynaPresenter presenter = getModulePresenter();
		if(presenter.getBehavior() instanceof TDynaViewCrudBaseBehavior) {
			final TDynaViewCrudBaseBehavior behavior = (TDynaViewCrudBaseBehavior)presenter.getBehavior();
			behavior.addBreadcrumbFormChangeListener();
		}
	}
	
	/**
	 * Process the selected item on the ListView
	 * @param m
	 */
	protected void processListViewSelectedItem(M m) {
		if(m==null) {
			setModelView(null);
			showListView();
		}else{
			selectedItemAction(m);
			hideListView();
		}
	}


	@Override
	public void loadModelView(M m) {
		this.addInListView(m);
		this.processListViewSelectedItem(m);
	}
	
}
