/**
 * 
 */
package com.covidsemfome.report.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import com.tedros.ejb.base.model.ITReportModel;

/**
 * @author Davis Gordon
 *
 */
public class PessoaReportModel implements ITReportModel<PessoaModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 956821922026096079L;

	private String nome;
	
	private String tipoVoluntario;
	
	private String statusVoluntario;
	
	private Boolean listarAcoes;
	
	private List<PessoaModel> result;
	/**
	 * 
	 */
	public PessoaReportModel() {
		// TODO Auto-generated constructor stub
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
	 * @return the tipoVoluntario
	 */
	public String getTipoVoluntario() {
		return tipoVoluntario;
	}
	/**
	 * @param tipoVoluntario the tipoVoluntario to set
	 */
	public void setTipoVoluntario(String tipoVoluntario) {
		this.tipoVoluntario = tipoVoluntario;
	}
	/**
	 * @return the statusVoluntario
	 */
	public String getStatusVoluntario() {
		return statusVoluntario;
	}
	/**
	 * @param statusVoluntario the statusVoluntario to set
	 */
	public void setStatusVoluntario(String statusVoluntario) {
		this.statusVoluntario = statusVoluntario;
	}
	/**
	 * @return the listarAcoes
	 */
	public boolean isListarAcoes() {
		return listarAcoes!=null && listarAcoes;
	}
	/**
	 * @param listarAcoes the listarAcoes to set
	 */
	public void setListarAcoes(Boolean listarAcoes) {
		this.listarAcoes = listarAcoes;
	}
	/**
	 * @return the result
	 */
	public List<PessoaModel> getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(List<PessoaModel> result) {
		this.result = result;
	}
	/**
	 * @return the listarAcoes
	 */
	public Boolean getListarAcoes() {
		return listarAcoes;
	}
	

}
