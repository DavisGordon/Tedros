/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 08/11/2013
 */
package com.tedros.fxapi.annotation.form;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javafx.beans.Observable;

import com.tedros.core.presenter.view.ITView;
import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.fxapi.form.ITModelForm;
import com.tedros.fxapi.form.TDefaultForm;
import com.tedros.fxapi.presenter.dynamic.view.TDynaView;
import com.tedros.fxapi.presenter.model.TEntityModelView;

/**
 * A set of information to build a detail view
 *
 * @author Davis Gordon
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.ANNOTATION_TYPE,ElementType.FIELD})
public @interface TDetailView  {
	
	/**
	 * The view type; 
	 * 
	 * default: {@link TDynaView}
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITView> viewClass() default TDynaView.class;
	
	/**
	 * The form title
	 * */
	public String formTitle();
	
	/**
	 * The list title
	 * */
	public String listTitle();
	
	/**
	 * The field property type
	 * */
	public Class<? extends Observable> propertyType();
	@SuppressWarnings("rawtypes")
	
	/**
	 * The entity model view type
	 * */
	public Class<? extends TEntityModelView> entityModelViewClass();
	
	/**
	 * The entity type
	 * */
	public Class<? extends ITEntity> entityClass();
	
	/**
	 * The form type.
	 * 
	 * Default: {@link TDefaultForm}
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITModelForm> formClass() default TDefaultForm.class;
	
	/**
	 * The field name 
	 * */
	public String field() default "";
	
}
