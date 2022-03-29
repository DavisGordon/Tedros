/**
 * 
 */
package com.template.report.model;

import com.tedros.ejb.base.model.ITModel;
import com.template.model.Produto;

/**
 * @author Davis Gordon
 *
 */
public class ProdutoItemModel implements ITModel {

	private static final long serialVersionUID = 7964845555411113410L;

	
	private String codigo;
	
	private String nome;
	
	private String marca;
	
	private String descricao;
	
	private String unidadeMedida;
	
	private String medida;
	
	
	/**
	 * 
	 */
	public ProdutoItemModel() {
		// TODO Auto-generated constructor stub
	}
	

	/**
	 * @param codigo
	 * @param nome
	 * @param marca
	 * @param descricao
	 * @param unidadeMedida
	 * @param medida
	 */
	public ProdutoItemModel(Produto p) {
		this.codigo = p.getCodigo();
		this.nome = p.getNome();
		this.marca = p.getMarca();
		this.descricao = p.getDescricao();
		this.unidadeMedida = p.getUnidadeMedida();
		this.medida = p.getMedida();
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
	 * @return the marca
	 */
	public String getMarca() {
		return marca;
	}


	/**
	 * @param marca the marca to set
	 */
	public void setMarca(String marca) {
		this.marca = marca;
	}


	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}


	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
	 * @return the medida
	 */
	public String getMedida() {
		return medida;
	}


	/**
	 * @param medida the medida to set
	 */
	public void setMedida(String medida) {
		this.medida = medida;
	}

}
