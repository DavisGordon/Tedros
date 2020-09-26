/**
 * 
 */
package com.covidsemfome.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
@Table(name = DomainTables.producao, schema = DomainSchema.riosemfome)
public class Producao extends TEntity {

	private static final long serialVersionUID = 469183400872816881L;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date data;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="acao_id", nullable=true)
	private Acao acao;
	
	@Column(length=1000, nullable = false)
	private String descricao;
	
	@OneToMany(mappedBy="producao", fetch = FetchType.EAGER, 
			orphanRemoval=true, cascade={CascadeType.ALL})
	private List<ProducaoInsumo> insumos;

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, false);
	}
	
	/**
	 * @return the data
	 */
	public Date getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Date data) {
		this.data = data;
	}

	/**
	 * @return the acao
	 */
	public Acao getAcao() {
		return acao;
	}

	/**
	 * @param acao the acao to set
	 */
	public void setAcao(Acao acao) {
		this.acao = acao;
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
	 * @return the insumos
	 */
	public List<ProducaoInsumo> getInsumos() {
		return insumos;
	}

	/**
	 * @param insumos the insumos to set
	 */
	public void setInsumos(List<ProducaoInsumo> insumos) {
		this.insumos = insumos;
	}
	
}
