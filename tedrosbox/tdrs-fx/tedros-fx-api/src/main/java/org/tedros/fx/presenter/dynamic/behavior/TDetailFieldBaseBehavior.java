package org.tedros.fx.presenter.dynamic.behavior;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import org.tedros.api.presenter.view.TViewMode;
import org.tedros.core.ITModule;
import org.tedros.core.TLanguage;
import org.tedros.core.context.TedrosAppManager;
import org.tedros.fx.annotation.control.TTableView;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDetailTableViewPresenter;
import org.tedros.fx.builder.ITBuilder;
import org.tedros.fx.builder.ITControlBuilder;
import org.tedros.fx.builder.ITFieldBuilder;
import org.tedros.fx.descriptor.TComponentDescriptor;
import org.tedros.fx.exception.TValidatorException;
import org.tedros.fx.modal.TMessageBox;
import org.tedros.fx.presenter.behavior.TActionType;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.fx.presenter.dynamic.decorator.TDetailFieldBaseDecorator;
import org.tedros.fx.presenter.model.TModelView;
import org.tedros.fx.util.TReflectionUtil;
import org.tedros.server.entity.ITEntity;

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

/**
 * The basic behavior of the table detail view. 
 * This behavior can be applied on detail entities.
 * A TableView is created to list the 
 * details. It can be set using the 
 * {@link TDetailTableViewPresenter} annotation on 
 * the TEntityModelView. 
 * @author Davis Gordon
 *
 * @param <M>
 * @param <E>
 */
@SuppressWarnings("rawtypes")
public abstract class TDetailFieldBaseBehavior<M extends TModelView, E extends ITEntity> 
extends TDynaViewSimpleBaseBehavior<M, E> {
	
	private ListChangeListener<Node> formListChangeListener;
	
	protected TDetailFieldBaseDecorator<M> decorator;
	protected Class<? extends ITEntity> entityClass;
	
	@Override
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
			
			getView().gettProgressIndicator().setSmallLogo();
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
	@SuppressWarnings("unchecked")
	public void selectedItemAction(TModelView new_val) {
		if(actionHelper.runBefore(TActionType.SELECTED_ITEM)){
			if(new_val==null)
				return;
			setModelView((M) new_val);
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
	
	/**
	 * Remove the listener from the form
	 */
	public synchronized void removeFormListChangeListener() {
		if(formListChangeListener!=null)
			getView().gettFormSpace().getChildren().removeListener(formListChangeListener);
	}
	/**
	 * Add the listener in the form
	 */
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
