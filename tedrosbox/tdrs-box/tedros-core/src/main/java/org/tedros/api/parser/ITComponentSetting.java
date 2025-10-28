/**
 * 
 */
package org.tedros.api.parser;

import java.lang.annotation.Annotation;

import org.tedros.api.descriptor.ITComponentDescriptor;

import javafx.scene.Node;

/**
 * Specify a custom class to setting a component.
 * 
 * @author Davis Gordon
 *
 */
public interface ITComponentSetting {
	
	/**
	 * Apply the settings in the component.
	 * 
	 * If the return is false the annotation parse for the component will not be called.
	 * */
	<A extends Annotation, N extends Node> boolean apply(A annotation, N component, ITComponentDescriptor descriptor);
	
}
