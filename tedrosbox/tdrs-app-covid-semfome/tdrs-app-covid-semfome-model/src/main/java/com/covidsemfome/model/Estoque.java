/**
 * 
 */
package com.covidsemfome.model;

import java.util.ArrayList;
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
@Table(name = DomainTables.estoque, schema = DomainSchema.riosemfome)
public class Estoque extends TEntity {

	private static final long serialVersionUID = -457987996337666023L;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	@JoinColumn(name="entrada_id", nullable=true)
	private Entrada entradaRef;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	@JoinColumn(name="producao_id", nullable=true)
	private Producao producaoRef;;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="coz_id", nullable=false)
	private Cozinha cozinha;
	
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date data;
	
	@Column(length=1000, nullable = true)
	private String observacao;
	
	@OneToMany(mappedBy="estoque", fetch = FetchType.EAGER, 
			orphanRemoval=true, cascade={CascadeType.ALL})
	private List<EstoqueItem> itens;

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

	/**
	 * @return the itens
	 */
	public List<EstoqueItem> getItens() {
		return itens;
	}

	/**
	 * @param itens the itens to set
	 */
	public void setItens(List<EstoqueItem> itens) {
		this.itens = itens;
	}

	/**
	 * @return the entradaRef
	 */
	public Entrada getEntradaRef() {
		return entradaRef;
	}

	/**
	 * @param entradaRef the entradaRef to set
	 */
	public void setEntradaRef(Entrada entradaRef) {
		this.entradaRef = entradaRef;
	}

	/**
	 * @return the producaoRef
	 */
	public Producao getProducaoRef() {
		return producaoRef;
	}

	/**
	 * @param producaoRef the producaoRef to set
	 */
	public void setProducaoRef(Producao producaoRef) {
		this.producaoRef = producaoRef;
	}

	public void addItem(EstoqueItem item) {
		if(itens==null)
			itens = new ArrayList<>();
		item.setEstoque(this);
		itens.add(item);
	}

	
}
