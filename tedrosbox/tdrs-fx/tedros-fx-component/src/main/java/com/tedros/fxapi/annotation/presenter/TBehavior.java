package com.tedros.fxapi.annotation.presenter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tedros.fxapi.control.action.TPresenterAction;
import com.tedros.fxapi.presenter.behavior.ITBehavior;
import com.tedros.fxapi.presenter.entity.behavior.TMasterCrudViewBehavior;
import com.tedros.fxapi.presenter.model.TEntityModelView;
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

	

	
}
