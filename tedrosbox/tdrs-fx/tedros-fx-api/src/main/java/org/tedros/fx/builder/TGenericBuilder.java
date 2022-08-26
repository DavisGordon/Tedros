/**
 * 
 */
package org.tedros.fx.builder;

import org.tedros.api.descriptor.ITComponentDescriptor;

/**
 * @author Davis Gordon
 *
 */
public abstract class TGenericBuilder<T> implements ITGenericBuilder<T> {

	private ITComponentDescriptor descriptor;
	
	/**
	 * 
	 */
	public TGenericBuilder() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public ITComponentDescriptor getComponentDescriptor() {
		return descriptor;
	}

	@Override
	public void setComponentDescriptor(ITComponentDescriptor componentDescriptor) {
		this.descriptor = componentDescriptor;
	}


}
