/**
 * 
 */
package org.somos.report.model;

import org.somos.model.EstoqueItem;

import com.tedros.ejb.base.model.ITModel;

/**
 * @author Davis Gordon
 *
 */
public class EstoqueItemModel implements ITModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8512071372960033941L;

	private String nome;
	
	private String produto;
	
	private Integer qtdEstocavel;
	
	private Integer qtdMinima;
	
	private Integer qtdInicial;
	
	private Integer qtdCalculado;
	
	private Integer qtdAjuste;
	
	private Integer vlrAjustado;

	/**
	 * @param qtd 
	 * @param produto
	 * @param qtdMinima
	 * @param qtdInicial
	 * @param qtdCalculado
	 * @param qtdAjuste
	 * @param vlrAjustado
	 */
	public EstoqueItemModel(EstoqueItem m, int qtd) {
		this.nome = m.getProduto().getNome();
		this.produto = m.getProduto().toString();
		this.qtdMinima = m.getQtdMinima();
		this.qtdInicial = m.getQtdInicial();
		this.qtdCalculado = m.getQtdCalculado();
		this.qtdAjuste = m.getQtdAjuste();
		this.vlrAjustado = m.getVlrAjustado();
		this.qtdEstocavel = qtd;
	}

	/**
	 * @return the produto
	 */
	public String getProduto() {
		return produto;
	}

	/**
	 * @param produto the produto to set
	 */
	public void setProduto(String produto) {
		this.produto = produto;
	}

	/**
	 * @return the qtdMinima
	 */
	public Integer getQtdMinima() {
		return qtdMinima;
	}

	/**
	 * @param qtdMinima the qtdMinima to set
	 */
	public void setQtdMinima(Integer qtdMinima) {
		this.qtdMinima = qtdMinima;
	}

	/**
	 * @return the qtdInicial
	 */
	public Integer getQtdInicial() {
		return qtdInicial;
	}

	/**
	 * @param qtdInicial the qtdInicial to set
	 */
	public void setQtdInicial(Integer qtdInicial) {
		this.qtdInicial = qtdInicial;
	}

	/**
	 * @return the qtdCalculado
	 */
	public Integer getQtdCalculado() {
		return qtdCalculado;
	}

	/**
	 * @param qtdCalculado the qtdCalculado to set
	 */
	public void setQtdCalculado(Integer qtdCalculado) {
		this.qtdCalculado = qtdCalculado;
	}

	/**
	 * @return the qtdAjuste
	 */
	public Integer getQtdAjuste() {
		return qtdAjuste;
	}

	/**
	 * @param qtdAjuste the qtdAjuste to set
	 */
	public void setQtdAjuste(Integer qtdAjuste) {
		this.qtdAjuste = qtdAjuste;
	}

	/**
	 * @return the vlrAjustado
	 */
	public Integer getVlrAjustado() {
		return vlrAjustado;
	}

	/**
	 * @param vlrAjustado the vlrAjustado to set
	 */
	public void setVlrAjustado(Integer vlrAjustado) {
		this.vlrAjustado = vlrAjustado;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the qtdEstocavel
	 */
	public Integer getQtdEstocavel() {
		return qtdEstocavel;
	}

	/**
	 * @param qtdEstocavel the qtdEstocavel to set
	 */
	public void setQtdEstocavel(Integer qtdEstocavel) {
		this.qtdEstocavel = qtdEstocavel;
	}
	
	

	
}
