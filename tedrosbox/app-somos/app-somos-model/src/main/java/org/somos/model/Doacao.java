package org.somos.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.somos.domain.DomainSchema;
import org.somos.domain.DomainTables;

import com.tedros.ejb.base.entity.TEntity;

@Entity
@Table(name = DomainTables.doacao, schema = DomainSchema.riosemfome)
public class Doacao extends TEntity {

	private static final long serialVersionUID = -1610507713559488026L;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="TAJUDA_ID", nullable=false)
	private TipoAjuda tipoAjuda;
	
	@Column(nullable = true)
	private BigDecimal valor;
	
	@Column(nullable = true)
	private Integer quantidade;
	
	@Column(name = "data", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date data;
	
	@Column(name = "observacao", length=2000, nullable = true)
	private String observacao;
	
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="pess_id", nullable=false)
	private Pessoa pessoa;
	
	@ManyToOne(targetEntity=Acao.class)
	@JoinColumn(name="acao_id")
	private Acao acao;

	public Doacao() {
	
	}
	
	
	@Override
	public String toString() {
		return "{"+((getId()!=null) ? String.valueOf(getId())+", " :  "") + (tipoAjuda!=null?tipoAjuda+", ":"") + (valor!=null?valor+", ":"")+"} " ;
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



	/**
	 * @return the tipoAjuda
	 */
	public TipoAjuda getTipoAjuda() {
		return tipoAjuda;
	}



	/**
	 * @param tipoAjuda the tipoAjuda to set
	 */
	public void setTipoAjuda(TipoAjuda tipoAjuda) {
		this.tipoAjuda = tipoAjuda;
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
	 * @return the pessoa
	 */
	public Pessoa getPessoa() {
		return pessoa;
	}


	/**
	 * @param pessoa the pessoa to set
	 */
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
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



	
	

}
