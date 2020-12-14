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
@Table(name = DomainTables.entrada, schema = DomainSchema.riosemfome)
public class Entrada extends TEntity implements Estocavel{

	private static final long serialVersionUID = -7490687024751537584L;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="coz_id", nullable=false)
	private Cozinha cozinha;
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date data;
	
	@Column(nullable = false)
	private String tipo;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="pess_id", nullable=true)
	private Pessoa doador;
	
	@OneToMany(mappedBy="entrada", fetch = FetchType.EAGER, 
			orphanRemoval=true, cascade={CascadeType.ALL})
	private List<EntradaItem> itens;

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
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the doador
	 */
	public Pessoa getDoador() {
		return doador;
	}

	/**
	 * @param doador the doador to set
	 */
	public void setDoador(Pessoa doador) {
		this.doador = doador;
	}

	/**
	 * @return the itens
	 */
	public List<EntradaItem> getItens() {
		return itens;
	}

	/**
	 * @param itens the itens to set
	 */
	public void setItens(List<EntradaItem> itens) {
		this.itens = itens;
		if(itens!=null)
			this.itens.forEach(e -> {
				if(e.getEntrada()==null)
					e.setEntrada(this);
			});
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return (tipo != null ? tipo + " para ": "")
				+ (cozinha != null ? cozinha  : "")
				+ (data != null ? " em " + TDateUtil.getFormatedDate(data, TDateUtil.DDMMYYYY)  : "")  ;
	}
}
