package com.tedros.fxapi.annotation.presenter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tedros.core.presenter.ITPresenter;
import com.tedros.ejb.base.model.ITModel;

/**
 * <pre>
 * Customize actions and titles in a {@link com.tedros.fxapi.presenter.view.TView} subClass.
 * </pre>
 * @author Davis Gordon
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.ANNOTATION_TYPE, ElementType.TYPE})
public @interface TPresenter {
	
	@SuppressWarnings("rawtypes")
	public Class<? extends ITPresenter> type();
	public TDecorator decorator();
	public TBehavior behavior();
	public Class<? extends ITModel> modelClass() default ITModel.class;
}
