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
public class EstoqueReportModel implements ITReportModel<EstoqueModel> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2877298075065399546L;
	
	private Cozinha cozinha;
	
	private String ids;
	
	private Date dataInicio;
	
	private Date dataFim;
	
	private String origem;
	
	private List<EstoqueModel> result;


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
	 * @return the result
	 */
	public List<EstoqueModel> getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(List<EstoqueModel> result) {
		this.result = result;
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
	

}
