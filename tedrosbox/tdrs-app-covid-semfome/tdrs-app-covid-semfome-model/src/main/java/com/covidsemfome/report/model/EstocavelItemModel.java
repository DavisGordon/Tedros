/**
 * 
 */
package com.covidsemfome.report.model;

import java.math.BigDecimal;

import com.covidsemfome.model.EntradaItem;
import com.covidsemfome.model.SaidaItem;
import com.tedros.ejb.base.model.ITModel;

/**
 * @author Davis Gordon
 *
 */
public class EstocavelItemModel implements ITModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4377701034950081139L;

	
	private String codigo;
	
	private String produto;
	
	private Integer quantidade;
	
	private BigDecimal valorUnitario;

	private String unidadeMedida;

	
	
	/**
	 * @param i
	 */
	public EstocavelItemModel(SaidaItem i) {
		this.codigo = i.getProduto().getCodigo();
		this.produto = i.getProduto().getNome();
		this.quantidade = i.getQuantidade();
	}

	/**
	 * @param i
	 */
	public EstocavelItemModel(EntradaItem i) {
		this.codigo = i.getProduto().getCodigo();
		this.produto = i.getProduto().getNome();
		this.quantidade = i.getQuantidade();
		this.valorUnitario = i.getValorUnitario();
		this.unidadeMedida = i.getUnidadeMedida();
	}
	
	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
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

}
