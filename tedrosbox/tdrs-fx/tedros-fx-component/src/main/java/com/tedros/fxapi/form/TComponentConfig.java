/**
 * 
 */
package com.tedros.fxapi.form;

import com.tedros.fxapi.descriptor.TComponentDescriptor;

/**
 * @author Davis Gordon
 *
 */
public abstract class TComponentConfig<T> {
	
	private TComponentDescriptor descriptor;
	
	private T component;

	/**
	 * @param descriptor
	 * @param component
	 */
	public TComponentConfig(TComponentDescriptor descriptor, T component) {
		this.descriptor = descriptor;
		this.component = component;
	}
	
	public abstract void config();

	/**
	 * @return the descriptor
	 */
	public TComponentDescriptor getDescriptor() {
		return descriptor;
	}

	/**
	 * @return the component
	 */
	public T getComponent() {
		return component;
	}

}
