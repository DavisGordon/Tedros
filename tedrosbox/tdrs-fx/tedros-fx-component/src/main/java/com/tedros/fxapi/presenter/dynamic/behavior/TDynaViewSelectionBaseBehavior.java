package com.tedros.fxapi.presenter.dynamic.behavior;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.tedros.core.ITModule;
import com.tedros.core.TInternationalizationEngine;
import com.tedros.core.TModule;
import com.tedros.core.context.TedrosAppManager;
import com.tedros.core.context.TedrosContext;
import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.ejb.base.result.TResult;
import com.tedros.fxapi.annotation.control.TTableView;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TSelectionModalPresenter;
import com.tedros.fxapi.annotation.view.TPaginator;
import com.tedros.fxapi.builder.ITBuilder;
import com.tedros.fxapi.builder.ITControlBuilder;
import com.tedros.fxapi.builder.ITFieldBuilder;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.collections.TFXCollections;
import com.tedros.fxapi.control.action.TPresenterAction;
import com.tedros.fxapi.descriptor.TComponentDescriptor;
import com.tedros.fxapi.domain.TViewMode;
import com.tedros.fxapi.form.ITModelForm;
import com.tedros.fxapi.modal.TMessageBox;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.dynamic.decorator.TDynaViewSelectionBaseDecorator;
import com.tedros.fxapi.presenter.model.TModelView;
import com.tedros.fxapi.presenter.paginator.TPagination;
import com.tedros.fxapi.process.TPaginationProcess;
import com.tedros.fxapi.util.TEntityListViewCallback;
import com.tedros.fxapi.util.TReflectionUtil;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.ListChangeListener;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.WeakEventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;


@SuppressWarnings("rawtypes")
public abstract class TDynaViewSelectionBaseBehavior<M extends TModelView, E extends ITEntity> 
extends TDynaViewSimpleBaseBehavior<M, E> {
	
	private ListChangeListener<Node> formListChangeListener;
	
	private TPresenterAction<TDynaPresenter<M>> cleanAction;
	private TPresenterAction<TDynaPresenter<M>> searchAction;
	private TPresenterAction<TDynaPresenter<M>> selectedItemAction;
	private TPresenterAction<TDynaPresenter<M>> cancelAction;
	private TPresenterAction<TDynaPresenter<M>> closeAction;
	
	private ITObservableList<TModelView> searchResultList;
	
	private Class<E> modelClass;
	private Class<? extends TModelView<E>> paginatorModelViewClass;
	private String serviceName;
	
	private boolean allowsMultipleSel;
	
	private TDynaViewSelectionBaseDecorator<M> decorator;

	

	@SuppressWarnings("unchecked")
	public void load(){
		super.load();
		try{
			TSelectionModalPresenter modalPresenter = getModelViewClass().getAnnotation(TSelectionModalPresenter.class);
			
			if(modalPresenter==null)
				throw new RuntimeException("The TSelectionModalPresenter annotation not found in the " + getModelViewClass().getSimpleName());

			final TPaginator tPaginator = modalPresenter.paginator();
			
			// set the  process
			if(tPaginator.entityClass()!=null && tPaginator.serviceName()!=null && tPaginator.modelViewClass()!=null){
				this.modelClass = (Class<E>) tPaginator.entityClass();
				this.serviceName = tPaginator.serviceName();
				this.paginatorModelViewClass = (Class<? extends TModelView<E>>) tPaginator.modelViewClass();
			}else
				throw new RuntimeException("The propertys entityClass, serviceName and modelViewClass in TPaginator is required in the " + getModelViewClass().getSimpleName());
			
			
			final TDynaPresenter<M> presenter = getPresenter();
			
			this.decorator = (TDynaViewSelectionBaseDecorator<M>) presenter.getDecorator();
			
			//final TForm tForm = presenter.getFormAnnotation();
			final TBehavior tBehavior = presenter.getPresenterAnnotation().behavior();
			
			// set the custom behavior actions
			if(tBehavior.searchAction()!=TPresenterAction.class)
				searchAction = tBehavior.searchAction().newInstance();
			if(tBehavior.cleanAction()!=TPresenterAction.class)
				cleanAction = tBehavior.cleanAction().newInstance();
			if(tBehavior.selectedItemAction()!=TPresenterAction.class)
				selectedItemAction = tBehavior.selectedItemAction().newInstance();
			if(tBehavior.cancelAction()!=TPresenterAction.class)
				cancelAction = tBehavior.cancelAction().newInstance();
			if(tBehavior.closeAction()!=TPresenterAction.class)
				closeAction = tBehavior.closeAction().newInstance();
			
			allowsMultipleSel = modalPresenter.allowsMultipleSelections();
			searchResultList = TFXCollections.iTObservableList();
			
			TModelView model = (M) paginatorModelViewClass.getConstructor(modelClass).newInstance(modelClass.newInstance());
	    	
			TTableView tableViewAnn = modalPresenter.tableView();

	    	Object[] arrControl = TReflectionUtil.getControlBuilder(Arrays.asList(tableViewAnn));
	    	ITFieldBuilder controlBuilder = (ITFieldBuilder) arrControl[1];
	    	((ITBuilder) controlBuilder).setComponentDescriptor(new TComponentDescriptor(null, model, null));
	    	TableView tableView = (TableView) ((ITControlBuilder) controlBuilder).build(tableViewAnn, this.searchResultList);
	    	tableView.setTooltip(new Tooltip(TInternationalizationEngine
					.getInstance(null)
					.getString("#{tedros.fxapi.label.double.click.select}")));
	    	
	    	decorator.setTableView(tableView);
			
			tableView.setTableMenuButtonVisible(true);
			
			EventHandler<MouseEvent> ev = e ->{
				if(e.getClickCount()==2) {
					int index = tableView.getSelectionModel().getSelectedIndex();
					TModelView new_ = (TModelView) tableView.getItems().get(index);
					selectedItemAction(new_);
				}
			};
			super.getListenerRepository().addListener("tvmclkeh", ev);
			tableView.setOnMouseClicked(new WeakEventHandler<>(ev));
			
			if(this.decorator.gettPaginator()!=null) {
				ChangeListener<TPagination> chl = (a0, a1, a2) -> {
					try {
						this.runFindAllProcess(a2);
					} catch (Throwable e) {
						e.printStackTrace();
					}
				};
				this.decorator.gettPaginator().paginationProperty().addListener(chl);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public void configSelectedListView() {
		ListView listView = this.decorator.gettListView();
		listView.setItems(getModels());
		listView.setEditable(true);
		listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		Callback<ListView<TModelView>, ListCell<TModelView>> callBack = (Callback<ListView<TModelView>, ListCell<TModelView>>) 
				 new TEntityListViewCallback();
		
		listView.setCellFactory(callBack);
		
		EventHandler<MouseEvent> ev = e ->{
			if(e.getClickCount()==2) {
				int index = listView.getSelectionModel().getSelectedIndex();
				listView.getItems().remove(index);
				listView.getSelectionModel().clearSelection();
			}
		};
		super.getListenerRepository().addListener("lvmclkeh", ev);
		listView.setOnMouseClicked(new WeakEventHandler<>(ev));
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
	 * Config the clean button 
	 * */
	public void configCleanButton() {
		final Button cleanButton = this.decorator.gettCleanButton();
			cleanButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					cleanAction();
				}
			});
		
	}
	
	/**
	 * Config the search button
	 * */
	public void configSearchButton() {
		final Button searchButton = this.decorator.gettSearchButton();
		searchButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				searchAction();
			}
		});
		
	}
	
	
	/**
	 * Config the cancel button;
	 * */
	public void configCancelButton() {
		final Button cancelButton = this.decorator.gettCancelButton();
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				cancelAction();
			}
		});
	}
	
	// ACTIONS
	
	/**
	 * Perform this action when a model is selected.
	 * */
	public void selectedItemAction(TModelView new_val) {
		final TDynaPresenter<M> presenter = getPresenter();
		if(selectedItemAction==null || (selectedItemAction!=null && selectedItemAction.runBefore(presenter))){
			if(new_val==null)
				return;
			if(!getModels().contains(new_val)) {
				if(!allowsMultipleSel)
					getModels().clear();
				getModels().add((M) new_val);
			}
		}
		if(selectedItemAction!=null)
			selectedItemAction.runAfter(presenter);
	}
	
	/**
	 * Perform this action when search button onAction is triggered.
	 * */
	public void searchAction() {
		final TDynaPresenter<M> presenter = getPresenter();
		if(searchAction==null || (searchAction!=null && searchAction.runBefore(presenter))){
			
			try{
				runFindAllProcess(null);
				
			} catch (Throwable e) {
				getView().tShowModal(new TMessageBox(e), true);
				e.printStackTrace();
			}
		}
		
		
	}
	

	@SuppressWarnings("unchecked")
	protected void runFindAllProcess(TPagination pagination)
			throws Throwable {
		
		if(pagination==null)
			pagination = decorator.gettPaginator().getPagination();
		
		final String id = UUID.randomUUID().toString();
		TPaginationProcess process = new TPaginationProcess(modelClass, this.serviceName) {};
		ChangeListener<State> prcl = (arg0, arg1, arg2) -> {
			
			if(arg2.equals(State.SUCCEEDED)){
				
				TResult<Map<String, Object>> resultados = (TResult<Map<String, Object>>) process.getValue();
				
				if(resultados != null) {
				
					Map<String, Object> result =  resultados.getValue();
					//ObservableList<M> models = getModels();
					if(this.searchResultList==null) {
						this.searchResultList = TFXCollections.iTObservableList();
						//setModelViewList(models);
					}else
						this.searchResultList.clear();
					
					for(E e : (List<E>) result.get("list")){
						try {
							M model = (M) this.paginatorModelViewClass.getConstructor(modelClass).newInstance(e);
							this.searchResultList.add(model);
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
					
					processPagination((long)result.get("total"));
					getListenerRepository().removeListener(id);
					
					if(searchAction!=null){
						searchAction.runAfter(getPresenter());
					}
				}
			}
		};
		super.getListenerRepository().addListener(id, prcl);
		process.stateProperty().addListener(new WeakChangeListener(prcl));
		process.findAll((ITEntity) super.getModelView().getModel(), pagination);
		runProcess(process);
	}
	
	private void processPagination(Long totalRows) {
		this.decorator.gettPaginator().reload(totalRows);
	}
		
	/**
	 * Perform this action when clean button onAction is triggered.
	 * */
	public void cleanAction() {
		final TDynaPresenter<M> presenter = getPresenter();
		if(cleanAction==null || (cleanAction!=null && cleanAction.runBefore(presenter))){
			processClean();
		}
		if(cleanAction!=null)
			cleanAction.runAfter(presenter);
	}

	private void processClean() {
		try {
			super.removeAllListenerFromModelView();
			M model = (M) getModelViewClass().getConstructor(modelClass).newInstance(modelClass.newInstance());
			setModelView(model);
			showForm(TViewMode.EDIT);
			this.searchResultList.clear();
			decorator.gettPaginator().reload(0);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}
	

	public TDynaPresenter getModulePresenter() {
				
		ITModule module = getPresenter().getModule() ;
		
		final TDynaPresenter presenter = (TDynaPresenter) TedrosAppManager.getInstance()
				.getModuleContext(module).getCurrentViewContext().getPresenter();
		
		if(presenter==null)
			throw new RuntimeException("The ITPresenter was not present in TViewContext while build the "+module.getClass().getSimpleName()+" module");
		return presenter;
	}
	
	/**
	 * Perform this action when close button onAction is triggered.
	 * */
	public void closeAction() {
		final TDynaPresenter<M> presenter = getPresenter();
		if(closeAction==null || (closeAction!=null && closeAction.runBefore(presenter))){
			try{
				closeModal();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		if(closeAction!=null)
			closeAction.runAfter(presenter);
	}
	
	/**
	 * Perform this action when cancel button onAction is triggered.
	 * */
	public void cancelAction() {
		final TDynaPresenter<M> presenter = getPresenter();
		if(cancelAction==null || (cancelAction!=null && cancelAction.runBefore(presenter))){
			try{
				processClean();
				getModels().clear();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		if(cancelAction!=null)
			cancelAction.runAfter(presenter);
	}

	
	private void closeModal() {
		super.invalidate();
		TedrosAppManager.getInstance()
		.getModuleContext((TModule)TedrosContext.getView()).getCurrentViewContext()
		.getPresenter().getView().tHideModal();
	}
	
	
	/**
	 * Build and show the form
	 * */
	public void showForm(TViewMode mode) {
		
		setViewMode(mode);
		
		ITModelForm<M> form = (mode!=null) 
				? buildForm(mode)
						:  buildForm(TViewMode.EDIT);
		setForm(form);
	}
	

	public synchronized void removeFormListChangeListener() {
		if(formListChangeListener!=null)
			getView().gettFormSpace().getChildren().removeListener(formListChangeListener);
	}
	
	public synchronized void addFormListChangeListener() {
		if(formListChangeListener!=null && !getView().gettFormSpace().getChildren().contains(formListChangeListener)){
			getView().gettFormSpace().getChildren().addListener(formListChangeListener);
		}
	}



	/**
	 * @return the formListChangeListener
	 */
	public ListChangeListener<Node> getFormListChangeListener() {
		return formListChangeListener;
	}



	/**
	 * @param formListChangeListener the formListChangeListener to set
	 */
	public void setFormListChangeListener(ListChangeListener<Node> formListChangeListener) {
		this.formListChangeListener = formListChangeListener;
	}

	/**
	 * @return the cleanAction
	 */
	public TPresenterAction<TDynaPresenter<M>> getCleanAction() {
		return cleanAction;
	}

	/**
	 * @param cleanAction the cleanAction to set
	 */
	public void setCleanAction(TPresenterAction<TDynaPresenter<M>> cleanAction) {
		this.cleanAction = cleanAction;
	}

	/**
	 * @return the searchAction
	 */
	public TPresenterAction<TDynaPresenter<M>> getSearchAction() {
		return searchAction;
	}

	/**
	 * @param searchAction the searchAction to set
	 */
	public void setSearchAction(TPresenterAction<TDynaPresenter<M>> searchAction) {
		this.searchAction = searchAction;
	}

	/**
	 * @return the selectedItemAction
	 */
	public TPresenterAction<TDynaPresenter<M>> getSelectedItemAction() {
		return selectedItemAction;
	}

	/**
	 * @param selectedItemAction the selectedItemAction to set
	 */
	public void setSelectedItemAction(TPresenterAction<TDynaPresenter<M>> selectedItemAction) {
		this.selectedItemAction = selectedItemAction;
	}

	/**
	 * @return the cancelAction
	 */
	public TPresenterAction<TDynaPresenter<M>> getCancelAction() {
		return cancelAction;
	}

	/**
	 * @param cancelAction the cancelAction to set
	 */
	public void setCancelAction(TPresenterAction<TDynaPresenter<M>> cancelAction) {
		this.cancelAction = cancelAction;
	}

	/**
	 * @return the modelClass
	 */
	public Class<E> getModelClass() {
		return modelClass;
	}



	/**
	 * @param modelClass the modelClass to set
	 */
	public void setModelClass(Class<E> modelClass) {
		this.modelClass = modelClass;
	}




	/**
	 * @return the decorator
	 */
	public TDynaViewSelectionBaseDecorator<M> getDecorator() {
		return decorator;
	}



	/**
	 * @param decorator the decorator to set
	 */
	public void setDecorator(TDynaViewSelectionBaseDecorator<M> decorator) {
		this.decorator = decorator;
	}
	
	
	
	
}
