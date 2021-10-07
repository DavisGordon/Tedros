/**
 * 
 */
package com.tedros.editorweb.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tedros.editorweb.domain.DomainSchema;
import com.tedros.editorweb.domain.DomainTables;
import com.tedros.ejb.base.entity.TEntity;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.html_template, schema = DomainSchema.schema)
public class HtmlTemplate extends TEntity {

	private static final long serialVersionUID = 4538572343970472745L;

	@Column(length=60)
	private String name;
		
	@Column(length=60)
	private String autor;

	@Column(length=10)
	private String version;
	
	@Column
	private String code;

	@OneToMany(mappedBy="template", 
	cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private Set<Script> scripts;
	
	@OneToMany(mappedBy="template", 
	cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private Set<Style> styles;

	@OneToMany(mappedBy="template", 
	cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private Set<ComponentTemplate> components;
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
	 * @return the autor
	 */
	public String getAutor() {
		return autor;
	}

	/**
	 * @param autor the autor to set
	 */
	public void setAutor(String autor) {
		this.autor = autor;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
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
	 * @return the components
	 */
	public Set<ComponentTemplate> getComponents() {
		return components;
	}

	/**
	 * @param components the components to set
	 */
	public void setComponents(Set<ComponentTemplate> components) {
		this.components = components;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, false);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return (name != null ? name : "");
	}


}
