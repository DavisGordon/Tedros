/**
 * 
 */
package org.tedros.core.ai.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.tedros.core.domain.DomainSchema;
import org.tedros.core.domain.DomainTables;
import org.tedros.server.entity.TEntity;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.ai_request_event, schema = DomainSchema.tedros_core)
public class TRequestEvent extends TEntity {

	private static final long serialVersionUID = -4376333627387648321L;

	@Column
	private String response;
	
	@Column(nullable=false)
	private String prompt;
	
	@Column(nullable=false)
	private String log;
	
	@Column(length=30, nullable=false)
	@Enumerated(EnumType.STRING)
	private TRequestType type;
	 
	/**
	 * 
	 */
	public TRequestEvent() {
		if(super.getInsertDate()==null)
			super.setInsertDate(new Date());
	}

	/**
	 * @param id
	 * @param lastUpdate
	 * @param insertDate
	 */
	public TRequestEvent(Long id, Date lastUpdate, Date insertDate) {
		super(id, lastUpdate, insertDate);
	}

	/**
	 * @param response
	 * @param prompt
	 * @param log
	 * @param type
	 */
	public TRequestEvent(String response, String prompt, String log, TRequestType type) {
		this.response = response;
		this.prompt = prompt;
		this.log = log;
		this.type = type;
		if(super.getInsertDate()==null)
			super.setInsertDate(new Date());
	}

	/**
	 * @return the response
	 */
	public String getResponse() {
		return response;
	}

	/**
	 * @param response the response to set
	 */
	public void setResponse(String response) {
		this.response = response;
	}

	/**
	 * @return the prompt
	 */
	public String getPrompt() {
		return prompt;
	}

	/**
	 * @param prompt the prompt to set
	 */
	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	/**
	 * @return the log
	 */
	public String getLog() {
		return log;
	}

	/**
	 * @param log the log to set
	 */
	public void setLog(String log) {
		this.log = log;
	}

	/**
	 * @return the type
	 */
	public TRequestType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(TRequestType type) {
		this.type = type;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((log == null) ? 0 : log.hashCode());
		result = prime * result + ((prompt == null) ? 0 : prompt.hashCode());
		result = prime * result + ((response == null) ? 0 : response.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		if (!(obj instanceof TRequestEvent))
			return false;
		TRequestEvent other = (TRequestEvent) obj;
		if (log == null) {
			if (other.log != null)
				return false;
		} else if (!log.equals(other.log))
			return false;
		if (prompt == null) {
			if (other.prompt != null)
				return false;
		} else if (!prompt.equals(other.prompt))
			return false;
		if (response == null) {
			if (other.response != null)
				return false;
		} else if (!response.equals(other.response))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

}
