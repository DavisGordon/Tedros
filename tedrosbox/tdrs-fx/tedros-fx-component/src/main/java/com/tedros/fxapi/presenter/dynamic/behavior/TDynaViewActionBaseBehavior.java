package com.tedros.fxapi.presenter.dynamic.behavior;

import java.lang.reflect.InvocationTargetException;

import com.tedros.core.ITModule;
import com.tedros.core.TModule;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.context.TedrosAppManager;
import com.tedros.core.context.TedrosContext;
import com.tedros.ejb.base.model.ITModel;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.process.TEjbService;
import com.tedros.fxapi.control.action.TPresenterAction;
import com.tedros.fxapi.domain.TViewMode;
import com.tedros.fxapi.exception.TValidatorException;
import com.tedros.fxapi.modal.TMessageBox;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.dynamic.decorator.TDynaViewActionBaseDecorator;
import com.tedros.fxapi.presenter.model.TModelView;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;


@SuppressWarnings("rawtypes")
public abstract class TDynaViewActionBaseBehavior<M extends TModelView, E extends ITModel> 
extends TDynaViewSimpleBaseBehavior<M, E> {
	
	private ToggleGroup radioGroup;
	private BooleanProperty modeBtnDisableProperty;
	private BooleanProperty modeBtnVisibleProperty;

	
	private TPresenterAction<TDynaPresenter<M>> cleanAction;
	private TPresenterAction<TDynaPresenter<M>> actionAction;
	private TPresenterAction<TDynaPresenter<M>> selectedItemAction;
	private TPresenterAction<TDynaPresenter<M>> closeAction;
	private TPresenterAction<TDynaPresenter<M>> changeModeAction;
	
	protected Class<E> modelClass;
	protected String serviceName;
		
	private TDynaViewActionBaseDecorator<M> decorator;

	

	@SuppressWarnings("unchecked")
	public void load(){
		super.load();
		try{
			
			final TDynaPresenter<M> presenter = getPresenter();
			
			this.decorator = (TDynaViewActionBaseDecorator<M>) presenter.getDecorator();
			
			final TEjbService tEjbService = getPresenter().getEjbServiceAnnotation();
			
			if(tEjbService!=null){
				this.serviceName = tEjbService.serviceName();
				this.modelClass = (Class<E>) tEjbService.model();
			}
			
			final TBehavior tBehavior = presenter.getPresenterAnnotation().behavior();
			
			// set the custom behavior actions
			if(tBehavior.actionAction()!=TPresenterAction.class)
				actionAction = tBehavior.actionAction().newInstance();
			if(tBehavior.cleanAction()!=TPresenterAction.class)
				cleanAction = tBehavior.cleanAction().newInstance();
			if(tBehavior.selectedItemAction()!=TPresenterAction.class)
				selectedItemAction = tBehavior.selectedItemAction().newInstance();
			if(tBehavior.closeAction()!=TPresenterAction.class)
				closeAction = tBehavior.closeAction().newInstance();
			if(tBehavior.changeModeAction()!=TPresenterAction.class)
				changeModeAction = tBehavior.changeModeAction().newInstance();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
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
		
		ChangeListener<Toggle> listener = (a0, a1, a2) -> changeModeAction();
		super.getListenerRepository().add("modesRadioCL", listener);
		radioGroup.selectedToggleProperty().addListener(new WeakChangeListener<Toggle>(listener));
		
		modeBtnDisableProperty = new SimpleBooleanProperty();
		modeBtnVisibleProperty = new SimpleBooleanProperty(true);
		
		editRadio.disableProperty().bindBidirectional(modeBtnDisableProperty);
		editRadio.visibleProperty().bindBidirectional(modeBtnVisibleProperty);
		readRadio.disableProperty().bindBidirectional(modeBtnDisableProperty);
		readRadio.visibleProperty().bindBidirectional(modeBtnVisibleProperty);
		
		ChangeListener<Boolean> invCL = (a0, a1, a2) -> {
			editRadio.disableProperty().unbindBidirectional(modeBtnDisableProperty);
			editRadio.visibleProperty().unbindBidirectional(modeBtnVisibleProperty);
			readRadio.disableProperty().unbindBidirectional(modeBtnDisableProperty);
			readRadio.visibleProperty().unbindBidirectional(modeBtnVisibleProperty);
		};
		getListenerRepository().add("invalidateModeUnBind", invCL);
		invalidateProperty().addListener(new WeakChangeListener<>(invCL));
		
		setViewMode(TViewMode.EDIT);
		
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
	
	public ToggleGroup getRadioGroup() {
		return radioGroup;
	}
	
	public boolean isRadioGroupBuilt(){
		return radioGroup!=null;
	}
	
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
	 * Config the action button
	 * */
	public void configActionButton() {
		final Button actionButton = this.decorator.gettActionButton();
		actionButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				actionAction();
			}
		});
		
	}
	
	
	// ACTIONS
	
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
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Perform this action when a model is selected.
	 * */
	public void selectedItemAction(TModelView new_val) {
		final TDynaPresenter<M> presenter = getPresenter();
		if(selectedItemAction==null || (selectedItemAction!=null && selectedItemAction.runBefore(presenter))){
			processSelectedItem(new_val);
		}
		if(selectedItemAction!=null)
			selectedItemAction.runAfter(presenter);
	}
	
	protected abstract void processSelectedItem(TModelView new_val);


	/**
	 * Perform this action when search button onAction is triggered.
	 * */
	public void actionAction() {
		final TDynaPresenter<M> presenter = getPresenter();
		if(actionAction==null || (actionAction!=null && actionAction.runBefore(presenter))){
			
			try{
				processAction();
			}catch(TValidatorException e){
				getView().tShowModal(new TMessageBox(e), true);
			} catch (Throwable e) {
				getView().tShowModal(new TMessageBox(e), true);
				e.printStackTrace();
			}
		}
		
		
	}
	

	protected abstract void processAction() throws TValidatorException, Exception;


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
		return actionAction;
	}

	/**
	 * @param searchAction the searchAction to set
	 */
	public void setSearchAction(TPresenterAction<TDynaPresenter<M>> searchAction) {
		this.actionAction = searchAction;
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
	 * @return the actionAction
	 */
	public TPresenterAction<TDynaPresenter<M>> getActionAction() {
		return actionAction;
	}


	/**
	 * @param actionAction the actionAction to set
	 */
	public void setActionAction(TPresenterAction<TDynaPresenter<M>> actionAction) {
		this.actionAction = actionAction;
	}


	/**
	 * @return the closeAction
	 */
	public TPresenterAction<TDynaPresenter<M>> getCloseAction() {
		return closeAction;
	}


	/**
	 * @param closeAction the closeAction to set
	 */
	public void setCloseAction(TPresenterAction<TDynaPresenter<M>> closeAction) {
		this.closeAction = closeAction;
	}


	/**
	 * @return the decorator
	 */
	public TDynaViewActionBaseDecorator<M> getDecorator() {
		return decorator;
	}


	/**
	 * @return the serviceName
	 */
	protected String getServiceName() {
		return serviceName;
	}


	/**
	 * @param serviceName the serviceName to set
	 */
	protected void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}



	
	
	
}
