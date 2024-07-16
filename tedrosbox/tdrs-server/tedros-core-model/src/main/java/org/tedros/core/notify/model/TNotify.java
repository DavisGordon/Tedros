/**
 * 
 */
package org.tedros.core.notify.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import org.tedros.common.model.TFileEntity;
import org.tedros.core.domain.DomainSchema;
import org.tedros.core.domain.DomainTables;
import org.tedros.server.entity.TReceptiveEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	
	@JsonIgnore
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="file_id")
	private TFileEntity file;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date scheduleTime;
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date processedTime;
	
	@JsonIgnore
	@Column
	@Enumerated(EnumType.STRING)
	private TAction action;
	
	@JsonIgnore
	@Column
	@Enumerated(EnumType.STRING)
	private TState state;
	
	@JsonIgnore
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(action, content, eventLog, file, processedTime, refCode, scheduleTime,
				state, subject, to);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof TNotify))
			return false;
		TNotify other = (TNotify) obj;
		return action == other.action && Objects.equals(content, other.content)
				&& Objects.equals(eventLog, other.eventLog) && Objects.equals(file, other.file)
				&& Objects.equals(processedTime, other.processedTime) && Objects.equals(refCode, other.refCode)
				&& Objects.equals(scheduleTime, other.scheduleTime) && state == other.state
				&& Objects.equals(subject, other.subject) && Objects.equals(to, other.to);
	}

	@Override
	public String toString() {
		return "TNotify [refCode=" + refCode + ", subject=" + subject + ", to=" + to + ", scheduleTime=" + scheduleTime
				+ ", processedTime=" + processedTime + ", action=" + action + ", state=" + state + "]";
	}
	
}
