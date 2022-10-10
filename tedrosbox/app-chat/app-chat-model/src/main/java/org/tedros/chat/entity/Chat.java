/**
 * 
 */
package org.tedros.chat.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.tedros.chat.domain.DomainSchema;
import org.tedros.chat.domain.DomainTables;
import org.tedros.server.entity.TEntity;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name=DomainTables.chat, schema=DomainSchema.schema)
public class Chat extends TEntity {

	private static final long serialVersionUID = -5878340620231378744L;

	@Column(length=255, nullable=false)
	private String code;
	
	@Column(length=60)
	private String title;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="user_id", nullable=false, updatable=false)
	private ChatUser owner;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name=DomainTables.chat_user, 
		schema=DomainSchema.schema,
		joinColumns=@JoinColumn(name="chat_id"), 
		inverseJoinColumns=@JoinColumn(name="user_id"))
	private Set<ChatUser> participants;
	
	@OneToMany(fetch=FetchType.EAGER, 
		cascade=CascadeType.ALL, mappedBy="chat")
	private Set<ChatMessage> messages;
	
	public void addParticipant(ChatUser usr) {
		if(this.participants==null)
			this.participants = new HashSet<>();
		this.participants.add(usr);
	}
	
	public void removeParticipant(ChatUser usr) {
		if(this.participants==null)
			return;
		this.participants.remove(usr);
	}
	
	public void addMessage(ChatMessage msg) {
		if(messages==null)
			this.messages = new HashSet<>();
		this.messages.add(msg);
	}
	
	public void removeMessage(ChatMessage msg) {
		if(this.messages==null)
			return;
		this.messages.remove(msg);
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
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
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

	/**
	 * @return the participants
	 */
	public Set<ChatUser> getParticipants() {
		return participants;
	}

	/**
	 * @param participants the participants to set
	 */
	public void setParticipants(Set<ChatUser> participants) {
		this.participants = participants;
	}

	/**
	 * @return the messages
	 */
	public Set<ChatMessage> getMessages() {
		return messages;
	}

	/**
	 * @param messages the messages to set
	 */
	public void setMessages(Set<ChatMessage> messages) {
		this.messages = messages;
	}

}
