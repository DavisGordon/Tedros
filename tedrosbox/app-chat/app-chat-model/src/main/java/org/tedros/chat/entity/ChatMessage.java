/**
 * 
 */
package org.tedros.chat.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.tedros.chat.domain.DomainSchema;
import org.tedros.chat.domain.DomainTables;
import org.tedros.common.model.TFileEntity;
import org.tedros.server.entity.TEntity;

/**
 * @author Davis Gordon
 *
 */

@Entity
@Table(name=DomainTables.message, schema=DomainSchema.schema)
public class ChatMessage extends TEntity implements Comparable<ChatMessage>{

	private static final long serialVersionUID = -8257824754212403138L;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="from", nullable=false, updatable=false)
	private ChatUser from;
	
	/*@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="to", nullable=false, updatable=false)*/
	private ChatUser to;
	
	@Column(length=2000)
	private String content;
	
	@ManyToOne(fetch=FetchType.EAGER, 
			cascade=CascadeType.ALL)
	@JoinColumn(name="file", updatable=false)
	private TFileEntity file;
	
	@Column(length=15)
	@Enumerated(EnumType.STRING)
	private TStatus status;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_chat", nullable=false)
	private Chat chat;
	
	@Column(nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTime; 
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="replied_to", nullable=true, updatable=false)
	private ChatMessage repliedTo;
	
	public ChatMessage() {
		this.dateTime = new Date();
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the file
	 */
	public TFileEntity getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(TFileEntity file) {
		this.file = file;
	}

	/**
	 * @return the status
	 */
	public TStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(TStatus status) {
		this.status = status;
	}

	/**
	 * @return the from
	 */
	public ChatUser getFrom() {
		return from;
	}

	/**
	 * @param from the from to set
	 */
	public void setFrom(ChatUser from) {
		this.from = from;
	}

	/**
	 * @return the to
	 */
	public ChatUser getTo() {
		return to;
	}

	/**
	 * @param to the to to set
	 */
	public void setTo(ChatUser to) {
		this.to = to;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @return the chat
	 */
	public Chat getChat() {
		return chat;
	}

	/**
	 * @param chat the chat to set
	 */
	public void setChat(Chat chat) {
		this.chat = chat;
	}

	/**
	 * @return the dateTime
	 */
	public Date getDateTime() {
		return dateTime;
	}

	/**
	 * @param dateTime the dateTime to set
	 */
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	@Override
	public int compareTo(ChatMessage o) {
		return this.dateTime.compareTo(o.getDateTime());
	}

	/**
	 * @return the repliedTo
	 */
	public ChatMessage getRepliedTo() {
		return repliedTo;
	}

	/**
	 * @param repliedTo the repliedTo to set
	 */
	public void setRepliedTo(ChatMessage repliedTo) {
		this.repliedTo = repliedTo;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((chat == null) ? 0 : chat.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((dateTime == null) ? 0 : dateTime.hashCode());
		result = prime * result + ((file == null) ? 0 : file.hashCode());
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((repliedTo == null) ? 0 : repliedTo.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof ChatMessage))
			return false;
		ChatMessage other = (ChatMessage) obj;
		if (chat == null) {
			if (other.chat != null)
				return false;
		} else if (!chat.equals(other.chat))
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (dateTime == null) {
			if (other.dateTime != null)
				return false;
		} else if (!dateTime.equals(other.dateTime))
			return false;
		if (file == null) {
			if (other.file != null)
				return false;
		} else if (!file.equals(other.file))
			return false;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (repliedTo == null) {
			if (other.repliedTo != null)
				return false;
		} else if (!repliedTo.equals(other.repliedTo))
			return false;
		if (status != other.status)
			return false;
		if (to == null) {
			if (other.to != null)
				return false;
		} else if (!to.equals(other.to))
			return false;
		return true;
	}
	
	
}
