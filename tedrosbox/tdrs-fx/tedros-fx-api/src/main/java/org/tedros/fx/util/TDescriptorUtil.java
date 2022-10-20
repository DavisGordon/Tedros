/**
 * 
 */
package org.tedros.fx.util;

import org.tedros.api.descriptor.ITComponentDescriptor;
import org.tedros.api.descriptor.ITFieldDescriptor;

import javafx.scene.Node;

/**
 * @author Davis Gordon
 *
 */
public final class TDescriptorUtil {

	/**
	 * 
	 */
	private TDescriptorUtil() {
		// TODO Auto-generated constructor stub
	}
	
	public static Node getComponent(String fieldName, ITComponentDescriptor descriptor) {
		ITFieldDescriptor fd = descriptor.getFieldDescriptor();
		if(fd.hasParent())
			return null;
		Node node = null;
		if(fd.getFieldName().equals(fieldName)) {
			node = fd.getComponent();
		}else {
			fd = descriptor.getFieldDescriptor(fieldName);
			if(fd==null)
				throw new RuntimeException("The field "+fieldName+" was not found in "
			+ descriptor.getModelView().getClass().getSimpleName());
			
			fd.setHasParent(true);
			node = fd.hasLayout() 
					 ? fd.getLayout()
							 : fd.getComponent();
		}
		return node;
	}

}
