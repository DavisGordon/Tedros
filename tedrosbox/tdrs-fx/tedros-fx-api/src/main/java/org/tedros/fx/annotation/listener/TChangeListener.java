package org.tedros.fx.annotation.listener;

import org.tedros.api.descriptor.ITComponentDescriptor;

import javafx.beans.value.ChangeListener;

public abstract class TChangeListener<T> implements ChangeListener<T> {

	private ITComponentDescriptor componentDescriptor;
	
	public ITComponentDescriptor getComponentDescriptor() {
		return componentDescriptor;
	}
	
	public void setComponentDescriptor(ITComponentDescriptor componentDescriptor) {
		this.componentDescriptor = componentDescriptor;
	}
	
}
