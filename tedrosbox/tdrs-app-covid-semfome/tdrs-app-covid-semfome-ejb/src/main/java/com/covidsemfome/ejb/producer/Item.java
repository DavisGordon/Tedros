/**
 * 
 */
package com.covidsemfome.ejb.producer;

/**
 * @author Davis Gordon
 *
 */
public class Item<T> {

	private T value;

	public Item() {
		
	}
	/**
	 * @param value
	 */
	public Item(T value) {
		super();
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public T getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return value.toString();
	}
}
