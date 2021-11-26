/**
 * 
 */
package com.covidsemfome.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.covidsemfome.domain.DomainSchema;
import com.covidsemfome.domain.DomainTables;
import com.tedros.common.model.TFileEntity;
import com.tedros.ejb.base.entity.TEntity;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.csf_imagem, schema = DomainSchema.riosemfome)
public class CsfImagem extends TEntity {
	
	private static final long serialVersionUID = -9097236160793290078L;
	
	@ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinTable(name="csfimagem_tfile", schema=DomainSchema.riosemfome,
    joinColumns=@JoinColumn(name="csf_id", referencedColumnName="id"),
    inverseJoinColumns=@JoinColumn(name="file_id", referencedColumnName="id"))
	private List<TFileEntity> files;

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, false);
	}

	/**
	 * @return the files
	 */
	public List<TFileEntity> getFiles() {
		return files;
	}

	/**
	 * @param files the files to set
	 */
	public void setFiles(List<TFileEntity> files) {
		this.files = files;
	}
	
	
}
