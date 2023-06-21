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

import org.tedros.api.presenter.view.TViewMode;

/**
 * <pre>
 * Build a trigger listener to be called.
 * </pre>
 *
 * @author Davis Gordon
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TTrigger  {
	/**
	 * Runs it after the form be built
	 * */
	boolean runAfterFormBuild() default false;
	/***
	 * Sets a target field to be available on it
	 */
	String targetFieldName() default "";
	
	/**
	 * Sets fields to be be available on it
	 */
	String[] associatedFieldBox() default "";
	/***
	 * Define the view mode to trigger
	 */
	TViewMode[] mode() default {TViewMode.EDIT};
	
	/**
	 * The trigger class
	 */
	@SuppressWarnings("rawtypes")
	 Class<? extends org.tedros.fx.control.trigger.TTrigger> type();
	/**
	 * If true the trigger will be applied in the TFieldBox children list.
	 * Default false
	 */
	boolean triggerFieldBox() default false;
	
}
