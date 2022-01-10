/**
 * 
 */
package com.covidsemfome.rest.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Davis Gordon
 *
 */
@XmlRootElement 
public class EstoqueConfigModel implements Serializable{

	
	private static final long serialVersionUID = 3274590223540865996L;

	private Long id;
	
	private IdNomeModel cozinha;
	
	private ProdutoModel produto;
	
	private Integer qtdMinima;
	
	private Integer qtdInicial;

	/**
	 * 
	 */
	public EstoqueConfigModel() {
	}

	/**
	 * @param id
	 * @param cozinha
	 * @param produto
	 * @param qtdMinima
	 * @param qtdInicial
	 */
	public EstoqueConfigModel(Long id, IdNomeModel cozinha, ProdutoModel produto, Integer qtdMinima,
			Integer qtdInicial) {
		this.id = id;
		this.cozinha = cozinha;
		this.produto = produto;
		this.qtdMinima = qtdMinima;
		this.qtdInicial = qtdInicial;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the cozinha
	 */
	public IdNomeModel getCozinha() {
		return cozinha;
	}

	/**
	 * @param cozinha the cozinha to set
	 */
	public void setCozinha(IdNomeModel cozinha) {
		this.cozinha = cozinha;
	}

	/**
	 * @return the produto
	 */
	public ProdutoModel getProduto() {
		return produto;
	}

	/**
	 * @param produto the produto to set
	 */
	public void setProduto(ProdutoModel produto) {
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


	
}
