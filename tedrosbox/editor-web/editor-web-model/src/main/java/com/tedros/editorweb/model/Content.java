/**
 * 
 */
package com.tedros.editorweb.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = DomainTables.content, schema = DomainSchema.schema)
public class Content extends TEntity {

	
	private static final long serialVersionUID = -6542613230305608053L;

	@Column(length=120)
	private String title;
	
	@Column
	private String desc;
	
	@Column
	private Integer preOrdering;
	
	@Column(length=160)
	private String styleAttr;
	
	@Column(length=160)
	private String classAttr;
	
	@ManyToOne
	@JoinColumn(name="page_id", nullable=false)
	private Page page;
	
	@ManyToOne
	@JoinColumn(name="template_id", nullable=false)
	private ComponentTemplate template;
	
	@OneToMany(mappedBy="content")
	private Set<Item> items;
	
	@ManyToMany
	@JoinTable(name="content_css", schema=DomainSchema.schema,
    joinColumns=@JoinColumn(name="cont_id", referencedColumnName="id"),
    inverseJoinColumns=@JoinColumn(name="css_id", referencedColumnName="id"))
	private Set<CssClass> cssClassList;

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
	 * @return the template
	 */
	public ComponentTemplate getTemplate() {
		return template;
	}

	/**
	 * @param template the template to set
	 */
	public void setTemplate(ComponentTemplate template) {
		this.template = template;
	}

	/**
	 * @return the items
	 */
	public Set<Item> getItems() {
		return items;
	}

	/**
	 * @param items the items to set
	 */
	public void setItems(Set<Item> items) {
		this.items = items;
	}

	/**
	 * @return the cssClassList
	 */
	public Set<CssClass> getCssClassList() {
		return cssClassList;
	}

	/**
	 * @param cssClassList the cssClassList to set
	 */
	public void setCssClassList(Set<CssClass> cssClassList) {
		this.cssClassList = cssClassList;
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
