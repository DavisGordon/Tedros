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
import com.tedros.util.TDateUtil;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.saida, schema = DomainSchema.riosemfome)
public class Saida extends TEntity implements Estocavel {

	private static final long serialVersionUID = 469183400872816881L;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date data;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="coz_id", nullable=false)
	private Cozinha cozinha;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="acao_id", nullable=false)
	private Acao acao;
	
	@Column(length=1000, nullable = true)
	private String observacao;
	
	@OneToMany(mappedBy="saida", fetch = FetchType.EAGER, 
			orphanRemoval=true, cascade={CascadeType.ALL})
	private List<SaidaItem> itens;

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, false);
	}
	
	/* (non-Javadoc)
	 * @see com.covidsemfome.model.Estocavel#getData()
	 */
	@Override
	public Date getData() {
		return data;
	}

	/* (non-Javadoc)
	 * @see com.covidsemfome.model.Estocavel#setData(java.util.Date)
	 */
	@Override
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

	

	/* (non-Javadoc)
	 * @see com.covidsemfome.model.Estocavel#getCozinha()
	 */
	@Override
	public Cozinha getCozinha() {
		return cozinha;
	}

	/* (non-Javadoc)
	 * @see com.covidsemfome.model.Estocavel#setCozinha(com.covidsemfome.model.Cozinha)
	 */
	@Override
	public void setCozinha(Cozinha cozinha) {
		this.cozinha = cozinha;
	}

	/* (non-Javadoc)
	 * @see com.covidsemfome.model.Estocavel#getItens()
	 */
	@Override
	public List<SaidaItem> getItens() {
		return itens;
	}

	public void setItens(List<SaidaItem> itens) {
		this.itens = itens;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return  (acao != null ?  acao  : "")
				+ (cozinha != null ? " [" + cozinha + "]" : "") 
				+ (data != null ? " em " + TDateUtil.getFormatedDate(data, TDateUtil.DDMMYYYY)  : "")  ;
	}

	/**
	 * @return the observacao
	 */
	public String getObservacao() {
		return observacao;
	}

	/**
	 * @param observacao the observacao to set
	 */
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
}
