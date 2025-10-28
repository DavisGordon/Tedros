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

import org.tedros.fx.annotation.query.TQuery;
import org.tedros.fx.model.TModelView;
import org.tedros.fx.process.TEntityProcess;

/**
 * A set of information to run an TEntityProcess
 *
 * @author Davis Gordon
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TProcess  {
	 
	/**
	 * The EJB service name
	 * */
	String service();
	
	/**
	 * The custom process class
	 * */
	@SuppressWarnings("rawtypes")
	Class<? extends TEntityProcess> type() default TEntityProcess.class;
	
	/**
	 * The model view type
	 * */
	@SuppressWarnings("rawtypes")
	Class<? extends TModelView> modelView() default TModelView.class;
	
	/**
	 * The query settings
	 * */
	TQuery query();
	
}
