/**
 * 
 */
package org.tedros.fx.annotation.query;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.tedros.server.entity.ITEntity;
import org.tedros.server.query.TSelect;

@Retention(RUNTIME)
@Target({ANNOTATION_TYPE, PARAMETER})
/**
 * @author Davis Gordon
 *
 */
public @interface TQuery {
	
	Class<? extends ITEntity> entity();
	
	String alias() default TSelect.ALIAS;
	
	TJoin[] join() default {};
	
	TCondition[] condition() default {};
	
	TOrder[] orderBy() default {};
	
	boolean asc() default true;
	

}
