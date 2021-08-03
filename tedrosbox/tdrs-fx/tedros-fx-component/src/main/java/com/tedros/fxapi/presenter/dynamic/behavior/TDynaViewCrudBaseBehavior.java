package com.tedros.fxapi.presenter.dynamic.behavior;

import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;

import com.tedros.core.ITModule;
import com.tedros.core.TModule;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.context.TEntry;
import com.tedros.core.context.TEntryBuilder;
import com.tedros.core.context.TedrosAppManager;
import com.tedros.core.context.TedrosContext;
import com.tedros.core.presenter.ITPresenter;
import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.ejb.base.model.ITModel;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.process.TEjbService;
import com.tedros.fxapi.collections.TFXCollections;
import com.tedros.fxapi.control.action.TPresenterAction;
import com.tedros.fxapi.domain.TViewMode;
import com.tedros.fxapi.exception.TException;
import com.tedros.fxapi.exception.TValidatorException;
import com.tedros.fxapi.form.ITModelForm;
import com.tedros.fxapi.layout.TBreadcrumbForm;
import com.tedros.fxapi.modal.TConfirmMessageBox;
import com.tedros.fxapi.modal.TMessageBox;
import com.tedros.fxapi.presenter.behavior.TActionState;
import com.tedros.fxapi.presenter.behavior.TActionType;
import com.tedros.fxapi.presenter.behavior.TProcessResult;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.dynamic.decorator.TDynaViewCrudBaseDecorator;
import com.tedros.fxapi.presenter.dynamic.view.TDynaView;
import com.tedros.fxapi.presenter.model.TModelView;
import com.tedros.fxapi.presenter.view.group.TGroupPresenter;
import com.tedros.fxapi.process.TEntityProcess;
import com.tedros.fxapi.util.HtmlPDFExportHelper;
import com.tedros.fxapi.util.TPrintUtil;

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

	private TPresenterAction<TDynaPresenter<M>> newAction;
	private TPresenterAction<TDynaPresenter<M>> printAction;
	private TPresenterAction<TDynaPresenter<M>> importAction;
	private TPresenterAction<TDynaPresenter<M>> saveAction;
	private TPresenterAction<TDynaPresenter<M>> deleteAction;
	private TPresenterAction<TDynaPresenter<M>> changeModeAction;
	private TPresenterAction<TDynaPresenter<M>> selectedItemAction;
	private TPresenterAction<TDynaPresenter<M>> editAction;
	private TPresenterAction<TDynaPresenter<M>> cancelAction;
	
	private Class<? extends TModelView> importFileModelViewClass;
	
	private boolean remoteMode;
	private String serviceName;
	private Class<E> entityClass;

	private Class<? extends TEntityProcess> entityProcessClass;
	
	private TDynaViewCrudBaseDecorator<M> decorator;

	private boolean saveAllModels;
	private boolean saveOnlyChangedModel;
	private boolean runNewActionAfterSave;

	private boolean skipConfigBreadcrumb;
	
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
			if(tBehavior.saveAction()!=TPresenterAction.class)
				saveAction = tBehavior.saveAction().newInstance();
			if(tBehavior.newAction()!=TPresenterAction.class)
				newAction = tBehavior.newAction().newInstance();
			if(tBehavior.importAction()!=TPresenterAction.class)
				importAction = tBehavior.importAction().newInstance();
			if(tBehavior.deleteAction()!=TPresenterAction.class)
				deleteAction = tBehavior.deleteAction().newInstance();
			if(tBehavior.selectedItemAction()!=TPresenterAction.class)
				selectedItemAction = tBehavior.selectedItemAction().newInstance();
			if(tBehavior.editAction()!=TPresenterAction.class)
				editAction = tBehavior.editAction().newInstance();
			if(tBehavior.cancelAction()!=TPresenterAction.class)
				cancelAction = tBehavior.cancelAction().newInstance();
			if(tBehavior.printAction()!=TPresenterAction.class)
				printAction = tBehavior.printAction().newInstance();
			if(tBehavior.changeModeAction()!=TPresenterAction.class)
				changeModeAction = tBehavior.changeModeAction().newInstance();
			
			ChangeListener<TModelView> mvcl = (a0, old_, new_) -> {
				processModelView(new_);
			};
			
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
			this.decorator.showScreenSaver();
			return;
		}
			
		if(!selectMode()) {
			if(decorator.isShowBreadcrumBar() && decorator.gettBreadcrumbForm()!=null)
				decorator.gettBreadcrumbForm().tEntryListProperty().clear();
			showForm(getViewMode());
			setDisableModelActionButtons(false);
			
		}
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
		sp.setId("t-header-box");
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
		if(selectedItemAction==null || (selectedItemAction!=null && selectedItemAction.runBefore(presenter))){
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
		if(selectedItemAction!=null)
			selectedItemAction.runAfter(presenter);
		setActionState(new TActionState<>(TActionType.SELECTED_ITEM, TProcessResult.FINISHED, new_val));
	}
	
	/**
	 * Perform this action when save button onAction is triggered.
	 * */
	public void saveAction() {
		setActionState(new TActionState<>(TActionType.SAVE, TProcessResult.RUNNING));
		final TDynaPresenter<M> presenter = getPresenter();
		if(saveAction==null || (saveAction!=null && saveAction.runBefore(presenter))){
			
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
			throw new Exception("No value was find to be saved!");
						
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
							if(saveAction!=null)
								saveAction.runAfter(getPresenter());
							setActionState(new TActionState(TActionType.SAVE, arg2, TProcessResult.NO_RESULT));
							return;
						}
						TResult result = resultados.get(0);
						if(result.getResult().equals(EnumResult.SUCESS)){
							E entity = (E) result.getValue();
							if(entity!=null){
								try {
									model.reload(entity);
									String msg = result.isPriorityMessage() 
											? result.getMessage()
													: iEngine.getFormatedString("#{tedros.fxapi.message.save}", model.getDisplayProperty().getValue());
									if(runNewAction) {
										getView().tModalVisibleProperty().addListener(new ChangeListener<Boolean>() {
											@Override
											public void changed(ObservableValue<? extends Boolean> arg0, Boolean b, Boolean c) {
												if(!c) {
													getView().tModalVisibleProperty().removeListener(this);
													if(saveAction!=null)
														saveAction.runAfter(getPresenter());
													newAction();
												}
											}
										});
									}
									addMessage(msg);
									setActionState(new TActionState(TActionType.SAVE, arg2, TProcessResult.SUCCESS));
								} catch (Exception e) {	
									e.printStackTrace();
									addMessage(e.getMessage());
									
								}
							}
							if(saveAction!=null && !runNewAction)
								saveAction.runAfter(getPresenter());
						}else{
							System.out.println(result.getMessage());
							String msg = result.getResult().equals(EnumResult.OUTDATED) 
									? iEngine.getString("#{tedros.fxapi.message.outdate}")
											: result.getMessage();
							addMessage(msg);
							setActionState(new TActionState(TActionType.SAVE, arg2, TProcessResult.get(result.getResult()), result.getMessage()));	
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
		final TDynaPresenter<M> presenter = getPresenter();
		try{
			if(importAction==null || (importAction!=null && importAction.runBefore(presenter))){
				
					StackPane pane = new StackPane();
					if(getModels()==null)
						setModelViewList(TFXCollections.iTObservableList());
					TDynaView view = new TDynaView(importFileModelViewClass, getModels());
					pane.setMaxSize(950, 600);
					pane.getChildren().add(view);
					pane.setId("t-tedros-color");
					pane.setStyle("-fx-effect: dropshadow( three-pass-box , white , 4 , 0.4 , 0 , 0 );");
					view.tLoad();
					TedrosAppManager.getInstance()
					.getModuleContext((TModule)TedrosContext.getView()).getCurrentViewContext()
					.getPresenter().getView().tShowModal(pane, false);
				
			}
			if(importAction!=null)
				importAction.runAfter(presenter);
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
		final TDynaPresenter<M> presenter = getPresenter();
		try{
			M model = null;
			if(newAction==null || (newAction!=null && newAction.runBefore(presenter))){
				
					final Class<E> entityClass = getEntityClass();
					model = (M) getModelViewClass().getConstructor(entityClass).newInstance(entityClass.newInstance());
					
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
			if(newAction!=null)
				newAction.runAfter(presenter);
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
		final TDynaPresenter<M> presenter = getPresenter();
		try{
			if(printAction==null || (printAction!=null && printAction.runBefore(presenter))){
				TPrintUtil.print((Node) super.getForm());
			}
			if(printAction!=null)
				printAction.runAfter(presenter);
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
		final TDynaPresenter<M> presenter = getPresenter();
		try{
			if(editAction==null || (editAction!=null && editAction.runBefore(presenter))){
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
			if(editAction!=null)
				editAction.runAfter(presenter);
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
		final TDynaPresenter<M> presenter = getPresenter();
		if(cancelAction==null || (cancelAction!=null && cancelAction.runBefore(presenter))){
			try{
				final M model = getModelView();
				if(model.isChanged()) {
				
					String message = iEngine.getFormatedString("#{tedros.fxapi.message.cancel}");
					
					final TConfirmMessageBox confirm = new TConfirmMessageBox(message);
					confirm.gettConfirmProperty().addListener(new ChangeListener<Number>() {
						@SuppressWarnings("unchecked")
						@Override
						public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
							if(arg2.equals(1)){
								if(model.getModel() instanceof ITEntity && ((ITEntity)model.getModel()).isNew()) {
									getView().tHideModal();	
									remove();
									
									if(cancelAction!=null)
										cancelAction.runAfter(presenter);
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
														if(result.getResult().equals(EnumResult.ERROR)) {
															System.out.println(result.getMessage());
															addMessage(result.getMessage());
															setActionState(new TActionState<>(TActionType.CANCEL, TProcessResult.ERROR, result.getMessage()));
															
														}else{
															E entity = (E) result.getValue();
															if(entity!=null) {
																model.reload(entity);
																setModelView(null);
																final TDynaPresenter<M> presenter = getPresenter();
																if(cancelAction!=null)
																	cancelAction.runAfter(presenter);
																setActionState(new TActionState<>(TActionType.CANCEL, TProcessResult.SUCCESS));
															}else {
																addMessage(iEngine.getString("#{tedros.fxapi.message.error}"));
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
					
					if(cancelAction!=null)
						cancelAction.runAfter(presenter);
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
									if(result.getResult().equals(EnumResult.ERROR)) {
										System.out.println(result.getMessage());
										addMessage(result.getMessage());
										setActionState(new TActionState(TActionType.DELETE, arg2, TProcessResult.ERROR, result.getMessage()));
									}else if(result.getResult().equals(EnumResult.WARNING)){
										E entity = (E) result.getValue();
										if(entity!=null){
											selected.reload(entity);
										}else{
											remove();
											removeAllListenerFromModelView();
											setModelView(null);
										}
										addMessage(result.getMessage());
										setActionState(new TActionState(TActionType.DELETE, arg2, TProcessResult.WARNING, result.getMessage()));
									}else
									if(result.getResult().equals(EnumResult.SUCESS)){
										remove();
										removeAllListenerFromModelView();
										setModelView(null);
										final TDynaPresenter<M> presenter = getPresenter();
										if(deleteAction!=null)
											deleteAction.runAfter(presenter);
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
		final TDynaPresenter<M> presenter = getPresenter();
		if(changeModeAction==null || (changeModeAction!=null && changeModeAction.runBefore(presenter))){
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
		
		if(changeModeAction!=null)
			changeModeAction.runAfter(presenter);
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

	/**
	 * @return the printAction
	 */
	public TPresenterAction<TDynaPresenter<M>> getPrintAction() {
		return printAction;
	}

	/**
	 * @param printAction the printAction to set
	 */
	public void setPrintAction(TPresenterAction<TDynaPresenter<M>> printAction) {
		this.printAction = printAction;
	}
	
}
