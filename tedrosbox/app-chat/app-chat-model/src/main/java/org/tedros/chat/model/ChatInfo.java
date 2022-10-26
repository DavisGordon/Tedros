/**
 * 
 */
package org.tedros.chat.model;

import java.io.Serializable;

import org.tedros.chat.entity.ChatUser;

/**
 * @author Davis Gordon
 *
 */
public class ChatInfo implements Serializable {

	private static final long serialVersionUID = 6041866320727934348L;
	
	private String code;
	private ChatUser owner;
	
	public ChatInfo() {
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}


	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}


	/**
	 * @return the owner
	 */
	public ChatUser getOwner() {
		return owner;
	}


	/**
	 * @param owner the owner to set
	 */
	public void setOwner(ChatUser owner) {
		this.owner = owner;
	}

}
