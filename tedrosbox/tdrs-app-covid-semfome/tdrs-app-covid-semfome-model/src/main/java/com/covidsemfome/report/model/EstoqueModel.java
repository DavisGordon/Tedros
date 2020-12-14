/**
 * 
 */
package com.covidsemfome.report.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.covidsemfome.model.Estoque;
import com.covidsemfome.model.EstoqueItem;
import com.tedros.ejb.base.entity.TEntity;
import com.tedros.util.TDateUtil;

/**
 * @author Davis Gordon
 *
 */
public class EstoqueModel extends TEntity {

	private static final long serialVersionUID = -457987996337666023L;
	
	private String  origem;
	
	private String cozinha;
	
	private String dataHora;
	
	private String observacao;
	
	private List<EstoqueItemModel> itens;

	/**
	 * @param origem
	 * @param cozinha
	 * @param dataHora
	 * @param observacao
	 * @param itens
	 */
	public EstoqueModel(Estoque m) {
		setId(m.getId());
		this.origem = m.getEntradaRef()!=null ? m.getEntradaRef().toString() : m.getSaidaRef().toString();
		this.cozinha = m.getCozinha().toString();
		this.dataHora = TDateUtil.getFormatedDate(m.getData(), TDateUtil.DDMMYYYY_HHMM);
		this.observacao = m.getObservacao();
		
		
		this.itens = new ArrayList<>();
		for(EstoqueItem i : m.getItens())
			itens.add(new EstoqueItemModel(i));
		
		Collections.sort(itens, new Comparator<EstoqueItemModel>() {
			@Override
			public int compare(EstoqueItemModel o1, EstoqueItemModel o2) {
				return o1.getNome().compareToIgnoreCase(o2.getNome());
			}});
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

	
}
