package org.tedros.fx.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.fx.annotation.listener.TInvalidationListener;
import org.tedros.fx.annotation.parser.TObservableParser;
import org.tedros.fx.annotation.parser.engine.TAnnotationParser;

import javafx.beans.value.ObservableValue;


/**
 * <pre>
 * An Observable is an entity that wraps content and allows to observe the content for invalidations.
 * 
 * An implementation of Observable may support lazy evaluation, which means that the content is not immediately 
 * recomputed after changes, but lazily the next time it is requested. All bindings and properties in this library 
 * support lazy evaluation.
 * 
 * Implementations of this class should strive to generate as few events as possible to avoid wasting too much time 
 * in event handlers. 
 * 
 * Implementations in this library mark themselves as invalid when the first invalidation event occurs. 
 * They do not generate anymore invalidation events until their value is recomputed and valid again.
 * </pre>
 * */
@Target(value=ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TObservable {

	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TObservableParser.class}
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TAnnotationParser>[] parser() default {TObservableParser.class};
	
	/**
	* <pre>
	* {@link ObservableValue} property
	* 
	*  Adds an InvalidationListener which will be notified whenever the Observable becomes invalid. 
	*  If the same listener is added more than once, then it will be notified more than once. 
	*  That is, no check is made to ensure uniqueness.
	*  
	*  Note that the same actual InvalidationListener instance may be safely registered for different Observables.
	*  
	*  The Observable stores a strong reference to the listener which will prevent the listener from being garbage 
	*  collected and may result in a memory leak. It is recommended to either unregister a listener by calling 
	*  removeListener after use or to use an instance of WeakInvalidationListener avoid this situation.
	*  
	* </pre>
	**/
	public Class<? extends TInvalidationListener> addListener() default TInvalidationListener.class;
}
