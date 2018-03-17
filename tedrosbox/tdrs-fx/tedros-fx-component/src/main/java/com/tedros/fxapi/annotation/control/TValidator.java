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

/**
 * Sets a field to be validated.
 *
 * @author Davis Gordon
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TValidator  {
	/**
	 * A list of fields associated of this field to be passed to the validator class.
	 * */
	public String[] associatedFieldsName() default "";
	
	/**
	 * The validator class of type {@link com.tedros.fxapi.control.validator.TValidator}
	 * */
	public Class<? extends com.tedros.fxapi.control.validator.TValidator> validatorClass();
	
}
