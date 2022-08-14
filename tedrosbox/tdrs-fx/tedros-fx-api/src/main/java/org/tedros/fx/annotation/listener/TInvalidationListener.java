package org.tedros.fx.annotation.listener;

import org.tedros.fx.descriptor.TComponentDescriptor;

import javafx.beans.InvalidationListener;

public abstract class TInvalidationListener implements InvalidationListener {

	private TComponentDescriptor componentDescriptor;
	
	public TComponentDescriptor getComponentDescriptor() {
		return componentDescriptor;
	}
	
	public void setComponentDescriptor(TComponentDescriptor componentDescriptor) {
		this.componentDescriptor = componentDescriptor;
	}
	
}
