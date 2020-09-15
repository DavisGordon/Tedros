package com.tedros.fxapi.annotation.presenter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.fxapi.annotation.view.TPaginator;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.entity.behavior.TMasterCrudViewBehavior;
import com.tedros.fxapi.presenter.entity.decorator.TMasterCrudViewDecorator;

/**
 * <pre>
 * List view width and pagination settings
 * </pre>
 * @author Davis Gordon
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TListViewPresenter {
	
	public double listViewMaxWidth() default 250;
	public double listViewMinWidth() default 250;
	
	public TPaginator paginator() default @TPaginator(entityClass = ITEntity.class, serviceName = "");
	
	public TPresenter presenter() default @TPresenter(	behavior = @TBehavior(type = TMasterCrudViewBehavior.class), 
														decorator = @TDecorator(type = TMasterCrudViewDecorator.class), 
														type = TDynaPresenter.class);
}
