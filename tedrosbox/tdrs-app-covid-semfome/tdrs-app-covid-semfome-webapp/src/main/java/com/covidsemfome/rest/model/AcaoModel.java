/**
 * 
 */
package com.covidsemfome.rest.model;

/**
 * @author Davis Gordon
 *
 */
public class AcaoModel {

	private Long id;
	
	private String titulo;
	
	private String descricao;
	
	private String data;

	private String status;
	
	private String observacao;
	
	private Integer qtdMinVoluntarios;
	
	private Integer qtdMaxVoluntarios;
	
	private Integer qtdVoluntariosInscritos;
	
	private boolean inscrito;

	
	
	public AcaoModel(Long id, String titulo, String descricao, 
			String data, String status, String observacao, 
			Integer qtdMinVoluntarios,  Integer qtdMaxVoluntarios, Integer qtdVoluntariosInscritos,
			boolean inscrito) {
		
		this.id = id;
		this.titulo = titulo;
		this.descricao = descricao;
		this.data = data;
		this.observacao = observacao;
		this.status = status;
		this.qtdMinVoluntarios = qtdMinVoluntarios;
		this.qtdMaxVoluntarios = qtdMaxVoluntarios;
		this.qtdVoluntariosInscritos = qtdVoluntariosInscritos;
		this.inscrito = inscrito;
		
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
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
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
	 * @return the data
	 */
	public String getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the observacao
	 */
	public String getObservacao() {
		return observacao;
	}

	/**
	 * @param observacao the observacao to set
	 */
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	/**
	 * @return the qtdMinVoluntarios
	 */
	public Integer getQtdMinVoluntarios() {
		return qtdMinVoluntarios;
	}

	/**
	 * @param qtdMinVoluntarios the qtdMinVoluntarios to set
	 */
	public void setQtdMinVoluntarios(Integer qtdMinVoluntarios) {
		this.qtdMinVoluntarios = qtdMinVoluntarios;
	}

	/**
	 * @return the qtdMaxVoluntarios
	 */
	public Integer getQtdMaxVoluntarios() {
		return qtdMaxVoluntarios;
	}

	/**
	 * @param qtdMaxVoluntarios the qtdMaxVoluntarios to set
	 */
	public void setQtdMaxVoluntarios(Integer qtdMaxVoluntarios) {
		this.qtdMaxVoluntarios = qtdMaxVoluntarios;
	}

	/**
	 * @return the qtdVoluntariosInscritos
	 */
	public Integer getQtdVoluntariosInscritos() {
		return qtdVoluntariosInscritos;
	}

	/**
	 * @param qtdVoluntariosInscritos the qtdVoluntariosInscritos to set
	 */
	public void setQtdVoluntariosInscritos(Integer qtdVoluntariosInscritos) {
		this.qtdVoluntariosInscritos = qtdVoluntariosInscritos;
	}

	/**
	 * @return the inscrito
	 */
	public boolean isInscrito() {
		return inscrito;
	}

	/**
	 * @param inscrito the inscrito to set
	 */
	public void setInscrito(boolean inscrito) {
		this.inscrito = inscrito;
	}
}
