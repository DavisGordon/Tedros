/**
 * 
 */
package org.tedros.fx.builder;

import org.tedros.fx.descriptor.TComponentDescriptor;

/**
 * @author Davis Gordon
 *
 */
public abstract class TGenericBuilder<T> implements ITGenericBuilder<T> {

	private TComponentDescriptor descriptor;
	
	/**
	 * 
	 */
	public TGenericBuilder() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public TComponentDescriptor getComponentDescriptor() {
		return descriptor;
	}

	@Override
	public void setComponentDescriptor(TComponentDescriptor componentDescriptor) {
		this.descriptor = componentDescriptor;
	}


}
