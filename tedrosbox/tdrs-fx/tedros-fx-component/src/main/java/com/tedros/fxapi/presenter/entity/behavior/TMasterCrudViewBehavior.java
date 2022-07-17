package com.tedros.fxapi.presenter.entity.behavior;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.model.ITModelView;
import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.TState;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TListViewPresenter;
import com.tedros.fxapi.annotation.view.TPaginator;
import com.tedros.fxapi.control.action.TPresenterAction;
import com.tedros.fxapi.exception.TException;
import com.tedros.fxapi.modal.TMessage;
import com.tedros.fxapi.modal.TMessageBox;
import com.tedros.fxapi.modal.TMessageType;
import com.tedros.fxapi.presenter.behavior.TActionType;
import com.tedros.fxapi.presenter.decorator.ITListViewDecorator;
import com.tedros.fxapi.presenter.dynamic.behavior.TDynaViewCrudBaseBehavior;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.fxapi.presenter.paginator.TPagination;
import com.tedros.fxapi.process.TEntityProcess;
import com.tedros.fxapi.process.TPaginationProcess;
import com.tedros.fxapi.process.TProcess;
import com.tedros.fxapi.util.TEntityListViewCallback;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker.State;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

@SuppressWarnings({ "rawtypes" })
public class TMasterCrudViewBehavior<M extends TEntityModelView<E>, E extends ITEntity>
extends TDynaViewCrudBaseBehavior<M, E> {
	
	private String paginatorServiceName;
	private String searchFieldName;
	private TPaginator tPagAnn = null;
	protected ITListViewDecorator<M> decorator;
		
	@SuppressWarnings("unchecked")
	@Override
	public void load() {
		super.load();
		this.decorator = (ITListViewDecorator<M>) getPresenter().getDecorator();
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
			
			configModesRadio();
			configCancelButton();
			
			configListViewChangeListener();
			configListViewCallBack();
			
			TListViewPresenter tAnnotation = getPresenter().getModelViewClass().getAnnotation(TListViewPresenter.class);
			if(tAnnotation!=null){
				tPagAnn = tAnnotation.paginator();
				if(tPagAnn.show()) {
					this.paginatorServiceName = tPagAnn.serviceName();
					this.searchFieldName = tPagAnn.searchFieldName();
					this.decorator.gettPaginator().setSearchFieldName(searchFieldName);
					try {
						if(tPagAnn.showSearchField() && StringUtils.isBlank(this.searchFieldName))
							throw new TException("The property searchFieldName in TPaginator annotation is required when showSearhField is true.");
					}catch(TException e){
						getView().tShowModal(new TMessageBox(e), true);
						e.printStackTrace();
					}
				}
			}
			if(!isUserNotAuthorized(TAuthorizationType.VIEW_ACCESS))
				loadModels();
			else
				setViewStateAsReady();
		}
	
	@SuppressWarnings("unchecked")
	public void loadModels() {
		try{
			if(isPaginateEnabled()) {
				TPaginationProcess process = new TPaginationProcess(super.getEntityClass(), this.paginatorServiceName) {};
				ChangeListener<State> prcl = (arg0, arg1, arg2) -> {
					
					if(arg2.equals(State.SUCCEEDED)){
						
						TResult<Map<String, Object>> resultados = (TResult<Map<String, Object>>) process.getValue();
						
						if(resultados != null) {
							
							if(resultados.getState().equals(TState.SUCCESS)) {
						
								Map<String, Object> result =  resultados.getValue();
								ObservableList<M> models = getModels();
								if(models==null) {
									models = FXCollections.observableArrayList();
									setModelViewList(models);
								}
								
								for(E e : (List<E>) result.get("list")){
									try {
										M model = (M) getModelViewClass().getConstructor(getEntityClass()).newInstance(e);
										models.add(model);
									} catch (Exception e1) {
										e1.printStackTrace();
									}
								}
								loadListView();
								processPagination((long)result.get("total"));
							
							}else {
								String msg = resultados.getMessage();
								System.out.println(msg);
								switch(resultados.getState()) {
									case ERROR:
										addMessage(new TMessage(TMessageType.ERROR, msg));
										break;
									default:
										addMessage(new TMessage(TMessageType.WARNING, msg));
										break;
								}
							}
							getListenerRepository().remove("processloadlistviewCL");
						}
					}
				};
				
				super.getListenerRepository().add("processloadlistviewCL", prcl);
				
				try {
					E entity = super.getEntityClass().newInstance();
					process.pageAll(entity, this.decorator.gettPaginator().getPagination());
					bindProgressIndicator(process);
					process.stateProperty().addListener(new WeakChangeListener(prcl));
					process.startProcess();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			} else {
				// processo para listagem dos models
				final TEntityProcess<E> process  = (TEntityProcess<E>) createEntityProcess();
				if(process!=null){
					
					ChangeListener<State> prcl = (arg0, arg1, arg2) -> {
						
						if(arg2.equals(State.SUCCEEDED)){
							
							List<?> resultados = process.getValue();
							
							if(!resultados.isEmpty()) {
								List<TMessage> msgs = new ArrayList<>();
								for(Object obj : resultados) {
									//TResult result = (TResult<?>) resultados.get(0);
									TResult result = (TResult<?>) obj;
									if(result.getState().equals(TState.SUCCESS)) {
										if(result.getValue()!=null && result.getValue() instanceof List){
											ObservableList<M> models = getModels();
											if(models==null) {
												models = FXCollections.observableArrayList();
												setModelViewList(models);
											}
											for(E e : (List<E>) result.getValue()){
												try {
													M model = (M) getModelViewClass().getConstructor(getEntityClass()).newInstance(e);
													models.add(model);
												} catch (Exception e1) {
													e1.printStackTrace();
												}
											}
										}
									}else {
										String msg = result.getMessage();
										System.out.println(msg);
										switch(result.getState()) {
											case ERROR:
												msgs.add(new TMessage(TMessageType.ERROR, msg));
												break;
											default:
												msgs.add(new TMessage(TMessageType.WARNING, msg));
												break;
										}
									}
								}
								addMessage(msgs);
							}else{
								ObservableList<M> models = getModels();
								if(models==null) {
									models = FXCollections.observableArrayList();
									setModelViewList(models);
								}
							}
							loadListView();
							getListenerRepository().remove("processloadlistviewCL");
						}
					};
					
					super.getListenerRepository().add("processloadlistviewCL", prcl);
					
					process.list();
					bindProgressIndicator(process);
					process.stateProperty().addListener(new WeakChangeListener(prcl));
					process.startProcess();
				}else{
					System.err.println("\nWARNING: Cannot create a process for the "+getModelViewClass().getSimpleName()+", check the @TCrudForm(processClass,serviceName) properties");
					loadListView();
				}
			
			} 
			
		}catch(Throwable e){
			getView().tShowModal(new TMessageBox(e), true);
			e.printStackTrace();
		}
		
	}

	/**
	 * @return
	 */
	private boolean isPaginateEnabled() {
		return tPagAnn!=null && tPagAnn.show();
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
	
	private void processPagination(Long totalRows) {
		this.decorator.gettPaginator().reload(totalRows);
	}
	
	@SuppressWarnings("unchecked")
	protected void loadListView() {
		final ObservableList<M> models = getModels();
		final ListView<M> listView = this.decorator.gettListView();
		listView.setItems((ObservableList<M>) (models==null ? FXCollections.observableArrayList() : models));
		super.getListenerRepository().remove("processloadlistviewCL");
		final M mv = super.getModelView(); //getPresenter().getModelView();
		processModelView(mv);
		
		if(this.decorator.gettPaginator()!=null) {
			ChangeListener<TPagination> chl = (a0, a1, a2) -> {
				try {
					paginate(a2);
				} catch (Throwable e) {
					e.printStackTrace();
				}
			};
			this.decorator.gettPaginator().paginationProperty().addListener(chl);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public void paginate(TPagination pagination) throws TException {
		final String chlId = UUID.randomUUID().toString();
		TPaginationProcess<E> process = new TPaginationProcess<E>(super.getEntityClass(), this.paginatorServiceName) {};
		ChangeListener<State> prcl = (arg0, arg1, arg2) -> {
			
			if(arg2.equals(State.SUCCEEDED)){
				
				TResult<Map<String, Object>> resultados = (TResult<Map<String, Object>>) process.getValue();
				
				if(resultados != null) {
					if(resultados.getState().equals(TState.SUCCESS)) {
						Map<String, Object> result =  resultados.getValue();
						ObservableList<M> models = getModels();
						models.clear();
						
						for(E e : (List<E>) result.get("list")){
							try {
								M model = (M) getModelViewClass().getConstructor(getEntityClass()).newInstance(e);
								models.add(model);
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}
						processPagination((long)result.get("total"));
					}else {
						String msg = resultados.getMessage();
						System.out.println(msg);
						switch(resultados.getState()) {
							case ERROR:
								addMessage(new TMessage(TMessageType.ERROR, msg));
								break;
							default:
								addMessage(new TMessage(TMessageType.WARNING, msg));
								break;
						}
					}
					getListenerRepository().remove(chlId);
				}
			}
		};
		
		super.getListenerRepository().add(chlId, prcl);
		
		if(StringUtils.isNotBlank(pagination.getSearch())) {
			
			try {
				Class target = super.getEntityClass();
				E entity = super.getEntityClass().newInstance();
				Method setter = null;
				do {
					try {
						Field f = target.getDeclaredField(pagination.getSearchFieldName());
						setter = target.getMethod("set"+StringUtils.capitalize(pagination.getSearchFieldName()), f.getType());
						break;
					} catch (NoSuchFieldException | SecurityException e) {
						target = target.getSuperclass();
					} catch (NoSuchMethodException e) {
						break;
					}
				}while(target != Object.class);
				
				if(setter==null)
					throw new TException("The setter method was not found for the field "+this.searchFieldName+" declared in TPaginator annotation.");
				
				setter.invoke(entity, pagination.getSearch().toUpperCase());
				process.findAll(entity, pagination);
				
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				throw new TException("An error occurred while trying paginate ", e);
			}
			
			
		}else {
			try {
				E entity = super.getEntityClass().newInstance();
				process.pageAll(entity, pagination);
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
			
		}
		bindProgressIndicator(process);
		process.stateProperty().addListener(new WeakChangeListener(prcl));
		process.startProcess();
	}

	/**
	 * @param process
	 */
	private void bindProgressIndicator(TProcess process) {
		this.decorator.gettListViewProgressIndicator().bind(process.runningProperty());
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
	
	@SuppressWarnings("unchecked")
	@Override
	public void loadModelView(ITModelView m) {
		M mv = (M) m;
		E e = (E) mv.getModel();
		if(e.isNew()) {
			this.addInListView((M) m);
			this.processListViewSelectedItem((M) m);
		}else{
			if(this.isPaginateEnabled()) {
				String orderBy = this.decorator.gettPaginator().getOrderBy();
				boolean orderAsc = this.decorator.gettPaginator().getOrderAsc();
				int totalRows = this.decorator.gettPaginator().getTotalRows();
				try {
					this.paginateLoadedModel(e, new TPagination(null, orderBy, orderAsc, 0, totalRows));
				} catch (TException e1) {
					e1.printStackTrace();
				}
			}else {
				final ListView<M> list = this.decorator.gettListView();
				Optional<M>  op = list.getItems().stream().filter(p->{
					return !p.getEntity().isNew() && p.getEntity().getId().equals(e.getId());
				}).findFirst();
				if(op.isPresent()) {
					list.selectionModelProperty().get().select(mv);
					this.processListViewSelectedItem((M) m);
				}else {
					this.addInListView((M) m);
					this.processListViewSelectedItem((M) m);
				}
			}
		}
	}
	

	@SuppressWarnings("unchecked")
	public void paginateLoadedModel(E entity, TPagination pagination) throws TException {
		final String chlId = UUID.randomUUID().toString();
		TPaginationProcess<E> process = new TPaginationProcess<E>(super.getEntityClass(), this.paginatorServiceName) {};
		ChangeListener<State> prcl = (arg0, arg1, arg2) -> {
			
			if(arg2.equals(State.SUCCEEDED)){
				
				TResult<Map<String, Object>> resultados = (TResult<Map<String, Object>>) process.getValue();
				
				if(resultados != null) {
					if(resultados.getState().equals(TState.SUCCESS)) {
						Map<String, Object> result =  resultados.getValue();
						ObservableList<M> models = getModels();
						models.clear();
						
						for(E e : (List<E>) result.get("list")){
							try {
								M model = (M) getModelViewClass().getConstructor(getEntityClass()).newInstance(e);
								models.add(model);
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}
						processPagination((long)result.get("total"));
						if(models.size()==1){
							M mv = models.get(0);
							final ListView<M> list = this.decorator.gettListView();
							list.selectionModelProperty().get().select(mv);
							this.processListViewSelectedItem(mv);
						}
					}else {
						String msg = resultados.getMessage();
						System.out.println(msg);
						switch(resultados.getState()) {
							case ERROR:
								addMessage(new TMessage(TMessageType.ERROR, msg));
								break;
							default:
								addMessage(new TMessage(TMessageType.WARNING, msg));
								break;
						}
					}
					getListenerRepository().remove(chlId);
				}
			}
		};
		
		super.getListenerRepository().add(chlId, prcl);
		process.findAll(entity, pagination);
		bindProgressIndicator(process);
		process.stateProperty().addListener(new WeakChangeListener(prcl));
		process.startProcess();
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
	public boolean invalidate() {
		if(this.decorator!=null && this.decorator.gettPaginator()!=null)
			this.decorator.gettPaginator().invalidate();
		return super.invalidate();
	}
		
}
