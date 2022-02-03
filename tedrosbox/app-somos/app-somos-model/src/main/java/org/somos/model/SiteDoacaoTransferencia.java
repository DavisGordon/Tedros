/**
 * 
 */
package org.somos.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.somos.domain.DomainSchema;
import org.somos.domain.DomainTables;

import com.tedros.common.model.TFileEntity;
import com.tedros.ejb.base.entity.TEntity;

/**
 * @author Davis Gordon
 *
 */

@Entity
@Table(name = DomainTables.site_doacao_transferencia, schema = DomainSchema.riosemfome)
public class SiteDoacaoTransferencia extends TEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3329312831896733023L;

	@Column(nullable = false)
	private String descricao;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="image_id")
	private TFileEntity image;
	

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, false);
	}

	@Override
	public String toString() {
		return descricao;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * @return the image
	 */
	public TFileEntity getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(TFileEntity image) {
		this.image = image;
	}
	
}
