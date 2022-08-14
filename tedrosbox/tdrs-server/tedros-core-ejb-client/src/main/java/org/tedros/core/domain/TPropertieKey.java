/**
 * 
 */
package org.tedros.core.domain;

/**
 * @author Davis Gordon
 *
 */
public enum TPropertieKey {

	ENABLE_INITIAL_CONFIG ("sys.enable.initial.config");
	
	private String value;
	
	private TPropertieKey(String k) {
		this.value = k;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	
	
}
