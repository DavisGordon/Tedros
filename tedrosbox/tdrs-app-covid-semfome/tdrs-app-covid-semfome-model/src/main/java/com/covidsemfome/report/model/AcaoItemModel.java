/**
 * 
 */
package com.covidsemfome.report.model;

import java.math.BigDecimal;
import java.util.Date;

import com.covidsemfome.model.Acao;
import com.tedros.ejb.base.model.ITModel;

/**
 * @author Davis Gordon
 *
 */
public class AcaoItemModel implements ITModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4377701034950081139L;

	private Long id;

	private String titulo;
	
	private String descricao;
	
	private Date data;

	private String status;
	
	private String observacao;
	
	private Integer qtdMinVoluntarios;
	
	private Integer qtdMaxVoluntarios;
	
	private BigDecimal vlrPrevisto;
	
	private BigDecimal vlrArrecadado;
	
	private BigDecimal vlrExecutado;
	
	private Integer totalInscritos;
	
	public AcaoItemModel(Acao acao) {
		this.id = acao.getId();
		this.titulo = acao.getTitulo();
		this.descricao = acao.getDescricao();
		this.data = acao.getData();
		this.status = acao.getStatus();
		this.observacao = acao.getObservacao();
		this.qtdMinVoluntarios = acao.getQtdMinVoluntarios();
		this.qtdMaxVoluntarios = acao.getQtdMaxVoluntarios();
		this.vlrPrevisto = acao.getVlrPrevisto();
		this.vlrArrecadado = acao.getVlrArrecadado();
		this.vlrExecutado = acao.getVlrExecutado();
		this.totalInscritos = acao.getVoluntarios()!=null ? acao.getVoluntarios().size() : 0;
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
	public Date getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Date data) {
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
	 * @return the vlrPrevisto
	 */
	public BigDecimal getVlrPrevisto() {
		return vlrPrevisto;
	}

	/**
	 * @param vlrPrevisto the vlrPrevisto to set
	 */
	public void setVlrPrevisto(BigDecimal vlrPrevisto) {
		this.vlrPrevisto = vlrPrevisto;
	}

	/**
	 * @return the vlrArrecadado
	 */
	public BigDecimal getVlrArrecadado() {
		return vlrArrecadado;
	}

	/**
	 * @param vlrArrecadado the vlrArrecadado to set
	 */
	public void setVlrArrecadado(BigDecimal vlrArrecadado) {
		this.vlrArrecadado = vlrArrecadado;
	}

	/**
	 * @return the vlrExecutado
	 */
	public BigDecimal getVlrExecutado() {
		return vlrExecutado;
	}

	/**
	 * @param vlrExecutado the vlrExecutado to set
	 */
	public void setVlrExecutado(BigDecimal vlrExecutado) {
		this.vlrExecutado = vlrExecutado;
	}

	/**
	 * @return the totalInscritos
	 */
	public Integer getTotalInscritos() {
		return totalInscritos;
	}

	/**
	 * @param totalInscritos the totalInscritos to set
	 */
	public void setTotalInscritos(Integer totalInscritos) {
		this.totalInscritos = totalInscritos;
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
