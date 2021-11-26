package com.tedros.fxapi.annotation.control;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tedros.ejb.base.model.ITModel;
import com.tedros.fxapi.presenter.model.TModelView;

/**
 * A set of observable collection type information 
 * 
 * @author Davis Gordon
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TModelViewCollectionType {
	
	/**
	 * The model view type
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TModelView> modelViewClass() default TModelView.class;
	
	/**
	 * The model or entity type
	 * */
	public Class<? extends ITModel> modelClass();
	
	/**
	 * <pre>
	 * Determines with this collection will be required.
	 * 
	 * Default value: false.
	 * </pre>
	 * */
	public boolean required() default false;
}
