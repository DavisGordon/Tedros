package org.tedros.fx.annotation.presenter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.fx.TFxKey;
import org.tedros.fx.annotation.control.TTableView;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.fx.presenter.entity.behavior.TDetailFieldBehavior;
import org.tedros.fx.presenter.entity.decorator.TDetailFieldDecorator;

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
														viewTitle=TFxKey.VIEW_DETAIL_TITLE), 
														type = TDynaPresenter.class);
}
