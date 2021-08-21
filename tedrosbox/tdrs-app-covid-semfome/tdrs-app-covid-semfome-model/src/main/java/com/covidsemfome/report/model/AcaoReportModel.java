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
public class AcaoReportModel extends TReportModel<AcaoItemModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 956821922026096079L;
	
	private String ids;

	private String titulo;
	
	private Date dataInicio;
	
	private Date dataFim;

	private String status;
	
	/**
	 * 
	 */
	public AcaoReportModel() {
		// TODO Auto-generated constructor stub
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

}
