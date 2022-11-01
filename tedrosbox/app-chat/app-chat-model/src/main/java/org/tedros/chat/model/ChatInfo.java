/**
 * 
 */
package org.tedros.chat.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.tedros.chat.entity.ChatUser;

/**
 * @author Davis Gordon
 *
 */
public class ChatInfo implements Serializable {

	private static final long serialVersionUID = 6041866320727934348L;
	
	private Long id;
	private ChatUser user;
	private Action action;
	

	private List<ChatUser> recipients;
	
	public ChatInfo() {
	}
	
	public void addRecipient(ChatUser... usr) {
		if(this.recipients==null)
			this.recipients = new ArrayList<>();
		this.recipients.addAll(Arrays.asList(usr));
	}
	
	public void removeRecipient(ChatUser usr) {
		if(this.recipients==null)
			return;
		this.recipients.remove(usr);
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the user
	 */
	public ChatUser getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(ChatUser user) {
		this.user = user;
	}

	/**
	 * @return the action
	 */
	public Action getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(Action action) {
		this.action = action;
	}

	/**
	 * @return the recipients
	 */
	public List<ChatUser> getRecipients() {
		return recipients;
	}

	/**
	 * @param recipients the recipients to set
	 */
	public void setRecipients(List<ChatUser> recipients) {
		this.recipients = recipients;
	}

}
