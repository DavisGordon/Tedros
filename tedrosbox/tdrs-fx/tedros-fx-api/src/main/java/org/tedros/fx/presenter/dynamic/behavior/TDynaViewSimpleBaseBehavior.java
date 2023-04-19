package org.tedros.fx.presenter.dynamic.behavior;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.tedros.api.presenter.ITPresenter;
import org.tedros.api.presenter.view.ITView;
import org.tedros.api.presenter.view.TViewState;
import org.tedros.app.process.ITProcess;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.core.context.TSecurityDescriptor;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.message.TMessage;
import org.tedros.core.message.TMessageType;
import org.tedros.fx.TFxKey;
import org.tedros.fx.control.action.TPresenterAction;
import org.tedros.fx.control.validator.TControlValidator;
import org.tedros.fx.control.validator.TValidatorResult;
import org.tedros.fx.exception.TValidatorException;
import org.tedros.fx.modal.TMessageBox;
import org.tedros.fx.presenter.behavior.TActionHelper;
import org.tedros.fx.presenter.behavior.TActionState;
import org.tedros.fx.presenter.behavior.TActionType;
import org.tedros.fx.presenter.behavior.TBehavior;
import org.tedros.fx.presenter.behavior.TProcessResult;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.fx.presenter.dynamic.decorator.TDynaViewSimpleBaseDecorator;
import org.tedros.fx.presenter.dynamic.view.TDynaView;
import org.tedros.fx.presenter.model.TModelView;
import org.tedros.fx.process.TModelProcess;
import org.tedros.server.model.ITModel;
import org.tedros.server.result.TResult;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.WeakListChangeListener;
import javafx.concurrent.Worker.State;
import javafx.scene.layout.StackPane;

/***
 * The basic behavior for the {@link TDynaView} view.
 * This behavior is applied on views and models 
 * that don't need to access the business layer on 
 * the application server. 
 * For model processing use {@link org.tedros.fx.annotation.process.TModelProcess}
 * in the TModelView.
 * @author Davis Gordon
 *
 * @param <M>
 * @param <E>
 */
@SuppressWarnings("rawtypes")
public abstract class TDynaViewSimpleBaseBehavior<M extends TModelView, E extends ITModel> 
extends TBehavior<M, TDynaPresenter<M>> {
	
	private TDynaViewSimpleBaseDecorator decorator;
	private TMessageBox tMessageBox;
	
	protected TActionHelper actionHelper = new TActionHelper();
	
	private Class<? extends TModelProcess> modelProcessClass;
	private Class<M> modelViewClass;
	
	private boolean skipChangeValidation;
	private boolean skipRequiredValidation;
	private boolean showMessages = true;
	
	private List<TAuthorizationType> userAuthorizations;
	
	private final ObjectProperty<TActionState<M>> actionStateProperty = new SimpleObjectProperty<>();
	private final ObservableList<TMessage> messagesProperty = FXCollections.observableArrayList();

	@Override
	@SuppressWarnings("unchecked")
	public void load(){	
		
		final TDynaPresenter<M> presenter = getPresenter(); 
		
		this.decorator = (TDynaViewSimpleBaseDecorator) this.getPresenter().getDecorator();
		
		this.modelViewClass = presenter.getModelViewClass();
		setModelView(presenter.getModelView());
		setModelViewList(presenter.getModelViews());
		
		buildUserAuthorizations();
		verifyViewAccessAutorization();
		
		final org.tedros.fx.annotation.process.TModelProcess tModelProcess = getPresenter().getModelProcessAnnotation();
		
		if(tModelProcess!=null){
			this.modelProcessClass = tModelProcess.type();
		}
		
		ListChangeListener<TMessage> msgLtnr = c -> {
			if(showMessages) {
				if(c.next()) {
					if(c.wasAdded()) {
						if(tMessageBox==null) {
							tMessageBox = new TMessageBox((List<TMessage>) c.getAddedSubList());
							getView().tShowModal(tMessageBox, true);
						}else
							tMessageBox.tAddMessage((List<TMessage>) c.getAddedSubList());
					}else if(c.wasRemoved()) {
						tMessageBox.tDispose();
						tMessageBox = null;
					}
				}
			}else if(!c.getList().isEmpty())
				messagesProperty.clear();
		};
		super.getListenerRepository().add("msgLtnr", msgLtnr);
		messagesProperty.addListener(new WeakListChangeListener<>(msgLtnr));
		
		ChangeListener<Boolean> modalCleanMsgLtnr = (a, b, c) -> {
			if(!c) {
				messagesProperty.clear();
				verifyViewAccessAutorization();
			}
		};
		super.getListenerRepository().add("modalCleanMsgLtnr", modalCleanMsgLtnr);
		getView().tModalVisibleProperty().addListener(new WeakChangeListener<Boolean>(modalCleanMsgLtnr));
	}
	
	/**
	 * Show the view screen saver
	 */
	public void showScreenSaver() {
		if(!getView().gettState().equals(TViewState.READY))
			super.setViewStateAsReady();
		decorator.showScreenSaver();
	}

	/**
	 * Checks if the logged in user are authorize to access the view.
	 * Show an access denied modal pane if not.
	 */
	protected void verifyViewAccessAutorization() {
		if(isUserNotAuthorized(TAuthorizationType.VIEW_ACCESS)){
			StackPane accessDenied = new StackPane();
			accessDenied.setId("t-access-denied");
			getView().tShowModal(accessDenied, false);
		}
	}
	
	/**
	 * Load the presenter action for the presenter.
	 * @param presenter
	 * @param action
	 */
	@SuppressWarnings("unchecked")
	protected void loadAction(ITPresenter presenter, Class<? extends TPresenterAction>... action) {
		try {
			List<TPresenterAction> l = actionHelper.build(presenter, action);
			if(l!=null)
				for(TPresenterAction a : l)
					this.addAction(a);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Add the presenter action.
	 * @param action
	 */
	public void addAction(TPresenterAction action) {
		this.actionHelper.add(action);
	}
	
	/**
	 * Retrieves a presenter action list of the given type.
	 * @param type
	 * @return List of TPresenterAction
	 */
	public List<TPresenterAction>  getActions(TActionType... type){
		return this.actionHelper.getAction(type);
	}

	/**
	 * If false none message from method addMessage will be displayed.
	 * */
	public void setShoWMessages(boolean show) {
		this.showMessages = show;
	}
	
	/**
	 * Set the actionState
	 * */
	@SuppressWarnings("unchecked")
	public void setActionState(TActionState actionState) {
		this.actionStateProperty.setValue(actionState);
	}
	
	/**
	 * Show a list of message in a modal view
	 * */
	public void addMessage(List<TMessage> msgList) {
		messagesProperty.addAll(msgList);
	}
	
	/**
	 * Show messages in a modal view
	 * */
	public void addMessage(TMessage... msg) {
		messagesProperty.addAll(msg);
	}
	
    /***
     * Get the authorizations list of the logged in user 
     * @return List of TAuthorizationType
     */
    public List<TAuthorizationType> getUserAuthorizations() {
		return userAuthorizations;
	}
    
    /**
     * Checks if the logged in user is authorized for the type.
     * @param type
     * @return true if user has the given type.
     */
    public boolean isUserAuthorized(TAuthorizationType type){
    	return userAuthorizations==null || userAuthorizations.contains(type);
    }
    
    /**
     * Checks if the logged in user is not authorized for the type.
     * @param type
     * @return true if the user does not have the provided type.
     */
    public boolean isUserNotAuthorized(TAuthorizationType type){
    	return userAuthorizations!=null && !userAuthorizations.contains(type);
    }
    
    private void buildUserAuthorizations() {
    	TSecurity modelViewSecurity = this.modelViewClass.getAnnotation(TSecurity.class);
		if(modelViewSecurity!=null){
			TSecurityDescriptor securityDescriptor = new TSecurityDescriptor(modelViewSecurity);
			userAuthorizations = new ArrayList<>();
			for(TAuthorizationType type : new TAuthorizationType[]{	TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
																	TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW, 
																	TAuthorizationType.EXPORT, TAuthorizationType.SEARCH, TAuthorizationType.PRINT}){
				if(TedrosContext.isUserAuthorized(securityDescriptor, type))
					userAuthorizations.add(type);
			}
		}
	}
	
	@Override
	public boolean invalidate() {
		ITView v = getView();
		if(v!=null && v.gettProgressIndicator()!=null) 
			v.gettProgressIndicator().removeBind();
		return super.invalidate();
		
	}
	
	/**
	 * Config a progress indicator to the process.
	 * Bind the view progress indicator to the process running property.
	 * @param process
	 */
	public void configProgressIndicator(final ITProcess<?> process) {
		getView().gettProgressIndicator().bind(process.runningProperty());
	}
	
	/**
	 * Return the {@link TModelView} class
	 * */
	public Class<M> getModelViewClass() {
		return this.modelViewClass;
	}
	
	/**
	 * <p>
	 * Create a {@link TModelProcess} process based on the 
	 * {@link org.tedros.fx.annotation.process.TModelProcess} 
	 * annotated in the given {@link TModelView}
	 * </p>  
	 * */
	@SuppressWarnings("unchecked")
	public TModelProcess<E> createModelProcess() throws Throwable {
		if(this.modelProcessClass != null && this.modelProcessClass != TModelProcess.class)
			return modelProcessClass.newInstance();
		
		return null;
	}
	
	/**
	 * Override to process the result on succeeded state 
	 * of runModelProcess method.
	 * @param modelProcess
	 * @return true to continue the process
	 */
	public boolean runWhenModelProcessSucceeded(TModelProcess<E> modelProcess){
		return true;
	}
	
	/**
	 * Override to process on failed state of runModelProcess method.
	 * @param modelProcess
	 */
	public void runWhenModelProcessFailed(TModelProcess<E> modelProcess){
		
	}
	
	/**
	 * Override to process on cancelled state of runModelProcess method.
	 * @param modelProcess
	 */
	public void runWhenModelProcessCancelled(TModelProcess<E> modelProcess){
		
	}
	
	/**
	 * Override to process on ready state of runModelProcess method.
	 * @param modelProcess
	 */
	public void runWhenModelProcessReady(TModelProcess<E> modelProcess){
		
	}
	
	/**
	 * Override to process on running state of runModelProcess method.
	 * @param modelProcess
	 */
	public void runWhenModelProcessRunning(TModelProcess<E> modelProcess){
		
	}
	
	/**
	 * Override to process on scheduled state of runModelProcess method.
	 * @param modelProcess
	 */
	public void runWhenModelProcessScheduled(TModelProcess<E> modelProcess){
		
	}
	
	/**
	 * Construct and run a TModelProcess on the current TModelView. 
	 * The action to be performed is based on the given TActionType.
	 * Target service must be defined by {@link org.tedros.fx.annotation.process.TModelProcess}
	 * in TModelView
	 * 
	 * @param action
	 * @throws Exception
	 * @throws TValidatorException
	 * @throws Throwable
	 */
	@SuppressWarnings("unchecked")
	public void runModelProcess(TActionType action)
			throws Exception, TValidatorException, Throwable {
		//recupera a lista de models views
		//final ObservableList<M> modelsViewsList = this.decorator.gettListView().getItems(); 
		final ObservableList<M> modelsViewsList = (ObservableList<M>) ((getModels()!=null) 
				? getModels() 
						: getModelView()!=null 
						? FXCollections.observableList(Arrays.asList(getModelView())) 
								: null) ;
						
		if(modelsViewsList == null)
			throw new Exception("No value was find to be saved!");
		
		// valida os models views
		if(!isSkipRequiredValidation())
			validateModels(modelsViewsList);
		
		for(int x=0; x<modelsViewsList.size(); x++){
			
			final M model = modelsViewsList.get(x);
			
			if((!isSkipChangeValidation()) && !model.isChanged())
				continue;
			
			final TModelProcess<E> process = createModelProcess();
			process.setActionType(action);
			process.setObjectToProcess((E) model.getModel());
			
			
			process.exceptionProperty().addListener(new ChangeListener<Throwable>() {

				@Override
				public void changed(ObservableValue<? extends Throwable> arg0, Throwable arg1, Throwable arg2) {
					arg2.printStackTrace();
				}				
			});
			
			process.stateProperty().addListener(new ChangeListener<State>() {
				@Override
				public void changed(ObservableValue<? extends State> arg0, State arg1, State arg2) {
					
					switch (arg2) {
						case SUCCEEDED:
							if(!runWhenModelProcessSucceeded(process))
								return;
							
							/*TODO: DEVERA SER VERIFICADO OUTRA ABORDAGEM, POIS DEIXAR IMPLICITO QUE TODO RESULTADO
							 * DEVERA RETORNAR UMA ENTIDADE NA PRIMEIRA POSIÇÃO CAUSA MUITA CONFUSÃO
							 * DEVERA SER ANALISADO  
							 * */
							final List<TResult<E>> resultados = process.getValue();
							if(resultados.isEmpty())
								return;
							TResult result = resultados.get(0);
							switch(result.getState()) {
								case SUCCESS:
									E entity = (E) result.getValue();
									if(entity!=null){
										model.reload(entity);
										String msg = iEngine.getFormatedString("#{tedros.fxapi.message.process}", model.toStringProperty().getValue());
										setActionState(new TActionState(action, arg2, TProcessResult.SUCCESS, msg, model));
										addMessage(new TMessage(TMessageType.INFO, msg));
									}else {
										setActionState(new TActionState(action, arg2, TProcessResult.NO_RESULT, model));
									}
								break;
								default:
									System.out.println(result.getMessage());
									String msg = result.getMessage();
									
									setActionState(new TActionState(action, arg2, TProcessResult.get(result.getState()), msg, model));
									switch(result.getState()) {
									case ERROR:
										if(msg==null)
											msg = TFxKey.MESSAGE_ERROR;
										addMessage(new TMessage(TMessageType.ERROR, iEngine.getFormatedString(msg)));
										break;
									default:
										if(msg!=null)
											addMessage(new TMessage(TMessageType.WARNING, iEngine.getFormatedString(msg)));
										break;
									}
								break;
							}
						break;
						case CANCELLED:
							runWhenModelProcessCancelled(process);
							setActionState(new TActionState(action, arg2));
						break;
						
						case FAILED:
							runWhenModelProcessFailed(process);
							setActionState(new TActionState(action, arg2));
						break;
						
						case READY:
							runWhenModelProcessReady(process);
							setActionState(new TActionState(action, arg2));
						break;
						
						case RUNNING:
							runWhenModelProcessRunning(process);
							setActionState(new TActionState(action, arg2));
						break;
						
						case SCHEDULED:
							runWhenModelProcessScheduled(process);
							setActionState(new TActionState(action, arg2));
						break;
					}
					
				}
			});
			runProcess(process);
		}
	}
	
	/**
	 * Run the given {@link ITProcess}
	 * */
	public void runProcess(final ITProcess<?> process) {
		process.startProcess();
		configProgressIndicator(process);
	}
	
	/**
	 * Validate all models in the {@link ObservableList}
	 * @throws TValidatorException and {@link Exception}
	 * */
	public void validateModels(final ObservableList<M> modelsViewList) throws Exception, TValidatorException {
		TControlValidator<M> validator = new TControlValidator<>();
		List<TValidatorResult<M>> lst = validator.validate(modelsViewList);
		
		if(lst.size()>0)
			throw new TValidatorException(lst);
	}
	
	/**
	 * Get the TModelProcess class
	 * @return Class
	 */
	public Class<? extends TModelProcess> getModelProcessClass() {
		return modelProcessClass;
	}

	/**
	 * Get the value of skipChangeValidation
	 * @return boolean
	 */
	public boolean isSkipChangeValidation() {
		return skipChangeValidation;
	}

	/**
	 * Define whether the process should skip model change validation.  
	 * @param skipChangeValidation
	 */
	public void setSkipChangeValidation(boolean skipChangeValidation) {
		this.skipChangeValidation = skipChangeValidation;
	}

	/**
	 * Get the value of skipRequiredValidation
	 * @return
	 */
	public boolean isSkipRequiredValidation() {
		return skipRequiredValidation;
	}

	/**
	 * Define if the process should ignore the 
	 * validation of mandatory fields.  
	 * @param skipChangeValidation
	 */
	public void setSkipRequiredValidation(boolean skipRequiredValidation) {
		this.skipRequiredValidation = skipRequiredValidation;
	}

	/**
	 * @return the actionStatePropemrty
	 */
	public ReadOnlyObjectProperty<TActionState<M>> actionStateProperty() {
		return actionStateProperty;
	}

	
}
