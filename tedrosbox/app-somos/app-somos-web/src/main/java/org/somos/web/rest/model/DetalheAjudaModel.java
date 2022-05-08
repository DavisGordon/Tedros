/**
 * 
 */
package org.somos.web.rest.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Davis Gordon
 *
 */
@XmlRootElement
public class DetalheAjudaModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7698837881125377969L;

	private String tipo;
	
	private String executor;
	
	private String idTransacao;
	
	private String statusTransacao;
	
	private String idPagamento;
	
	private String statusPagamento;
	
	private String valorPagamento;
	
	private String dataHoraPagamento;

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

	/**
	 * @return the dataHoraPagamento
	 */
	public String getDataHoraPagamento() {
		return dataHoraPagamento;
	}

	/**
	 * @param dataHoraPagamento the dataHoraPagamento to set
	 */
	public void setDataHoraPagamento(String dataHoraPagamento) {
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
}
