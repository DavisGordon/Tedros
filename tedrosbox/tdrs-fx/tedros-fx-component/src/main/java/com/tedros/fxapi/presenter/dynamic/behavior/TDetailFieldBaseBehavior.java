package com.tedros.fxapi.presenter.dynamic.behavior;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import com.tedros.core.ITModule;
import com.tedros.core.TLanguage;
import com.tedros.core.context.TedrosAppManager;
import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.fxapi.annotation.control.TTableView;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDetailTableViewPresenter;
import com.tedros.fxapi.builder.ITBuilder;
import com.tedros.fxapi.builder.ITControlBuilder;
import com.tedros.fxapi.builder.ITFieldBuilder;
import com.tedros.fxapi.descriptor.TComponentDescriptor;
import com.tedros.fxapi.domain.TViewMode;
import com.tedros.fxapi.exception.TValidatorException;
import com.tedros.fxapi.modal.TMessageBox;
import com.tedros.fxapi.presenter.behavior.TActionType;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.dynamic.decorator.TDetailFieldBaseDecorator;
import com.tedros.fxapi.presenter.model.TModelView;
import com.tedros.fxapi.util.TReflectionUtil;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.WeakEventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;


@SuppressWarnings("rawtypes")
public abstract class TDetailFieldBaseBehavior<M extends TModelView, E extends ITEntity> 
extends TDynaViewSimpleBaseBehavior<M, E> {
	
	private ListChangeListener<Node> formListChangeListener;
	
	protected TDetailFieldBaseDecorator<M> decorator;
	protected Class<? extends ITEntity> entityClass;
	

	@SuppressWarnings("unchecked")
	public void load(){
		super.load();
		try{
			TDetailTableViewPresenter modalPresenter = getModelViewClass().getAnnotation(TDetailTableViewPresenter.class);
			
			if(modalPresenter==null)
				throw new RuntimeException("The TDetailTableViewPresenter annotation not found in the " + getModelViewClass().getSimpleName());

			final TDynaPresenter<M> presenter = getPresenter();
			
			this.decorator = (TDetailFieldBaseDecorator<M>) presenter.getDecorator();
			this.entityClass = (Class<? extends ITEntity>) presenter.getModelClass();
			//final TForm tForm = presenter.getFormAnnotation();
			final TBehavior tBehavior = presenter.getPresenterAnnotation().behavior();
			
			// set the custom behavior actions
			loadAction(presenter, tBehavior.action());
			
			TTableView tableViewAnn = modalPresenter.tableView();

			TModelView model = (M) super.getModelViewClass().getConstructor(entityClass).newInstance(entityClass.newInstance());

			
	    	Object[] arrControl = TReflectionUtil.getControlBuilder(Arrays.asList(tableViewAnn));
	    	ITFieldBuilder controlBuilder = (ITFieldBuilder) arrControl[1];
	    	((ITBuilder) controlBuilder).setComponentDescriptor(new TComponentDescriptor(null, model, null));
	    	TableView tableView = (TableView) ((ITControlBuilder) controlBuilder).build(tableViewAnn, getModels());
	    	tableView.setTooltip(new Tooltip(TLanguage
					.getInstance(null)
					.getString("#{tedros.fxapi.label.double.click.select}")));
	    	
	    	decorator.settTableView(tableView);
			
			tableView.setTableMenuButtonVisible(true);
			
			EventHandler<MouseEvent> ev = e ->{
				if(e.getClickCount()==2) {
					int index = tableView.getSelectionModel().getSelectedIndex();
					TModelView new_ = (TModelView) tableView.getItems().get(index);
					selectedItemAction(new_);
				}
			};
			super.getListenerRepository().add("tvmclkeh", ev);
			tableView.setOnMouseClicked(new WeakEventHandler<>(ev));
			
			ChangeListener<TModelView> mvcl = (a0, old_, new_) -> {
				if(new_!=null && decorator.gettRemoveButton()!=null)
					decorator.gettRemoveButton().setDisable(!getModels().contains(new_));
			};
			
			super.getListenerRepository().add("setmodelviewCL", mvcl);
			super.modelViewProperty().addListener(new WeakChangeListener(mvcl));
			
			
		}catch(Exception e){
			e.printStackTrace();
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
	 * Config the add button
	 * */
	public void configAddButton() {
		final Button addButton = this.decorator.gettAddButton();
		addButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				addAction();
			}
		});
		
	}
	
	
	/**
	 * Config the remove button;
	 * */
	public void configRemoveButton() {
		final Button removeButton = this.decorator.gettRemoveButton();
		removeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				removeAction();
			}
		});
	}
	
	// ACTIONS
	
	/**
	 * Perform this action when a model is selected.
	 * */
	public void selectedItemAction(TModelView new_val) {
		if(actionHelper.runBefore(TActionType.SELECTED_ITEM)){
			if(new_val==null)
				return;
			setModelView(new_val);
			showForm(TViewMode.EDIT);
		
		}
		actionHelper.runAfter(TActionType.SELECTED_ITEM);
	}
	
	/**
	 * Perform this action when search button onAction is triggered.
	 * */
	public void addAction() {
		if(actionHelper.runBefore(TActionType.ADD)){
			
			try{
				M mv = super.getModelView();
				final ObservableList<M> modelsViewsList =  FXCollections.observableList(Arrays.asList(mv));
								
				// valida os models views
				validateModels(modelsViewsList);
				
				if(!getModels().contains(mv))
					getModels().add(mv);
				
				processClean();
				
			} catch (TValidatorException e) {
				getView().tShowModal(new TMessageBox(e), true);
			} catch (Exception e) {
				getView().tShowModal(new TMessageBox(e), true);
				e.printStackTrace();
			}
			actionHelper.runAfter(TActionType.ADD);
		}
		
	}
	
	
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
			M model = (M) getModelViewClass().getConstructor(entityClass).newInstance(entityClass.newInstance());
			setModelView(model);
			showForm(TViewMode.EDIT);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
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
	public void removeAction() {
		if(actionHelper.runBefore(TActionType.REMOVE)){
			try{
				M mv = super.getModelView();
				getModels().remove(mv);
				this.processClean();
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		actionHelper.runAfter(TActionType.REMOVE);
	}

	/**
	 * Build and show the form
	 * */
	public void showForm(TViewMode mode) {
		
		setViewMode(mode);
		
		if (mode!=null) 
			buildForm(mode);
		else
			buildForm(TViewMode.EDIT);
	}
	

	public synchronized void removeFormListChangeListener() {
		if(formListChangeListener!=null)
			getView().gettFormSpace().getChildren().removeListener(formListChangeListener);
	}
	
	@SuppressWarnings("unlikely-arg-type")
	public synchronized void addFormListChangeListener() {
		if(formListChangeListener!=null && !getView().gettFormSpace().getChildren().contains(formListChangeListener)){
			getView().gettFormSpace().getChildren().addListener(formListChangeListener);
		}
	}

	/**
	 * @return the formListChangeListener
	 */
	public ListChangeListener<Node> getFormListChangeListener() {
		return formListChangeListener;
	}

	/**
	 * @param formListChangeListener the formListChangeListener to set
	 */
	public void setFormListChangeListener(ListChangeListener<Node> formListChangeListener) {
		this.formListChangeListener = formListChangeListener;
	}

	/**
	 * @return the decorator
	 */
	public TDetailFieldBaseDecorator<M> getDecorator() {
		return decorator;
	}

	/**
	 * @param decorator the decorator to set
	 */
	public void setDecorator(TDetailFieldBaseDecorator<M> decorator) {
		this.decorator = decorator;
	}

}
