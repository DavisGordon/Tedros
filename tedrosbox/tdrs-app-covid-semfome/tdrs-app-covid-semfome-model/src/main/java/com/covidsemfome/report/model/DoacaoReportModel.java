/**
 * 
 */
package com.covidsemfome.report.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.covidsemfome.model.TipoAjuda;
import com.tedros.ejb.base.model.TReportModel;

/**
 * @author Davis Gordon
 *
 */
public class DoacaoReportModel extends TReportModel<DoacaoItemModel> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2877298075065399546L;

	private String nome;
	
	private String acaoId;
	
	private Date dataInicio;
	
	private Date dataFim;
	
	private List<TipoAjuda> tiposAjuda;
	
	
	public BigDecimal getTotalValor(){
		BigDecimal t = new BigDecimal(0);
		
		if(getResult()!=null)
			for (DoacaoItemModel i : getResult()) {
				if(i.getValor()!=null)
					t = t.add(i.getValor());
			}
		return t;
	}
	
	public Long getTotalQuantidade(){
		Long t = 0L;
		if(getResult()!=null)
			for (DoacaoItemModel i : getResult()) {
				if(i.getQuantidade()!=null)
					t  += i.getQuantidade();
			}
		return t;
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
	 * @return the acaoId
	 */
	public String getAcaoId() {
		return acaoId;
	}

	/**
	 * @param acaoId the acaoId to set
	 */
	public void setAcaoId(String acaoId) {
		this.acaoId = acaoId;
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
	 * @return the tiposAjuda
	 */
	public List<TipoAjuda> getTiposAjuda() {
		return tiposAjuda;
	}

	/**
	 * @param tiposAjuda the tiposAjuda to set
	 */
	public void setTiposAjuda(List<TipoAjuda> tiposAjuda) {
		this.tiposAjuda = tiposAjuda;
	}

}
