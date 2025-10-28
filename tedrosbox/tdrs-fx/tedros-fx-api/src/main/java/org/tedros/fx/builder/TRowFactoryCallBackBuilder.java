/**
 * 
 */
package org.tedros.fx.builder;

import org.tedros.api.descriptor.ITComponentDescriptor;

/**
 * @author Davis Gordon
 *
 */
public abstract class TRowFactoryCallBackBuilder<S> implements ITRowFactoryCallBackBuilder<S> {

	private ITComponentDescriptor descriptor;
	
	/**
	 * 
	 */
	public TRowFactoryCallBackBuilder() {
	}


	/* (non-Javadoc)
	 * @see org.tedros.fx.builder.ITBuilder#getComponentDescriptor()
	 */
	@Override
	public ITComponentDescriptor getComponentDescriptor() {
		return descriptor;
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.builder.ITBuilder#setComponentDescriptor(org.tedros.fx.descriptor.TComponentDescriptor)
	 */
	@Override
	public void setComponentDescriptor(ITComponentDescriptor componentDescriptor) {
		this.descriptor = componentDescriptor;
	}

}
