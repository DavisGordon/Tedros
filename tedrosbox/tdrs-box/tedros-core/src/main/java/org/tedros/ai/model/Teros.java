/**
 * 
 */
package org.tedros.ai.model;

import java.util.List;

import org.tedros.server.model.ITModel;

/**
 * @author Davis Gordon
 *
 */
public class Teros implements ITModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Message> messages;
	

	/**
	 * @return the messages
	 */
	public List<Message> getMessages() {
		return messages;
	}


	/**
	 * @param messages the messages to set
	 */
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

}
