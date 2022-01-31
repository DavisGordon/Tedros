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
public class EstocavelModel implements Serializable{

	
	private static final long serialVersionUID = 3274590223540865996L;

	private String contentType;
	
	private Long id;
	
	private IdNomeModel cozinha;
	
	private String data;
	
	private String dataDesc;
	
	private String tipo;
	
	private IdNomeModel doador;
	
	private IdNomeModel acao;
	
	private String observacao;
	
	private List<EstocavelItemModel> itens;

	/**
	 * 
	 */
	public EstocavelModel() {
	}



	/**
	 * @param id
	 * @param cozinha
	 * @param data
	 * @param acao
	 * @param observacao
	 * @param itens
	 */
	public EstocavelModel(Long id, IdNomeModel cozinha, Date data, IdNomeModel acao, String observacao,
			List<EstocavelItemModel> itens) {
		this.id = id;
		this.cozinha = cozinha;
		this.data = ApiUtils.formatToDateTime(data);
		this.dataDesc = ApiUtils.formatDateHourToView(data);
		this.acao = acao;
		this.observacao = observacao;
		this.itens = itens;
	}

	

	/**
	 * @param id
	 * @param cozinha
	 * @param data
	 * @param tipo
	 * @param doador
	 * @param itens
	 */
	public EstocavelModel(Long id, IdNomeModel cozinha, Date data, String tipo, IdNomeModel doador,
			List<EstocavelItemModel> itens) {
		this.id = id;
		this.cozinha = cozinha;
		this.data = ApiUtils.formatToDateTime(data);
		this.dataDesc = ApiUtils.formatDateHourToView(data);
		this.tipo = tipo;
		this.doador = doador;
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

	/**
	 * @return the doador
	 */
	public IdNomeModel getDoador() {
		return doador;
	}

	/**
	 * @param doador the doador to set
	 */
	public void setDoador(IdNomeModel doador) {
		this.doador = doador;
	}

	/**
	 * @return the acao
	 */
	public IdNomeModel getAcao() {
		return acao;
	}

	/**
	 * @param acao the acao to set
	 */
	public void setAcao(IdNomeModel acao) {
		this.acao = acao;
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
	public List<EstocavelItemModel> getItens() {
		return itens;
	}

	/**
	 * @param itens the itens to set
	 */
	public void setItens(List<EstocavelItemModel> itens) {
		this.itens = itens;
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
	 * @return the contentType
	 */
	public String getContentType() {
		return contentType;
	}



	/**
	 * @param contentType the contentType to set
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	
	
}
