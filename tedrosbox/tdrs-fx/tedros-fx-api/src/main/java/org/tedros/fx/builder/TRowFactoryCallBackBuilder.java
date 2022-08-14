/**
 * 
 */
package org.tedros.fx.builder;

import org.tedros.fx.descriptor.TComponentDescriptor;

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
	 * @see org.tedros.fx.builder.ITBuilder#getComponentDescriptor()
	 */
	@Override
	public TComponentDescriptor getComponentDescriptor() {
		return descriptor;
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.builder.ITBuilder#setComponentDescriptor(org.tedros.fx.descriptor.TComponentDescriptor)
	 */
	@Override
	public void setComponentDescriptor(TComponentDescriptor componentDescriptor) {
		this.descriptor = componentDescriptor;
	}

}
