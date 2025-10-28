/**
 * 
 */
package org.tedros.server.model;

/**
 * @author Davis Gordon
 *
 */
public interface ITValueModel<T> extends ITModel{

	T getValue();
	
	void setValue(T value);
}
