/**
 * 
 */
package com.tedros.editorweb.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.tedros.editorweb.domain.DomainSchema;
import com.tedros.editorweb.domain.DomainTables;
import com.tedros.ejb.base.entity.TEntity;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.page, schema = DomainSchema.schema)
public class Page extends TEntity {

	private static final long serialVersionUID = -8842088266166228875L;

	@Column(length=80)
	private String title;
	
	@Column
	private Boolean main;
	
	@Column(length=160)
	private String styleAttr;
	
	@Column(length=160)
	private String classAttr;
	
	@ManyToOne
	@JoinColumn(name="domain_id", nullable=false, updatable=false)
	private Domain domain;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="template_id", nullable=false, updatable=true)
	private HtmlTemplate template;
	
	@OneToMany(mappedBy="page", 
			cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private Set<Metadata> metas;
	
	@OneToMany(mappedBy="page", 
			cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private Set<Script> scripts;
	
	@OneToMany(mappedBy="page", 
			cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private Set<Style> styles;
	
	@OneToMany(mappedBy="page", 
			cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private Set<Content> contents;
	
	

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
	 * @return the main
	 */
	public Boolean getMain() {
		return main;
	}

	/**
	 * @param main the main to set
	 */
	public void setMain(Boolean main) {
		this.main = main;
	}

	/**
	 * @return the styleAttr
	 */
	public String getStyleAttr() {
		return styleAttr;
	}

	/**
	 * @param styleAttr the styleAttr to set
	 */
	public void setStyleAttr(String styleAttr) {
		this.styleAttr = styleAttr;
	}

	/**
	 * @return the classAttr
	 */
	public String getClassAttr() {
		return classAttr;
	}

	/**
	 * @param classAttr the classAttr to set
	 */
	public void setClassAttr(String classAttr) {
		this.classAttr = classAttr;
	}

	/**
	 * @return the domain
	 */
	public Domain getDomain() {
		return domain;
	}

	/**
	 * @param domain the domain to set
	 */
	public void setDomain(Domain domain) {
		this.domain = domain;
	}

	/**
	 * @return the template
	 */
	public HtmlTemplate getTemplate() {
		return template;
	}

	/**
	 * @param template the template to set
	 */
	public void setTemplate(HtmlTemplate template) {
		this.template = template;
	}

	/**
	 * @return the metas
	 */
	public Set<Metadata> getMetas() {
		return metas;
	}

	/**
	 * @param metas the metas to set
	 */
	public void setMetas(Set<Metadata> metas) {
		this.metas = metas;
	}

	/**
	 * @return the scripts
	 */
	public Set<Script> getScripts() {
		return scripts;
	}

	/**
	 * @param scripts the scripts to set
	 */
	public void setScripts(Set<Script> scripts) {
		this.scripts = scripts;
	}

	/**
	 * @return the styles
	 */
	public Set<Style> getStyles() {
		return styles;
	}

	/**
	 * @param styles the styles to set
	 */
	public void setStyles(Set<Style> styles) {
		this.styles = styles;
	}

	/**
	 * @return the contents
	 */
	public Set<Content> getContents() {
		return contents;
	}

	/**
	 * @param contents the contents to set
	 */
	public void setContents(Set<Content> contents) {
		this.contents = contents;
	}
	

	
}
