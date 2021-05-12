package com.tedros.fxapi.presenter.entity.behavior;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.ejb.base.result.TResult;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TListViewPresenter;
import com.tedros.fxapi.annotation.view.TPaginator;
import com.tedros.fxapi.control.action.TPresenterAction;
import com.tedros.fxapi.exception.TException;
import com.tedros.fxapi.modal.TMessageBox;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.entity.decorator.TMasterCrudViewDecorator;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.fxapi.presenter.paginator.TPagination;
import com.tedros.fxapi.process.TEntityProcess;
import com.tedros.fxapi.process.TPaginationProcess;
import com.tedros.fxapi.util.TEntityListViewCallback;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker.State;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.util.Callback;

@SuppressWarnings({ "rawtypes" })
public class TMasterCrudViewBehavior<M extends TEntityModelView, E extends ITEntity>
extends com.tedros.fxapi.presenter.dynamic.behavior.TDynaViewCrudBaseBehavior<M, E> {
	
	private String paginatorServiceName;
	private String searchFieldName;
	private TPaginator tPagAnn = null;
	protected TMasterCrudViewDecorator<M> decorator;
		
	@Override
	public void load() {
		super.load();
		this.decorator = (TMasterCrudViewDecorator<M>) getPresenter().getDecorator();
		initialize();
	}
		
	public void initialize() {
		
			configCancelAction();
			configColapseButton();
			configNewButton();
			configSaveButton();
			configDeleteButton();
			configImportButton();
			
			configModesRadio();
			configCancelButton();
			
			configListViewChangeListener();
			configListViewCallBack();
			
			
			TListViewPresenter tAnnotation = getPresenter().getModelViewClass().getAnnotation(TListViewPresenter.class);
			if(tAnnotation!=null){
				tPagAnn = tAnnotation.paginator();
				this.paginatorServiceName = tPagAnn.serviceName();
				this.searchFieldName = tPagAnn.searchFieldName();
				try {
					if(tPagAnn.showSearchField() && StringUtils.isBlank(this.searchFieldName))
						throw new TException("The property searchFieldName in TPaginator annotation is required when showSearhField is true.");
				}catch(TException e){
					getView().tShowModal(new TMessageBox(e), true);
					e.printStackTrace();
				}
			}
			
			loadModels();
		}
	
	@SuppressWarnings("unchecked")
	public void loadModels() {
		try{
			if(tPagAnn!=null && tPagAnn.show()) {
				TPaginationProcess process = new TPaginationProcess(super.getEntityClass(), this.paginatorServiceName) {};
				ChangeListener<State> prcl = (arg0, arg1, arg2) -> {
					
					if(arg2.equals(State.SUCCEEDED)){
						
						TResult<Map<String, Object>> resultados = (TResult<Map<String, Object>>) process.getValue();
						
						if(resultados != null) {
						
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
							
							loadListView();
							processPagination((long)result.get("total"));
							getListenerRepository().remove("processloadlistviewCL");
						}
					}
				};
				
				super.getListenerRepository().add("processloadlistviewCL", prcl);
				try {
					E entity = super.getEntityClass().newInstance();
					process.pageAll(entity, this.decorator.gettPaginator().getPagination());
					process.stateProperty().addListener(new WeakChangeListener(prcl));
					process.startProcess();
				} catch (InstantiationException | IllegalAccessException e) {
					// TODO Auto-generated catch block
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
							
								TResult result = (TResult<?>) resultados.get(0);
								if(result.getValue()!=null && result.getValue() instanceof List<?>){
									ObservableList<M> models = getModels();
									if(models==null) {
										models = FXCollections.observableArrayList();
										setModelViewList(models);
									}
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
								}
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

	protected void configCancelAction() {
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
	}
	
	private void processPagination(Long totalRows) {
		this.decorator.gettPaginator().reload(totalRows);
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
	private void paginate(TPagination pagination) throws TException {
		final String id = UUID.randomUUID().toString();
		TPaginationProcess<E> process = new TPaginationProcess<E>(super.getEntityClass(), this.paginatorServiceName) {};
		ChangeListener<State> prcl = (arg0, arg1, arg2) -> {
			
			if(arg2.equals(State.SUCCEEDED)){
				
				TResult<Map<String, Object>> resultados = (TResult<Map<String, Object>>) process.getValue();
				
				if(resultados != null) {
				
					Map<String, Object> result =  resultados.getValue();
					ObservableList<M> models = getModels();
					models.clear();
					
					for(E e : (List<E>) result.get("list")){
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
					processPagination((long)result.get("total"));
					getListenerRepository().remove(id);
				}
			}
		};
		
		super.getListenerRepository().add(id, prcl);
		if(StringUtils.isNotBlank(pagination.getSearch())) {
			
			try {
				Class target = super.getEntityClass();
				E entity = super.getEntityClass().newInstance();
				Method setter = null;
				do {
					try {
						Field f = target.getDeclaredField(this.searchFieldName);
						setter = target.getMethod("set"+StringUtils.capitalize(searchFieldName), f.getType());
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
		
		process.stateProperty().addListener(new WeakChangeListener(prcl));
		process.startProcess();
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
		if(this.decorator!=null && this.decorator.gettPaginator()!=null)
			this.decorator.gettPaginator().invalidate();
		return super.invalidate();
	}
		
}
