/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 23/03/2015
 */
package org.tedros.fx.annotation.property;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.fx.annotation.TObservable;
import org.tedros.fx.annotation.TObservableValue;
import org.tedros.fx.annotation.parser.TBooleanPropertyParser;
import org.tedros.fx.annotation.parser.engine.TAnnotationParser;

import javafx.beans.property.BooleanProperty;

/**
 * <pre>
 * Specifies listeners to the {@link BooleanProperty}
 * </pre>
 *
 * @author Davis Gordon
 *
 */
@Target(value=ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TBooleanProperty {
	
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TBooleanPropertyParser.class}
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TAnnotationParser>[] parser() default {TBooleanPropertyParser.class};

	/**
	 * <pre>
	 * An ObservableValue is an entity that wraps a value and allows to observe 
	 * the value for changes. 
	 * </pre>
	 * @see TObservableValue
	 * */
	public TObservableValue observableValue() default @TObservableValue();
	
	/**
	 * <pre>
	 * An Observable is an entity that wraps content and allows to observe 
	 * the content for invalidations.
	 * </pre>
	 * @see TObservable
	 * */
	public TObservable observable() default @TObservable();
	
	/**
	 * <pre>
	 * Set true to enable the parser execution 
	 * </pre>
	 * */
	public boolean parse();
}
