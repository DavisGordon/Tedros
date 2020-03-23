package com.tedros.fxapi.annotation.view;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.builder.ITViewBuilder;
import com.tedros.fxapi.builder.TCrudEntityViewBuilder;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.entity.behavior.TMainCrudViewWithListViewBehavior;
import com.tedros.fxapi.presenter.entity.decorator.TMainCrudViewWithListViewDecorator;

/**
 * <pre>
 * Customize actions and titles in a {@link com.tedros.fxapi.presenter.view.TView} subClass.
 * </pre>
 * @author Davis Gordon
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TEntityCrudViewWithListView {
	
	@SuppressWarnings("rawtypes")
	public Class<? extends ITViewBuilder> builder() default TCrudEntityViewBuilder.class;
	
	public double listViewMaxWidth() default 250;
	public double listViewMinWidth() default 250;
	
	public TPresenter presenter() default @TPresenter(	behavior = @TBehavior(type = TMainCrudViewWithListViewBehavior.class), 
														decorator = @TDecorator(type = TMainCrudViewWithListViewDecorator.class), 
														type = TDynaPresenter.class);
}
