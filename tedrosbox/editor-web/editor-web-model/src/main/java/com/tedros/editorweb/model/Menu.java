/**
 * 
 */
package com.tedros.editorweb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.tedros.editorweb.domain.DomainSchema;
import com.tedros.editorweb.domain.DomainTables;
import com.tedros.ejb.base.entity.TEntity;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.menu, schema = DomainSchema.schema)
public class Menu extends TEntity {

	private static final long serialVersionUID = -5635128279844203578L;

	@Column(length=60)
	private String desc;
	
	@Column
	private Integer preOrdering;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="page_id")
	private Page page;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="content_id")
	private Content content;

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
	 * @return the page
	 */
	public Page getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(Page page) {
		this.page = page;
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
