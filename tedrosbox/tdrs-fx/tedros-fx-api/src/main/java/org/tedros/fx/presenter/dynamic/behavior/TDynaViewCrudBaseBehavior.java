package org.tedros.fx.presenter.dynamic.behavior;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.tedros.core.ITModule;
import org.tedros.core.TModule;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.context.TEntry;
import org.tedros.core.context.TEntryBuilder;
import org.tedros.core.context.TedrosAppManager;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.message.TMessage;
import org.tedros.core.message.TMessageType;
import org.tedros.core.presenter.ITPresenter;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.collections.TFXCollections;
import org.tedros.fx.domain.TViewMode;
import org.tedros.fx.exception.TException;
import org.tedros.fx.exception.TValidatorException;
import org.tedros.fx.form.ITModelForm;
import org.tedros.fx.layout.TBreadcrumbForm;
import org.tedros.fx.modal.TConfirmMessageBox;
import org.tedros.fx.modal.TMessageBox;
import org.tedros.fx.presenter.behavior.TActionState;
import org.tedros.fx.presenter.behavior.TActionType;
import org.tedros.fx.presenter.behavior.TProcessResult;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.fx.presenter.dynamic.decorator.TDynaViewCrudBaseDecorator;
import org.tedros.fx.presenter.dynamic.view.TDynaGroupView;
import org.tedros.fx.presenter.dynamic.view.TDynaView;
import org.tedros.fx.presenter.model.TModelView;
import org.tedros.fx.presenter.view.group.TGroupPresenter;
import org.tedros.fx.process.TEntityProcess;
import org.tedros.fx.util.HtmlPDFExportHelper;
import org.tedros.fx.util.TPrintUtil;
import org.tedros.server.entity.ITEntity;
import org.tedros.server.model.ITModel;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.WeakEventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;


@SuppressWarnings("rawtypes")
public abstract class TDynaViewCrudBaseBehavior<M extends TModelView, E extends ITModel> 
extends TDynaViewSimpleBaseBehavior<M, E> {
	
	private ChangeListener<ITModelForm<M>> breadcrumbFormChangeListener;
	
	private ToggleGroup radioGroup;
	private BooleanProperty modeBtnDisableProperty;
	private BooleanProperty modeBtnVisibleProperty;
	
	private Class<? extends TModelView> importFileModelViewClass;
	
	private boolean remoteMode;
	private String serviceName;
	private Class<E> entityClass;

	private Class<? extends TEntityProcess> entityProcessClass;
	
	private TDynaViewCrudBaseDecorator<M> decorator;

	protected boolean saveAllModels;
	protected boolean saveOnlyChangedModel;
	protected boolean runNewActionAfterSave;

	protected boolean skipConfigBreadcrumb;
	
	@SuppressWarnings("unchecked")
	public void load(){
		super.load();
		try{
			final TDynaPresenter<M> presenter = getPresenter();
			// check the crud settings
			
			final TEjbService tEjbService = getPresenter().getEjbServiceAnnotation();
			final org.tedros.fx.annotation.process.TEntityProcess tEntityProcess = getPresenter().getEntityProcessAnnotation();
			
			this.entityClass = (Class<E>) presenter.getModelClass();
			
			if(tEjbService!=null){
				this.remoteMode = tEjbService.remoteMode();
				this.serviceName = tEjbService.serviceName();
				this.entityClass = (Class<E>) tEjbService.model();
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
			saveOnlyChangedModel = tBehavior.saveOnlyChangedModels();
			runNewActionAfterSave = tBehavior.runNewActionAfterSave();
			importFileModelViewClass = tBehavior.importModelViewClass();
			
			if(decorator.gettImportButton()!=null && importFileModelViewClass==TModelView.class)
				throw new RuntimeException("The property importFileModelViewClass in TBehavior is required for import action");
			
			if(this.decorator.isShowBreadcrumBar())
				configBreadcrumbForm();
						
			// set the custom behavior actions
			loadAction(presenter, tBehavior.action());
			
			ChangeListener<TModelView> mvcl = (a0, old_, new_) -> {
				processModelView(new_);
			};
			this.decorator.showScreenSaver();
			super.getListenerRepository().add("setmodelviewCL", mvcl);
			super.modelViewProperty().addListener(new WeakChangeListener(mvcl));
			
			ChangeListener<ITModelForm<M>> abfchl = (ob, o, form) -> {
				if(form!=null)
					runAfterBuildForm(form);
			};
			super.getListenerRepository().add("afterBuildFormChl", abfchl);
			super.formProperty().addListener(new WeakChangeListener(abfchl));
			
			/*super.actionStateProperty().addListener((a,b,c)->{
				if(c!=null)
					System.out.println(c.toString());
			});
			*/
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	public void processModelView(TModelView model) {
		if(model== null) {
			if(decorator.isShowBreadcrumBar() && decorator.gettBreadcrumbForm()!=null)
				decorator.gettBreadcrumbForm().tEntryListProperty().clear();
			super.clearForm();
			setDisableModelActionButtons(true);
			showScreenSaver();
			return;
		}
			
		if(!selectMode()) {
			if(decorator.isShowBreadcrumBar() && decorator.gettBreadcrumbForm()!=null)
				decorator.gettBreadcrumbForm().tEntryListProperty().clear();
			showForm(getViewMode());
			setDisableModelActionButtons(false);
		}else
			super.showScreenSaver();
	}
	
	protected void runAfterBuildForm(ITModelForm<M> form) {
		
		
	}

	public void setDisableModelActionButtons(boolean flag) {
		if(decorator.gettCancelButton()!=null)
			decorator.gettCancelButton().setDisable(flag);
		if(decorator.gettSaveButton()!=null && isUserAuthorized(TAuthorizationType.SAVE))
			decorator.gettSaveButton().setDisable(flag);
		if(decorator.gettImportButton()!=null && isUserAuthorized(TAuthorizationType.SAVE))
			decorator.gettImportButton().setDisable(!flag);
		if(decorator.gettDeleteButton()!=null && isUserAuthorized(TAuthorizationType.DELETE))
			decorator.gettDeleteButton().setDisable(flag);
		if(decorator.gettPrintButton()!=null && isUserAuthorized(TAuthorizationType.PRINT))
			decorator.gettPrintButton().setDisable(flag);
		if(decorator.gettEditModeRadio()!=null && isUserAuthorized(TAuthorizationType.EDIT))
			decorator.gettEditModeRadio().setDisable(flag);
		if(decorator.gettReadModeRadio()!=null && isUserAuthorized(TAuthorizationType.READ))
			decorator.gettReadModeRadio().setDisable(flag);
		
	}
	
	/**
	 * Config the colapse button
	 * */
	public void configColapseButton() {
		final Button colapseButton = this.decorator.gettColapseButton();
		if(colapseButton!=null) {
			EventHandler<ActionEvent> eh = e -> colapseAction();
			super.getListenerRepository().add("colapseButtonClickEH", eh);
			colapseButton.setOnAction(new WeakEventHandler<ActionEvent>(eh));
		}
	}
	
	/**
	 * Config the print button;
	 * */
	public void configPrintButton() {
		if(isUserAuthorized(TAuthorizationType.PRINT)){
			final Button printButton = this.decorator.gettPrintButton();
			if(printButton!=null) {
				EventHandler<ActionEvent> eh = e -> printAction();
				super.getListenerRepository().add("printButtonClickEH", eh);
				printButton.setOnAction(new WeakEventHandler<ActionEvent>(eh));
			}
		}
	}
	
	/**
	 * Config the import button;
	 * */
	public void configImportButton() {
		if(isUserAuthorized(TAuthorizationType.SAVE)){
			final Button importButton = this.decorator.gettImportButton();
			if(importButton!=null) {
				EventHandler<ActionEvent> eh = e -> importAction();
				super.getListenerRepository().add("importButtonClickEH", eh);
				importButton.setOnAction(new WeakEventHandler<ActionEvent>(eh));
			}
		}
	}
	
	/**
	 * Config the new button 
	 * */
	public void configNewButton() {
		if(isUserAuthorized(TAuthorizationType.NEW)){
			final Button newButton = this.decorator.gettNewButton();
			if(newButton!=null) {
				EventHandler<ActionEvent> eh = e -> newAction();
				super.getListenerRepository().add("newButtonClickEH", eh);
				newButton.setOnAction(new WeakEventHandler<ActionEvent>(eh));
			}
		}
	}
	
	/**
	 * Config the save button
	 * */
	public void configSaveButton() {
		if(isUserAuthorized(TAuthorizationType.SAVE)){
			final Button saveButton = this.decorator.gettSaveButton();
			if(saveButton!=null) {
				EventHandler<ActionEvent> eh = e -> {
					saveAction();
				};
				super.getListenerRepository().add("saveButtonClickEH", eh);
				saveButton.setOnAction(new WeakEventHandler<ActionEvent>(eh));
			}
		}
	}
	
	/**
	 * Config the delete button;
	 * */
	public void configDeleteButton() {
		if(isUserAuthorized(TAuthorizationType.DELETE)){
			final Button removeButton = this.decorator.gettDeleteButton();
			if(removeButton!=null) {
				EventHandler<ActionEvent> eh = e -> deleteAction();
				super.getListenerRepository().add("deleteButtonClickEH", eh);
				removeButton.setOnAction(new WeakEventHandler<ActionEvent>(eh));
			}
		}
	}
	
	/**
	 * Config the edit button;
	 * */
	public void configEditButton() {
		if(isUserAuthorized(TAuthorizationType.EDIT)){
			final Button editButton = this.decorator.gettEditButton();
			EventHandler<ActionEvent> eh = e -> editAction();
			super.getListenerRepository().add("editButtonClickEH", eh);
			editButton.setOnAction(new WeakEventHandler<ActionEvent>(eh));
		}
	}
	
	/**
	 * Config the cancel button;
	 * */
	public void configCancelButton() {
		final Button cancelButton = this.decorator.gettCancelButton();
		if(cancelButton!=null) {
			EventHandler<ActionEvent> eh = e -> cancelAction();
			super.getListenerRepository().add("cancelButtonClickEH", eh);
			cancelButton.setOnAction(new WeakEventHandler<ActionEvent>(eh));
		}
		
	}
	
	/**
	 * Config the mode options tool bar
	 * */
	public void configModesRadio() {
		
		
		final RadioButton editRadio = this.decorator.gettEditModeRadio();
		final RadioButton readRadio = this.decorator.gettReadModeRadio();
		final Hyperlink exportLink = this.decorator.gettExportReadHyperLink();
		
		if(editRadio==null && readRadio==null)
			return;
		
		radioGroup = new ToggleGroup();
		editRadio.setToggleGroup(radioGroup);
		readRadio.setToggleGroup(radioGroup);
		
		exportLink.setOnAction(e -> {
			WebView wv = super.getForm().gettWebView();
			if(wv!=null) {
				String output = HtmlPDFExportHelper.getDestFile("ExportedView", new Date());
				String html = (String) wv.getEngine().executeScript("document.documentElement.outerHTML");
				HtmlPDFExportHelper.generate(super.getPresenter(), output, html);
			}
			
		});
		
		ChangeListener<Toggle> listener = (a0, a1, a2) -> changeModeAction();
		super.getListenerRepository().add("modesRadioCL", listener);
		radioGroup.selectedToggleProperty().addListener(new WeakChangeListener<Toggle>(listener));
		
		modeBtnDisableProperty = new SimpleBooleanProperty();
		modeBtnVisibleProperty = new SimpleBooleanProperty(true);
		
		editRadio.disableProperty().bindBidirectional(modeBtnDisableProperty);
		editRadio.visibleProperty().bindBidirectional(modeBtnVisibleProperty);
		readRadio.disableProperty().bindBidirectional(modeBtnDisableProperty);
		readRadio.visibleProperty().bindBidirectional(modeBtnVisibleProperty);
		//exportLink.disableProperty().bindBidirectional(modeBtnDisableProperty);
		exportLink.visibleProperty().bindBidirectional(modeBtnVisibleProperty);
		
		ChangeListener<Boolean> invCL = (a0, a1, a2) -> {
			editRadio.disableProperty().unbindBidirectional(modeBtnDisableProperty);
			editRadio.visibleProperty().unbindBidirectional(modeBtnVisibleProperty);
			readRadio.disableProperty().unbindBidirectional(modeBtnDisableProperty);
			readRadio.visibleProperty().unbindBidirectional(modeBtnVisibleProperty);
			//exportLink.disableProperty().unbindBidirectional(modeBtnDisableProperty);
			exportLink.visibleProperty().unbindBidirectional(modeBtnVisibleProperty);
		};
		getListenerRepository().add("invalidateModeUnBind", invCL);
		invalidateProperty().addListener(new WeakChangeListener<>(invCL));
		
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
	
	public void setViewMode(TViewMode mode){
		super.setViewMode(mode);
		final Hyperlink exportLink = this.decorator.gettExportReadHyperLink();
		if(exportLink!=null)
			exportLink.setDisable(mode==null || mode.equals(TViewMode.EDIT));
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
		
		if(isSkipConfigBreadcrumb())
			return;
		
		final TDynaView<M> view = getView();
		
		final ObservableList<TEntry<Object>> entryList = FXCollections.observableArrayList();
		
		final TBreadcrumbForm tBreadcrumbForm = this.decorator.gettBreadcrumbForm();
		tBreadcrumbForm.tEntryListProperty().bindContentBidirectional(entryList);
		
		int index = view.gettHeaderVerticalLayout().getChildren().size();
		
		StackPane sp = new StackPane();
		//sp.setId("t-header-box");
		sp.getStyleClass().add("t-panel-background-color");
		sp.getChildren().add(this.decorator.gettBreadcrumbFormToolBar());
		
		view.gettHeaderVerticalLayout().getChildren().add(index, sp);
		
		breadcrumbFormChangeListener = new ChangeListener<ITModelForm<M>>(){
			
			@Override
			public void changed(ObservableValue<? extends ITModelForm<M>> arg0, ITModelForm<M> arg1,
					ITModelForm<M> form) {
				
				if(form!=null) {
						
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
						behavior.removeBreadcrumbFormChangeListener();
						
						setForm((ITModelForm) form);
						
						behavior.addBreadcrumbFormChangeListener();
					}
				}
			}
		};
		
		addBreadcrumbFormChangeListener();
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
		setActionState(new TActionState<>(TActionType.SELECTED_ITEM, TProcessResult.RUNNING));
		final TDynaPresenter<M> presenter = getPresenter();
		presenter.setModelView(new_val);
		if(this.actionHelper.runBefore(TActionType.SELECTED_ITEM)){
			if(new_val==null)
				return;
			super.formProperty().addListener(new ChangeListener<ITModelForm>() {

				@Override
				public void changed(ObservableValue<? extends ITModelForm> arg0, ITModelForm arg1,
						ITModelForm form) {
					if(form!=null) {
						formProperty().removeListener(this);
						form.tLoadedProperty().addListener(new ChangeListener<Boolean>() {

							@Override
							public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1,
									Boolean loaded) {
								if(loaded) {
									form.tLoadedProperty().removeListener(this);
									setActionState(new TActionState<>(TActionType.SELECTED_ITEM, TProcessResult.SUCCESS));
								}
								
							}
							
						});
					}
					
				}
				
			});
			setModelView(new_val);
			
		}
		this.actionHelper.runAfter(TActionType.SELECTED_ITEM);
		setActionState(new TActionState<>(TActionType.SELECTED_ITEM, TProcessResult.FINISHED, new_val));
	}
	
	/**
	 * Perform this action when save button onAction is triggered.
	 * */
	public void saveAction() {
		setActionState(new TActionState<>(TActionType.SAVE, TProcessResult.RUNNING));
		if(this.actionHelper.runBefore(TActionType.SAVE)){
			try{
				boolean flag = true;
				if(getEntityProcessClass()!=null || (StringUtils.isNotBlank(this.serviceName) && this.entityClass!=null)){
					runSaveEntityProcess();
					flag = false;
				}else if(getModelProcessClass()!=null){
					runModelProcess(TActionType.SAVE);
					flag = false;
				}
				
				if(flag){
					throw new TException("Error: No process configuration found in "+getModelViewClass().getSimpleName()+" model view class, use @TEntityProcess, @TEjbService or @TModelProcess to do that.");
				}
				setActionState(new TActionState<>(TActionType.SAVE, TProcessResult.FINISHED));
			}catch(TValidatorException e){
				getView().tShowModal(new TMessageBox(e), true);
				setActionState(new TActionState<>(TActionType.SAVE, TProcessResult.FINISHED));
			} catch (Throwable e) {
				e.printStackTrace();
				getView().tShowModal(new TMessageBox(e), true);
				setActionState(new TActionState<>(TActionType.SAVE, TProcessResult.ERROR));
			}
		}
	}
	
	

	@SuppressWarnings("unchecked")
	private void runSaveEntityProcess()
			throws Exception, TValidatorException, Throwable {
		//recupera a lista de models views
		final ObservableList<M> modelsViewsList = (ObservableList<M>) ((saveAllModels && getModels()!=null) 
				? getModels() 
						: getModelView()!=null 
						? FXCollections.observableList(Arrays.asList(getModelView())) 
								: null) ;
						
		if(modelsViewsList == null)
			throw new Exception("None entity found to save!");
						
		// valida os models views
		validateModels(modelsViewsList);
		
		boolean runNewAction = ((ITEntity)this.getModelView().getModel()).isNew() && this.runNewActionAfterSave;
		
		// salva os models views
		for(int x=0; x<modelsViewsList.size(); x++){
			
			final M model = modelsViewsList.get(x);
			if(saveOnlyChangedModel && !model.isChanged())
				continue;
			
			final TEntityProcess process  = createEntityProcess();
			process.save( (ITEntity) model.getModel());
			process.stateProperty().addListener(new ChangeListener<State>() {
				@Override
				public void changed(ObservableValue<? extends State> arg0, State arg1, State arg2) {
					if(arg2.equals(State.SUCCEEDED)){
						List<TResult<E>> resultados = (List<TResult<E>>) process.getValue();
						if(resultados.isEmpty()) {
							actionHelper.runAfter(TActionType.SAVE);
							setActionState(new TActionState(TActionType.SAVE, arg2, TProcessResult.NO_RESULT));
							return;
						}
						TResult result = resultados.get(0);
						if(result.getState().equals(TState.SUCCESS)){
							E entity = (E) result.getValue();
							if(entity!=null){
								try {
									model.reload(entity);
									String msg = result.isPriorityMessage() 
											? result.getMessage()
													: iEngine.getFormatedString("#{tedros.fxapi.message.save}", model.toStringProperty().getValue());
									if(runNewAction) {
										getView().tModalVisibleProperty().addListener(new ChangeListener<Boolean>() {
											@Override
											public void changed(ObservableValue<? extends Boolean> arg0, Boolean b, Boolean c) {
												if(!c) {
													getView().tModalVisibleProperty().removeListener(this);
													actionHelper.runAfter(TActionType.SAVE);
													newAction();
												}
											}
										});
									}
									addMessage(new TMessage(TMessageType.INFO, msg));
									setActionState(new TActionState(TActionType.SAVE, arg2, TProcessResult.SUCCESS));
								} catch (Exception e) {	
									e.printStackTrace();
									addMessage(new TMessage(TMessageType.ERROR, e.getMessage()));
									
								}
							}
							if(!runNewAction)
								actionHelper.runAfter(TActionType.SAVE);
						}else{
							System.out.println(result.getMessage());
							String msg = result.getState().equals(TState.OUTDATED) 
									? iEngine.getString("#{tedros.fxapi.message.outdate}")
											: result.getMessage();
							switch(result.getState()) {
								case ERROR:
									addMessage(new TMessage(TMessageType.ERROR, msg));
									break;
								default:
									addMessage(new TMessage(TMessageType.WARNING, msg));
									break;
							}
							setActionState(new TActionState(TActionType.SAVE, arg2, TProcessResult.get(result.getState()), result.getMessage()));	
						}
						
					}else {
						setActionState(new TActionState(TActionType.SAVE, arg2));
					}
				}

				
			});
			runProcess(process);
		}
	}
	
	/**
	 * Perform this action when import button onAction is triggered.
	 * */
	@SuppressWarnings("unchecked")
	public void importAction() {
		setActionState(new TActionState<>(TActionType.IMPORT, TProcessResult.RUNNING));
		try{
			if(this.actionHelper.runBefore(TActionType.IMPORT)){
					StackPane pane = new StackPane();
					if(getModels()==null)
						setModelViewList(TFXCollections.iTObservableList());
					TDynaGroupView view = new TDynaGroupView(importFileModelViewClass, getModels());
					pane.setMaxSize(950, 600);
					pane.getChildren().add(view);
					pane.setId("t-tedros-color");
					pane.setStyle("-fx-effect: dropshadow( three-pass-box , white , 4 , 0.4 , 0 , 0 );");
					view.tLoad();
					TedrosAppManager.getInstance()
					.getModuleContext((TModule)TedrosContext.getView()).getCurrentViewContext()
					.getPresenter().getView().tShowModal(pane, false);
				
			}
			this.actionHelper.runAfter(TActionType.IMPORT);
			setActionState(new TActionState<>(TActionType.IMPORT, TProcessResult.FINISHED));
		}catch(Exception e){
			e.printStackTrace();
			setActionState(new TActionState<>(TActionType.IMPORT, TProcessResult.ERROR, e.getMessage()));
		}
	}
		
	/**
	 * Perform this action when new button onAction is triggered.
	 * */
	public void newAction() {
		setActionState(new TActionState<>(TActionType.NEW, TProcessResult.RUNNING));
		try{
			M model = null;
			if(this.actionHelper.runBefore(TActionType.NEW)){
					final Class<E> entityClass = getEntityClass();
					model = entityClass!=null 
							? (M) getModelViewClass().getConstructor(entityClass).newInstance(entityClass.newInstance())
									: getModelViewClass().newInstance();
					
					if(processNewEntityBeforeBuildForm(model)) {
						super.formProperty().addListener(new ChangeListener<ITModelForm>() {

							@Override
							public void changed(ObservableValue<? extends ITModelForm> arg0, ITModelForm arg1,
									ITModelForm form) {
								if(form!=null) {
									formProperty().removeListener(this);
									form.tLoadedProperty().addListener(new ChangeListener<Boolean>() {

										@Override
										public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1,
												Boolean loaded) {
											if(loaded) {
												form.tLoadedProperty().removeListener(this);
												setActionState(new TActionState<>(TActionType.NEW, TProcessResult.SUCCESS));
											}
											
										}
										
									});
								}
								
							}
							
						});
						setModelView(model);
					}
			}
			this.actionHelper.runAfter(TActionType.NEW);
			setActionState(new TActionState<>(TActionType.NEW, TProcessResult.FINISHED));
		}catch(Exception e){
			e.printStackTrace();
			setActionState(new TActionState<>(TActionType.NEW, TProcessResult.ERROR, e.getMessage()));
		}
	}
	
	/**
	 * Called by the newAction() method to perform a custom behavior 
	 * like add the new item in a {@link ListView} or {@link TableView}.  
	 * @return boolean
	 * */
	public abstract boolean processNewEntityBeforeBuildForm(M model);
	
	/**
	 * Perform this action when print button onAction is triggered.
	 * */
	public void printAction() {
		setActionState(new TActionState<>(TActionType.PRINT, TProcessResult.RUNNING));
		try{
			if(this.actionHelper.runBefore(TActionType.PRINT))
				TPrintUtil.print((Node) super.getForm());
			this.actionHelper.runAfter(TActionType.PRINT);
			setActionState(new TActionState<>(TActionType.PRINT, TProcessResult.FINISHED));
		}catch(Exception e){
			e.printStackTrace();
			setActionState(new TActionState<>(TActionType.PRINT, TProcessResult.ERROR, e.getMessage()));
		}
	}
	
	/**
	 * Perform this action when edit button onAction is triggered.
	 * */
	public void editAction() {
		setActionState(new TActionState<>(TActionType.EDIT, TProcessResult.RUNNING));
		try{
			if(this.actionHelper.runBefore(TActionType.EDIT)){
				editEntity(getModelView());
				super.getForm().tLoadedProperty().addListener(new ChangeListener<Boolean>() {

					@Override
					public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
						if(arg2) {
							setActionState(new TActionState(TActionType.EDIT, TProcessResult.SUCCESS));
							getForm().tLoadedProperty().removeListener(this);
						}
					}
					
				});
			}
			this.actionHelper.runAfter(TActionType.EDIT);
			setActionState(new TActionState<>(TActionType.EDIT, TProcessResult.FINISHED));
		}catch(Exception e){
			e.printStackTrace();
			setActionState(new TActionState<>(TActionType.EDIT, TProcessResult.ERROR, e.getMessage()));
		}
	}
	
	public void editEntity(TModelView model) {
		showForm(TViewMode.EDIT);
	}
	
	public TDynaPresenter getModulePresenter() {
				
		ITModule module = getPresenter().getModule() ;
		
		ITPresenter presenter = 
				TedrosAppManager.getInstance()
				.getModuleContext(module).getCurrentViewContext().getPresenter();
		
		if(presenter==null)
			throw new RuntimeException("The ITPresenter was not present in TViewContext while build the "+module.getClass().getSimpleName()+" module");
		
		presenter = presenter instanceof TGroupPresenter
				? ((TGroupPresenter)presenter).getSelectedView().gettPresenter()
						: presenter;
				
		
		return (TDynaPresenter) presenter;
	}
	
	public String canInvalidate() {
		final ObservableList<M> models = getModels();
		
		boolean unsaved = false;
		if(models!=null)
			for(M i : models) {
				if(i.isChanged()) {
					unsaved = true;
					break;
				}
			};
		
		if(unsaved) {
			return iEngine.getFormatedString("#{tedros.fxapi.message.invalidate}");
		}else
			return null;
	}
	
	
	/**
	 * Perform this action when cancel button onAction is triggered.
	 * */
	public void cancelAction() {
		setActionState(new TActionState<>(TActionType.CANCEL, TProcessResult.RUNNING));
		if(this.actionHelper.runBefore(TActionType.CANCEL)){
			try{
				final M model = getModelView();
				if(model.isChanged()) {
				
					String message = iEngine.getFormatedString("#{tedros.fxapi.message.cancel}");
					
					final TConfirmMessageBox confirm = new TConfirmMessageBox(message);
					confirm.tConfirmProperty().addListener(new ChangeListener<Number>() {
						@SuppressWarnings("unchecked")
						@Override
						public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
							if(arg2.equals(1)){
								if(model.getModel() instanceof ITEntity && ((ITEntity)model.getModel()).isNew()) {
									getView().tHideModal();	
									remove();
									
									actionHelper.runAfter(TActionType.CANCEL);
									setActionState(new TActionState<>(TActionType.CANCEL, TProcessResult.SUCCESS));
								}else{
									try{
										final TEntityProcess process = createEntityProcess();
										process.findById((ITEntity) model.getModel());
										process.stateProperty().addListener(new ChangeListener<State>() {
											@Override
											public void changed(ObservableValue<? extends State> arg0, State arg1, State arg2) {
													if(arg2.equals(State.SUCCEEDED)){
														List<TResult<E>> resultados = (List<TResult<E>>) process.getValue();
														if(resultados.isEmpty())
															return;
														TResult result = resultados.get(0);
														if(result.getState().equals(TState.ERROR)) {
															System.out.println(result.getMessage());
															addMessage(new TMessage(TMessageType.ERROR, result.getMessage()));
															setActionState(new TActionState<>(TActionType.CANCEL, TProcessResult.ERROR, result.getMessage()));
														}else{
															E entity = (E) result.getValue();
															if(entity!=null) {
																model.reload(entity);
																setModelView(null);
																actionHelper.runAfter(TActionType.CANCEL);
																setActionState(new TActionState<>(TActionType.CANCEL, TProcessResult.SUCCESS));
															}else {
																addMessage(new TMessage(TMessageType.WARNING, iEngine.getString("#{tedros.fxapi.message.error}")));
																setActionState(new TActionState<>(TActionType.CANCEL, TProcessResult.NO_RESULT));
															}
														}
													}else {
														setActionState(new TActionState<>(TActionType.CANCEL, arg2));
													}
											}
										});
										getView().tHideModal();	
										runProcess(process);
									}catch(Throwable e){
										e.printStackTrace();
										getView().tHideModal();	
										getView().tShowModal(new TMessageBox(e), true);
										setActionState(new TActionState<>(TActionType.CANCEL, TProcessResult.ERROR, e.getMessage()));
									}
								}
							}else {
								getView().tHideModal();	
								setActionState(new TActionState<>(TActionType.CANCEL, TProcessResult.FINISHED));
							}
						}
					});
					
					getView().tShowModal(confirm, false);
				}else{
					if(model.getModel() instanceof ITEntity && ((ITEntity)model.getModel()).isNew()) {
						getView().tHideModal();	
						remove();
					}else{
						setModelView(null);
						getView().tHideModal();	
					}
					
					this.actionHelper.runAfter(TActionType.CANCEL);
					setActionState(new TActionState<>(TActionType.CANCEL, TProcessResult.SUCCESS));
				}
				
			}catch(Exception e){
				e.printStackTrace();
				getView().tHideModal();	
				getView().tShowModal(new TMessageBox(e), true);
				setActionState(new TActionState<>(TActionType.CANCEL, TProcessResult.ERROR, e.getMessage()));
			}
		}
	}
	
	/**
	 * Perform this action when delete button onAction is triggered.
	 * */
	public void deleteAction() {
		setActionState(new TActionState(TActionType.DELETE,  TProcessResult.RUNNING));
		if(this.actionHelper.runBefore(TActionType.DELETE)){
			if(getModelView()==null)
				return;
			
			String message = iEngine.getFormatedString("#{tedros.fxapi.message.delete}", getModelView().toStringProperty().getValue()==null 
					? "" 
						: getModelView().toStringProperty().getValue());
			
			final TConfirmMessageBox confirm = new TConfirmMessageBox(message);
			confirm.tConfirmProperty().addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
					if(arg2.equals(1)){
						getView().tHideModal();	
						startRemoveProcess(true);
					}else {
						getView().tHideModal();	
						setActionState(new TActionState(TActionType.DELETE,  TProcessResult.FINISHED));
					}
				}
			});
			
			getView().tShowModal(confirm, false);
		}
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
									if(result.getState().equals(TState.ERROR)) {
										System.out.println(result.getMessage());
										addMessage(new TMessage(TMessageType.ERROR, result.getMessage()));
										setActionState(new TActionState(TActionType.DELETE, arg2, TProcessResult.ERROR, result.getMessage()));
									}else if(result.getState().equals(TState.WARNING)){
										E entity = (E) result.getValue();
										if(entity!=null){
											selected.reload(entity);
										}else{
											remove();
											removeAllListenerFromModelView();
											setModelView(null);
										}
										addMessage(new TMessage(TMessageType.WARNING, result.getMessage()));
										setActionState(new TActionState(TActionType.DELETE, arg2, TProcessResult.WARNING, result.getMessage()));
									}else
									if(result.getState().equals(TState.SUCCESS)){
										remove();
										removeAllListenerFromModelView();
										setModelView(null);
										actionHelper.runAfter(TActionType.DELETE);
										setActionState(new TActionState(TActionType.DELETE, arg2, TProcessResult.SUCCESS));
									}
								}	
						}
					});
					runProcess(process);
				}catch(Throwable e){
					e.printStackTrace();
					getView().tShowModal(new TMessageBox(e), true);
					setActionState(new TActionState(TActionType.DELETE, TProcessResult.ERROR, e.getMessage()));
				}
			}else{
				remove();
				removeAllListenerFromModelView();
				setModelView(null);
				setActionState(new TActionState(TActionType.DELETE, TProcessResult.SUCCESS));
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public TEntityProcess createEntityProcess() throws Throwable {
		
		if((StringUtils.isNotBlank(serviceName)) && this.entityClass!=null && (this.entityProcessClass==null || this.entityProcessClass == TEntityProcess.class)){
			return new TEntityProcess(entityClass, this.serviceName) {};
		}
		
		if(this.entityProcessClass != null && this.entityProcessClass != TEntityProcess.class)
			return entityProcessClass.newInstance();
		
		return null;
	}
	
	/**
	 * Called by the startRemoveProcess() method to perform a custom behavior 
	 * like remove a item from a {@link ListView} or {@link TableView}.  
	 * */
	public void remove() {
		if(getModels()!=null) {
			int index = getModels().indexOf(getModelView());
			remove(index);
		}
	}
	
	public void remove(int index) {
		if(getModels()!=null) {
			getModels().remove(index);
		}
	}
	/**
	 * Perform this action when a mode radio change listener is triggered.
	 * */
	public void changeModeAction() {
		setActionState(new TActionState(TActionType.CHANGE_MODE, TProcessResult.RUNNING));
		if(this.actionHelper.runBefore(TActionType.CHANGE_MODE)){
			if(getModelView()!=null){
				if(decorator.isShowBreadcrumBar() && decorator.gettBreadcrumbForm()!=null)
					decorator.gettBreadcrumbForm().tEntryListProperty().clear();
				showForm(null);
				super.getForm().tLoadedProperty().addListener(new ChangeListener<Boolean>() {

					@Override
					public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
						if(arg2) {
							setActionState(new TActionState(TActionType.CHANGE_MODE, TProcessResult.SUCCESS));
							getForm().tLoadedProperty().removeListener(this);
						}
					}
					
				});
			}
		}
		this.actionHelper.runAfter(TActionType.CHANGE_MODE);
		setActionState(new TActionState(TActionType.CHANGE_MODE, TProcessResult.FINISHED));
	}
	
	/**
	 * Build and show the form
	 * */
	public void showForm(TViewMode mode) {
		
		setViewMode(mode);
		
		if (mode!=null) 
			buildForm(mode);
		else if (radioGroup!=null) {
			if (isReaderModeSelected())
				buildForm(TViewMode.READER);
			else
				buildForm(TViewMode.EDIT);
		}else 
			buildForm(TViewMode.EDIT);
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

	public synchronized void removeBreadcrumbFormChangeListener() {
		if(breadcrumbFormChangeListener!=null)
			formProperty().removeListener(breadcrumbFormChangeListener);
	}
	
	public synchronized void addBreadcrumbFormChangeListener() {
		if(breadcrumbFormChangeListener!=null){
			formProperty().addListener(breadcrumbFormChangeListener);
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

	/**
	 * @return the skipConfigBreadcrumb
	 */
	public boolean isSkipConfigBreadcrumb() {
		return skipConfigBreadcrumb;
	}

	/**
	 * @param skipConfigBreadcrumb the skipConfigBreadcrumb to set
	 */
	public void setSkipConfigBreadcrumb(boolean skipConfigBreadcrumb) {
		this.skipConfigBreadcrumb = skipConfigBreadcrumb;
	}

	
}
