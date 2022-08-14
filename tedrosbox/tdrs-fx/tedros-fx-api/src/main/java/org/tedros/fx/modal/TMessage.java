/**
 * 
 */
package org.tedros.fx.modal;

/**
 * @author Davis Gordon
 *
 */
public class TMessage {
	
	private TMessageType type;
	
	private String value;

	/**
	 * @param type
	 * @param value
	 */
	public TMessage(TMessageType type, String value) {
		this.type = type;
		this.value = value;
	}

	/**
	 * @return the type
	 */
	public TMessageType getType() {
		return type;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

}
