/**
 * 
 */
package org.somos.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.somos.domain.DomainSchema;
import org.somos.domain.DomainTables;

import com.tedros.ejb.base.entity.TEntity;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.detalheAjuda, schema = DomainSchema.riosemfome)
public class DetalheAjuda extends TEntity {

	private static final long serialVersionUID = -1344928634026915354L;

	@Column(length=30, nullable=false)
	private String tipo;
	
	@Column(length=60, nullable=false)
	private String executor;
	
	@Column(length=40)
	private String idTransacao;
	
	@Column(length=60)
	private String statusTransacao;
	
	@Column(length=40)
	private String idPagamento;
	
	@Column(length=60)
	private String statusPagamento;
	
	@Column(length=60)
	private String valorPagamento;
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHoraPagamento;

	@Column
	private String detalhe;

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
	 * @return the executor
	 */
	public String getExecutor() {
		return executor;
	}

	/**
	 * @param executor the executor to set
	 */
	public void setExecutor(String executor) {
		this.executor = executor;
	}

	/**
	 * @return the idTransacao
	 */
	public String getIdTransacao() {
		return idTransacao;
	}

	/**
	 * @param idTransacao the idTransacao to set
	 */
	public void setIdTransacao(String idTransacao) {
		this.idTransacao = idTransacao;
	}

	/**
	 * @return the statusTransacao
	 */
	public String getStatusTransacao() {
		return statusTransacao;
	}

	/**
	 * @param statusTransacao the statusTransacao to set
	 */
	public void setStatusTransacao(String statusTransacao) {
		this.statusTransacao = statusTransacao;
	}

	/**
	 * @return the idPagamento
	 */
	public String getIdPagamento() {
		return idPagamento;
	}

	/**
	 * @param idPagamento the idPagamento to set
	 */
	public void setIdPagamento(String idPagamento) {
		this.idPagamento = idPagamento;
	}

	/**
	 * @return the statusPagamento
	 */
	public String getStatusPagamento() {
		return statusPagamento;
	}

	/**
	 * @param statusPagamento the statusPagamento to set
	 */
	public void setStatusPagamento(String statusPagamento) {
		this.statusPagamento = statusPagamento;
	}

	/**
	 * @return the dataHoraPagamento
	 */
	public Date getDataHoraPagamento() {
		return dataHoraPagamento;
	}

	/**
	 * @param dataHoraPagamento the dataHoraPagamento to set
	 */
	public void setDataHoraPagamento(Date dataHoraPagamento) {
		this.dataHoraPagamento = dataHoraPagamento;
	}

	/**
	 * @return the detalhe
	 */
	public String getDetalhe() {
		return detalhe;
	}

	/**
	 * @param detalhe the detalhe to set
	 */
	public void setDetalhe(String detalhe) {
		this.detalhe = detalhe;
	}

	/**
	 * @return the valorPagamento
	 */
	public String getValorPagamento() {
		return valorPagamento;
	}

	/**
	 * @param valorPagamento the valorPagamento to set
	 */
	public void setValorPagamento(String valorPagamento) {
		this.valorPagamento = valorPagamento;
	}
	
}
