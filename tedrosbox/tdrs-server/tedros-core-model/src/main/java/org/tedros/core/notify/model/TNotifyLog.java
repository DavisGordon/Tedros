/**
 * 
 */
package org.tedros.core.notify.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import org.tedros.core.domain.DomainSchema;
import org.tedros.core.domain.DomainTables;
import org.tedros.server.entity.TEntity;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.notifyLog, schema = DomainSchema.tedros_core)
public class TNotifyLog extends TEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6494028013118541411L;

	@ManyToOne()
	@JoinColumn(name="noti_id")
	private TNotify notify;

	@Column
	@Enumerated(EnumType.STRING)
	private TState state;
	
	@Column(length=500)
	private String description;

	/**
	 * 
	 */
	public TNotifyLog() {
	}

	/**
	 * @param notify
	 * @param state
	 * @param description
	 */
	public TNotifyLog(TNotify notify, TState state, String description) {
		this.notify = notify;
		this.state = state;
		this.description = description;
		super.setInsertDate(new Date());
	}

	/**
	 * @return the notify
	 */
	public TNotify getNotify() {
		return notify;
	}

	/**
	 * @param notify the notify to set
	 */
	public void setNotify(TNotify notify) {
		this.notify = notify;
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
