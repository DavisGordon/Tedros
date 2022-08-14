package org.tedros.fx.annotation.presenter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.fx.control.action.TPresenterAction;
import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.fx.util.TEntityListViewCallback;

import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 * <pre>
 * Customize actions and titles in a {@link org.tedros.fx.presenter.view.TView} subClass.
 * </pre>
 * @author Davis Gordon
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TEntityPresenter {
	
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
	
}
