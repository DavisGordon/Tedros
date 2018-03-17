package com.tedros.fxapi.annotation.listener;

import com.tedros.fxapi.descriptor.TComponentDescriptor;

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
