/**
 * 
 */
package org.tedros.chat.domain;

/**
 * @author Davis Gordon
 *
 */
public enum ChatPropertie {
	
	SERVER_IP("chat.server.ip"),
	SERVER_PORT("chat.server.port");
	
	private String value;
	
	private ChatPropertie(String v) {
		this.value = v;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
}
