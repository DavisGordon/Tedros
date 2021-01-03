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
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.covidsemfome.domain.DomainSchema;
import com.covidsemfome.domain.DomainTables;
import com.tedros.ejb.base.entity.TEntity;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.estoque_item, schema = DomainSchema.riosemfome, 
uniqueConstraints= {@UniqueConstraint(name="estoqueProdUnIdx",columnNames = { "prod_id", "est_id" })})
public class EstoqueItem extends TEntity {

	
	private static final long serialVersionUID = -4707274871142027314L;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="prod_id", nullable=false)
	private Produto produto;
	
	@Column(nullable = false)
	private Integer qtdMinima;
	
	@Column(nullable = true)
	private Integer qtdInicial;
	
	@Column(nullable = false)
	private Integer qtdCalculado;
	
	@Column(nullable = true)
	private Integer qtdAjuste;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="est_id", nullable=false)
	private Estoque estoque;
	
	public EstoqueItem() {
		
	}
	
	public EstoqueItem(Produto p) {
		this.produto = p;
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
		if (getClass() != obj.getClass())
			return false;
		EstoqueItem other = (EstoqueItem) obj;
		if (produto == null) {
			if (other.produto != null)
				return false;
		} else if (!produto.equals(other.produto))
			return false;
		return true;
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
	
	public Integer calcularESomar(EstocavelItem estocavel, EstoqueItem anterior) {
		this.setQtdInicial(anterior.getVlrAjustado());
		if(estocavel instanceof EntradaItem) {
			this.addQuantidade(estocavel.getQuantidade());
			return estocavel.getQuantidade();
		} else {
			 this.addQuantidade(anterior.getVlrAjustado() - estocavel.getQuantidade());
			 return estocavel.getQuantidade() * -1;
		}
	}
	
	public Integer calcularESomar(EstocavelItem estocavel, EstoqueConfig config) {
		this.setQtdInicial(config.getQtdInicial());
		if(estocavel instanceof EntradaItem) {
			this.addQuantidade(config.getQtdInicial() + estocavel.getQuantidade());
			return estocavel.getQuantidade();
		} else {
			this.addQuantidade(config.getQtdInicial() - estocavel.getQuantidade());
			return estocavel.getQuantidade() * -1;
		}
	}
	
	public Integer somar(EstocavelItem atual, EstocavelItem retido) {
		return this.addQuantidade(calcular(atual, retido));
	}
	
	public Integer calcular(EstocavelItem atual, EstocavelItem retido) {
		if(atual instanceof EntradaItem) {
			return atual.getQuantidade() - retido.getQuantidade();
		} else {
			return (atual.getQuantidade() - retido.getQuantidade()) * -1;
		}
	}
	
	public Integer somar(EstocavelItem estocavel) {
		this.setQtdInicial(0);
		return this.addQuantidade(calcular(estocavel));
	}
	
	public Integer calcular(EstocavelItem estocavel) {
		if(estocavel instanceof EntradaItem) {
			return estocavel.getQuantidade();
		} else {
			return estocavel.getQuantidade() * -1;
		}
	}
	
	public Integer calcularESubtrair(EstocavelItem estocavel) {
		this.setQtdInicial(0);
		if(estocavel instanceof EntradaItem) {
			this.remQuantidade(estocavel.getQuantidade());
			return estocavel.getQuantidade() * -1;
		} else {
			this.addQuantidade(estocavel.getQuantidade());
			return estocavel.getQuantidade();
		}
	}
	
	public Integer addQuantidade(Integer qtd) {
		if(this.qtdCalculado==null)
			this.qtdCalculado = qtd;
		else
			this.qtdCalculado += qtd;
		
		if(this.qtdCalculado<0)
			this.qtdCalculado = 0;
		
		return this.qtdCalculado;
	}
	
	public Integer remQuantidade(Integer qtd) {
		if(this.qtdCalculado==null)
			this.qtdCalculado = qtd;
		else {
			this.qtdCalculado = (this.qtdCalculado - qtd < 0) 
					? 0 
							: this.qtdCalculado - qtd;
		}
		return this.qtdCalculado;
	}
	
	public Integer getVlrAjustado() {
		if(this.qtdAjuste==null)
			this.qtdAjuste = 0;
		
		return  (this.qtdCalculado == null) 
					? this.qtdAjuste 
							: this.qtdCalculado + this.qtdAjuste;
		
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "EstoqueItem [" + (produto != null ? "produto=" + produto + ", " : "")
				+ (getVlrAjustado() != null ? "getVlrAjustado()=" + getVlrAjustado() : "") + "]";
	}
	
}
