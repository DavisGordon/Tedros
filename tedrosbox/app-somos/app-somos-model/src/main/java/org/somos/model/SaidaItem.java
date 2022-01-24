/**
 * 
 */
package org.somos.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.somos.domain.DomainSchema;
import org.somos.domain.DomainTables;

import com.tedros.ejb.base.entity.TEntity;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.saida_item, schema = DomainSchema.riosemfome, 
uniqueConstraints= {@UniqueConstraint(name="saidaProdUnIdx",columnNames = { "prod_id", "saida_id" })})
public class SaidaItem extends TEntity implements EstocavelItem{

	private static final long serialVersionUID = 9196388452387171165L;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="prod_id", nullable=false)
	private Produto produto;
	
	@Column(nullable = false)
	private Integer quantidade;

	@OneToOne
	@JoinColumn(name = "saida_id", referencedColumnName = "id")
	private Saida saida;
	
	public SaidaItem() {
	}

	/**
	 * @param produto
	 */
	public SaidaItem(Produto produto) {
		this.produto = produto;
	}

	/**
	 * @return the saida
	 */
	public Saida getSaida() {
		return saida;
	}

	/**
	 * @param saida the saida to set
	 */
	public void setSaida(Saida saida) {
		this.saida = saida;
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
	 * @return the quantidade
	 */
	public Integer getQuantidade() {
		return quantidade;
	}

	/**
	 * @param quantidade the quantidade to set
	 */
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return  (produto != null ?  produto + " " : "")
				+ (quantidade != null ? "[" + quantidade + "]" : "");
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
		if (!(obj instanceof SaidaItem))
			return false;
		SaidaItem other = (SaidaItem) obj;
		if (produto == null) {
			if (other.produto != null)
				return false;
		} else if (!produto.equals(other.produto))
			return false;
		return true;
	}


}
