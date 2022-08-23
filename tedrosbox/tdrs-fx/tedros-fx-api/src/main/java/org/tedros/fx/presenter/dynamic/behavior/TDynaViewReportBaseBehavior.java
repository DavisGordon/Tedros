package org.tedros.fx.presenter.dynamic.behavior;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import org.tedros.common.model.TFileEntity;
import org.tedros.core.ITModule;
import org.tedros.core.TLanguage;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.context.TedrosAppManager;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.control.PopOver;
import org.tedros.core.controller.TPropertieController;
import org.tedros.core.domain.TSystemPropertie;
import org.tedros.core.message.TMessage;
import org.tedros.core.message.TMessageType;
import org.tedros.core.setting.model.TPropertie;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.domain.TViewMode;
import org.tedros.fx.exception.TException;
import org.tedros.fx.exception.TValidatorException;
import org.tedros.fx.modal.TMessageBox;
import org.tedros.fx.presenter.behavior.TActionType;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.fx.presenter.dynamic.decorator.TDynaViewReportBaseDecorator;
import org.tedros.fx.presenter.model.TModelView;
import org.tedros.fx.process.TEntityProcess;
import org.tedros.fx.process.TReportProcess;
import org.tedros.fx.process.TReportProcessEnum;
import org.tedros.server.model.ITReportModel;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.util.TFileUtil;
import org.tedros.util.TedrosFolder;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;


@SuppressWarnings("rawtypes")
public abstract class TDynaViewReportBaseBehavior<M extends TModelView, E extends ITReportModel> 
extends TDynaViewSimpleBaseBehavior<M, E> {
	
	private ToggleGroup radioGroup;
	private BooleanProperty modeBtnDisableProperty;
	private BooleanProperty modeBtnVisibleProperty;
	
	private Class<E> modelClass;

	private TReportProcess runningProcess;
	private Class<? extends TReportProcess> reportProcessClass;
	
	private TDynaViewReportBaseDecorator<M> decorator;
	
	private String organization;
	private TFileEntity logotype;

	@SuppressWarnings("unchecked")
	public void load(){
		super.load();
		try{
			final TDynaPresenter<M> presenter = getPresenter();
			// check the crud settings
			
			final org.tedros.fx.annotation.process.TReportProcess tReportProcess = getPresenter().getReportProcessAnnotation();
			
			// set the crud process
			if(tReportProcess!=null){
				this.reportProcessClass = (Class<? extends TReportProcess>) tReportProcess.type();
				this.modelClass = (Class<E>) tReportProcess.model();
			}
			
			this.decorator = (TDynaViewReportBaseDecorator<M>) presenter.getDecorator();
			
			//final TForm tForm = presenter.getFormAnnotation();
			final TBehavior tBehavior = presenter.getPresenterAnnotation().behavior();
			
			// set the custom behavior actions
			loadAction(presenter, tBehavior.action());
			
			TEntityProcess<TPropertie> pp = new TEntityProcess(TPropertie.class, TPropertieController.JNDI_NAME) {};
			pp.stateProperty().addListener((a,o,n)->{
				if(n.equals(State.SUCCEEDED)) {
					proccessResult(pp);
				}
			});
			TPropertie ex = new TPropertie();
			ex.setKey(TSystemPropertie.ORGANIZATION.getValue());
			pp.find(ex);
			pp.startProcess();
			
			TEntityProcess<TPropertie> pp1 = new TEntityProcess(TPropertie.class, TPropertieController.JNDI_NAME) {};
			pp1.stateProperty().addListener((a,o,n)->{
				if(n.equals(State.SUCCEEDED)) {
					proccessResult(pp1);
				}
			});
			TPropertie ex1 = new TPropertie();
			ex1.setKey(TSystemPropertie.REPORT_LOGOTYPE.getValue());
			pp1.find(ex1);
			pp1.startProcess();
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	/**
	 * @param pp
	 */
	private void proccessResult(TEntityProcess<TPropertie> pp) {
		List<TResult<TPropertie>> l = pp.getValue();
		if(l!=null && !l.isEmpty()) {
			TResult<TPropertie> r = l.get(0);
			if(r.getState().equals(TState.SUCCESS)) {
				TPropertie p = r.getValue();
				if(p.getKey().equals(TSystemPropertie.ORGANIZATION.getValue()) 
						&& p.getValue()!=null) {
					this.organization = p.getValue();
				}
				if(p.getKey().equals(TSystemPropertie.REPORT_LOGOTYPE.getValue()) 
						&& p.getFile()!=null) {
					this.logotype = p.getFile();
				}
			}
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
		if(isUserAuthorized(TAuthorizationType.SEARCH)){
			final Button searchButton = this.decorator.gettSearchButton();
			searchButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					searchAction();
				}
			});
		}
	}
	
	/**
	 * Config the excel button;
	 * */
	public void configExcelButton() {
		if(isUserAuthorized(TAuthorizationType.EXPORT)){
			final Button excelButton = this.decorator.gettExcelButton();
			excelButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					excelAction();
				}
			});
		}
	}
	
	
	/**
	 * Config the pdf button;
	 * */
	public void configPdfButton() {
		if(isUserAuthorized(TAuthorizationType.EXPORT)){
			final Button pdfButton = this.decorator.gettPdfButton();
			pdfButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					pdfAction();
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
	 * Config the cancel button;
	 * */
	public void configOpenExportFolderButton() {
		final Button openExportButton = this.decorator.gettOpenExportFolderButton();
		openExportButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				openExportFolderAction();
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
	
	
	// ACTIONS
	
	/**
	 * Perform this action when colapse button onAction is triggered.
	 * */
	public abstract void colapseAction();
	
	/**
	 * Perform this action when a model is selected.
	 * */
	public void selectedItemAction(M new_val) {
		setModelView(new_val);
		if(actionHelper.runBefore(TActionType.SELECTED_ITEM)){
			if(new_val==null)
				return;
			
			if(!selectMode())
				showForm(getViewMode());
			
		}
		actionHelper.runAfter(TActionType.SELECTED_ITEM);
	}
	
	/**
	 * Perform this action when search button onAction is triggered.
	 * */
	public void searchAction() {
		if(actionHelper.runBefore(TActionType.SEARCH)){
			try{
				if(getReportProcessClass()!=null){
					runSearchReportProcess();
				}else{
					throw new TException("Error: No process configuration found in "+getModelViewClass().getSimpleName()+" model view class, use @TEntityProcess or @TModelProcess to do that.");
				}
				
			}catch(TValidatorException e){
				getView().tShowModal(new TMessageBox(e), true);
			} catch (Throwable e) {
				getView().tShowModal(new TMessageBox(e), true);
				e.printStackTrace();
			}
		}
		
		
	}
	
	@SuppressWarnings("unchecked")
	private void startExportProcess(TReportProcessEnum type, String folderPath){
		
		try {
			runningProcess  = createProcess();
		
			if(this.organization!=null)
				runningProcess.setOrganization(organization);
			if(this.logotype!=null) 
				runningProcess.setLogoInputStream(new ByteArrayInputStream(this.logotype.getByte().getBytes()));
			
			if(type.equals(TReportProcessEnum.EXPORT_XLS))
				runningProcess.exportXLS((ITReportModel) getModelView().getModel(), folderPath);
			else
				runningProcess.exportPDF((ITReportModel) getModelView().getModel(), folderPath);
			
			runningProcess.stateProperty().addListener(new ChangeListener<State>() {
				@Override
				public void changed(ObservableValue<? extends State> arg0, State arg1, State arg2) {
					if(arg2.equals(State.SUCCEEDED)){
						TResult<E> result = (TResult<E>) runningProcess.getValue();
						if(result.getState().getValue() == TState.SUCCESS.getValue()){
							String msg = iEngine.getFormatedString("#{tedros.fxapi.message.export}", result.getMessage());
							getView().tShowModal(new TMessageBox(msg), true);
							try {
								TedrosContext.openDocument(result.getMessage());
							} catch (Exception e) {
								getView().tShowModal(new TMessageBox(e), true);
							}
						}
						if(result.getState().getValue() == TState.ERROR.getValue()){
							System.out.println(result.getMessage());
							String msg = iEngine.getFormatedString("#{tedros.fxapi.message.error}");
							getView().tShowModal(new TMessageBox(msg), true);
						}
					}
				}
	
				
			});
			runProcess(runningProcess);
		} catch (InstantiationException | IllegalAccessException e1) {
			e1.printStackTrace();
			String msg = iEngine.getFormatedString("#{tedros.fxapi.message.error}");
			getView().tShowModal(new TMessageBox(msg), true);
		}
	}

	@SuppressWarnings("unchecked")
	private void runSearchReportProcess()
			throws Exception, TValidatorException, Throwable {
		//recupera a lista de models views
		//final ObservableList<M> modelsViewsList = this.decorator.gettListView().getItems(); 
		
		final ObservableList modelsViewsList =  (ObservableList) (getModelView()!=null 
						? FXCollections.observableList(Arrays.asList(getModelView()))
								: null);
						
		if(modelsViewsList == null)
			throw new Exception("No value was find to be saved!");
						
		// valida os models views
		validateModels(modelsViewsList);
		// salva os models views
		for(int x=0; x<modelsViewsList.size(); x++){
			
			final M model = (M) modelsViewsList.get(x);
			
			runningProcess  = createProcess();
			runningProcess.search((ITReportModel) model.getModel());
			runningProcess.stateProperty().addListener(new ChangeListener<State>() {
				@Override
				public void changed(ObservableValue<? extends State> arg0, State arg1, State arg2) {
					if(arg2.equals(State.SUCCEEDED)){
						TResult<E> result = (TResult<E>) runningProcess.getValue();
						if(result==null)
							return;
						
						E entity = (E) result.getValue();
						if(entity!=null){
							try {
								model.reload(entity);
								if(entity!=null && !entity.getResult().isEmpty())
									setDisableModelActionButtons(false);
							} catch (Exception e) 
							{	
								addMessage(new TMessage(TMessageType.ERROR, iEngine.getString("#{tedros.fxapi.message.error}")+"\n"+e.getMessage()));
								e.printStackTrace();
							}
						}
						if(result.getState().getValue() == TState.ERROR.getValue()){
							System.out.println(result.getMessage());
							addMessage(new TMessage(TMessageType.ERROR, result.getMessage()));
						}
					}
					actionHelper.runAfter(TActionType.SEARCH);
				}

				
			});
			runProcess(runningProcess);
		}
	}
	
		
	/**
	 * Perform this action when clean button onAction is triggered.
	 * */
	public void cleanAction() {
		if(actionHelper.runBefore(TActionType.CLEAN)){
			setDisableModelActionButtons(true);
			try {
				super.removeAllListenerFromModelView();
				M model = (M) getModelViewClass().getConstructor(modelClass).newInstance(modelClass.newInstance());
				setModelView(model);
				showForm(TViewMode.EDIT);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		}
		actionHelper.runAfter(TActionType.CLEAN);
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
	 * Perform this action when cancel button onAction is triggered.
	 * */
	public void cancelAction() {
		if(actionHelper.runBefore(TActionType.CANCEL)){
			try{
				if(runningProcess!=null && runningProcess.isRunning())
					runningProcess.cancel();
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		actionHelper.runAfter(TActionType.CANCEL);
	}
	
	/**
	 * Perform this action when open export folder button onAction is triggered.
	 * */
	public void openExportFolderAction() {
		
		Thread thread = new Thread(new Runnable() {
		      @Override
		      public void run() {
				Platform.runLater(new Runnable() {
		            @Override
		            public void run() {
		                if(!TFileUtil.open(new File(TedrosFolder.EXPORT_FOLDER.getFullPath()))) {
		                	Label label = new Label(TLanguage.getInstance(null).getString("#{tedros.fxapi.message.os.not.support.operation}"));
		        			label.setId("t-label");
		        			label.setStyle(	"-fx-font: Arial; "+
		        							"-fx-font-size: 1.0em; "+
		        							"-fx-font-weight: bold; "+
		        							"-fx-font-smoothing-type:lcd; "+
		        							"-fx-text-fill: #000000; "+
		        							"-fx-padding: 2 5 5 2; ");
		        			
		        			PopOver p = new PopOver(label);
		        			p.show(decorator.gettOpenExportFolderButton());
		                }
		            }
		          });
		      	}
			});
		thread.setDaemon(true);
		thread.start();
		
	}
	
	/**
	 * Perform this action when excel button onAction is triggered.
	 * */
	public void excelAction() {
		if(actionHelper.runBefore(TActionType.EXPORT_EXCEL)){
			if(getModelView()==null)
				return;
			startExportProcess(TReportProcessEnum.EXPORT_XLS, null);
		}
		actionHelper.runAfter(TActionType.EXPORT_EXCEL);
		
	}
	/**
	 * Perform this action when excel button onAction is triggered.
	 * */
	public void pdfAction() {
		if(actionHelper.runBefore(TActionType.EXPORT_PDF)){
			if(getModelView()==null)
				return;
			
			startExportProcess(TReportProcessEnum.EXPORT_PDF, null);
		}
		actionHelper.runAfter(TActionType.EXPORT_PDF);
		
	}
	
	public TReportProcess createProcess() throws InstantiationException, IllegalAccessException  {
		
		if(this.reportProcessClass != null && this.reportProcessClass != TReportProcess.class)
			return reportProcessClass.newInstance();
		
		return null;
	}
	
	/**
	 * Perform this action when a mode radio change listener is triggered.
	 * */
	public void changeModeAction() {
		if(actionHelper.runBefore(TActionType.CHANGE_MODE)){
			if(getModelView()!=null){
				showForm(null);
			}
		}
		actionHelper.runAfter(TActionType.CHANGE_MODE);
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
	
	/**
	 * Return the {@link TReportProcess} class
	 * */
	public Class<? extends TReportProcess> getReportProcessClass() {
		return reportProcessClass;
	}

	/**
	 * @return the modeBtnDisableProperty
	 */
	public BooleanProperty getModeBtnDisableProperty() {
		return modeBtnDisableProperty;
	}

	/**
	 * @param modeBtnDisableProperty the modeBtnDisableProperty to set
	 */
	public void setModeBtnDisableProperty(BooleanProperty modeBtnDisableProperty) {
		this.modeBtnDisableProperty = modeBtnDisableProperty;
	}

	/**
	 * @return the modeBtnVisibleProperty
	 */
	public BooleanProperty getModeBtnVisibleProperty() {
		return modeBtnVisibleProperty;
	}

	/**
	 * @param modeBtnVisibleProperty the modeBtnVisibleProperty to set
	 */
	public void setModeBtnVisibleProperty(BooleanProperty modeBtnVisibleProperty) {
		this.modeBtnVisibleProperty = modeBtnVisibleProperty;
	}

	public void setDisableModelActionButtons(boolean flag) {
		if(decorator.gettCancelButton()!=null)
			decorator.gettCancelButton().setDisable(flag);
		if(decorator.gettPdfButton()!=null && isUserAuthorized(TAuthorizationType.EXPORT))
			decorator.gettPdfButton().setDisable(flag);
		if(decorator.gettExcelButton()!=null && isUserAuthorized(TAuthorizationType.EXPORT))
			decorator.gettExcelButton().setDisable(flag);
	}

	/**
	 * @return the modelClass
	 */
	public Class<E> getModelClass() {
		return modelClass;
	}

	/**
	 * @return the runningProcess
	 */
	public TReportProcess getRunningProcess() {
		return runningProcess;
	}

	/**
	 * @return the decorator
	 */
	public TDynaViewReportBaseDecorator<M> getDecorator() {
		return decorator;
	}
	
	
	
}
