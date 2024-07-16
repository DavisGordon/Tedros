/**
 * 
 */
package org.tedros.core.ai.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import org.tedros.core.ai.model.completion.chat.TAiChatModel;
import org.tedros.core.domain.DomainSchema;
import org.tedros.core.domain.DomainTables;
import org.tedros.server.entity.TEntity;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.ai_chat_completion, schema = DomainSchema.tedros_core)
public class TAiChatCompletion extends TEntity {

	private static final long serialVersionUID = -3141246726851985970L;

	@Column(nullable=false, length=40)
	private String title;

	@Column(length=60, nullable=false)
	@Enumerated(EnumType.STRING)
    private TAiChatModel model = TAiChatModel.GPT35_TURBO;
    
    @Column
	private Double temperature = 1.0D;
	
	@Column
	private Integer maxTokens=1024;
	
	@Column(length=100)
	private String user;
	
	@Column
	private Long userId;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinTable(	name=DomainTables.ai_chat_event,
				schema=DomainSchema.tedros_core,
				joinColumns= @JoinColumn(name="req_id"),
				inverseJoinColumns= @JoinColumn(name="event_id"))
	private List<TRequestEvent> events;
    
	
	/**
	 * 
	 */
	public TAiChatCompletion() {
	}

	/**
	 * @param id
	 * @param lastUpdate
	 * @param insertDate
	 */
	public TAiChatCompletion(Long id, Date lastUpdate, Date insertDate) {
		super(id, lastUpdate, insertDate);
	}

	public void addEvent(String result) {
		addEvent(result, null);
	}

	public void addEvent(String result, TUsage usage) {
		addEvent(TRequestEvent.build(TRequestType.CHAT, result, usage, this.model.value(), 
				this.temperature, this.maxTokens, null));
	}
	
	public void addEvent(TRequestEvent e) {
		if(events==null)
			events = new ArrayList<>();
		events.add(e);
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
	 * @return the model
	 */
	public TAiChatModel getModel() {
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(TAiChatModel model) {
		this.model = model;
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
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
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
		//result = prime * result + ((messages == null) ? 0 : messages.hashCode());
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result + ((temperature == null) ? 0 : temperature.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof TAiChatCompletion))
			return false;
		if (super.equals(obj))
			return true;
		TAiChatCompletion other = (TAiChatCompletion) obj;
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
		/*if (messages == null) {
			if (other.messages != null)
				return false;
		} else if (!messages.equals(other.messages))
			return false;*/
		if (model != other.model)
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
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

}
