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

import com.tedros.fxapi.domain.TViewMode;

/**
 * <pre>
 * Build a listener to be called when the control action is executed.
 * </pre>
 *
 * @author Davis Gordon
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TTrigger  {
	public boolean runAfterFormBuild() default false;
	public String targetFieldName() default "";
	public String[] associatedFieldBox() default "";
	public TViewMode[] mode() default {TViewMode.EDIT};
	public Class<? extends com.tedros.fxapi.control.trigger.TTrigger> triggerClass();
	
}
