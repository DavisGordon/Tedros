package com.tedros.fxapi.annotation.presenter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.entity.behavior.TDetailCrudViewBehavior;
import com.tedros.fxapi.presenter.entity.decorator.TDetailCrudViewDecorator;

/**
 * <pre>
 * This presenter are configured to build a detail view 
 * with a ListView component to list the models.
 * 
 * Supports TBreadcumBar, 
 * </pre>
 * @author Davis Gordon
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TDetailListViewPresenter {
	
	public double listViewMaxWidth() default 250;
	public double listViewMinWidth() default 250;
	
	/**<pre>
	 * The presenter settings.
	 * 
	 * Default behavior @TBehavior(type = TDetailCrudViewBehavior.class)
	 * Default decorator @TDecorator(type = TDetailCrudViewDecorator.class)
	 * </pre>
	 * */
	public TPresenter presenter() default @TPresenter(	behavior = @TBehavior(type = TDetailCrudViewBehavior.class), 
														decorator = @TDecorator(type = TDetailCrudViewDecorator.class), 
														type = TDynaPresenter.class);
}
