/**
 * 
 */
package com.covidsemfome.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.covidsemfome.domain.DomainSchema;
import com.covidsemfome.domain.DomainTables;
import com.tedros.ejb.base.entity.TEntity;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.estoque_item, schema = DomainSchema.riosemfome)
public class EstoqueItem extends TEntity {

	
	private static final long serialVersionUID = -4707274871142027314L;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="prod_id", nullable=false)
	private Produto produto;
	
	@Column(nullable = false)
	private Integer qtdMinima;
	
	@Column(nullable = false)
	private Integer qtdInicial;
	
	@Column(nullable = false)
	private Integer qtdCalculado;
	
	@Column(nullable = true)
	private Integer qtdAjuste;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="est_id", nullable=false)
	private Estoque estoque;

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, false);
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

	/**
	 * @return the qtdCalculado
	 */
	public Integer getQtdCalculado() {
		return qtdCalculado;
	}

	/**
	 * @param qtdCalculado the qtdCalculado to set
	 */
	public void setQtdCalculado(Integer qtdCalculado) {
		this.qtdCalculado = qtdCalculado;
	}

	/**
	 * @return the qtdAjuste
	 */
	public Integer getQtdAjuste() {
		return qtdAjuste;
	}

	/**
	 * @param qtdAjuste the qtdAjuste to set
	 */
	public void setQtdAjuste(Integer qtdAjuste) {
		this.qtdAjuste = qtdAjuste;
	}

	/**
	 * @return the estoque
	 */
	public Estoque getEstoque() {
		return estoque;
	}

	/**
	 * @param estoque the estoque to set
	 */
	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}
	
}
