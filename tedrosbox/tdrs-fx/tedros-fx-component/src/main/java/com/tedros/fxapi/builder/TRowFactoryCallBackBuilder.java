/**
 * 
 */
package com.tedros.fxapi.builder;

import com.tedros.fxapi.descriptor.TComponentDescriptor;

/**
 * @author Davis Gordon
 *
 */
public abstract class TRowFactoryCallBackBuilder<S> implements ITRowFactoryCallBackBuilder<S> {

	private TComponentDescriptor descriptor;
	
	/**
	 * 
	 */
	public TRowFactoryCallBackBuilder() {
	}


	/* (non-Javadoc)
	 * @see com.tedros.fxapi.builder.ITBuilder#getComponentDescriptor()
	 */
	@Override
	public TComponentDescriptor getComponentDescriptor() {
		return descriptor;
	}

	/* (non-Javadoc)
	 * @see com.tedros.fxapi.builder.ITBuilder#setComponentDescriptor(com.tedros.fxapi.descriptor.TComponentDescriptor)
	 */
	@Override
	public void setComponentDescriptor(TComponentDescriptor componentDescriptor) {
		this.descriptor = componentDescriptor;
	}

}
