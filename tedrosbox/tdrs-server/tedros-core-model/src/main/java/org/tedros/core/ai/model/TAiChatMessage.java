/**
 * 
 */
package org.tedros.core.ai.model;

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

import org.tedros.core.ai.model.completion.chat.TChatRole;
import org.tedros.core.domain.DomainSchema;
import org.tedros.core.domain.DomainTables;
import org.tedros.server.entity.TEntity;

/**
 * @author Davis Gordon
 *
 */

@Entity
@Table(name = DomainTables.ai_chat_message, schema = DomainSchema.tedros_core)
public class TAiChatMessage extends TEntity implements Comparable<TAiChatMessage>{

	private static final long serialVersionUID = -3932793065263852763L;

	@Column(length=20, nullable=false)
	@Enumerated(EnumType.STRING)
	private TChatRole role;
	
	@Column
	private String content;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="chat_id", nullable=false)
	private TAiChatCompletion chat;
	
	/**
	 * 
	 */
	public TAiChatMessage() {
	}

	/**
	 * @param role
	 * @param content
	 */
	public TAiChatMessage(TAiChatCompletion chat, TChatRole role, String content) {
		this.chat = chat;
		this.role = role;
		this.content = content;
		super.setInsertDate(new Date());
	}

	/**
	 * @param id
	 * @param lastUpdate
	 * @param insertDate
	 */
	public TAiChatMessage(Long id, Date lastUpdate, Date insertDate) {
		super(id, lastUpdate, insertDate);
		// TODO Auto-generated constructor stub
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

	/**
	 * @return the chat
	 */
	public TAiChatCompletion getChat() {
		return chat;
	}

	/**
	 * @param chat the chat to set
	 */
	public void setChat(TAiChatCompletion chat) {
		this.chat = chat;
	}

	@Override
	public int compareTo(TAiChatMessage o) {
		
		return o!=null && this.getId()!=null
				? this.getId().compareTo(o.getId())
						: this.getInsertDate()!=null && o!=null && o.getInsertDate()!=null
						? this.getInsertDate().compareTo(o.getInsertDate())
								: -1;
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
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof TAiChatMessage))
			return false;
		if (super.equals(obj))
			return true;
		TAiChatMessage other = (TAiChatMessage) obj;
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
		if (role != other.role)
			return false;
		return true;
	}


}
