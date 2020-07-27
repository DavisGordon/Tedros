/**
 * 
 */
package com.covidsemfome.report.model;

import java.math.BigDecimal;
import java.util.Date;

import com.tedros.ejb.base.model.ITModel;

/**
 * @author Davis Gordon
 *
 */
public class DoacaoItemModel implements ITModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8512071372960033941L;

	private Date data;
	
	private String tipoAjuda;
	
	private Long quantidade;
	
	private BigDecimal valor;
	
	private String pessoa;
	
	private String acao;

	/**
	 * @param data
	 * @param tipoAjuda
	 * @param quantidade
	 * @param valor
	 * @param pessoa
	 * @param acao
	 */
	public DoacaoItemModel(Date data, String tipoAjuda, Long quantidade, BigDecimal valor, String pessoa, String acao) {
		this.data = data;
		this.tipoAjuda = tipoAjuda;
		this.quantidade = quantidade;
		this.valor = valor;
		this.pessoa = pessoa;
		this.acao = acao;
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
	 * @return the tipoAjuda
	 */
	public String getTipoAjuda() {
		return tipoAjuda;
	}

	/**
	 * @param tipoAjuda the tipoAjuda to set
	 */
	public void setTipoAjuda(String tipoAjuda) {
		this.tipoAjuda = tipoAjuda;
	}

	/**
	 * @return the quantidade
	 */
	public Long getQuantidade() {
		return quantidade;
	}

	/**
	 * @param quantidade the quantidade to set
	 */
	public void setQuantidade(Long quantidade) {
		this.quantidade = quantidade;
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
	 * @return the pessoa
	 */
	public String getPessoa() {
		return pessoa;
	}

	/**
	 * @param pessoa the pessoa to set
	 */
	public void setPessoa(String pessoa) {
		this.pessoa = pessoa;
	}

	/**
	 * @return the acao
	 */
	public String getAcao() {
		return acao;
	}

	/**
	 * @param acao the acao to set
	 */
	public void setAcao(String acao) {
		this.acao = acao;
	}

}
