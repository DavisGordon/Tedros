package com.tedros.fxapi.annotation.presenter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.modal.behavior.TEditModalBehavior;
import com.tedros.fxapi.presenter.modal.decorator.TEditModalDecorator;

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
