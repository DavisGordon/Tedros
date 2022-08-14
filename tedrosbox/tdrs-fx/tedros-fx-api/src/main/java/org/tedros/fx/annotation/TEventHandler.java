package org.tedros.fx.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javafx.event.EventHandler;
import javafx.event.EventType;


/**
 * Handler for events of a specific class / type.
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface TEventHandler {

	/**
	 * the event type to associate with the given eventHandler
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends EventType> eventType();
	
	/**
	 * The default implementation of EventHandler, this is same to set null.
	 * */
	@SuppressWarnings("rawtypes")
	public abstract class DefaultEventHandler implements EventHandler{
		
	}
	
	/**
	 * the handler to register, or not set to unregister
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends EventHandler> eventHandler () default DefaultEventHandler.class;
	
	/**
	 * <pre>
	 * Force the parser execution 
	 * </pre>
	 * */
	public boolean parse();
	
}
