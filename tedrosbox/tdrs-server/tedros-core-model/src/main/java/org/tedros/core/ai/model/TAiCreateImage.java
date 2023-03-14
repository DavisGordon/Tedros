/**
 * 
 */
package org.tedros.core.ai.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.tedros.common.model.TFileEntity;
import org.tedros.core.ai.model.image.TImageSize;
import org.tedros.core.domain.DomainSchema;
import org.tedros.core.domain.DomainTables;
import org.tedros.server.entity.TEntity;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.ai_create_image, schema = DomainSchema.tedros_core)
public class TAiCreateImage extends TEntity {
	
	private static final long serialVersionUID = 1129868432634550152L;

	@Column(nullable=false, length=40)
	private String title;
	
	@Column(nullable=false)
	private String prompt;
	
	@Column
	private Integer quantity;
	
	@Column(length=8, nullable=false)
	@Enumerated(EnumType.STRING)
	private TImageSize size;
	
	@Column(length=6, nullable=false)
	@Enumerated(EnumType.STRING)
	private TResponseFormat format;
	
	@OneToMany(fetch=FetchType.EAGER,
			cascade=CascadeType.ALL, 
			orphanRemoval=true)
	@JoinColumn(name="create_img_id")
	private Set<TAiImage> data;
	
	@Column(length=100)
	private String user;
	
	@Column
	private Long userId;

	@ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinTable(	name=DomainTables.ai_createimage_event,
				schema=DomainSchema.tedros_core,
				joinColumns= @JoinColumn(name="req_id"),
				inverseJoinColumns= @JoinColumn(name="event_id"))
	private Set<TRequestEvent> events;

	/**
	 * 
	 */
	public TAiCreateImage() {
	}

	/**
	 * @param id
	 * @param lastUpdate
	 * @param insertDate
	 */
	public TAiCreateImage(Long id, Date lastUpdate, Date insertDate) {
		super(id, lastUpdate, insertDate);
		// TODO Auto-generated constructor stub
	}


	public void addEvent(String result) {
		addEvent(result, null);
	}

	public void addEvent(String result, TUsage usage) {
		addEvent(TRequestEvent.build(TRequestType.CREATE_IMAGE, result, usage, "DALLE", 
				null, null, this.quantity));
	}
	
	public void addEvent(TRequestEvent e) {
		if(events==null)
			events = new HashSet<>();
		events.add(e);
	}

	
	public void addImage(String url) {
		if(data==null)
			data = new HashSet<>();
		data.add(new TAiImage(TResponseFormat.URL, url));
	}
	
	public void addImage(TFileEntity file) {
		if(data==null)
			data = new HashSet<>();
		data.add(new TAiImage(TResponseFormat.BASE64, file));
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
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the format
	 */
	public TResponseFormat getFormat() {
		return format;
	}

	/**
	 * @param format the format to set
	 */
	public void setFormat(TResponseFormat format) {
		this.format = format;
	}

	/**
	 * @return the data
	 */
	public Set<TAiImage> getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Set<TAiImage> data) {
		this.data = data;
	}

	/**
	 * @return the events
	 */
	public Set<TRequestEvent> getEvents() {
		return events;
	}

	/**
	 * @param events the events to set
	 */
	public void setEvents(Set<TRequestEvent> events) {
		this.events = events;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((events == null) ? 0 : events.hashCode());
		result = prime * result + ((format == null) ? 0 : format.hashCode());
		result = prime * result + ((prompt == null) ? 0 : prompt.hashCode());
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof TAiCreateImage))
			return false;
		if (super.equals(obj))
			return true;
		TAiCreateImage other = (TAiCreateImage) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (events == null) {
			if (other.events != null)
				return false;
		} else if (!events.equals(other.events))
			return false;
		if (format != other.format)
			return false;
		if (prompt == null) {
			if (other.prompt != null)
				return false;
		} else if (!prompt.equals(other.prompt))
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return (title != null ?  title : "");
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
	 * @return the size
	 */
	public TImageSize getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(TImageSize size) {
		this.size = size;
	}

}
