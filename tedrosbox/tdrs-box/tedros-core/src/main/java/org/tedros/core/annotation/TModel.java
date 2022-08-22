/**
 * 
 */
package org.tedros.core.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.tedros.core.ITModule;
import org.tedros.core.model.ITModelView;
import org.tedros.server.model.ITModel;

@Documented
@Retention(RUNTIME)
@Target(ANNOTATION_TYPE)
/**
 * @author Davis Gordon
 *
 */
public @interface TModel {
	/**
	 * <ore>
	 * The ITModelView type which the view on the ITModule accept to load and edit.
	 * </pre>
	 * 
	 * */
	@SuppressWarnings("rawtypes")
	Class<? extends ITModelView> modelViewType();
	
	/**
	 * <ore>
	 * The ITModel type which the ITModelView wraps.
	 * </pre>
	 * */
	Class<? extends ITModel> modelType();
	
	/**
	 * <ore>
	 * The ITModule type which accept the ITModelView to load and edit.
	 * </pre>
	 * */
	Class<? extends ITModule> moduleType();
	
}
