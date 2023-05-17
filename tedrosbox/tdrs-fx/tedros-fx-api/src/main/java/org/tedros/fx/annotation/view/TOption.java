/**
 * 
 */
package org.tedros.fx.annotation.view;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.server.query.TSelect;


/**
 * @author Davis Gordon
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ANNOTATION_TYPE})
public @interface TOption {

	public String text();
	public String field();
	public String alias() default TSelect.ALIAS;
}
