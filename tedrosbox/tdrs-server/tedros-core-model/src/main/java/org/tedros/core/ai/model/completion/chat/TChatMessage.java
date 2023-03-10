/**
 * 
 */
package org.tedros.core.ai.model.completion.chat;

import org.tedros.server.model.ITModel;


/**
 * @author Davis Gordon
 *
 */
public class TChatMessage implements ITModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1046169658340032454L;
	/**
	 * Must be either 'system', 'user', or 'assistant'.<br>
	 * You may use {@link ChatMessageRole} enum.
	 */
	private TChatRole role;
	private String content;
	/**
	 * 
	 */
	public TChatMessage() {
	}
	/**
	 * @param role
	 * @param content
	 */
	public TChatMessage(TChatRole role, String content) {
		this.role = role;
		this.content = content;
	}
	/**
	 * @return the role
	 */
	public TChatRole getRole() {
		return role;
	}
	/**
	 * @param role the role to set
	 */
	public void setRole(TChatRole role) {
		this.role = role;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

}
