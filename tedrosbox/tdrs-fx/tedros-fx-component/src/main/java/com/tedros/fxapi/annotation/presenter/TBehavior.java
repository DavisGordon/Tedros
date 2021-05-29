package com.tedros.fxapi.annotation.presenter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.fxapi.control.action.TPresenterAction;
import com.tedros.fxapi.presenter.behavior.ITBehavior;
import com.tedros.fxapi.presenter.entity.behavior.TMasterCrudViewBehavior;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.fxapi.presenter.model.TImportModelView;
import com.tedros.fxapi.presenter.model.TModelView;
import com.tedros.fxapi.util.TEntityListViewCallback;

import javafx.scene.control.ListView;
import javafx.util.Callback;

@Retention(RetentionPolicy.RUNTIME)
@Target(value=ElementType.ANNOTATION_TYPE)
public @interface TBehavior {
	
	@SuppressWarnings("rawtypes")
	public Class<? extends ITBehavior> type() default TMasterCrudViewBehavior.class;
	
	/**
	 *<pre>
	 *Specifies a {@link Callback} to {@link ListView} inside a {@link TEntityModelView}.  
	 *</pre>  
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends Callback> listViewCallBack() default TEntityListViewCallback.class;

	/**
	 * <pre>
	 * Specifies an action to the print event dispatched by the print button;
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TPresenterAction> printAction() default TPresenterAction.class;
	
	/**
	 * <pre>
	 * Specifies an action to the new event dispatched by the new button;
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TPresenterAction> newAction() default TPresenterAction.class;
	
	/**
	 * <pre>
	 * Specifies an action to the save event dispatched by the save button;
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TPresenterAction> saveAction() default TPresenterAction.class;
	
	/**
	 * <pre>
	 * Specifies an action to the delete event dispatched by the delete button;
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TPresenterAction> deleteAction() default TPresenterAction.class;
	
	/**
	 * <pre>
	 * Specifies an action to the change mode event dispatched by the new change mode radio button;
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TPresenterAction> changeModeAction() default TPresenterAction.class;
	
	/**
	 * <pre>
	 * Specifies an action to the select item event dispatched by the list view;
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TPresenterAction> selectedItemAction() default TPresenterAction.class;
	
	/**
	 * <pre>
	 * Specifies an action to the edit event dispatched from the edit button;
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TPresenterAction> editAction() default TPresenterAction.class;
	
	/**
	 * <pre>
	 * Specifies an action to the cancel event dispatched from the cancel button;
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TPresenterAction> cancelAction() default TPresenterAction.class;

	/**
	 * <pre>
	 * Specifies an action to the search event dispatched from the search button;
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TPresenterAction> searchAction() default TPresenterAction.class;

	/**
	 * <pre>
	 * Specifies an action to the clean event dispatched from the clean button;
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TPresenterAction> cleanAction() default TPresenterAction.class;

	/**
	 * <pre>
	 * Specifies an action to the excel event dispatched from the excel button;
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TPresenterAction> excelAction() default TPresenterAction.class;

	/**
	 * <pre>
	 * Specifies an action to the pdf event dispatched from the pdf button;
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TPresenterAction> pdfAction() default TPresenterAction.class;

	/**
	 * <pre>
	 * Specifies an action to the select event dispatched from the select button;
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TPresenterAction> selectAction() default TPresenterAction.class;

	/**
	 * <pre>
	 * Specifies an action to the modal close event dispatched from the close button;
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TPresenterAction> closeAction() default TPresenterAction.class;
	
	/**
	 * <pre>
	 * Specifies an action to the import event dispatched from the import button;
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TPresenterAction> importAction() default TPresenterAction.class;
	
	/**
	 * <pre>
	 * Process the save action for all changed entity's if false only the current 
	 * entity in edition is processed.
	 * 
	 * Default: true
	 * </pre>
	 * */
	public boolean saveAllModels() default true;
	
	/**
	 * <pre>
	 * Process the save action only if the model is changed.
	 * 
	 * Default: true
	 * </pre>
	 * */
	public boolean saveOnlyChangedModels() default true;

	/**
	 * <pre>
	 * Specifies an action to the action event dispatched from the action button;
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TPresenterAction>  actionAction() default TPresenterAction.class;

	/**
	 * <pre>
	 * Specifies an action to the add event dispatched from the add button;
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TPresenterAction> addAction() default TPresenterAction.class;
	
	/**
	 * <pre>
	 * Specifies an action to the remove event dispatched from the remove button;
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TPresenterAction> removeAction() default TPresenterAction.class;
	
	/**
	 * <pre>
	 * The import model view class, must be configured to build the import view properly
	 * with the correct ejb service (@TEjbService) decorator of type TImportFileModalDecorator.class
	 * and behavior of type TImportFileModalBehavior.class. The ejb service must implement ITEjbImportController
	 * 
	 * This import model view must set the properties importedEntityClass and importedModelViewClass 
	 * in your TBehavior
	 * 
	 * Example:
	 * <code>
	 * @TEjbService(serviceName = "IProdutoImportControllerRemote", model=ProdutoImport.class)
	 * @TPresenter(decorator = @TDecorator(type=TImportFileModalDecorator.class, viewTitle="Importar produtos"),
	 *		behavior = @TBehavior(type=TImportFileModalBehavior.class, 
	 *		importedEntityClass=Produto.class, importedModelViewClass=ProdutoModelView.class))
	 * public class ProdutoImportModelView extends TImportModelView&ltProdutoImport&gt {
	 * </code>
	 * 
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TImportModelView> importModelViewClass() default TImportModelView.class;
	
	/**
	 * The entity object type returned by the import process. 
	 * This must be set in a TImportModelView type.
	 * */
	public Class<? extends ITEntity> importedEntityClass() default ITEntity.class;
	
	/**
	 * The model view type to be built with the result of the import process. 
	 * This must be set in a TImportModelView type.
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TModelView> importedModelViewClass() default TModelView.class;

	
	
}
