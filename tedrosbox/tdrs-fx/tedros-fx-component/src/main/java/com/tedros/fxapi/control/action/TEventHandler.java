package com.tedros.fxapi.control.action;


import com.tedros.fxapi.descriptor.TComponentDescriptor;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;



public abstract class TEventHandler<T extends Event> implements EventHandler<T> {
	private TComponentDescriptor descriptor;
	private EventType<T> eventType;
	
	public TEventHandler(EventType<T> eventType) {
		this.eventType = eventType;
	}

	/**
	 * @return the descriptor
	 */
	public TComponentDescriptor getDescriptor() {
		return descriptor;
	}

	/**
	 * @param descriptor the descriptor to set
	 */
	public void setDescriptor(TComponentDescriptor descriptor) {
		this.descriptor = descriptor;
	}

	/**
	 * @return the eventType
	 */
	public EventType<T> getEventType() {
		return eventType;
	}
}
