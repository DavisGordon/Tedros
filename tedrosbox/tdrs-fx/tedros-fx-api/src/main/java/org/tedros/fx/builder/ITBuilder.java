package org.tedros.fx.builder;

import org.tedros.api.descriptor.ITComponentDescriptor;

public interface ITBuilder {
	
	public ITComponentDescriptor getComponentDescriptor();
	
	public void setComponentDescriptor(ITComponentDescriptor componentDescriptor);
	
}
