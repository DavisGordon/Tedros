package org.tedros.fx.annotation.presenter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.fx.annotation.assistant.TAiAssistant;
import org.tedros.fx.annotation.page.TPage;
import org.tedros.fx.annotation.query.TQuery;
import org.tedros.fx.model.TModelView;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.fx.presenter.entity.behavior.TMasterCrudViewBehavior;
import org.tedros.fx.presenter.entity.decorator.TMasterCrudViewDecorator;
import org.tedros.server.entity.ITEntity;
import org.tedros.server.model.TJsonModel;

/**
 * <pre>
 * List view width and pagination settings
 * </pre>
 * @author Davis Gordon
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TListViewPresenter {
	
	static final double WIDTH = 350;
	
	double listViewMaxWidth() default WIDTH;
	double listViewMinWidth() default WIDTH;
	
	/**
	 * Autou reload the list view after save and delete action
	 * @default false
	 */
	boolean reloadListViewAfterCrudActions() default false;
	/**
	 * Auto hides the list view menu after a selected action
	 * @default false
	 */
	boolean autoHideListView() default false;
	
	/**
	 * Setting the pagination
	 */
	TPage page() default @TPage(show = false, serviceName = "", 
			query = @TQuery(entity = ITEntity.class));
	
	TAiAssistant aiAssistant() default @TAiAssistant(
			jsonModel = TJsonModel.class, 
			modelViewClass = TModelView.class);
	
	TPresenter presenter() default @TPresenter(	
			behavior = @TBehavior(type = TMasterCrudViewBehavior.class), 
			decorator = @TDecorator(type = TMasterCrudViewDecorator.class), 
			type = TDynaPresenter.class);
}
