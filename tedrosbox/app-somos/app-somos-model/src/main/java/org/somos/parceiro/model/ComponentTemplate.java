/**
 * 
 */
package org.somos.parceiro.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.somos.domain.DomainSchema;
import org.somos.domain.DomainTables;

import com.tedros.ejb.base.entity.TEntity;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.component_template, schema = DomainSchema.riosemfome)
public class ComponentTemplate extends TEntity {

	private static final long serialVersionUID = 4312938090887592509L;

	@Column(length=60)
	private String name;
	
	@Column
	private String code;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="template_css", schema=DomainSchema.riosemfome,
    joinColumns=@JoinColumn(name="templ_id", referencedColumnName="id"),
    inverseJoinColumns=@JoinColumn(name="css_id", referencedColumnName="id"))
	private List<CssClass> cssClassList;

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

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
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

	/**
	 * @return the cssClassList
	 */
	public List<CssClass> getCssClassList() {
		return cssClassList;
	}

	/**
	 * @param cssClassList the cssClassList to set
	 */
	public void setCssClassList(List<CssClass> cssClassList) {
		this.cssClassList = cssClassList;
	}

}
