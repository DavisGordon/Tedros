/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 23/03/2015
 */
package com.tedros.fxapi.annotation.property;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javafx.beans.property.ReadOnlyBooleanProperty;

import com.tedros.fxapi.annotation.TObservable;
import com.tedros.fxapi.annotation.TObservableValue;
import com.tedros.fxapi.annotation.parser.TAnnotationParser;
import com.tedros.fxapi.annotation.parser.TReadOnlyBooleanPropertyParser;

/**
 * <pre>
 * Specifies listeners to the {@link ReadOnlyBooleanProperty}
 * </pre>
 *
 * @author Davis Gordon
 *
 */
@Target(value=ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TReadOnlyBooleanProperty {
	
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TReadOnlyBooleanPropertyParser.class}
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TAnnotationParser>[] parser() default {TReadOnlyBooleanPropertyParser.class};

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
