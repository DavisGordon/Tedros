/**
 * 
 */
package org.tedros.fx.annotation.query;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.tedros.server.query.TSelect;

@Retention(RUNTIME)
@Target(ANNOTATION_TYPE)
/**
 * @author Davis Gordon
 *
 */
public @interface TOrder {

	String alias() default TSelect.ALIAS;
	
	String field();
	
	String label() default "";
}
