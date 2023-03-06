/**
 * 
 */
package org.tedros.core.ai.model;

import org.tedros.server.model.ITModel;

/**
 * @author Davis Gordon
 *
 */
public class TImage implements ITModel {

	 /**
	 * 
	 */
	private static final long serialVersionUID = 4134489944586948518L;

	/**
     * The URL where the image can be accessed 
     * or Base64 encoded image string.
     */
    private String value;

    private TResponseFormat format;
	
	/**
	 * 
	 */
	public TImage() {
	}

	/**
	 * @param value
	 * @param format
	 */
	public TImage(String value, TResponseFormat format) {
		this.value = value;
		this.format = format;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the format
	 */
	public TResponseFormat getFormat() {
		return format;
	}

	/**
	 * @param format the format to set
	 */
	public void setFormat(TResponseFormat format) {
		this.format = format;
	}

}
