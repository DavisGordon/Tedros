package org.tedros.fx.control.action;


import org.tedros.api.descriptor.ITComponentDescriptor;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;



public abstract class TEventHandler<T extends Event> implements EventHandler<T> {
	private ITComponentDescriptor descriptor;
	private EventType<T> eventType;
	
	public TEventHandler(EventType<T> eventType) {
		this.eventType = eventType;
	}

	/**
	 * @return the descriptor
	 */
	public ITComponentDescriptor getDescriptor() {
		return descriptor;
	}

	/**
	 * @param descriptor the descriptor to set
	 */
	public void setDescriptor(ITComponentDescriptor descriptor) {
		this.descriptor = descriptor;
	}

	/**
	 * @return the eventType
	 */
	public EventType<T> getEventType() {
		return eventType;
	}
}
