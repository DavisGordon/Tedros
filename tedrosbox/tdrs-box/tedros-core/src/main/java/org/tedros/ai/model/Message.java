/**
 * 
 */
package org.tedros.ai.model;

import org.tedros.server.model.ITModel;

/**
 * @author Davis Gordon
 *
 */
public class Message implements ITModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String content;	
	private String user;
	
	/**
	 * @param content
	 * @param user
	 */
	public Message(String content, String user) {
		super();
		this.content = content;
		this.user = user;
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

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

}
