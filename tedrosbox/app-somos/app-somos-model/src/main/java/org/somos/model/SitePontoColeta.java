/**
 * 
 */
package org.somos.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
@Table(name = DomainTables.site_pontocoleta, schema = DomainSchema.riosemfome)
public class SitePontoColeta extends TEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3329312831896733023L;

	@Column(length=300, nullable = false)
	private String descricao;
	
	@OneToOne
	@JoinColumn(name = "doacao_id", referencedColumnName = "id")
	private SiteSMDoacao doacao;
	
	@Column
	private Integer ordem;

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
	 * @return the ordem
	 */
	public Integer getOrdem() {
		return ordem;
	}

	/**
	 * @param ordem the ordem to set
	 */
	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}

	/**
	 * @return the doacao
	 */
	public SiteSMDoacao getDoacao() {
		return doacao;
	}

	/**
	 * @param doacao the doacao to set
	 */
	public void setDoacao(SiteSMDoacao doacao) {
		this.doacao = doacao;
	}
	
}
