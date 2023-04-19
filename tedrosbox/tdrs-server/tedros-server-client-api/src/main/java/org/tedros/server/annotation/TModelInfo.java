/**
 * 
 */
package org.tedros.server.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(RUNTIME)
@Target(TYPE)
/**
 * The model info
 * 
 * @author Davis Gordon
 *
 */
public @interface TModelInfo {
	String value();
}
