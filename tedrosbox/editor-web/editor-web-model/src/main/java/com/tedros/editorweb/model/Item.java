/**
 * 
 */
package com.tedros.editorweb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tedros.editorweb.domain.DomainSchema;
import com.tedros.editorweb.domain.DomainTables;
import com.tedros.editorweb.domain.ItemType;
import com.tedros.ejb.base.entity.TEntity;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.item, schema = DomainSchema.schema)
public class Item extends TEntity {

	private static final long serialVersionUID = -4005237411597700724L;

	@Column(length=200)
	private String title;
	
	@Column
	private String desc;
	
	@Column
	private Integer preOrdering;
	
	@Column(length=160)
	private String url;
	
	@Enumerated(EnumType.STRING)
	private ItemType type;
	
	@ManyToOne
	@JoinColumn(name="content_id", nullable=false, updatable=false)
	private Content content;
	
	@ManyToOne
	@JoinColumn(name="media_id", nullable=false, updatable=true)
	private Media media;

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
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the type
	 */
	public ItemType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(ItemType type) {
		this.type = type;
	}

	/**
	 * @return the content
	 */
	public Content getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(Content content) {
		this.content = content;
	}

	/**
	 * @return the media
	 */
	public Media getMedia() {
		return media;
	}

	/**
	 * @param media the media to set
	 */
	public void setMedia(Media media) {
		this.media = media;
	}

	/**
	 * @return the preOrdering
	 */
	public Integer getPreOrdering() {
		return preOrdering;
	}

	/**
	 * @param preOrdering the preOrdering to set
	 */
	public void setPreOrdering(Integer preOrdering) {
		this.preOrdering = preOrdering;
	}

}
