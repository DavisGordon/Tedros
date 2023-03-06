/**
 * 
 */
package org.tedros.core.ai.model;

/**
 * @author Davis Gordon
 *
 */
public enum TImageSize {

	X256 ("256x256"),
	X512 ("512x512"),
	X1024 ("1024x1024");
	
	private String value;

	/**
	 * @param value
	 */
	private TImageSize(String value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	
	
}
