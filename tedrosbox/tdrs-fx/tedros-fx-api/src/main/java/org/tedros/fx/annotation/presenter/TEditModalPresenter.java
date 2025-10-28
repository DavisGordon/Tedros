package org.tedros.fx.annotation.presenter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.fx.presenter.modal.behavior.TEditModalBehavior;
import org.tedros.fx.presenter.modal.decorator.TEditModalDecorator;

/**
 * <pre>
 * The edit modal view settings
 * 
 * </pre>
 * @author Davis Gordon
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TEditModalPresenter {
	
	/**
	 * The list view max width
	 * */
	public double listViewMaxWidth() default 250;
	/**
	 * The list view min width
	 * */
	public double listViewMinWidth() default 250;
	
	/**
	 * The modal view build settings 
	 * */
	public TPresenter presenter() default 
	@TPresenter(behavior = @TBehavior(type = TEditModalBehavior.class), 
				decorator = @TDecorator(type = TEditModalDecorator.class, 
							buildSaveButton=false, buildDeleteButton=false,
							viewTitle="#{tedros.fxapi.view.modal.edit.title}"), 
							type = TDynaPresenter.class);
}
