package com.tedros.fxapi.presenter.dynamic.behavior;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.tedros.core.ITModule;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.context.TEntry;
import com.tedros.core.context.TEntryBuilder;
import com.tedros.core.context.TedrosAppManager;
import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.ejb.base.model.ITModel;
import com.tedros.ejb.base.service.TResult;
import com.tedros.ejb.base.service.TResult.EnumResult;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.process.TEjbService;
import com.tedros.fxapi.control.action.TPresenterAction;
import com.tedros.fxapi.domain.TViewMode;
import com.tedros.fxapi.exception.TException;
import com.tedros.fxapi.exception.TValidatorException;
import com.tedros.fxapi.form.ITForm;
import com.tedros.fxapi.form.ITModelForm;
import com.tedros.fxapi.layout.TBreadcrumbForm;
import com.tedros.fxapi.modal.TConfirmMessageBox;
import com.tedros.fxapi.modal.TMessageBox;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.dynamic.decorator.TDynaViewCrudBaseDecorator;
import com.tedros.fxapi.presenter.dynamic.view.TDynaView;
import com.tedros.fxapi.presenter.model.TModelView;
import com.tedros.fxapi.process.TEntityProcess;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPaneBuilder;


@SuppressWarnings("rawtypes")
public abstract class TDynaViewCrudBaseBehavior<M extends TModelView, E extends ITModel> 
extends TDynaViewSimpleBaseBehavior<M, E> {
	
	private ListChangeListener<Node> formListChangeListener;
	
	private ToggleGroup radioGroup;
	private BooleanProperty modeBtnDisableProperty;
	private BooleanProperty modeBtnVisibleProperty;

	private TPresenterAction<TDynaPresenter<M>> newAction;
	private TPresenterAction<TDynaPresenter<M>> saveAction;
	private TPresenterAction<TDynaPresenter<M>> deleteAction;
	private TPresenterAction<TDynaPresenter<M>> changeModeAction;
	private TPresenterAction<TDynaPresenter<M>> selectedItemAction;
	private TPresenterAction<TDynaPresenter<M>> editAction;
	private TPresenterAction<TDynaPresenter<M>> cancelAction;
	
	private boolean remoteMode;
	private String serviceName;
	private Class<E> entityClass;

	private Class<? extends TEntityProcess> entityProcessClass;
	
	private TDynaViewCrudBaseDecorator<M> decorator;

	private boolean saveAllModels;
	
	@SuppressWarnings("unchecked")
	public void load(){
		super.load();
		try{
			final TDynaPresenter<M> presenter = getPresenter();
			// check the crud settings
			
			final TEjbService tEjbService = getPresenter().getEjbServiceAnnotation();
			final com.tedros.fxapi.annotation.process.TEntityProcess tEntityProcess = getPresenter().getEntityProcessAnnotation();
			
			this.entityClass = (Class<E>) presenter.getModelClass();
			
			if(tEjbService!=null){
				this.remoteMode = tEjbService.remoteMode();
				this.serviceName = tEjbService.serviceName();
				this.entityClass = (Class<E>) tEjbService.entity();
			}
			// set the crud process
			if(tEntityProcess!=null){
				this.entityProcessClass = (Class<? extends TEntityProcess>) tEntityProcess.process();
				this.entityClass = (Class<E>) tEntityProcess.entity();
			}
			
			this.decorator = (TDynaViewCrudBaseDecorator<M>) presenter.getDecorator();
			
			//final TForm tForm = presenter.getFormAnnotation();
			final TBehavior tBehavior = presenter.getPresenterAnnotation().behavior();
			
			saveAllModels = tBehavior.saveAllModels();
			
			// set the custom behavior actions
			if(tBehavior.saveAction()!=TPresenterAction.class)
				saveAction = tBehavior.saveAction().newInstance();
			if(tBehavior.newAction()!=TPresenterAction.class)
				newAction = tBehavior.newAction().newInstance();
			if(tBehavior.deleteAction()!=TPresenterAction.class)
				deleteAction = tBehavior.deleteAction().newInstance();
			if(tBehavior.selectedItemAction()!=TPresenterAction.class)
				selectedItemAction = tBehavior.selectedItemAction().newInstance();
			if(tBehavior.editAction()!=TPresenterAction.class)
				editAction = tBehavior.editAction().newInstance();
			if(tBehavior.cancelAction()!=TPresenterAction.class)
				cancelAction = tBehavior.cancelAction().newInstance();
			if(tBehavior.changeModeAction()!=TPresenterAction.class)
				changeModeAction = tBehavior.changeModeAction().newInstance();
			
			// set the form settings
			//setFormName((tForm!=null) ? tForm.name() : "@TForm(name='SET A NAME')");
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Config the colapse button
	 * */
	public void configColapseButton() {
		final Button colapseButton = this.decorator.gettColapseButton();
		if(colapseButton!=null){
			colapseButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					colapseAction();
				}
			});
		}
	}
	
	/**
	 * Config the new button 
	 * */
	public void configNewButton() {
		if(isUserAuthorized(TAuthorizationType.NEW)){
			final Button newButton = this.decorator.gettNewButton();
			newButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					newAction();
				}
			});
		}
	}
	
	/**
	 * Config the save button
	 * */
	public void configSaveButton() {
		if(isUserAuthorized(TAuthorizationType.SAVE)){
			final Button saveButton = this.decorator.gettSaveButton();
			saveButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					saveAction();
				}
			});
		}
	}
	
	/**
	 * Config the delete button;
	 * */
	public void configDeleteButton() {
		if(isUserAuthorized(TAuthorizationType.DELETE)){
			final Button removeButton = this.decorator.gettDeleteButton();
			removeButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					deleteAction();
				}
			});
		}
	}
	
	/**
	 * Config the edit button;
	 * */
	public void configEditButton() {
		if(isUserAuthorized(TAuthorizationType.EDIT)){
			final Button editButton = this.decorator.gettEditButton();
			editButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					editAction();
				}
			});
		}
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
	
	/**
	 * Config the mode options tool bar
	 * */
	public void configModesRadio() {
		
		radioGroup = new ToggleGroup();
		final RadioButton editRadio = this.decorator.gettEditModeRadio();
		final RadioButton readRadio = this.decorator.gettReadModeRadio();
		
		editRadio.setToggleGroup(radioGroup);
		readRadio.setToggleGroup(radioGroup);
		
		radioGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> arg0, Toggle arg1, Toggle arg2) {
				changeModeAction();
			}
		});
		
		modeBtnDisableProperty = new SimpleBooleanProperty();
		modeBtnVisibleProperty = new SimpleBooleanProperty(true);
		
		editRadio.disableProperty().bindBidirectional(modeBtnDisableProperty);
		editRadio.visibleProperty().bindBidirectional(modeBtnVisibleProperty);
		readRadio.disableProperty().bindBidirectional(modeBtnDisableProperty);
		readRadio.visibleProperty().bindBidirectional(modeBtnVisibleProperty);
		
		if(isUserNotAuthorized(TAuthorizationType.EDIT))
			editRadio.setDisable(true);
		if(isUserNotAuthorized(TAuthorizationType.READ))
			readRadio.setDisable(true);
		
		if(isUserAuthorized(TAuthorizationType.EDIT))
			setViewMode(TViewMode.EDIT);
		else if(isUserAuthorized(TAuthorizationType.READ))
			setViewMode(TViewMode.READER);
		
		selectMode();
	}
	
	/**
	 * Select the Mode Radio button with the {@link TViewMode} setted.
	 * */
	public boolean selectMode(){
		
		if(!isRadioGroupBuilt())
			return false;
		
		TViewMode tMode = getViewMode();
		if(tMode==null){
			if(isUserAuthorized(TAuthorizationType.READ))
				setViewMode(TViewMode.READER);
			if(isUserAuthorized(TAuthorizationType.EDIT))
				setViewMode(TViewMode.EDIT);
		}
		
		
		tMode = getViewMode();
		
		if( tMode!=null && ((tMode.equals(TViewMode.EDIT) && !isEditModeSelected()) 
				|| (tMode.equals(TViewMode.READER) && !isReaderModeSelected())
				|| (!isReaderModeSelected() && !isEditModeSelected())))
		{
			if(tMode.equals(TViewMode.EDIT) && isUserAuthorized(TAuthorizationType.EDIT)){
				radioGroup.selectToggle(this.decorator.gettEditModeRadio());
				return true;
			}
		
			if(tMode.equals(TViewMode.READER) && isUserAuthorized(TAuthorizationType.READ)){
				radioGroup.selectToggle(this.decorator.gettReadModeRadio());
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Config the breadcrumbbar to navigate between forms.
	 * */
	public void configBreadcrumbForm() {
		
		final TDynaView<M> view = getView();
		
		final ObservableList<TEntry<Object>> entryList = FXCollections.observableArrayList();
		
		final TBreadcrumbForm tBreadcrumbForm = this.decorator.gettBreadcrumbForm();
		tBreadcrumbForm.tEntryListProperty().bindContentBidirectional(entryList);
		
		int index = view.gettHeaderVerticalLayout().getChildren().size();
		view.gettHeaderVerticalLayout().getChildren().add(index, StackPaneBuilder.create().id("t-header-box").children(this.decorator.gettBreadcrumbFormToolBar()).build());
		
		formListChangeListener = new ListChangeListener<Node>(){
			
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends Node> change) {
				if(change.next() && change.getAddedSize()>=1){
					removeFormListChangeListener();
					List added = change.getAddedSubList();
					Object obj = added.get(0);
					if(obj instanceof ScrollPane){
						ScrollPane scroll = (ScrollPane) obj;
						Node node = scroll.getContent();
						if(node instanceof ITForm){
							ITForm form = (ITForm) scroll.getContent();
							
							TDynaPresenter formPresenter = (TDynaPresenter) form.gettPresenter();
							TEntry<Object> entry = TEntryBuilder.create()
									.addEntry(TBreadcrumbForm.TBUTTON_TITLE, formPresenter.getBehavior().getFormName())
									.addEntry(TBreadcrumbForm.TFORM, form)
									.build();
							
							if(!entryList.contains(entry)){
								entryList.add(entry);
							}else{
								tBreadcrumbForm.tRemoveEntryListChangeListener();
								
								final int i = entryList.indexOf(entry);
								entryList.remove(entry);
								entryList.add(i, entry);
								
								tBreadcrumbForm.tBuildBreadcrumbar(false);
								tBreadcrumbForm.tAddEntryListChangeListener();
								
								final TDynaPresenter presenter = getModulePresenter();
								final TDynaViewCrudBaseBehavior behavior = (TDynaViewCrudBaseBehavior)presenter.getBehavior();
								behavior.removeFormListChangeListener();
								
								setForm((ITModelForm) form);
								
								behavior.addFormListChangeListener();
							}
							
							addFormListChangeListener();
							
						}else{
							addFormListChangeListener();
							removeAllButtons(entryList, tBreadcrumbForm);
						}
					}else{
						addFormListChangeListener();
						removeAllButtons(entryList, tBreadcrumbForm);
					}
						
				}
			}

			private void removeAllButtons(final ObservableList<TEntry<Object>> formItemList, final TBreadcrumbForm tBreadcrumbForm) {
				formItemList.clear();
			}
		};
		
		addFormListChangeListener();
	}
	
	// ACTIONS
	
	/**
	 * Perform this action when colapse button onAction is triggered.
	 * */
	public abstract void colapseAction();
	
	/**
	 * Perform this action when a model is selected.
	 * */
	public void selectedItemAction(M new_val) {
		final TDynaPresenter<M> presenter = getPresenter();
		setModelView(new_val);
		if(selectedItemAction==null || (selectedItemAction!=null && selectedItemAction.runBefore(presenter))){
			if(new_val==null)
				return;
			
			if(!selectMode())
				showForm(getViewMode());
			
		}
		if(selectedItemAction!=null)
			selectedItemAction.runAfter(presenter);
	}
	
	/**
	 * Perform this action when save button onAction is triggered.
	 * */
	public void saveAction() {
		final TDynaPresenter<M> presenter = getPresenter();
		if(saveAction==null || (saveAction!=null && saveAction.runBefore(presenter))){
			
			final ObservableList<String> mensagens = FXCollections.observableArrayList();
			mensagens.addListener(new ListChangeListener<String>(){
				@Override
				public void onChanged(javafx.collections.ListChangeListener.Change<? extends String> c) {
					final TMessageBox tMessageBox = new TMessageBox();
					tMessageBox.tAddMessage(c.getList());
					getView().tShowModal(tMessageBox, true);
				}
			});
			
			try{
				boolean flag = true;
				if(getEntityProcessClass()!=null){
					runSaveEntityProcess(mensagens);
					flag = false;
				}else if(getModelProcessClass()!=null){
					runModelProcess(mensagens, "SAVE");
					flag = false;
				}
				
				if(flag){
					throw new TException("Error: No process configuration found in "+getModelViewClass().getSimpleName()+" model view class, use @TEntityProcess or @TModelProcess to do that.");
				}
				
			}catch(TValidatorException e){
				getView().tShowModal(new TMessageBox(e), true);
			} catch (Throwable e) {
				getView().tShowModal(new TMessageBox(e), true);
				e.printStackTrace();
			}
		}
		
		if(saveAction!=null)
			saveAction.runAfter(presenter);
	}
	
	

	@SuppressWarnings("unchecked")
	private void runSaveEntityProcess(final ObservableList<String> mensagens)
			throws Exception, TValidatorException, Throwable {
		//recupera a lista de models views
		//final ObservableList<M> modelsViewsList = this.decorator.gettListView().getItems(); 
		
		final ObservableList<M> modelsViewsList = (ObservableList<M>) ((saveAllModels && getModels()!=null) 
				? getModels() 
						: getModelView()!=null 
						? FXCollections.observableList(Arrays.asList(getModelView())) 
								: null) ;
						
		if(modelsViewsList == null)
			throw new Exception("No value was find to be saved!");
						
		// valida os models views
		validateModels(modelsViewsList);
		// salva os models views
		for(int x=0; x<modelsViewsList.size(); x++){
			
			final M model = modelsViewsList.get(x);
			
			if(!model.isChanged())
				continue;
			
			final TEntityProcess process  = createEntityProcess();
			process.save( (ITEntity) model.getModel());
			process.stateProperty().addListener(new ChangeListener<State>() {
				@Override
				public void changed(ObservableValue<? extends State> arg0, State arg1, State arg2) {
					if(arg2.equals(State.SUCCEEDED)){
						List<TResult<E>> resultados = (List<TResult<E>>) process.getValue();
						if(resultados.isEmpty())
							return;
						
						TResult result = resultados.get(0);
						E entity = (E) result.getValue();
						if(entity!=null){
							try {
								String msg = result.isPriorityMessage() 
										? result.getMessage()
												: iEngine.getFormatedString("#{tedros.fxapi.message.save}", model.getDisplayProperty().getValue());
								model.reload(entity);
								mensagens.add(msg);
							} catch (Exception e) 
							{	
								mensagens.add(iEngine.getString("#{tedros.fxapi.message.error.save}")+"\n"+e.getMessage());
								e.printStackTrace();
							}
						}
						if(result.getResult().getValue() == EnumResult.ERROR.getValue()){
							System.out.println(result.getMessage());
							mensagens.add(result.getMessage());
						}
						if(result.getResult().getValue() == EnumResult.OUTDATED.getValue()){
							System.out.println(result.getMessage());
							mensagens.add(iEngine.getString("#{tedros.fxapi.message.outdate}"));
						}
					}	
				}

				
			});
			runProcess(process);
		}
	}
	
		
	/**
	 * Perform this action when new button onAction is triggered.
	 * */
	public void newAction() {
		final TDynaPresenter<M> presenter = getPresenter();
		if(newAction==null || (newAction!=null && newAction.runBefore(presenter))){
			try{
				final Class<E> entityClass = getEntityClass();
				final M model = (M) getModelViewClass().getConstructor(entityClass).newInstance(entityClass.newInstance());
				setModelView(model);
				setNewEntity(model);
				editEntity(model);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		if(newAction!=null)
			newAction.runAfter(presenter);
	}
	
	/**
	 * Called by the newAction() method to perform a custom behavior 
	 * like add the new item in a {@link ListView} or {@link TableView}.  
	 * */
	public abstract void setNewEntity(M model);
	
	/**
	 * Perform this action when edit button onAction is triggered.
	 * */
	public void editAction() {
		final TDynaPresenter<M> presenter = getPresenter();
		if(editAction==null || (editAction!=null && editAction.runBefore(presenter))){
			try{
				editEntity(getModelView());
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		if(editAction!=null)
			editAction.runAfter(presenter);
	}
	
	
	public void editEntity(TModelView model) {
		showForm(TViewMode.EDIT);
	}
	/**
	 * Called by the editAction()
	 * *
	public void editEntity(TModelView model) {
		if(this.decorator.isShowBreadcrumBar())
			showFormInMainPresenter(model);
		else
			showForm(TMode.EDIT);
	}
	
	private void showFormInMainPresenter(TModelView model) {
		
		final TDynaPresenter presenter = getModulePresenter();
		
		ITModelForm form =  ((TDynaViewCrudBaseBehavior)presenter.getBehavior()).isReaderModeSelected() // check in the main behavior  
				? TReaderFormBuilder.create(model).build() 
						: TFormBuilder.create(model).build();
		
		form.settPresenter(presenter);
		presenter.getBehavior().setForm(form);
	}*/

	public TDynaPresenter getModulePresenter() {
				
		ITModule module = getPresenter().getModule() ;
		
		final TDynaPresenter presenter = (TDynaPresenter) TedrosAppManager.getInstance()
				.getModuleContext(module).getCurrentViewContext().getPresenter();
		
		if(presenter==null)
			throw new RuntimeException("The ITPresenter was not present in TViewContext while build the "+module.getClass().getSimpleName()+" module");
		return presenter;
	}
	
	/**
	 * Perform this action when cancel button onAction is triggered.
	 * */
	public void cancelAction() {
		final TDynaPresenter<M> presenter = getPresenter();
		if(cancelAction==null || (cancelAction!=null && cancelAction.runBefore(presenter))){
			try{
				clearForm();
				setModelView(null);
				decorator.showScreenSaver();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		if(cancelAction!=null)
			cancelAction.runAfter(presenter);
	}
	
	
	/**
	 * Perform this action when delete button onAction is triggered.
	 * */
	public void deleteAction() {
		final TDynaPresenter<M> presenter = getPresenter();
		if(deleteAction==null || (deleteAction!=null && deleteAction.runBefore(presenter))){
			if(getModelView()==null)
				return;
			
			String message = iEngine.getFormatedString("#{tedros.fxapi.message.delete}", getModelView().getDisplayProperty().getValue()==null ? "" : getModelView().getDisplayProperty().getValue());
			
			final TConfirmMessageBox confirm = new TConfirmMessageBox(message);
			confirm.gettConfirmProperty().addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
					if(arg2.equals(1)){
						startRemoveProcess(true);
					}
					getView().tHideModal();	
				}
			});
			
			getView().tShowModal(confirm, false);
		}
		
		if(deleteAction!=null)
			deleteAction.runAfter(presenter);
		
	}
	
	/**
	 * Called by the deleteAction() method to start the remove request in the remote service. 
	 * To cancel the request override this method like this example:
	 * <code>
	 * public void startRemoveProcess(boolean removeFromDataBase){
	 * 	super.startRemoveProcess(false);		
	 * }
	 * <code>
	 * */
	@SuppressWarnings("unchecked")
	public void startRemoveProcess(boolean removeFromDataBase) {
		
		final M selected = getModelView();
		
		if(selected.getModel() instanceof ITEntity){
			ITEntity entity = (ITEntity) selected.getModel(); 
			if(removeFromDataBase && !entity.isNew()){
				try{
					final TEntityProcess process = createEntityProcess();
					process.delete(entity);
					process.stateProperty().addListener(new ChangeListener<State>() {
						@Override
						public void changed(ObservableValue<? extends State> arg0, State arg1, State arg2) {
								if(arg2.equals(State.SUCCEEDED)){
									List<TResult<E>> resultados = (List<TResult<E>>) process.getValue();
									if(resultados.isEmpty())
										return;
									TResult result = resultados.get(0);
									if(result.getResult().getValue() == EnumResult.ERROR.getValue())
										System.out.println(result.getMessage());
									if(result.getResult().getValue() != EnumResult.ERROR.getValue()){
										remove();
										removeAllListenerFromModelView();
										setModelView(null);
										clearForm();
										decorator.showScreenSaver();
									}
								}	
						}
					});
					runProcess(process);
				}catch(Throwable e){
					getView().tShowModal(new TMessageBox(e), true);
					e.printStackTrace();
				}
			}else{
				remove();
				removeAllListenerFromModelView();
				setModelView(null);
				clearForm();
				decorator.showScreenSaver();
			}
		}
	}
	
	public TEntityProcess createEntityProcess() throws Throwable {
		
		if((StringUtils.isNotBlank(serviceName)) && this.entityProcessClass == TEntityProcess.class){
			return new TEntityProcess(entityClass, this.serviceName, this.remoteMode) {
				@Override
				public void execute(List resultList) {
				}
			};
		}
		
		if(this.entityProcessClass != null && this.entityProcessClass != TEntityProcess.class)
			return entityProcessClass.newInstance();
		
		return null;
	}
	
	/**
	 * Called by the startRemoveProcess() method to perform a custom behavior 
	 * like remove a item from a {@link ListView} or {@link TableView}.  
	 * */
	public abstract void remove();
	
	/**
	 * Perform this action when a mode radio change listener is triggered.
	 * */
	public void changeModeAction() {
		final TDynaPresenter<M> presenter = getPresenter();
		if(changeModeAction==null || (changeModeAction!=null && changeModeAction.runBefore(presenter))){
			if(getModelView()!=null){
				showForm(null);
			}
		}
		
		if(changeModeAction!=null)
			changeModeAction.runAfter(presenter);
		
	}
	
	/**
	 * Build and show the form
	 * */
	public void showForm(TViewMode mode) {
		
		setViewMode(mode);
		
		ITModelForm<M> form = (mode!=null) 
				? buildForm(mode)
						: radioGroup!=null 
						?  (isReaderModeSelected() 
								? buildForm(TViewMode.READER) 
										: buildForm(TViewMode.EDIT))
								: buildForm(TViewMode.EDIT);
		setForm(form);
	}
	
	/**
	 * Return true if the reader mode is selected
	 * */
	public boolean isReaderModeSelected(){
		return radioGroup.getSelectedToggle()!=null && radioGroup.getSelectedToggle().equals(this.decorator.gettReadModeRadio());
	}
	
	/**
	 * Return true if the edit mode is selected
	 * */
	public boolean isEditModeSelected(){
		return radioGroup.getSelectedToggle()!=null && radioGroup.getSelectedToggle().equals(this.decorator.gettEditModeRadio());
	}

	
	public TPresenterAction<TDynaPresenter<M>> getNewAction() {
		return newAction;
	}

	
	public void setNewAction(TPresenterAction<TDynaPresenter<M>> newAction) {
		this.newAction = newAction;
	}

	
	public TPresenterAction<TDynaPresenter<M>> getSaveAction() {
		return saveAction;
	}

	
	public void setSaveAction(TPresenterAction<TDynaPresenter<M>> saveAction) {
		this.saveAction = saveAction;
	}

	
	public TPresenterAction<TDynaPresenter<M>> getDeleteAction() {
		return deleteAction;
	}

	
	public void setDeleteAction(TPresenterAction<TDynaPresenter<M>> deleteAction) {
		this.deleteAction = deleteAction;
	}

	
	public TPresenterAction<TDynaPresenter<M>> getChangeModeAction() {
		return changeModeAction;
	}

	
	public void setChangeModeAction(TPresenterAction<TDynaPresenter<M>> changeModeAction) {
		this.changeModeAction = changeModeAction;
	}

	
	public TPresenterAction<TDynaPresenter<M>> getSelectedItemAction() {
		return selectedItemAction;
	}

	
	public void setSelectedItemAction(TPresenterAction<TDynaPresenter<M>> selectedItemAction) {
		this.selectedItemAction = selectedItemAction;
	}

	public TPresenterAction<TDynaPresenter<M>> getEditAction() {
		return editAction;
	}

	public void setEditAction(TPresenterAction<TDynaPresenter<M>> editAction) {
		this.editAction = editAction;
	}

	public TPresenterAction<TDynaPresenter<M>> getCancelAction() {
		return cancelAction;
	}

	public void setCancelAction(TPresenterAction<TDynaPresenter<M>> cancelAction) {
		this.cancelAction = cancelAction;
	}

	
	public ToggleGroup getRadioGroup() {
		return radioGroup;
	}
	
	public boolean isRadioGroupBuilt(){
		return radioGroup!=null;
	}

	
	public BooleanProperty getModeBtnDisableProperty() {
		return modeBtnDisableProperty;
	}

	
	public BooleanProperty getModeBtnVisibleProperty() {
		return modeBtnVisibleProperty;
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
	 * Return true if the process is setting to remote 
	 * */
	public boolean isRemoteMode() {
		return remoteMode;
	}
	
	/**
	 * Return the name of the ejb service
	 * */
	public String getServiceName() {
		return serviceName;
	}
	
	/**
	 * Return the {@link TEntityProcess} class
	 * */
	public Class<? extends TEntityProcess> getEntityProcessClass() {
		return entityProcessClass;
	}
	
	/**
	 * Return the {@link ITEntity} class
	 * */
	public Class<E> getEntityClass() {
		return entityClass;
	}
	
}
