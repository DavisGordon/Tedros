/**
 * 
 */
package com.tedros.editorweb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.tedros.editorweb.domain.DomainSchema;
import com.tedros.editorweb.domain.DomainTables;
import com.tedros.editorweb.domain.MediaType;
import com.tedros.ejb.base.entity.TEntity;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.media, schema = DomainSchema.schema)
public class Media extends TEntity {

	private static final long serialVersionUID = 2080595470900774156L;

	@Column(length=80)
	private String name;
	
	@Column(length=250)
	private String desc;
	
	@Column
	private Integer preOrdering;
	
	@Column(length=250)
	private String url;
	
	@Enumerated(EnumType.STRING)
	private MediaType type;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	public MediaType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(MediaType type) {
		this.type = type;
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
