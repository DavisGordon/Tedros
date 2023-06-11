package org.tedros.fx.presenter.dynamic.behavior;

import java.lang.reflect.InvocationTargetException;

import org.tedros.api.presenter.view.TViewMode;
import org.tedros.core.ITModule;
import org.tedros.core.TModule;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.context.TedrosAppManager;
import org.tedros.core.context.TedrosContext;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.exception.TValidatorException;
import org.tedros.fx.modal.TMessageBox;
import org.tedros.fx.model.TModelView;
import org.tedros.fx.presenter.behavior.TActionType;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.fx.presenter.dynamic.decorator.TDynaViewActionBaseDecorator;
import org.tedros.server.model.ITModel;

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


/**
 * The basic behavior for custom action view.
 * 
 * For entity processing, use {@link TEjbService} 
 * on TEntityModelView.
 * @author Davis Gordon
 *
 * @param <M>
 * @param <E>
 */
@SuppressWarnings("rawtypes")
public abstract class TDynaViewActionBaseBehavior<M extends TModelView, E extends ITModel> 
extends TDynaViewSimpleBaseBehavior<M, E> {
	
	private ToggleGroup radioGroup;
	private BooleanProperty modeBtnDisableProperty;
	private BooleanProperty modeBtnVisibleProperty;
	
	protected Class<E> modelClass;
	protected String serviceName;
		
	private TDynaViewActionBaseDecorator<M> decorator;
	
	@Override
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
			loadAction(presenter, tBehavior.action());
			
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
		if(actionHelper.runBefore(TActionType.CHANGE_MODE)){
			if(getModelView()!=null){
				showForm(null);
			}
		}
		actionHelper.runAfter(TActionType.CHANGE_MODE);
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
		if(actionHelper.runBefore(TActionType.CLEAN)){
			processClean();
		}
		actionHelper.runAfter(TActionType.CLEAN);
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
		if(actionHelper.runBefore(TActionType.SELECTED_ITEM)){
			processSelectedItem(new_val);
		}
		actionHelper.runAfter(TActionType.SELECTED_ITEM);
	}
	
	/**
	 * Process the selected item 
	 * @param new_val
	 */
	protected abstract void processSelectedItem(TModelView new_val);


	/**
	 * Perform this action when search button onAction is triggered.
	 * */
	public void actionAction() {
		if(actionHelper.runBefore(TActionType.ACTION)){
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
	
	/**
	 * Process the custom action
	 * 
	 * @throws TValidatorException
	 * @throws Exception
	 */
	protected abstract void processAction() throws TValidatorException, Exception;

	/**
	 * Returns the presenter of the current open ITModule.
	 * This is commonly used when dealing with a view/behavior 
	 * under another view/behavior such as a detail view or modal view.
	 * @return TDynaPresenter
	 */
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
		if(actionHelper.runBefore(TActionType.CLOSE)){
			try{
				closeModal();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		actionHelper.runAfter(TActionType.CLOSE);
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
