package com.tedros.fxapi.annotation.presenter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tedros.fxapi.annotation.control.TTableView;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.entity.behavior.TDetailFieldBehavior;
import com.tedros.fxapi.presenter.entity.decorator.TDetailFieldDecorator;

/**
 * <pre>
 * This presenter are configured to build a detail view 
 * with a TableView component to list the models.
 * 
 * Supports TBreadcumBar, 
 * </pre>
 * @author Davis Gordon
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TDetailTableViewPresenter {
	
	/**
	 * The table view settings.
	 * */
	public TTableView tableView();
	
	/**<pre>
	 * The presenter settings.
	 * 
	 * Default behavior @TBehavior(type = TDetailFieldBehavior.class)
	 * Default decorator @TDecorator(type = TDetailFieldDecorator.class, viewTitle="#{tedros.fxapi.view.detail.title}")
	 * </pre>
	 * */
	public TPresenter presenter() default @TPresenter(	behavior = @TBehavior(type = TDetailFieldBehavior.class), 
														decorator = @TDecorator(type = TDetailFieldDecorator.class, 
														viewTitle="#{tedros.fxapi.view.detail.title}"), 
														type = TDynaPresenter.class);
}
