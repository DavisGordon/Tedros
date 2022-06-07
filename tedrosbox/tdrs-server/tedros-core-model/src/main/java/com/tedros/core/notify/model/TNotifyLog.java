/**
 * 
 */
package com.tedros.core.notify.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tedros.core.domain.DomainSchema;
import com.tedros.core.domain.DomainTables;
import com.tedros.ejb.base.entity.TEntity;

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
