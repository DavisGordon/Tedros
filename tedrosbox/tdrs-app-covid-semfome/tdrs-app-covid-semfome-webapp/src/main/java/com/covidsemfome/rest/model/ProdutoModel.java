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
public class ProdutoModel implements Serializable{

	
	private static final long serialVersionUID = 3274590223540865996L;

	private Long id;
	
	private String codigo;
	
	private String nome;
	
	private String marca;
	
	private String descricao;
	
	private String unidadeMedida;
	
	private String medida;

	/**
	 * 
	 */
	public ProdutoModel() {
	}

	/**
	 * @param id
	 * @param codigo
	 * @param nome
	 * @param marca
	 * @param descricao
	 * @param unidadeMedida
	 * @param medida
	 */
	public ProdutoModel(Long id, String codigo, String nome, String marca, String descricao, String unidadeMedida,
			String medida) {
		this.id = id;
		this.codigo = codigo;
		this.nome = nome;
		this.marca = marca;
		this.descricao = descricao;
		this.unidadeMedida = unidadeMedida;
		this.medida = medida;
	}

	public ProdutoModel(Long id, String codigo, String nome) {
		this.id = id;
		this.codigo = codigo;
		this.nome = nome;
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
