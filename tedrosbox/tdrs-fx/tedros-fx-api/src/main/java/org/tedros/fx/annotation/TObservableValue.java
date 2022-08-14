package org.tedros.fx.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.fx.annotation.listener.TChangeListener;
import org.tedros.fx.annotation.parser.TAnnotationParser;
import org.tedros.fx.annotation.parser.TObservableValueParser;

import javafx.beans.value.ObservableValue;

/**
 * <pre>
 * An ObservableValue is an entity that wraps a value and allows to observe the value for changes. 
 * In general this interface should not be implemented directly but one of its sub-interfaces 
 * ( ObservableBooleanValue etc.).
 * 
 * The value of the ObservableValue can be requested with getValue().
 * 
 * An implementation of ObservableValue may support lazy evaluation, which means that the value is not 
 * immediately recomputed after changes, but lazily the next time the value is requested. 
 * All bindings and properties in this library support lazy evaluation.
 * 
 * An ObservableValue generates two types of events: change events and invalidation events. 
 * A change event indicates that the value has changed. An invalidation event is generated, if the current 
 * value is not valid anymore. This distinction becomes important, if the ObservableValue supports lazy evaluation, 
 * because for a lazily evaluated value one does not know if an invalid value really has changed until it is 
 * recomputed. For this reason, generating change events requires eager evaluation while invalidation events can be 
 * generated for eager and lazy implementations.
 * 
 * Implementations of this class should strive to generate as few events as possible to avoid wasting too much time 
 * in event handlers. Implementations in this library mark themselves as invalid when the first invalidation event occurs. 
 * They do not generate anymore invalidation events until their value is recomputed and valid again.
 * 
 * Two types of listeners can be attached to an ObservableValue: InvalidationListener to listen to invalidation events 
 * and ChangeListener to listen to change events.
 * 
 * Important note: attaching a ChangeListener enforces eager computation even if the implementation of the ObservableValue 
 * supports lazy evaluation.
 * </pre>
 * */
@Target(value=ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TObservableValue {

	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TObservableValueParser.class}
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TAnnotationParser>[] parser() default {TObservableValueParser.class};
	
	/**
	* <pre>
	* {@link ObservableValue} property
	* 
	*  Adds a ChangeListener which will be notified whenever the value of the ObservableValue changes. 
	*  If the same listener is added more than once, then it will be notified more than once. 
	*  That is, no check is made to ensure uniqueness.
	*  
	*  Note that the same actual ChangeListener instance may be safely registered for different ObservableValues.
	*  
	*  The ObservableValue stores a strong reference to the listener which will prevent the listener from being garbage 
	*  collected and may result in a memory leak. It is recommended to either unregister a listener by calling removeListener 
	*  after use or to use an instance of WeakChangeListener avoid this situation.
	*  
	* </pre>
	**/
	@SuppressWarnings("rawtypes")
	public Class<? extends TChangeListener> addListener() default TChangeListener.class;
}
