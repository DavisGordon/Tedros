package org.tedros.fx.annotation.page;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.fx.annotation.query.TQuery;
import org.tedros.fx.presenter.model.TModelView;
import org.tedros.server.controller.ITEjbController;

@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.ANNOTATION_TYPE, ElementType.TYPE})
public @interface TPage {
	
	/**
	 * Show the paginator
	 * 
	 * @default true
	 * */
	boolean show() default true;
	
	/**
	 * Show the search field
	 * 
	 * @default false
	 * */
	boolean showSearch() default false;
	
	/**
	 * The query to list and search
	 * */
	TQuery query();
	
	/**
	 * The ejb jndi name to lookup the service, this must implement ITEjbController
	 * 
	 * @see ITEjbController
	 * */
	String serviceName();
	
	/**
	 * The model view class
	 * */
	@SuppressWarnings("rawtypes")
	Class<? extends TModelView> modelView() default TModelView.class;
	/**
	 * Show the order by combobox
	 * 
	 * @default false
	 * */
	boolean showOrderBy() default false;
	
	//public TOption[] orderBy() default {@TOption(text=TFxKey.CODE, field="id")};
}
