package org.tedros.core.security.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import org.tedros.core.domain.DomainSchema;
import org.tedros.core.domain.DomainTables;
import org.tedros.server.entity.TEntity;

@Entity
@Table(name = DomainTables.auditLog, schema = DomainSchema.tedros_core)
public class TAuditLog extends TEntity {
	
	private static final long serialVersionUID = 2125379739952921937L;

	@Column(length=100, nullable = false)
	private String userName;
	
	@Column(length=100)
	private String login;
	
	@Column(length=500, nullable = false)
	private String description;
	
	@Column(length=15)
	@Enumerated(EnumType.STRING)
	private TEvent event;
	
	
	public TAuditLog() {
		
	}


	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}


	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}


	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}


	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
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


	/**
	 * @return the event
	 */
	public TEvent getEvent() {
		return event;
	}


	/**
	 * @param event the event to set
	 */
	public void setEvent(TEvent event) {
		this.event = event;
	}


}
