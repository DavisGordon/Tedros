package com.tedros.fxapi.control.action;

import javafx.event.Event;



public abstract class TActionEvent {
	public abstract boolean runBefore(Event arg0);
	public abstract boolean runAfter(Event arg0);
}
