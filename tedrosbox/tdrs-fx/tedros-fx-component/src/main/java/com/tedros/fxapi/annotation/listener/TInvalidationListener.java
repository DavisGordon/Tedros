package com.tedros.fxapi.annotation.listener;

import javafx.beans.InvalidationListener;

import com.tedros.fxapi.descriptor.TComponentDescriptor;

public abstract class TInvalidationListener implements InvalidationListener {

	private TComponentDescriptor componentDescriptor;
	
	public TComponentDescriptor getComponentDescriptor() {
		return componentDescriptor;
	}
	
	public void setComponentDescriptor(TComponentDescriptor componentDescriptor) {
		this.componentDescriptor = componentDescriptor;
	}
	
}
