/**
 * 
 */
package com.tedros.core.notify.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tedros.core.domain.DomainSchema;
import com.tedros.core.domain.DomainTables;
import com.tedros.ejb.base.entity.TEntity;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.notify, schema = DomainSchema.tedros_core)
public class TNotify extends TEntity {

	private static final long serialVersionUID = -3005149082271796683L;

	@Column(length=20)
	private String refCode;
	
	@Column(length=120, nullable=false)
	private String subject;
	
	@Column(length=400, nullable=false)
	private String to;
	
	@Column(nullable=false)
	private String content;
	
	@Column(length=40, nullable=false)
	private String charset;
	
	@Column(length=120)
	private String attachName;
	
	@Lob
	@Basic(fetch=FetchType.EAGER)
	private byte[] attachFile;
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date sentTime;
	
	@Column
	@Enumerated(EnumType.STRING)
	private TState state;
	
	@OneToMany(mappedBy="notify")
	private List<TNotifyLog> eventLog;

	/**
	 * @return the refCode
	 */
	public String getRefCode() {
		return refCode;
	}

	/**
	 * @param refCode the refCode to set
	 */
	public void setRefCode(String refCode) {
		this.refCode = refCode;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the to
	 */
	public String getTo() {
		return to;
	}

	/**
	 * @param to the to to set
	 */
	public void setTo(String to) {
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
	 * @return the charset
	 */
	public String getCharset() {
		return charset;
	}

	/**
	 * @param charset the charset to set
	 */
	public void setCharset(String charset) {
		this.charset = charset;
	}

	/**
	 * @return the attachName
	 */
	public String getAttachName() {
		return attachName;
	}

	/**
	 * @param attachName the attachName to set
	 */
	public void setAttachName(String attachName) {
		this.attachName = attachName;
	}

	/**
	 * @return the attachFile
	 */
	public byte[] getAttachFile() {
		return attachFile;
	}

	/**
	 * @param attachFile the attachFile to set
	 */
	public void setAttachFile(byte[] attachFile) {
		this.attachFile = attachFile;
	}

	/**
	 * @return the sentTime
	 */
	public Date getSentTime() {
		return sentTime;
	}

	/**
	 * @param sentTime the sentTime to set
	 */
	public void setSentTime(Date sentTime) {
		this.sentTime = sentTime;
	}

	/**
	 * @return the state
	 */
	public TState getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(TState state) {
		this.state = state;
	}

	/**
	 * @return the eventLog
	 */
	public List<TNotifyLog> getEventLog() {
		return eventLog;
	}

	/**
	 * @param eventLog the eventLog to set
	 */
	public void setEventLog(List<TNotifyLog> eventLog) {
		this.eventLog = eventLog;
	}
	
}
