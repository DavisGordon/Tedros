package com.tedros.fxapi.presenter.dynamic.behavior;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.tedros.app.process.ITProcess;
import com.tedros.core.TSecurityDescriptor;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.core.context.TedrosContext;
import com.tedros.core.presenter.ITPresenter;
import com.tedros.core.presenter.view.ITView;
import com.tedros.ejb.base.model.ITModel;
import com.tedros.ejb.base.result.TResult;
import com.tedros.fxapi.annotation.process.TEjbService;
import com.tedros.fxapi.control.action.TPresenterAction;
import com.tedros.fxapi.control.validator.TControlValidator;
import com.tedros.fxapi.control.validator.TValidatorResult;
import com.tedros.fxapi.exception.TValidatorException;
import com.tedros.fxapi.modal.TMessage;
import com.tedros.fxapi.modal.TMessageBox;
import com.tedros.fxapi.modal.TMessageType;
import com.tedros.fxapi.presenter.behavior.TActionHelper;
import com.tedros.fxapi.presenter.behavior.TActionState;
import com.tedros.fxapi.presenter.behavior.TActionType;
import com.tedros.fxapi.presenter.behavior.TBehavior;
import com.tedros.fxapi.presenter.behavior.TProcessResult;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.model.TModelView;
import com.tedros.fxapi.process.TModelProcess;

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

@SuppressWarnings("rawtypes")
public abstract class TDynaViewSimpleBaseBehavior<M extends TModelView, E extends ITModel> 
extends TBehavior<M, TDynaPresenter<M>> {
	
	protected TActionHelper actionHelper = new TActionHelper();
	
	private Class<? extends TModelProcess> modelProcessClass;
	private Class<M> modelViewClass;
	private boolean skipChangeValidation;
	private boolean skipRequiredValidation;
	private boolean showMessages = true;
	
	private List<TAuthorizationType> userAuthorizations;
	
	private final ObjectProperty<TActionState<M>> actionStateProperty = new SimpleObjectProperty<>();
	private final ObservableList<TMessage> messagesProperty = FXCollections.observableArrayList();
    
	@SuppressWarnings("unchecked")
	@Override
	public void load(){	
		
		final TDynaPresenter<M> presenter = getPresenter(); 
		
		this.modelViewClass = presenter.getModelViewClass();
		setModelView(presenter.getModelView());
		setModelViewList(presenter.getModelViews());
		
		buildUserAuthorizations();
		verifyViewAccessAutorization();
		
		final com.tedros.fxapi.annotation.process.TModelProcess tModelProcess = getPresenter().getModelProcessAnnotation();
		
		if(tModelProcess!=null){
			this.modelProcessClass = tModelProcess.type();
		}
		
		ListChangeListener<TMessage> msgLtnr = c -> {
			if(showMessages) {
				if(!c.getList().isEmpty()) {
					final TMessageBox tMessageBox = new TMessageBox();
					tMessageBox.tAddMessages((ObservableList<TMessage>) c.getList());
					getView().tShowModal(tMessageBox, true);
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
	 * 
	 */
	protected void verifyViewAccessAutorization() {
		if(isUserNotAuthorized(TAuthorizationType.VIEW_ACCESS)){
			StackPane accessDenied = new StackPane();
			accessDenied.setId("t-access-denied");
			getView().tShowModal(accessDenied, false);
		}
	}
	
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

	public void addAction(TPresenterAction action) {
		this.actionHelper.add(action);
	}
	
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
	 * Set a TActionState
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
	 * Show a message in a modal view
	 * */
	public void addMessage(TMessage... msg) {
		messagesProperty.addAll(msg);
	}
	
    public List<TAuthorizationType> getUserAuthorizations() {
		return userAuthorizations;
	}
    
    public boolean isUserAuthorized(TAuthorizationType type){
    	return userAuthorizations==null || userAuthorizations.contains(type);
    }
    
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
	
	
	
	public boolean invalidate() {
		ITView v = getView();
		if(v!=null && v.gettProgressIndicator()!=null) 
			v.gettProgressIndicator().removeBind();
		return super.invalidate();
		
	}
	
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
	 * Create a {@link TModelProcess} process based on the {@link TEjbService} 
	 * or {@link com.tedros.fxapi.annotation.process.TEntityProcess} 
	 * annotated in the given {@link TModelView}
	 * </p>  
	 * */
	@SuppressWarnings("unchecked")
	public TModelProcess<E> createModelProcess() throws Throwable {
		if(this.modelProcessClass != null && this.modelProcessClass != TModelProcess.class)
			return modelProcessClass.newInstance();
		
		return null;
	}
	
	public boolean runWhenModelProcessSucceeded(TModelProcess<E> modelProcess){
		return true;
	}
	
	public void runWhenModelProcessFailed(TModelProcess<E> modelProcess){
		
	}
	
	public void runWhenModelProcessCancelled(TModelProcess<E> modelProcess){
		
	}
	
	public void runWhenModelProcessReady(TModelProcess<E> modelProcess){
		
	}
	
	public void runWhenModelProcessRunning(TModelProcess<E> modelProcess){
		
	}
	
	public void runWhenModelProcessScheduled(TModelProcess<E> modelProcess){
		
	}
	
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
							switch(result.getResult()) {
								case SUCESS:
									E entity = (E) result.getValue();
									if(entity!=null){
										model.reload(entity);
										String msg = iEngine.getFormatedString("#{tedros.fxapi.message.process}", model.getDisplayProperty().getValue());
										setActionState(new TActionState(action, arg2, TProcessResult.SUCCESS, msg, model));
										addMessage(new TMessage(TMessageType.INFO, msg));
									}else {
										setActionState(new TActionState(action, arg2, TProcessResult.NO_RESULT, model));
									}
								break;
								default:
									System.out.println(result.getMessage());
									String msg = result.getMessage();
									setActionState(new TActionState(action, arg2, TProcessResult.get(result.getResult()), msg, model));
									switch(result.getResult()) {
									case ERROR:
										addMessage(new TMessage(TMessageType.ERROR, msg));
										break;
									default:
										addMessage(new TMessage(TMessageType.WARNING, msg));
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
	
	

	public Class<? extends TModelProcess> getModelProcessClass() {
		return modelProcessClass;
	}

	public boolean isSkipChangeValidation() {
		return skipChangeValidation;
	}

	public void setSkipChangeValidation(boolean skipChangeValidation) {
		this.skipChangeValidation = skipChangeValidation;
	}

	public boolean isSkipRequiredValidation() {
		return skipRequiredValidation;
	}

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
