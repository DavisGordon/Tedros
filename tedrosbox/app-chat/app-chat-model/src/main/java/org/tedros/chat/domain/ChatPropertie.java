/**
 * 
 */
package org.tedros.chat.domain;

/**
 * @author Davis Gordon
 *
 */
public enum ChatPropertie {
	
	CHAT_SERVER_IP("chat.server.ip", "Define the chat server ip"),
	CHAT_SERVER_PORT("chat.server.port","Define the chat server port");
	
	private String value;
	private String description;
	
	private ChatPropertie(String v, String description) {
		this.value = v;
		this.description = description;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
}
