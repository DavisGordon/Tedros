/**
 * 
 */
package org.tedros.chat.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.tedros.chat.domain.DomainSchema;
import org.tedros.chat.domain.DomainTables;
import org.tedros.common.model.TFileEntity;
import org.tedros.core.security.model.TUser;
import org.tedros.server.entity.TEntity;

/**
 * @author Davis Gordon
 *
 */

@Entity
@Table(name=DomainTables.message, schema=DomainSchema.schema)
public class ChatMessage extends TEntity {

	private static final long serialVersionUID = -8257824754212403138L;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="from", nullable=false, updatable=false)
	private TUser from;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="to", nullable=false, updatable=false)
	private TUser to;
	
	@Column(length=2000)
	private String content;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="file", updatable=false)
	private TFileEntity file;
	
	@Column(length=15)
	@Enumerated(EnumType.STRING)
	private TStatus status;

	/**
	 * @return the from
	 */
	public TUser getFrom() {
		return from;
	}

	/**
	 * @param from the from to set
	 */
	public void setFrom(TUser from) {
		this.from = from;
	}

	/**
	 * @return the to
	 */
	public TUser getTo() {
		return to;
	}

	/**
	 * @param to the to to set
	 */
	public void setTo(TUser to) {
		this.to = to;
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
	
	
}
