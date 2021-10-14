/**
 * 
 */
package com.tedros.editorweb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tedros.editorweb.domain.DomainSchema;
import com.tedros.editorweb.domain.DomainTables;
import com.tedros.ejb.base.entity.TEntity;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.metadata, schema = DomainSchema.schema)
public class Metadata extends TEntity {

	private static final long serialVersionUID = -8842088266166228875L;

	@Column(length=120)
	private String httpEquiv;
	
	@Column(length=200)
	private String content;
	
	@Column(length=160)
	private String name;
	
	@Column(length=20)
	private String charSet;
	
	@ManyToOne
	@JoinColumn(name="page_id", nullable=false, updatable=false)
	private Page page;

	/**
	 * @return the httpEquiv
	 */
	public String getHttpEquiv() {
		return httpEquiv;
	}

	/**
	 * @param httpEquiv the httpEquiv to set
	 */
	public void setHttpEquiv(String httpEquiv) {
		this.httpEquiv = httpEquiv;
	}

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
	 * @return the charSet
	 */
	public String getCharSet() {
		return charSet;
	}

	/**
	 * @param charSet the charSet to set
	 */
	public void setCharSet(String charSet) {
		this.charSet = charSet;
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
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	
}
