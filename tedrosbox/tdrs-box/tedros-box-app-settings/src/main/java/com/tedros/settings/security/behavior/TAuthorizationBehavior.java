package com.tedros.settings.security.behavior;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.tedros.core.TInternationalizationEngine;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.core.context.TedrosContext;
import com.tedros.core.security.model.TAuthorization;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TListViewPresenter;
import com.tedros.fxapi.annotation.view.TPaginator;
import com.tedros.fxapi.control.action.TPresenterAction;
import com.tedros.fxapi.exception.TException;
import com.tedros.fxapi.exception.TProcessException;
import com.tedros.fxapi.modal.TMessageBox;
import com.tedros.fxapi.presenter.behavior.TActionType;
import com.tedros.fxapi.presenter.dynamic.behavior.TDynaViewCrudBaseBehavior;
import com.tedros.fxapi.presenter.entity.decorator.TMasterCrudViewDecorator;
import com.tedros.fxapi.presenter.paginator.TPagination;
import com.tedros.fxapi.process.TPaginationProcess;
import com.tedros.fxapi.util.TEntityListViewCallback;
import com.tedros.settings.security.model.TAuthorizationModelView;
import com.tedros.settings.security.process.TAuthorizationProcess;

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
public class TAuthorizationBehavior
extends TDynaViewCrudBaseBehavior<TAuthorizationModelView, TAuthorization> {
	
	private String paginatorServiceName;
	private String searchFieldName;
	
	protected TMasterCrudViewDecorator decorator;
		
	@Override
	public void load() {
		super.load();
		this.decorator = (TMasterCrudViewDecorator) getPresenter().getDecorator();
		initialize();
	}
		
	@SuppressWarnings("unchecked")
	public void initialize() {
		try{
			
			configCancelAction();
			configColapseButton();
			configSaveButton();
			configNewButton();
			
			configModesRadio();
			configCancelButton();
			
			configListViewChangeListener();
			configListViewCallBack();
			
			addAction(new TPresenterAction(TActionType.NEW) {
				@Override
				public boolean runBefore() {
					List<TAuthorization> authorizations = new ArrayList<>();
					TInternationalizationEngine iEngine = TInternationalizationEngine.getInstance(null);
					for (Class clazz : TedrosContext.getClassesAnnotatedWith(TSecurity.class) ) {
						try {
							
							TSecurity tSecurity = (TSecurity) clazz.getAnnotation(TSecurity.class);
								
							for(TAuthorizationType authorizationType : tSecurity.allowedAccesses()){
								
								TAuthorization authorization = new TAuthorization();;
								authorization.setType(authorizationType.name());
								authorization.setSecurityId(tSecurity.id());
								authorization.setEnabled("Sim");
								
								authorization.setAppName(iEngine.getString(tSecurity.appName()));
								
								if(StringUtils.isNotBlank(tSecurity.moduleName()))
									authorization.setModuleName(iEngine.getString(tSecurity.moduleName()));
								
								if(StringUtils.isNotBlank(tSecurity.viewName()))
									authorization.setViewName(iEngine.getString(tSecurity.viewName()));
								
								authorization.setTypeDescription(iEngine.getString(TAuthorizationType.getFromName(authorizationType.name()).getValue()));
								authorizations.add(authorization);
							}
							
						}catch(Exception e){
							e.printStackTrace();
						}
					}
					
					try {
						TAuthorizationProcess process = new TAuthorizationProcess();
						ChangeListener<State> prcl = (arg0, arg1, arg2) -> {
							
							if(arg2.equals(State.SUCCEEDED)){
								
								List<TResult<TAuthorization>> lst =  process.getValue();
								
								if(lst != null) {
									TResult res = lst.get(0);
									if(res.getResult().equals(EnumResult.SUCESS)) {
										List<String> msg = (List<String>) res.getValue();
										if(!msg.isEmpty()) {
											TMessageBox tMessageBox = new TMessageBox(msg);
											getView().tShowModal(tMessageBox, true);
										}else{
											TMessageBox tMessageBox = new TMessageBox(res.getMessage());
											getView().tShowModal(tMessageBox, true);
										}
										TPagination pag = new TPagination(null, "appName", true, 0, 50);
										try {
											paginate(pag);
										} catch (TException e) {
											e.printStackTrace();
										}
									}
								}
							}
						};
						process.stateProperty().addListener(prcl);
						process.process(authorizations);
						runProcess(process);
						
					} catch (TProcessException e) {
						e.printStackTrace();
					}
					
					return false;
				}

				@Override
				public void runAfter() {
				}
			});
			
			TPaginator tPagAnn = null;
			TListViewPresenter tAnnotation = getPresenter().getModelViewClass().getAnnotation(TListViewPresenter.class);
			
			tPagAnn = tAnnotation.paginator();
			this.paginatorServiceName = tPagAnn.serviceName();
			this.searchFieldName = tPagAnn.searchFieldName();
			if(tPagAnn.showSearchField() && StringUtils.isBlank(this.searchFieldName))
				throw new TException("The property searchFieldName in TPaginator annotation is required when showSearhField is true.");
			
			
			TPaginationProcess process = new TPaginationProcess(super.getEntityClass(), this.paginatorServiceName) {};
			ChangeListener<State> prcl = (arg0, arg1, arg2) -> {
				
				if(arg2.equals(State.SUCCEEDED)){
					
					TResult<Map<String, Object>> resultados = (TResult<Map<String, Object>>) process.getValue();
					
					if(resultados != null) {
					
						Map<String, Object> result =  resultados.getValue();
						ObservableList<TAuthorizationModelView> models = getModels();
						if(models==null) {
							models = FXCollections.observableArrayList();
							setModelViewList(models);
						}
						
						for(TAuthorization e : (List<TAuthorization>) result.get("list")){
							try {
								TAuthorizationModelView model = (TAuthorizationModelView) getModelViewClass().getConstructor(getEntityClass()).newInstance(e);
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
				TAuthorization entity = super.getEntityClass().newInstance();
				process.pageAll(entity, this.decorator.gettPaginator().getPagination());
				process.stateProperty().addListener(new WeakChangeListener(prcl));
				process.startProcess();
			} catch (InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			
		}catch(Throwable e){
			getView().tShowModal(new TMessageBox(e), true);
			e.printStackTrace();
		}
		
	}

	protected void configCancelAction() {
		addAction(new TPresenterAction(TActionType.CANCEL) {

			@Override
			public boolean runBefore() {
				return true;
			}

			@Override
			public void runAfter() {
				final ListView<TAuthorizationModelView> listView = decorator.gettListView();
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
		final ObservableList<TAuthorizationModelView> models = getModels();
		final ListView<TAuthorizationModelView> listView = this.decorator.gettListView();
		listView.setItems((ObservableList<TAuthorizationModelView>) (models==null ? FXCollections.observableArrayList() : models));
		listView.setEditable(true);
		listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		super.getListenerRepository().remove("processloadlistviewCL");
		final TAuthorizationModelView mv = getPresenter().getModelView();
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
		TPaginationProcess<TAuthorization> process = new TPaginationProcess<TAuthorization>(super.getEntityClass(), this.paginatorServiceName) {};
		ChangeListener<State> prcl = (arg0, arg1, arg2) -> {
			
			if(arg2.equals(State.SUCCEEDED)){
				
				TResult<Map<String, Object>> resultados = (TResult<Map<String, Object>>) process.getValue();
				
				if(resultados != null) {
				
					Map<String, Object> result =  resultados.getValue();
					ObservableList<TAuthorizationModelView> models = getModels();
					models.clear();
					
					for(TAuthorization e : (List<TAuthorization>) result.get("list")){
						try {
							TAuthorizationModelView model = (TAuthorizationModelView) getModelViewClass().getConstructor(getEntityClass()).newInstance(e);
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
				TAuthorization entity = super.getEntityClass().newInstance();
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
				TAuthorization entity = super.getEntityClass().newInstance();
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
			Callback<ListView<TAuthorizationModelView>, ListCell<TAuthorizationModelView>> callBack = (Callback<ListView<TAuthorizationModelView>, ListCell<TAuthorizationModelView>>) 
					((tBehavior!=null) 
							? tBehavior.listViewCallBack().newInstance() 
									: new TEntityListViewCallback<TAuthorizationModelView>());
			
			final ListView<TAuthorizationModelView> listView = this.decorator.gettListView();
			listView.setCellFactory(callBack);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	@SuppressWarnings("unchecked")
	protected void configListViewChangeListener() {
		
		ChangeListener<TAuthorizationModelView> chl = (a0, old_, new_) -> {
			processListViewSelectedItem(new_);
		};
		
		super.getListenerRepository().add("listviewselecteditemviewCL", chl);
		
		this.decorator.gettListView()
		.getSelectionModel()
		.selectedItemProperty()
		.addListener(new WeakChangeListener<>(chl));
		
	}

	protected void processListViewSelectedItem(TAuthorizationModelView new_) {
		if(new_==null) {
			setModelView(null);
			showListView();
		}else{
			selectedItemAction(new_);
			hideListView();
		}
	}
	
	public void remove() {
		final ListView<TAuthorizationModelView> listView = this.decorator.gettListView();
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
	
	
	@Override
	public boolean invalidate() {
		if(this.decorator!=null && this.decorator.gettPaginator()!=null)
			this.decorator.gettPaginator().invalidate();
		return super.invalidate();
	}

	

	@Override
	public boolean processNewEntityBeforeBuildForm(TAuthorizationModelView model) {
		// TODO Auto-generated method stub
		return false;
	}
		
}
