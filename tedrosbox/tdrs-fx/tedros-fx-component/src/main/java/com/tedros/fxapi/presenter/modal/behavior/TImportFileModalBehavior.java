package com.tedros.fxapi.presenter.modal.behavior;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.ejb.base.model.ITImportModel;
import com.tedros.ejb.base.model.ITModel;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.process.TEjbService;
import com.tedros.fxapi.domain.TViewMode;
import com.tedros.fxapi.exception.TProcessException;
import com.tedros.fxapi.exception.TValidatorException;
import com.tedros.fxapi.modal.TMessageBox;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.dynamic.behavior.TDynaViewActionBaseBehavior;
import com.tedros.fxapi.presenter.dynamic.decorator.TDynaViewActionBaseDecorator;
import com.tedros.fxapi.presenter.modal.decorator.TImportFileModalDecorator;
import com.tedros.fxapi.presenter.model.TModelView;
import com.tedros.fxapi.process.TImportProcess;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker.State;

@SuppressWarnings({ "rawtypes" })
public class TImportFileModalBehavior<M extends TModelView, E extends ITModel>
extends TDynaViewActionBaseBehavior<M, E> {
	
	private Class<? extends ITEntity> entityClass;
	private Class<? extends TModelView> modelViewClass;
	
	private TImportFileModalDecorator<M> decorator;
		
	@Override
	public void load() {
		super.load();
		this.decorator = (TImportFileModalDecorator<M>) getDecorator();
		initialize();
	}
		
	public void initialize() {
		
		final TDynaPresenter<M> presenter = getPresenter();
		final TBehavior tBehavior = presenter.getPresenterAnnotation().behavior();
		
		this.entityClass = tBehavior.importedEntityClass();
		this.modelViewClass = tBehavior.importedModelViewClass();
				
		configActionButton();
		configCloseButton();
		configModesRadio();
		
		try {
			loadEntity();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		
	}
	
	protected void loadEntity() throws Throwable {
		
		final String id = UUID.randomUUID().toString();
		TImportProcess process = new TImportProcess(serviceName) {};
		ChangeListener<State> prcl = (arg0, arg1, arg2) -> {
			
			if(arg2.equals(State.SUCCEEDED)){
				
				TResult<ITImportModel> resultados = (TResult<ITImportModel>) process.getValue();
				
				if(resultados != null) {
				
					ITImportModel result =  resultados.getValue();
					
					try {
						M model = (M) getModelViewClass().getConstructor(result.getClass()).newInstance(result);
						setModelView(model);
						showForm(TViewMode.EDIT);
					} catch (Throwable e) {
						e.printStackTrace();
					}
				}
			}
		};
		super.getListenerRepository().addListener(id, prcl);
		process.stateProperty().addListener(new WeakChangeListener(prcl));
		process.getImportRules();
		runProcess(process);
	}
	
	@Override
	public String canInvalidate() {
		return null;
	}

	@Override
	protected void processSelectedItem(TModelView new_val) {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void processAction() throws TValidatorException, Exception {
		
		final ObservableList<M> modelsViewsList = FXCollections.observableList(Arrays.asList(getModelView())) ;
						
						
		// valida os models views
		validateModels(modelsViewsList);
		
		final String id = UUID.randomUUID().toString();
		
		try {
			TImportProcess process = new TImportProcess(serviceName) {};
		
			ChangeListener<State> prcl = (arg0, arg1, arg2) -> {
				
				if(arg2.equals(State.SUCCEEDED)){
					
					TResult result = (TResult) process.getValue();
					
					if(result != null) {
					
						String msg = result.isPriorityMessage() 
								? result.getMessage()
										: iEngine.getString("#{tedros.fxapi.message.import}");
				
						final TMessageBox tMessageBox = new TMessageBox();
						tMessageBox.tAddMessage(msg);
						getView().tShowModal(tMessageBox, true);	
							
						if(result.getResult().equals(EnumResult.SUCESS)) {
							
							List<ITEntity> lst = (List) result.getValue();
							for(ITEntity e : lst ) {
								try {
									M model =  (M) modelViewClass.getConstructor(entityClass).newInstance(e);
									getModels().add((M) model);
								} catch (Throwable t) {
									t.printStackTrace();
								}
							}
							setModelViewList(null);
							
							invalidate();
							closeAction();
						}
						
					}
				}
			};
			
			super.getListenerRepository().addListener(id, prcl);
			process.stateProperty().addListener(new WeakChangeListener(prcl));
			process.importFile((ITImportModel) getModelView().getModel());
			runProcess(process);
			
		} catch (TProcessException e1) {
			e1.printStackTrace();
		}
		
		
	}
		
}
