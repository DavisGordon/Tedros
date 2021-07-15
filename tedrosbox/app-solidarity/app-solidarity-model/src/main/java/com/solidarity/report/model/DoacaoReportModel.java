/**
 * 
 */
package com.solidarity.report.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.solidarity.model.TipoAjuda;
import com.tedros.ejb.base.model.ITReportModel;

/**
 * @author Davis Gordon
 *
 */
public class DoacaoReportModel implements ITReportModel<DoacaoItemModel> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2877298075065399546L;

	private String nome;
	
	private String acaoId;
	
	private Date dataInicio;
	
	private Date dataFim;
	
	private List<TipoAjuda> tiposAjuda;
	
	private List<DoacaoItemModel> result;
	
	public BigDecimal getTotalValor(){
		BigDecimal t = new BigDecimal(0);
		
		if(result!=null)
			for (DoacaoItemModel i : result) {
				if(i.getValor()!=null)
					t = t.add(i.getValor());
			}
		return t;
	}
	
	public Long getTotalQuantidade(){
		Long t = 0L;
		if(result!=null)
			for (DoacaoItemModel i : result) {
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

	/**
	 * @return the result
	 */
	public List<DoacaoItemModel> getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(List<DoacaoItemModel> result) {
		this.result = result;
	}

}
