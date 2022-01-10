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
public class EstoqueItemModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 838858574627878332L;

	private Long id;
	
	private ProdutoModel produto;
	
	private Integer qtdMinima;
	
	private Integer qtdInicial;
	
	private Integer qtdCalculado;
	
	private Integer qtdAjuste;
	
	private Integer total;

	/**
	 * 
	 */
	public EstoqueItemModel() {
	}

	/**
	 * @param id
	 * @param produto
	 * @param qtdMinima
	 * @param qtdInicial
	 * @param qtdCalculado
	 * @param qtdAjuste
	 */
	public EstoqueItemModel(Long id, ProdutoModel produto, Integer qtdMinima, Integer qtdInicial, Integer qtdCalculado,
			Integer qtdAjuste) {
		this.id = id;
		this.produto = produto;
		this.qtdMinima = qtdMinima;
		this.qtdInicial = qtdInicial;
		this.qtdCalculado = qtdCalculado;
		this.qtdAjuste = qtdAjuste;
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
	 * @return the total
	 */
	public Integer getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}

	
}
