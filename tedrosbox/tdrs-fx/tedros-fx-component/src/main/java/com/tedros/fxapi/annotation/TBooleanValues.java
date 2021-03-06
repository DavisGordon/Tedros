/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 02/04/2014
 */
package com.tedros.fxapi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Boolean values
 *
 * @author Davis Gordon
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface TBooleanValues {
	public String trueValue() default "#{tedros.fxapi.label.true}";
	public String falseValue() default "#{tedros.fxapi.label.false}";
}
