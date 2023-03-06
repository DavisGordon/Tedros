/**
 * 
 */
package org.tedros.core.ai.model;

/**
 * @author Davis Gordon
 *
 */
public enum TResponseFormat {

	URL ("url"),
	BASE64 ("b64_json");
	
	private String value;

	/**
	 * @param value
	 */
	private TResponseFormat(String value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	
	
}
