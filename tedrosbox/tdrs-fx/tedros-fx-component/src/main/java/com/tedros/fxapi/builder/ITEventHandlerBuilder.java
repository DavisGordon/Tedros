package com.tedros.fxapi.builder;

import javafx.event.Event;
import javafx.event.EventHandler;

/**
 * <pre>
 * Build a handler for events of a specific class / type.
 * 
 * T - the event class this handler can handle
 * </pre>
 * */
public interface ITEventHandlerBuilder<E extends Event> extends ITGenericBuilder<EventHandler<E>> {
	
}
