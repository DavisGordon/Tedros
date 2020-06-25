package com.covidsemfome.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tedros.ejb.base.entity.TEntity;
import com.covidsemfome.domain.DomainSchema;
import com.covidsemfome.domain.DomainTables;

@Entity
@Table(name = DomainTables.doacao, schema = DomainSchema.riosemfome)
public class Doacao extends TEntity {

	private static final long serialVersionUID = -1610507713559488026L;
	
	@Column(length=20, nullable = false)
	private String tipo;
	
	@Column(nullable = true)
	private BigDecimal valor;
	
	@Column(name = "data", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date data;
	

	@Column(name = "observacao", length=300, nullable = true)
	private String observacao;
	
	
	@OneToOne
	@JoinColumn(name = "doa_id", referencedColumnName = "id")
	private Doador doador;

	public Doacao() {
	
	}
	
	
	
	@Override
	public String toString() {
		return "{"+((getId()!=null) ? String.valueOf(getId())+", " :  "") + (tipo!=null?tipo+", ":"") + (valor!=null?valor+", ":"")+"} " ;
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(obj == null || !(obj instanceof Doacao))
			return false;
		
		Doacao e = (Doacao) obj;
		
		if(getId()!=null &&  e.getId()!=null){
			return getId().equals(e.getId());
		}	
		
		return false;
	}
	

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}



	/**
	 * @return the valor
	 */
	public BigDecimal getValor() {
		return valor;
	}



	/**
	 * @param valor the valor to set
	 */
	public void setValor(BigDecimal valor) {
		this.valor = valor;
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
	 * @return the doador
	 */
	public Doador getDoador() {
		return doador;
	}



	/**
	 * @param doador the doador to set
	 */
	public void setDoador(Doador doador) {
		this.doador = doador;
	}
	
	

}
