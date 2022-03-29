/**
 * 
 */
package com.template.report.model;

import com.tedros.ejb.base.model.TReportModel;

/**
 * @author Davis Gordon
 *
 */
public class ProdutoReportModel extends TReportModel<ProdutoItemModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 956821922026096079L;
	
	private String codigo;
	
	private String nome;
	
	private String marca;
	
	private String unidadeMedida;
	
	private String medida;
	
	/**
	 * 
	 */
	public ProdutoReportModel() {
		// TODO Auto-generated constructor stub
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
