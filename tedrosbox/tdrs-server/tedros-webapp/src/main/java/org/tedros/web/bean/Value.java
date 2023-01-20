/**
 * 
 */
package org.tedros.web.bean;

import java.io.Serializable;

/**
 * @author Davis Gordon
 *
 */
public class Value<T> implements Serializable {

	private static final long serialVersionUID = -192985955679090790L;
	
	private T obj;
	/**
	 * 
	 */
	public Value() {
		
	}
	/**
	 * @return the obj
	 */
	public T getObj() {
		return obj;
	}
	/**
	 * @param obj the obj to set
	 */
	public void setObj(T obj) {
		this.obj = obj;
	}

}
