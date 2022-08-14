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

import org.tedros.fx.annotation.TObservableValue;
import org.tedros.fx.annotation.parser.TAnnotationParser;
import org.tedros.fx.annotation.parser.TObservableListPropertyParser;

import javafx.beans.property.ObjectProperty;

/**
 * <pre>
 * Specifies listeners to the {@link ObjectProperty}
 * </pre>
 *
 * @author Davis Gordon
 *
 */
@Target(value=ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TObservableListProperty {
	
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TObservableListPropertyParser.class}
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TAnnotationParser>[] parser() default {TObservableListPropertyParser.class};

	/**
	 * <pre>
	 * Add a list of String 
	 * </pre>
	 * @see TObservableValue
	 * */
	public String[] addAll() default {};
	
	/**
	 * <pre>
	 * Set true to enable the parser execution 
	 * </pre>
	 * */
	public boolean parse();
	
	
}
