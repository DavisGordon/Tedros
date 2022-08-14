package org.tedros.fx.annotation.presenter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.core.presenter.ITPresenter;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.server.model.ITModel;

/**
 * <pre>
 * Settings for actions, layout and titles in the view
 * </pre>
 * @author Davis Gordon
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.ANNOTATION_TYPE, ElementType.TYPE})
public @interface TPresenter {
	
	/**
	 * The presenter class responsible to hold and control the objects to build and invalidate the view  
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITPresenter> type() default TDynaPresenter.class;
	
	/**
	 * The decorator responsible to build the layout of the view
	 * */
	public TDecorator decorator() default @TDecorator();
	
	/**
	 * The behavior responsible for the actions of the view
	 * */
	public TBehavior behavior() default @TBehavior();
	
	/**
	 * The model class associated with the view
	 * */
	public Class<? extends ITModel> modelClass() default ITModel.class;
}
