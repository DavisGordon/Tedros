/**
 * 
 */
package org.tedros.core.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.tedros.api.presenter.view.ITDynaView;
import org.tedros.api.presenter.view.ITView;
import org.tedros.core.model.ITModelView;
import org.tedros.server.model.ITModel;

@Documented
@Retention(RUNTIME)
@Target(ANNOTATION_TYPE)
/**
 * @author Davis Gordon
 *
 */
public @interface TItem {
	
	/**
	 * The view title
	 */
	String title();
	
	/**
	 * The view description
	 */
	String description() default "";
	
	/**
	 * The view type.
	 * @see {@link ITDynaView}
	 */
	@SuppressWarnings("rawtypes")
	Class<? extends ITView> view() default ITView.class;
	
	/**
	 * <ore>
	 * The ITModelView type which the view on the ITModule accept to load and edit.
	 * </pre>
	 * 
	 * */
	@SuppressWarnings("rawtypes")
	Class<? extends ITModelView> modelView() default ITModelView.class;
	
	/**
	 * <ore>
	 * The ITModel type which the ITModelView wraps.
	 * </pre>
	 * */
	Class<? extends ITModel> model() default ITModel.class;
	
	/**
	 * Group the view header for grouped views.
	 */
	boolean groupHeaders() default false;
	
	
}
