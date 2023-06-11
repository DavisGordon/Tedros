package org.tedros.fx.presenter.modal.behavior;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.tedros.api.presenter.view.TViewMode;
import org.tedros.core.message.TMessageType;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.exception.TProcessException;
import org.tedros.fx.exception.TValidatorException;
import org.tedros.fx.modal.TMessageBox;
import org.tedros.fx.model.TModelView;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.fx.presenter.dynamic.behavior.TDynaViewActionBaseBehavior;
import org.tedros.fx.process.TImportProcess;
import org.tedros.server.controller.ITEjbImportController;
import org.tedros.server.entity.ITEntity;
import org.tedros.server.model.ITImportModel;
import org.tedros.server.model.ITModel;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker.State;

/**
 * The behavior of the import file view.
 * For import processing use {@link TEjbService} 
 * with a service of type {@link ITEjbImportController}
 * on TEntityModelView 
 * 
 * @author Davis Gordon
 *
 * @param <M>
 * @param <E>
 */
@SuppressWarnings({ "rawtypes" })
public class TImportFileModalBehavior<M extends TModelView, E extends ITModel>
extends TDynaViewActionBaseBehavior<M, E> {
	
	private Class<? extends ITEntity> entityClass;
	private Class<? extends TModelView> modelViewClass;
	
	@Override
	public void load() {
		super.load();
		initialize();
	}
	
	/**
	 * Initialize the view
	 */
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
	
	/**
	 * Create and run a TImportProcess to load 
	 * the import rules from the service.
	 * @throws Throwable
	 */
	@SuppressWarnings("unchecked")
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
		super.getListenerRepository().add(id, prcl);
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
	}

	@Override
	@SuppressWarnings("unchecked")
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
				
						final TMessageBox tMessageBox = new TMessageBox(msg, TMessageType.INFO);
						getView().tShowModal(tMessageBox, true);	
							
						if(result.getState().equals(TState.SUCCESS)) {
							
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
			
			super.getListenerRepository().add(id, prcl);
			process.stateProperty().addListener(new WeakChangeListener(prcl));
			process.importFile((ITImportModel) getModelView().getModel());
			runProcess(process);
			
		} catch (TProcessException e1) {
			e1.printStackTrace();
		}
	}
}
