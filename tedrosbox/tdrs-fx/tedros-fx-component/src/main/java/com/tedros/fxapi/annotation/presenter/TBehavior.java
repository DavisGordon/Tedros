package com.tedros.fxapi.annotation.presenter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javafx.scene.control.ListView;
import javafx.util.Callback;

import com.tedros.fxapi.control.action.TPresenterAction;
import com.tedros.fxapi.presenter.behavior.ITBehavior;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.fxapi.util.TEntityListViewCallback;

@Retention(RetentionPolicy.RUNTIME)
@Target(value=ElementType.ANNOTATION_TYPE)
public @interface TBehavior {
	
	@SuppressWarnings("rawtypes")
	public Class<? extends ITBehavior> type();
	
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
	
	
}
