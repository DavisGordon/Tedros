/**
 * 
 */
package org.tedros.fx.component;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.tedros.fx.builder.ITFieldBuilder;
import org.tedros.fx.builder.TComponentBuilder;


/**
 * <pre>
 * Build a custom component
 * 
 * @author Davis Gordon
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface TComponent {
	
	/**
	 *<pre>
	 * The builder of type {@link ITFieldBuilder} for this component.
	 * 
	 *  Default value: {@link TComponentBuilder}
	 *</pre> 
	 * */
	Class<? extends ITFieldBuilder> builder() default TComponentBuilder.class;
	
	Class<? extends ITComponent> type();
	
}
