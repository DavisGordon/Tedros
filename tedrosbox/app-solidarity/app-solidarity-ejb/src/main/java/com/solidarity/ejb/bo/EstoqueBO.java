/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.solidarity.ejb.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.solidarity.ejb.eao.EstoqueEAO;
import com.solidarity.model.Estoque;
import com.solidarity.model.EstoqueItem;
import com.solidarity.model.Produto;
import com.solidarity.report.model.EstoqueModel;
import com.solidarity.report.model.EstoqueReportModel;
import com.tedros.ejb.base.bo.TGenericBO;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@RequestScoped
public class EstoqueBO extends TGenericBO<Estoque> {

	@Inject
	private EstoqueEAO eao;
	
	
	@Override
	public EstoqueEAO getEao() {
		return eao;
	}
	
	@Override
	public Estoque save(Estoque e) throws Exception {
		
		if(!e.isNew()) {
			
			// estoque gerado 
			Estoque estRef = eao.findById(e);
			
			// recupera os estoques da cozinha gerados a posteriore, se houver
			List<Estoque> post = eao.getPosteriores(e.getId(), e.getCozinha());
			//separa os itens a serem analisados
			final List<EstoqueItem> iE = e.getItens();
			final List<EstoqueItem> iERef = estRef.getItens();
			final Map<Produto, Integer> values = new HashMap<>();
			for (EstoqueItem eI : iE) {
				//recupera estoque item 
				EstoqueItem eIRef = new EstoqueItem(eI.getProduto());
				if(iERef.contains(eIRef)) {
					eIRef = iERef.get(iERef.indexOf(eIRef));
					int eIQtd = eI.getQtdAjuste()!=null ? eI.getQtdAjuste().intValue() : 0;
					int eIQtdRef = eIRef.getQtdAjuste()!=null ? eIRef.getQtdAjuste().intValue() : 0;
					
					if(eIQtd!=eIQtdRef) {
						int qtd = eIQtd - eIQtdRef;
						values.put(eI.getProduto(), qtd);
					}
				}
			}
			
			super.save(e);
			
			if(post!=null && !post.isEmpty()) {
				for(Estoque eP : post) {
					for(Produto p : values.keySet()) {
						Integer qtd = values.get(p);
						EstoqueItem ei = new EstoqueItem(p);
						if(eP.getItens().contains(ei)) {
							final EstoqueItem ei2 = eP.getItens().get(eP.getItens().indexOf(ei));
							ei2.setQtdInicial(ei2.getQtdInicial() + qtd);
							ei2.addQuantidade(qtd);
						}
					}
					super.save(eP);
				}
				
			}
		}
		
		return super.save(e);
	}
	
	public EstoqueReportModel pesquisar(EstoqueReportModel m){
		String ids = m.getIds();
		List<Long> idsl = null;
		if(ids!=null){
			idsl = new ArrayList<>();
			String[] arr = ids.split(",");
			for(String i : arr)
				idsl.add(Long.valueOf(i));
		}
		
		List<Estoque> lst = eao.pesquisar(idsl, m.getCozinha(), m.getDataInicio(), m.getDataFim(), m.getOrigem());
		if(lst!=null) {
			List<EstoqueModel> itens = new ArrayList<>();
			for(Estoque a : lst){
				itens.add(new EstoqueModel(a));
			}
			m.setResult(itens);
		}
		
		return m;
	}
	
	
}
