/**
 * 
 */
package com.covidsemfome.report.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.covidsemfome.model.EntradaItem;
import com.covidsemfome.model.EstocavelItem;
import com.covidsemfome.model.Estoque;
import com.covidsemfome.model.EstoqueItem;
import com.covidsemfome.model.SaidaItem;
import com.tedros.ejb.base.entity.TEntity;
import com.tedros.util.TDateUtil;

/**
 * @author Davis Gordon
 *
 */
public class EstoqueModel extends TEntity {

	private static final long serialVersionUID = -457987996337666023L;
	
	
	private String estocavelLabel;
	
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
		
		boolean entrada =  m.getEntradaRef()!=null;
		this.estocavelLabel = entrada ? "Entrada" : "Saida";
		this.origem = entrada ? m.getEntradaRef().toString() : m.getSaidaRef().toString();
		this.cozinha = m.getCozinha().toString();
		this.dataHora = TDateUtil.getFormatedDate(m.getData(), TDateUtil.DDMMYYYY_HHMM);
		this.observacao = m.getObservacao();
		
		List<? extends EstocavelItem> estocavelItens = entrada
				? m.getEntradaRef().getItens() 
						: m.getSaidaRef().getItens();
		
		this.itens = new ArrayList<>();
		for(EstoqueItem i : m.getItens()) {
			
			EstocavelItem item = entrada 
					? new EntradaItem(i.getProduto()) 
							: new SaidaItem(i.getProduto());
			int qtd = 0;
			int idx = estocavelItens.indexOf(item);
			if(idx!=-1) {
				item = estocavelItens.get(idx);
				qtd = item.getQuantidade();
			}
			itens.add(new EstoqueItemModel(i, qtd));
			
		}
		
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

	
}
