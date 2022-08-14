/**
 * 
 */
package org.tedros.server.model;

/**
 * @author Davis Gordon
 *
 */
public class TItemModel<T> implements ITModel {

	private static final long serialVersionUID = -9134181205493195454L;
	
	private String key;
	
	private T value;
	

	/**
	 * 
	 */
	public TItemModel() {
	}


	/**
	 * @param key
	 * @param value
	 */
	public TItemModel(String key, T value) {
		this.key = key;
		this.value = value;
	}


	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}


	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}


	/**
	 * @return the value
	 */
	public T getValue() {
		return value;
	}


	/**
	 * @param value the value to set
	 */
	public void setValue(T value) {
		this.value = value;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return (value != null 
				?  (value instanceof String) ? (String) value : value.toString() 
						: "");
	}

}
