/**
 * 
 */
package org.tedros.core.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 
 * Register all ITModelView types by associating them with a TModule
 * Used when another application wants to load one of them.
 * 
 * @author Davis Gordon
 *
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface TLoadable {

	TModel[] value();
}
