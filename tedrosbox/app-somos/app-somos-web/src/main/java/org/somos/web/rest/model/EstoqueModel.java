/**
 * 
 */
package org.somos.web.rest.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.somos.web.rest.util.ApiUtils;

/**
 * @author Davis Gordon
 *
 */
@XmlRootElement 
public class EstoqueModel implements Serializable{

	
	private static final long serialVersionUID = 3274590223540865996L;

	private Long id;
	
	private IdNomeModel cozinha;
	
	private String origem;
	
	private String data;
	
	private String dataDesc;
	
	private String observacao;
	
	private List<EstoqueItemModel> itens;

	/**
	 * 
	 */
	public EstoqueModel() {
	}

	/**
	 * @param id
	 * @param cozinha
	 * @param data
	 * @param dataDesc
	 * @param observacao
	 * @param itens
	 */
	public EstoqueModel(Long id, IdNomeModel cozinha, String origem, Date data, String observacao,
			List<EstoqueItemModel> itens) {
		this.id = id;
		this.cozinha = cozinha;
		this.origem = origem;
		this.data = ApiUtils.formatToDateTime(data);
		this.dataDesc = ApiUtils.formatDateHourToView(data);
		this.observacao = observacao;
		this.itens = itens;
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
	 * @return the cozinha
	 */
	public IdNomeModel getCozinha() {
		return cozinha;
	}

	/**
	 * @param cozinha the cozinha to set
	 */
	public void setCozinha(IdNomeModel cozinha) {
		this.cozinha = cozinha;
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
	 * @return the dataDesc
	 */
	public String getDataDesc() {
		return dataDesc;
	}

	/**
	 * @param dataDesc the dataDesc to set
	 */
	public void setDataDesc(String dataDesc) {
		this.dataDesc = dataDesc;
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
	 * @return the itens
	 */
	public List<EstoqueItemModel> getItens() {
		return itens;
	}

	/**
	 * @param itens the itens to set
	 */
	public void setItens(List<EstoqueItemModel> itens) {
		this.itens = itens;
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
