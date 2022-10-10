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
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="to", nullable=false, updatable=false)
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
	
	
}
