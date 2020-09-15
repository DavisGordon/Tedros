package com.tedros.fxapi.annotation.view;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tedros.ejb.base.controller.ITEjbController;
import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.fxapi.presenter.model.TModelView;

@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.ANNOTATION_TYPE, ElementType.TYPE})
public @interface TPaginator {
	
	/**
	 * Show the paginator
	 * 
	 * @default false
	 * */
	public boolean show() default false;
	
	/**
	 * Show the search field
	 * 
	 * @default false
	 * */
	public boolean showSearchField() default false;
	
	/**
	 * Show the paginator
	 * 
	 * @default false
	 * */
	public String searchFieldName() default "";
	
	/**
	 * The ejb jndi name to lookup the service, this must implement ITEjbController
	 * 
	 * @see ITEjbController
	 * */
	public String serviceName();
	
	/**
	 * The entity class
	 * */
	public Class<? extends ITEntity> entityClass();
	
	/**
	 * The model view class
	 * */
	public Class<? extends TModelView> modelViewClass() default TModelView.class;
	/**
	 * Show the order by combobox
	 * 
	 * @default true
	 * */
	public boolean showOrderBy() default true;
	
	/**
	 * The order by options
	 * 
	 * @default @TOption(text="#{tedros.fxapi.label.code}", value="id")
	 * */
	public TOption[] orderBy() default {@TOption(text="#{tedros.fxapi.label.code}", value="id")};
}
