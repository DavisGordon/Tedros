/**
 * 
 */
package com.tedros.editorweb.model;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tedros.common.model.TFileEntity;
import com.tedros.editorweb.domain.ComponentType;
import com.tedros.editorweb.domain.DomainSchema;
import com.tedros.editorweb.domain.DomainTables;
import com.tedros.ejb.base.entity.TEntity;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.component_template, schema = DomainSchema.schema)
public class ComponentTemplate extends TEntity {

	private static final long serialVersionUID = 4312938090887592509L;

	@Column(length=60)
	private String name;
	
	@Column
	private String code;
	
	@Enumerated(EnumType.STRING)
	private ComponentType type;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="img_id")
	private TFileEntity imgExample;
	
	@ManyToOne
	@JoinColumn(name="template_id", nullable=false, updatable=true)
	private HtmlTemplate template;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="template_css", schema=DomainSchema.schema,
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

	/**
	 * @return the type
	 */
	public ComponentType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(ComponentType type) {
		this.type = type;
	}

	/**
	 * @return the imgExample
	 */
	public TFileEntity getImgExample() {
		if(imgExample==null)
			imgExample = new TFileEntity();
		return imgExample;
	}

	/**
	 * @param imgExample the imgExample to set
	 */
	public void setImgExample(TFileEntity imgExample) {
		this.imgExample = imgExample;
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

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, "template");
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
