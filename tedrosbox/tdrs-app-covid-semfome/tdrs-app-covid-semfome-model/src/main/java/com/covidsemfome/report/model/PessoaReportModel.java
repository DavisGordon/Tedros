/**
 * 
 */
package com.covidsemfome.report.model;

import java.util.Date;

import com.tedros.ejb.base.model.TReportModel;

/**
 * @author Davis Gordon
 *
 */
public class PessoaReportModel extends TReportModel<PessoaModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 956821922026096079L;

	private String nome;
	
	private String tipoVoluntario;
	
	private String statusVoluntario;
	
	private Date dataInicio;
	
	private Date dataFim;
	
	private Boolean listarAcoes;
	
	private Boolean detalhado;
	
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
	 * @return the listarAcoes
	 */
	public Boolean getListarAcoes() {
		return listarAcoes;
	}
	/**
	 * @return the dataInicio
	 */
	public Date getDataInicio() {
		return dataInicio;
	}
	/**
	 * @param dataInicio the dataInicio to set
	 */
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	/**
	 * @return the dataFim
	 */
	public Date getDataFim() {
		return dataFim;
	}
	/**
	 * @param dataFim the dataFim to set
	 */
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	/**
	 * @return the detalhado
	 */
	public Boolean getDetalhado() {
		return detalhado;
	}
	/**
	 * @param detalhado the detalhado to set
	 */
	public void setDetalhado(Boolean detalhado) {
		this.detalhado = detalhado;
	}
	
	public boolean isDetalhado() {
		return detalhado!=null && detalhado;
	}

}
