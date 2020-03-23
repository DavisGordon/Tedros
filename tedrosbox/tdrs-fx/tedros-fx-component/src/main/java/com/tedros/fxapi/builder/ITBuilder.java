package com.tedros.fxapi.builder;

import com.tedros.fxapi.descriptor.TComponentDescriptor;

public interface ITBuilder {
	
	public TComponentDescriptor getComponentDescriptor();
	
	public void setComponentDescriptor(TComponentDescriptor componentDescriptor);
	
}
