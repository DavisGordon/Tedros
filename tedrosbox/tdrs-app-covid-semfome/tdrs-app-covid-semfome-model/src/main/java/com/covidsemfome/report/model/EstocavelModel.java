/**
 * 
 */
package com.covidsemfome.report.model;

import java.util.ArrayList;
import java.util.List;

import com.covidsemfome.model.Entrada;
import com.covidsemfome.model.EntradaItem;
import com.covidsemfome.model.Saida;
import com.covidsemfome.model.SaidaItem;
import com.tedros.ejb.base.entity.TEntity;
import com.tedros.util.TDateUtil;

/**
 * @author Davis Gordon
 *
 */
public class EstocavelModel extends TEntity {

	private static final long serialVersionUID = -457987996337666023L;
	
	
	private String estocavelLabel;
	
	private String acao;
	
	private String cozinha;
	
	private String dataHora;
	
	private String tipo;
	
	private String doador;
	
	private String observacao;
	
	private List<EstocavelItemModel> itens;

	public EstocavelModel(Entrada m) {
		
		setId(m.getId());
		this.estocavelLabel = "Entrada";
		this.doador = m.getDoador()!=null ? m.getDoador().getNome() : null;
		this.cozinha = m.getCozinha().toString();
		this.dataHora = TDateUtil.getFormatedDate(m.getData(), TDateUtil.DDMMYYYY_HHMM);
		this.tipo = m.getTipo();
		
		this.itens = new ArrayList<>();
		for(EntradaItem i : m.getItens()) 
			itens.add(new EstocavelItemModel(i));
	}
	
	public EstocavelModel(Saida m) {
		
		setId(m.getId());
		this.estocavelLabel = "Saida";
		this.cozinha = m.getCozinha().toString();
		this.dataHora = TDateUtil.getFormatedDate(m.getData(), TDateUtil.DDMMYYYY_HHMM);
		this.acao = m.getAcao().getTitulo() +" em " + this.dataHora;
		this.observacao = m.getObservacao();
		this.itens = new ArrayList<>();
		for(SaidaItem i : m.getItens()) 
			itens.add(new EstocavelItemModel(i));
	}

	/**
	 * @return the estocavelLabel
	 */
	public String getEstocavelLabel() {
		return estocavelLabel;
	}

	/**
	 * @param estocavelLabel the estocavelLabel to set
	 */
	public void setEstocavelLabel(String estocavelLabel) {
		this.estocavelLabel = estocavelLabel;
	}

	/**
	 * @return the acao
	 */
	public String getAcao() {
		return acao;
	}

	/**
	 * @param acao the acao to set
	 */
	public void setAcao(String acao) {
		this.acao = acao;
	}

	/**
	 * @return the cozinha
	 */
	public String getCozinha() {
		return cozinha;
	}

	/**
	 * @param cozinha the cozinha to set
	 */
	public void setCozinha(String cozinha) {
		this.cozinha = cozinha;
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
	public String getDoador() {
		return doador;
	}

	/**
	 * @param doador the doador to set
	 */
	public void setDoador(String doador) {
		this.doador = doador;
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
	

}
