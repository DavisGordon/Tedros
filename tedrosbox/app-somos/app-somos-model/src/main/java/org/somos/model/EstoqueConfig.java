/**
 * 
 */
package org.somos.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

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
@Table(name = DomainTables.estoque_config, schema = DomainSchema.riosemfome, 
uniqueConstraints= {@UniqueConstraint(name="cozProdUnIdx",columnNames = { "COZ_ID", "PROD_ID" })})
public class EstoqueConfig extends TEntity {

	private static final long serialVersionUID = 5992191290923951701L;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="COZ_ID", nullable=false)
	private Cozinha cozinha;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="PROD_ID", nullable=false)
	private Produto produto;
	
	@Column(nullable = false)
	private Integer qtdMinima;
	
	@Column(nullable = false)
	private Integer qtdInicial;

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, false);
	}
	
	/**
	 * @return the cozinha
	 */
	public Cozinha getCozinha() {
		return cozinha;
	}

	/**
	 * @param cozinha the cozinha to set
	 */
	public void setCozinha(Cozinha cozinha) {
		this.cozinha = cozinha;
	}

	/**
	 * @return the produto
	 */
	public Produto getProduto() {
		return produto;
	}

	/**
	 * @param produto the produto to set
	 */
	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	/**
	 * @return the qtdMinima
	 */
	public Integer getQtdMinima() {
		return qtdMinima;
	}

	/**
	 * @param qtdMinima the qtdMinima to set
	 */
	public void setQtdMinima(Integer qtdMinima) {
		this.qtdMinima = qtdMinima;
	}

	/**
	 * @return the qtdInicial
	 */
	public Integer getQtdInicial() {
		return qtdInicial;
	}

	/**
	 * @param qtdInicial the qtdInicial to set
	 */
	public void setQtdInicial(Integer qtdInicial) {
		this.qtdInicial = qtdInicial;
	}
		
}
