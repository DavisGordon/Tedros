/**
 * 
 */
package org.somos.web.rest.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.somos.model.AjudaCampanha;
import org.somos.web.rest.util.ApiUtils;

/**
 * @author Davis Gordon
 *
 */
@XmlRootElement
public class AjudaCampanhaModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8024810097187358874L;

	private Long id;
	
	private Long idCampanha;
	
	private String titulo;

	private String valor;

	private String periodo;
	
	private Long idFormaAjuda;
	
	private String formaAjuda;
	
	private DetalheAjudaModel detalhe;
	
	private String dataHora;
	
	private String desc;
	
	
	public AjudaCampanhaModel() {
		super();
	}

	public AjudaCampanhaModel(AjudaCampanha e) {
		this.id = e.getId();
		this.titulo = e.getCampanha().getTitulo();
		this.valor = e.getValor();
		this.periodo = e.getPeriodo();
		this.formaAjuda = e.getFormaAjuda().getTipo();
		this.detalhe = e.getDetalheAjuda()!=null 
				? new DetalheAjudaModel(e.getDetalheAjuda())
						: null;
		this.dataHora = ApiUtils.formatDateHourToView(e.getInsertDate());
		
		desc = "Forma de ajuda: "+formaAjuda;
		if(periodo!=null)
			desc += ", Periodo: "+periodo;
		if(valor!=null)
			desc += ", Valor: "+valor;
		if(dataHora!=null)
			desc += ", Data: "+dataHora;
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
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * @param valor the valor to set
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}

	/**
	 * @return the periodo
	 */
	public String getPeriodo() {
		return periodo;
	}

	/**
	 * @param periodo the periodo to set
	 */
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	/**
	 * @return the formaAjuda
	 */
	public String getFormaAjuda() {
		return formaAjuda;
	}

	/**
	 * @param formaAjuda the formaAjuda to set
	 */
	public void setFormaAjuda(String formaAjuda) {
		this.formaAjuda = formaAjuda;
	}


	/**
	 * @return the dataHora
	 */
	public String getDataHora() {
		return dataHora;
	}

	/**
	 * @param dataHora the dataHora to set
	 */
	public void setDataHora(String dataHora) {
		this.dataHora = dataHora;
	}

	/**
	 * @return the idCampanha
	 */
	public Long getIdCampanha() {
		return idCampanha;
	}

	/**
	 * @param idCampanha the idCampanha to set
	 */
	public void setIdCampanha(Long idCampanha) {
		this.idCampanha = idCampanha;
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
	 * @return the idFormaAjuda
	 */
	public Long getIdFormaAjuda() {
		return idFormaAjuda;
	}

	/**
	 * @param idFormaAjuda the idFormaAjuda to set
	 */
	public void setIdFormaAjuda(Long idFormaAjuda) {
		this.idFormaAjuda = idFormaAjuda;
	}

	/**
	 * @return the detalhe
	 */
	public DetalheAjudaModel getDetalhe() {
		return detalhe;
	}

	/**
	 * @param detalhe the detalhe to set
	 */
	public void setDetalhe(DetalheAjudaModel detalhe) {
		this.detalhe = detalhe;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

}
