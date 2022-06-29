/**
 * 
 */
package com.tedros.core.notify.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tedros.common.model.TFileEntity;
import com.tedros.core.domain.DomainSchema;
import com.tedros.core.domain.DomainTables;
import com.tedros.ejb.base.entity.TReceptiveEntity;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.notify, schema = DomainSchema.tedros_core)
public class TNotify extends TReceptiveEntity {

	private static final long serialVersionUID = -3005149082271796683L;

	@Column(length=20)
	private String refCode;
	
	@Column(length=120, nullable=false)
	private String subject;
	
	@Column(length=400, nullable=false)
	private String to;
	
	@Column(nullable=false)
	private String content;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="file_id")
	private TFileEntity file;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date scheduleTime;
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date processedTime;
	
	@Column
	@Enumerated(EnumType.STRING)
	private TAction action;
	
	@Column
	@Enumerated(EnumType.STRING)
	private TState state;
	
	@OneToMany(mappedBy="notify", 
	cascade=CascadeType.ALL, 
	fetch=FetchType.EAGER)
	private List<TNotifyLog> eventLog;

	/**
	 * 
	 */
	public TNotify() {
		refCode = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	}

	public void addEventLog(TState state, String desc) {
		if(eventLog==null)
			eventLog = new ArrayList<>();
		eventLog.add(new TNotifyLog(this, state, desc));
	}
	
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
	 * @return the scheduleTime
	 */
	public Date getScheduleTime() {
		return scheduleTime;
	}

	/**
	 * @param scheduleTime the scheduleTime to set
	 */
	public void setScheduleTime(Date scheduleTime) {
		this.scheduleTime = scheduleTime;
	}

	/**
	 * @return the action
	 */
	public TAction getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(TAction action) {
		this.action = action;
	}

	/**
	 * @return the processedTime
	 */
	public Date getProcessedTime() {
		return processedTime;
	}

	/**
	 * @param processedTime the processedTime to set
	 */
	public void setProcessedTime(Date processedTime) {
		this.processedTime = processedTime;
	}
	
}
