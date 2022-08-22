package org.tedros.fx.annotation.control;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.fx.presenter.model.TModelView;
import org.tedros.server.model.ITModel;

/**
 * Specify the types to build a TModelView.
 * Must be used when the generic type of a property is a TModelView. 
 * 
 * @author Davis Gordon
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TModelViewType {
	
	/**
	 * The model view type
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TModelView> modelViewClass() default TModelView.class;
	
	/**
	 * The model or entity type
	 * */
	public Class<? extends ITModel> modelClass();
	
}
