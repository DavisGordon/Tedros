/**
 * 
 */
package com.solidarity.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.solidarity.domain.DomainSchema;
import com.solidarity.domain.DomainTables;
import com.tedros.ejb.base.entity.TEntity;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.entrada_item, schema = DomainSchema.solidarity, 
uniqueConstraints= {@UniqueConstraint(name="entradaProdUnIdx",columnNames = { "prod_id", "ent_id" })})
public class EntradaItem extends TEntity implements EstocavelItem {

	private static final long serialVersionUID = -3216481324029671441L;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="prod_id", nullable=false)
	private Produto produto;
	
	@Column(nullable = false)
	private Integer quantidade;
	
	@Column(nullable = true)
	private BigDecimal valorUnitario;

	@Column(length=15, nullable = true)
	private String unidadeMedida;
	
	@OneToOne
	@JoinColumn(name = "ent_id", referencedColumnName = "id")
	private Entrada entrada;
	
	public EntradaItem() {
		
	}
	
	public EntradaItem(Produto p) {
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
		/*if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;*/
		EntradaItem other = (EntradaItem) obj;
		if (produto == null) {
			if (other.produto != null)
				return false;
		} else if (!produto.equals(other.produto))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see com.solidarity.model.EstocavelItem#getProduto()
	 */
	@Override
	public Produto getProduto() {
		return produto;
	}

	/* (non-Javadoc)
	 * @see com.solidarity.model.EstocavelItem#setProduto(com.solidarity.model.Produto)
	 */
	@Override
	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	/* (non-Javadoc)
	 * @see com.solidarity.model.EstocavelItem#getQuantidade()
	 */
	@Override
	public Integer getQuantidade() {
		return quantidade;
	}

	/* (non-Javadoc)
	 * @see com.solidarity.model.EstocavelItem#setQuantidade(java.lang.Integer)
	 */
	@Override
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	/**
	 * @return the valorUnitario
	 */
	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}

	/**
	 * @param valorUnitario the valorUnitario to set
	 */
	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	
	/**
	 * @return the unidadeMedida
	 */
	public String getUnidadeMedida() {
		return unidadeMedida;
	}

	/**
	 * @param unidadeMedida the unidadeMedida to set
	 */
	public void setUnidadeMedida(String unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}

	/**
	 * @return the entrada
	 */
	public Entrada getEntrada() {
		return entrada;
	}

	/**
	 * @param entrada the entrada to set
	 */
	public void setEntrada(Entrada entrada) {
		this.entrada = entrada;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "EntradaItem [" + (produto != null ? "produto=" + produto + ", " : "")
				+ (quantidade != null ? "quantidade=" + quantidade : "") + "]";
	}

	

	
	
}
