package org.tedros.fx.annotation.listener;

import org.tedros.fx.descriptor.TComponentDescriptor;

import javafx.beans.value.ChangeListener;

public abstract class TChangeListener<T> implements ChangeListener<T> {

	private TComponentDescriptor componentDescriptor;
	
	public TComponentDescriptor getComponentDescriptor() {
		return componentDescriptor;
	}
	
	public void setComponentDescriptor(TComponentDescriptor componentDescriptor) {
		this.componentDescriptor = componentDescriptor;
	}
	
}
