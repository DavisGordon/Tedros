/**
 * 
 */
package com.covidsemfome.report.model;

import java.util.Date;
import java.util.List;

import com.covidsemfome.model.Cozinha;
import com.tedros.ejb.base.model.ITReportModel;

/**
 * @author Davis Gordon
 *
 */
public class EstocavelReportModel implements ITReportModel<EstocavelModel> {

	private static final long serialVersionUID = 956821922026096079L;
	
	private String tipo;
	
	private Cozinha cozinha;
	
	private String ids;
	
	private Date dataInicio;
	
	private Date dataFim;
	
	private String origem;
	
	private List<EstocavelModel> result;
	/**
	 * 
	 */
	public EstocavelReportModel() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return the cozinha
	 */
	public Cozinha getCozinha() {
		return cozinha;
	}
	/**
	 * @param cozinha the cozinha to set
	 */
	public void setCozinha(Cozinha cozinha) {
		this.cozinha = cozinha;
	}
	/**
	 * @return the ids
	 */
	public String getIds() {
		return ids;
	}
	/**
	 * @param ids the ids to set
	 */
	public void setIds(String ids) {
		this.ids = ids;
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
	 * @return the origem
	 */
	public String getOrigem() {
		return origem;
	}
	/**
	 * @param origem the origem to set
	 */
	public void setOrigem(String origem) {
		this.origem = origem;
	}
	/**
	 * @return the result
	 */
	public List<EstocavelModel> getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(List<EstocavelModel> result) {
		this.result = result;
	}
	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}
	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
}
