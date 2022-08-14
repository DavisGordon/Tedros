package org.tedros.fx.annotation.presenter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.fx.annotation.view.TPaginator;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.fx.presenter.entity.behavior.TMasterCrudViewBehavior;
import org.tedros.fx.presenter.entity.decorator.TMasterCrudViewDecorator;
import org.tedros.server.entity.ITEntity;

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
	public boolean refreshListViewAfterActions() default false;
	
	public TPaginator paginator() default @TPaginator(entityClass = ITEntity.class, serviceName = "");
	
	public TPresenter presenter() default @TPresenter(	behavior = @TBehavior(type = TMasterCrudViewBehavior.class), 
														decorator = @TDecorator(type = TMasterCrudViewDecorator.class), 
														type = TDynaPresenter.class);
}
