/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 02/04/2014
 */
package org.tedros.fx.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Davis Gordon
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface TStringBooleanValues {
	public String[] trueCodes();
	public String[] falseCodes();
	public String trueValue() default "#{tedros.fxapi.label.true}";
	public String falseValue() default "#{tedros.fxapi.label.false}";
}
