package com.tedros.fxapi.presenter.dynamic.behavior;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.tedros.app.process.ITProcess;
import com.tedros.core.TSecurityDescriptor;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.core.context.TedrosContext;
import com.tedros.ejb.base.model.ITModel;
import com.tedros.ejb.base.service.TResult;
import com.tedros.ejb.base.service.TResult.EnumResult;
import com.tedros.fxapi.annotation.process.TEjbService;
import com.tedros.fxapi.control.validator.TControlValidator;
import com.tedros.fxapi.control.validator.TValidatorResult;
import com.tedros.fxapi.exception.TValidatorException;
import com.tedros.fxapi.presenter.behavior.TBehavior;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.model.TModelView;
import com.tedros.fxapi.process.TModelProcess;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker.State;

@SuppressWarnings("rawtypes")
public abstract class TDynaViewSimpleBaseBehavior<M extends TModelView, E extends ITModel> 
extends TBehavior<M, TDynaPresenter<M>> {
	
	
	private Class<? extends TModelProcess> modelProcessClass;
	private Class<M> modelViewClass;
	private boolean skipChangeValidation;
	private boolean skipRequiredValidation;
	
	private List<TAuthorizationType> userAuthorizations;
    
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
																	TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW}){
				if(TedrosContext.isUserAuthorized(securityDescriptor, type))
					userAuthorizations.add(type);
			}
		}
	}
	
	@Override
	public void load(){	
		
		final TDynaPresenter<M> presenter = getPresenter(); 
		
		this.modelViewClass = presenter.getModelViewClass();
		setModelView(presenter.getModelView());
		setModelViewList(presenter.getModelViews());
		
		buildUserAuthorizations();
		
		if(isUserNotAuthorized(TAuthorizationType.VIEW_ACCESS)){
			getView().tShowModal(presenter.getDecorator().getScreenSaverPane(), false);
		}
		
		final com.tedros.fxapi.annotation.process.TModelProcess tModelProcess = getPresenter().getModelProcessAnnotation();
		
		if(tModelProcess!=null){
			this.modelProcessClass = tModelProcess.type();
		}
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
	public void runModelProcess(final ObservableList<String> mensagens, String operationID)
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
			process.setOperationID(operationID);
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
							
							/*TODO: DEVERÁ SER VERIFICADO OUTRA ABORDAGEM, POIS DEIXAR IMPLICITO QUE TODO RESULTADO
							 * DEVERÁ RETORNAR UMA ENTIDADE NA PRIMEIRA POSIÇÃO CAUSA MUITA CONFUSÃO
							 * DEVERÁ SER ANALISADO  
							 * */
							final List<TResult<E>> resultados = process.getValue();
							if(resultados.isEmpty())
								return;
							TResult result = resultados.get(0);
							E entity = (E) result.getValue();
							if(entity!=null){
								try {
									//listView.getItems().set(index, modelViewClass.getConstructor(entityClass).newInstance(entityClass.newInstance()));
									model.reload(entity);
									mensagens.add(model.getDisplayProperty().getValue()+" processado com sucesso!");
								} catch (Exception e) 
								{	
									mensagens.add(model.getDisplayProperty().getValue()+" # Erro ao processar:\n"+e.getMessage());
									e.printStackTrace();
								}
							}
							if(result.getResult().getValue() == EnumResult.ERROR.getValue()){
								System.out.println(result.getMessage());
								mensagens.add(result.getMessage());
							}
						break;

						case CANCELLED:
							runWhenModelProcessCancelled(process);
						break;
						
						case FAILED:
							runWhenModelProcessFailed(process);
						break;
						
						case READY:
							runWhenModelProcessReady(process);
						break;
						
						case RUNNING:
							runWhenModelProcessRunning(process);
						break;
						
						case SCHEDULED:
							runWhenModelProcessScheduled(process);
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
		
}
