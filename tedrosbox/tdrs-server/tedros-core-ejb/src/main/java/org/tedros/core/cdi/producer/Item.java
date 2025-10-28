<<<<<<<< HEAD:tedrosbox/app-solidarity/app-solidarity-ejb/src/main/java/com/solidarity/ejb/producer/Item.java
/**
 * 
 */
package com.solidarity.ejb.producer;

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
========
/**
 * 
 */
package org.tedros.core.cdi.producer;

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
	public T get() {
		return value;
	}
	
	@Override
	public String toString() {
		return value.toString();
	}
}
>>>>>>>> tedrosbox_v17_globalweb:tedrosbox/tdrs-server/tedros-core-ejb/src/main/java/org/tedros/core/cdi/producer/Item.java
