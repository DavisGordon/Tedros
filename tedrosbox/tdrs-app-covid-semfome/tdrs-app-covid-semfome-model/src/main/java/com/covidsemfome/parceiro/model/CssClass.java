/**
 * 
 */
package com.covidsemfome.parceiro.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.covidsemfome.domain.DomainSchema;
import com.covidsemfome.domain.DomainTables;
import com.tedros.ejb.base.annotation.TEntityImportRule;
import com.tedros.ejb.base.annotation.TFieldImportRule;
import com.tedros.ejb.base.annotation.TFileType;
import com.tedros.ejb.base.entity.TEntity;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.css_class, schema = DomainSchema.riosemfome)
@TEntityImportRule(description = "Regras para importar um arquivo para a tabela de CssClass ", 
fileType = { TFileType.CSV, TFileType.XLS })
public class CssClass extends TEntity {

	private static final long serialVersionUID = -5109571290237608170L;

	@Column(length=60)
	@TFieldImportRule(required = true, 
	description = "Nome da classe css", column = "Name",
	example="fullscreen")
	private String name;
	
	@TFieldImportRule(required = true, 
			description = "Descricão da classe", column = "Desc",
			example="Fills the height of the screen.")
	@Column(length=120)
	private String desc;

	public CssClass() {
	}
	
	/**
	 * @param name
	 */
	public CssClass(String name) {
		this.name = name;
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
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return (name != null ? name : "");
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof CssClass))
			return false;
		CssClass other = (CssClass) obj;
		if(!super.isNew() && !other.isNew())
			return super.getId().equals(other.getId());
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
