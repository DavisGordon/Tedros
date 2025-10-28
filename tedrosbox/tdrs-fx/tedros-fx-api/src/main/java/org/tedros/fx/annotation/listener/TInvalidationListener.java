package org.tedros.fx.annotation.listener;

import org.tedros.api.descriptor.ITComponentDescriptor;

import javafx.beans.InvalidationListener;

public abstract class TInvalidationListener implements InvalidationListener {

	private ITComponentDescriptor componentDescriptor;
	
	public ITComponentDescriptor getComponentDescriptor() {
		return componentDescriptor;
	}
	
	public void setComponentDescriptor(ITComponentDescriptor componentDescriptor) {
		this.componentDescriptor = componentDescriptor;
	}
	
}
