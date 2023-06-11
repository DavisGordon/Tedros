/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 08/11/2013
 */
package org.tedros.fx.annotation.control;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.fx.builder.ITGenericBuilder;
import org.tedros.fx.builder.NullEntityBuilder;
import org.tedros.fx.domain.TOptionProcessType;
import org.tedros.fx.model.TModelView;
import org.tedros.fx.process.TOptionsProcess;
import org.tedros.server.entity.ITEntity;

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
	@SuppressWarnings("rawtypes")
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
