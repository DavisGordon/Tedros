/**
 * 
 */
package org.tedros.core.ai.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.tedros.core.domain.DomainSchema;
import org.tedros.core.domain.DomainTables;
import org.tedros.core.security.model.TUser;
import org.tedros.server.entity.TEntity;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.ai_completion, schema = DomainSchema.tedros_core)
public class TAiCompletion extends TEntity {

	private static final long serialVersionUID = 6032008792193989534L;

	@Column(nullable=false, length=40)
	private String title;
	
	@Column
	private String response;
	
	@Column(nullable=false)
	private String prompt;
	
	@Column
	private Double temperature;
	
	@Column
	private Integer maxTokens;
	
	@Column(length=100, nullable=false)
	@Enumerated(EnumType.STRING)
	private TAiModel model = TAiModel.TEXT_DAVINCI_003;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="user_id", nullable=false, updatable=false)
	private TUser user;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinTable(	name=DomainTables.ai_completion_event,
				schema=DomainSchema.tedros_core,
				joinColumns= @JoinColumn(name="req_id"),
				inverseJoinColumns= @JoinColumn(name="event_id"))
	private List<TRequestEvent> events;
	
	/**
	 * 
	 */
	public TAiCompletion() {
	}

	public void addEvent(String result) {
		if(events==null)
			events = new ArrayList<>();
		StringBuilder log = new StringBuilder();
		log.append("model="+this.model);
		if(this.temperature!=null)
			log.append(", temperature="+this.temperature);
		if(this.maxTokens!=null)
			log.append("maxTokens="+this.maxTokens);
		log.append(result);
		events.add(new TRequestEvent(response, prompt, log.toString(), TRequestType.COMPLETION));
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
	 * @return the temperature
	 */
	public Double getTemperature() {
		return temperature;
	}


	/**
	 * @param temperature the temperature to set
	 */
	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}


	/**
	 * @return the maxTokens
	 */
	public Integer getMaxTokens() {
		return maxTokens;
	}


	/**
	 * @param maxTokens the maxTokens to set
	 */
	public void setMaxTokens(Integer maxTokens) {
		this.maxTokens = maxTokens;
	}


	/**
	 * @return the model
	 */
	public TAiModel getModel() {
		return model;
	}


	/**
	 * @param model the model to set
	 */
	public void setModel(TAiModel model) {
		this.model = model;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the user
	 */
	public TUser getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(TUser user) {
		this.user = user;
	}

	/**
	 * @return the events
	 */
	public List<TRequestEvent> getEvents() {
		return events;
	}

	/**
	 * @param events the events to set
	 */
	public void setEvents(List<TRequestEvent> events) {
		this.events = events;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((events == null) ? 0 : events.hashCode());
		result = prime * result + ((maxTokens == null) ? 0 : maxTokens.hashCode());
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result + ((prompt == null) ? 0 : prompt.hashCode());
		result = prime * result + ((response == null) ? 0 : response.hashCode());
		result = prime * result + ((temperature == null) ? 0 : temperature.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof TAiCompletion))
			return false;
		if (super.equals(obj))
			return true;
		TAiCompletion other = (TAiCompletion) obj;
		if (events == null) {
			if (other.events != null)
				return false;
		} else if (!events.equals(other.events))
			return false;
		if (maxTokens == null) {
			if (other.maxTokens != null)
				return false;
		} else if (!maxTokens.equals(other.maxTokens))
			return false;
		if (model != other.model)
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
		if (temperature == null) {
			if (other.temperature != null)
				return false;
		} else if (!temperature.equals(other.temperature))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return (title != null ? title : "");
	}

}
