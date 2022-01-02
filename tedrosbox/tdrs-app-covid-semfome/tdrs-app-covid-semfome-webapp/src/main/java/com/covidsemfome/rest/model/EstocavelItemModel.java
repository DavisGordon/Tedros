/**
 * 
 */
package com.covidsemfome.rest.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Davis Gordon
 *
 */
@XmlRootElement
public class EstocavelItemModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 838858574627878332L;

	//@FormParam("id")
	private Long id;
	
	//@FormParam("produto")
	private IdNomeModel produto;
	
	//@FormParam("quantidade")
	private Integer quantidade;
	
	//@FormParam("valorUnitario")
	private BigDecimal valorUnitario;

	//@FormParam("unidadeMedida")
	private String unidadeMedida;

	/**
	 * 
	 */
	public EstocavelItemModel() {
	}

	/**
	 * @param id
	 * @param produto
	 * @param quantidade
	 */
	public EstocavelItemModel(Long id, IdNomeModel produto, Integer quantidade) {
		this.id = id;
		this.produto = produto;
		this.quantidade = quantidade;
	}

	/**
	 * @param id
	 * @param produto
	 * @param quantidade
	 * @param valorUnitario
	 * @param unidadeMedida
	 */
	public EstocavelItemModel(Long id, IdNomeModel produto, Integer quantidade, BigDecimal valorUnitario, String unidadeMedida) {
		this.id = id;
		this.produto = produto;
		this.quantidade = quantidade;
		this.valorUnitario = valorUnitario;
		this.unidadeMedida = unidadeMedida;
	}

	/**
	 * @return the produto
	 */
	public IdNomeModel getProduto() {
		return produto;
	}

	/**
	 * @param produto the produto to set
	 */
	public void setProduto(IdNomeModel produto) {
		this.produto = produto;
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
	 * @return the valorUnitario
	 */
	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}

	/**
	 * @param valorUnitario the valorUnitario to set
	 */
	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	/**
	 * @return the unidadeMedida
	 */
	public String getUnidadeMedida() {
		return unidadeMedida;
	}

	/**
	 * @param unidadeMedida the unidadeMedida to set
	 */
	public void setUnidadeMedida(String unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
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
	
	
}
