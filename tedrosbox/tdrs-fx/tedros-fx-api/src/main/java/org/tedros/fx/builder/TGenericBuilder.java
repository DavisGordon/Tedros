/**
 * 
 */
package org.tedros.fx.builder;

import org.tedros.api.descriptor.ITComponentDescriptor;

/**
 * The generic type builder.
 * Use the getComponentDescriptor() method 
 * to access the model and the form fields.
 *  
 * @author Davis Gordon
 */
public abstract class TGenericBuilder<T> implements ITGenericBuilder<T> {

	private ITComponentDescriptor descriptor;
	
	/**
	 * 
	 */
	public TGenericBuilder() {
	}

	/**
	 * @return the ITComponentDescriptor with the model and form fields
	 */
	@Override
	public ITComponentDescriptor getComponentDescriptor() {
		return descriptor;
	}

	@Override
	public void setComponentDescriptor(ITComponentDescriptor componentDescriptor) {
		this.descriptor = componentDescriptor;
	}


}
