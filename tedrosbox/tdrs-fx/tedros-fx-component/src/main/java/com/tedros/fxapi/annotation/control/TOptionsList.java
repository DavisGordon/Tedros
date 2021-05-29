/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 08/11/2013
 */
package com.tedros.fxapi.annotation.control;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.fxapi.builder.ITGenericBuilder;
import com.tedros.fxapi.builder.NullEntityBuilder;
import com.tedros.fxapi.domain.TOptionProcessType;
import com.tedros.fxapi.presenter.model.TModelView;
import com.tedros.fxapi.process.TOptionsProcess;

/**
 * A set of information to build a option list
 *
 * @author Davis Gordon
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TOptionsList  {
	 
	/**
	 * The EJB service name
	 * */
	public String serviceName();
	
	/**
	 * The custom process class
	 * */
	public Class<? extends TOptionsProcess> optionsProcessClass() default TOptionsProcess.class;
	
	/**
	 * The model view type
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TModelView> optionModelViewClass() default TModelView.class;
	
	/**
	 * The entity type
	 * */
	public Class<? extends ITEntity> entityClass() default ITEntity.class;
	
	/**
	 * The option process type
	 * */
	public TOptionProcessType optionProcessType() default TOptionProcessType.LIST_ALL;
	
	/**
	 * Required if optionProcessType is TOptionProcessType.SEARCH
	 * The build method must return an example entity with the values to be searched.
	 * */
	public Class<? extends ITGenericBuilder<? extends ITEntity>> exampleEntityBuilder() default NullEntityBuilder.class;
}
