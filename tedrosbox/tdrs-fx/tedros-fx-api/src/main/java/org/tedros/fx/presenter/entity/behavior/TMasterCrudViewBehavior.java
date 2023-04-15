package org.tedros.fx.presenter.entity.behavior;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.message.TMessage;
import org.tedros.core.message.TMessageType;
import org.tedros.core.model.TModelViewUtil;
import org.tedros.fx.TFxKey;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TListViewPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.view.TPaginator;
import org.tedros.fx.control.action.TPresenterAction;
import org.tedros.fx.exception.TException;
import org.tedros.fx.modal.TMessageBox;
import org.tedros.fx.presenter.behavior.TActionType;
import org.tedros.fx.presenter.decorator.ITListViewDecorator;
import org.tedros.fx.presenter.dynamic.behavior.TDynaViewCrudBaseBehavior;
import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.fx.presenter.paginator.TPagination;
import org.tedros.fx.process.TEntityProcess;
import org.tedros.fx.process.TPaginationProcess;
import org.tedros.fx.process.TProcess;
import org.tedros.fx.util.TEntityListViewCallback;
import org.tedros.server.entity.ITEntity;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker.State;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.util.Callback;
/**
 * The behavior of the master detail view. 
 * This behavior can be applied on master 
 * entities with detail entities. A ListView
 * with pagination is created to list the 
 * database result. It can be set using the 
 * {@link TListViewPresenter} annotation on 
 * the TEntityModelView. 
 * For entity processing, use {@link TEjbService} or
 * {@link org.tedros.fx.annotation.process.TEntityProcess} 
 * on TEntityModelView.
 * @author Davis Gordon
 *
 * @param <M>
 * @param <E>
 */
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
	
	/**
	 * Initialize the behavior
	 */
	@SuppressWarnings("unchecked")
	public void initialize() {
		
		if(getModels()==null)
			super.setModelViewList(FXCollections.observableArrayList());
		
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
		configListViewContextMenu();
		
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
		
		if(this.decorator.gettAiAssistant()!=null) {
			this.decorator.gettAiAssistant().settView(getView());
			this.decorator.gettAiAssistant().settTargetModel(super.modelViewProperty());
			this.decorator.gettAiAssistant().settModels(getModels());
		}
		
		if(!isUserNotAuthorized(TAuthorizationType.VIEW_ACCESS))
			loadModels();
		else
			setViewStateAsReady();
	}
	
	/**
	 * Retrieve entities from the server 
	 * and load the models property in 
	 * the TBehavior superclass
	 */
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
								models.clear();
								TModelViewUtil<M,E> mvu = new TModelViewUtil<>(getModelViewClass(), getEntityClass());
								for(E e : (List<E>) result.get("list")){
									M model = mvu.convertToModelView(e);
									models.add(model);
								}
								processPagination((long)result.get("total"));
								loadListView();
							}else{
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
								setViewStateAsReady();
							}
							getListenerRepository().remove("processloadlistviewCL");
						}
					}
				};
				
				super.getListenerRepository().add("processloadlistviewCL", prcl);
				
				TModelViewUtil<M,E> mvu = new TModelViewUtil<>(getModelViewClass(), getEntityClass());
				E entity = mvu.getNewModelInstance();
				process.pageAll(entity, this.decorator.gettPaginator().getPagination());
				bindProgressIndicator(process);
				process.stateProperty().addListener(new WeakChangeListener(prcl));
				process.startProcess();
				
			} else {
				// list model process
				final TEntityProcess<E> process  = (TEntityProcess<E>) createEntityProcess();
				if(process!=null){
					ChangeListener<State> prcl = (arg0, arg1, arg2) -> {
						
						if(arg2.equals(State.SUCCEEDED)){
							
							List<?> resultados = process.getValue();
							
							if(!resultados.isEmpty()) {
								List<TMessage> msgs = new ArrayList<>();
								for(Object obj : resultados) {
									TResult result = (TResult<?>) obj;
									if(result.getState().equals(TState.SUCCESS)) {
										if(result.getValue()!=null && result.getValue() instanceof List){
											ObservableList<M> models = getModels();
											models.clear();
											TModelViewUtil<M,E> mvu = new TModelViewUtil<>(getModelViewClass(), getEntityClass());
											for(E e : (List<E>) result.getValue()){
												M model = mvu.convertToModelView(e);
												models.add(model);
											}
										}
									}else{
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
	 * Checks if paging is enabled.
	 * @return
	 */
	private boolean isPaginateEnabled() {
		return tPagAnn!=null && tPagAnn.show();
	}

	/**
	 * Config the cancel action.
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
	
	private void processPagination(Long totalRows) {
		this.decorator.gettPaginator().reload(totalRows);
	}
	
	/**
	 * Loads the ListView with the models property 
	 * in the TBehavior superclass.
	 */
	@SuppressWarnings("unchecked")
	protected void loadListView() {
		final ObservableList<M> models = getModels();
		final ListView<M> listView = this.decorator.gettListView();
		listView.setItems((ObservableList<M>) (models==null ? FXCollections.observableArrayList() : models));
		super.getListenerRepository().remove("processloadlistviewCL");
		final M mv = super.getModelView(); 
		processModelView(mv);
	}
	/**
	 * Calls the server's paging service
	 * @param pagination
	 * @throws TException
	 */
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
	
	private void configListViewContextMenu()  {
		final ListView<M> listView = this.decorator.gettListView();
		ContextMenu ctx = new ContextMenu();
		MenuItem reload = new MenuItem(iEngine.getString(TFxKey.BUTTON_RELOAD));
		reload.setOnAction(e->{
			try {
				if(this.isPaginateEnabled()) {
					TPagination p = this.decorator.gettPaginator().paginationProperty().getValue();
					if(p!=null)
						this.paginate(p);
				}else {
					this.loadModels();
				}
			} catch (TException e1) {
				e1.printStackTrace();
			}
				
		});
		
		ctx.getItems().add(reload);
		listView.setContextMenu(ctx);
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
	
	/**
	 * Config the ListView and pagination listener
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
		
		if(this.decorator.gettPaginator()!=null) {
			ChangeListener<TPagination> chl0 = (a0, a1, a2) -> {
				try {
					paginate(a2);
				} catch (Throwable e) {
					e.printStackTrace();
				}
			};
			super.getListenerRepository().add("listviewpaginatorCL", chl0);
			this.decorator.gettPaginator()
			.paginationProperty().addListener(new WeakChangeListener<>(chl0));
		}
		
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
	
	@Override
	public void loadModelViewList(ObservableList<M> models) {
		super.loadModelViewList(models);
		this.processPagination((long) models.size());
	}
	
	@Override
	public void loadModelView(M m) {
		M mv = m;
		E e = mv.getModel();
		if(e.isNew()) {
			this.addInListView(m);
			this.processListViewSelectedItem(m);
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
					this.processListViewSelectedItem(m);
				}else {
					this.addInListView(m);
					this.processListViewSelectedItem(m);
				}
			}
		}
	}
	
	/**
	 * Find and paginate all entities on the server 
	 * using the entity provided as an example.
	 * @param entity
	 * @param pagination
	 * @throws TException
	 */
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

	
	/**
	 * Remove the selected model from the ListView
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
	public boolean invalidate() {
		if(this.decorator!=null && this.decorator.gettPaginator()!=null)
			this.decorator.gettPaginator().invalidate();
		if(this.decorator!=null && this.decorator.gettAiAssistant()!=null)
			this.decorator.gettAiAssistant().tInvalidate();;
		return super.invalidate();
	}
		
}
