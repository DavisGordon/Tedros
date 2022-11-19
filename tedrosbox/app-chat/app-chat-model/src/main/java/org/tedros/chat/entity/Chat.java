/**
 * 
 */
package org.tedros.chat.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
	
	private Long totalReceivedMessages;
	private Long totalViewedMessages;
	private Long totalSentMessages;
	
	
	public Chat() {
		
	}
	
	public Chat(Long id) {
		super.setId(id);
	}
	
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
	 * @return the totalReceivedMessages
	 */
	public Long getTotalReceivedMessages() {
		return totalReceivedMessages;
	}

	/**
	 * @param totalReceivedMessages the totalReceivedMessages to set
	 */
	public void setTotalReceivedMessages(Long totalReceivedMessages) {
		this.totalReceivedMessages = totalReceivedMessages;
	}

	/**
	 * @return the totalSentMessages
	 */
	public Long getTotalSentMessages() {
		return totalSentMessages;
	}

	/**
	 * @param totalSentMessages the totalSentMessages to set
	 */
	public void setTotalSentMessages(Long totalSentMessages) {
		this.totalSentMessages = totalSentMessages;
	}

	/**
	 * @return the totalViewedMessages
	 */
	public Long getTotalViewedMessages() {
		return totalViewedMessages;
	}

	/**
	 * @param totalViewedMessages the totalViewedMessages to set
	 */
	public void setTotalViewedMessages(Long totalViewedMessages) {
		this.totalViewedMessages = totalViewedMessages;
	}

}
