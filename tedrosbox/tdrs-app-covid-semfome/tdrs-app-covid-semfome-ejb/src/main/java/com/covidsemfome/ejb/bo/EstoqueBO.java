/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.ejb.bo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.commons.collections4.ListUtils;

import com.covidsemfome.ejb.eao.EstoqueEAO;
import com.covidsemfome.model.Entrada;
import com.covidsemfome.model.EntradaItem;
import com.covidsemfome.model.Estoque;
import com.covidsemfome.model.EstoqueConfig;
import com.covidsemfome.model.EstoqueItem;
import com.covidsemfome.model.Produto;
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
	private ProdutoBO prodBO;
	
	@Inject
	private EntradaBO entBO;
	
	@Inject
	private EstoqueEAO eao;
	
	@Inject
	private EstoqueConfigBO estConBO;
	
	@Override
	public EstoqueEAO getEao() {
		return eao;
	}
	
	public void gerarEstoque(Entrada entrada) throws Exception {
		
		Entrada entSaved = null; 
		boolean isCozAlterada = false;
		if(!entrada.isNew()) {
			entSaved = entBO.findById(entrada);
			isCozAlterada = !entSaved.getCozinha().getId().equals(entrada.getCozinha().getId());
			
		}
		
		List<Produto> prods = prodBO.listAll(Produto.class);
		List<EstoqueConfig> eCon = estConBO.listAll(EstoqueConfig.class);
		
		if(!isCozAlterada) {
			// Cozinha nao foi alterada
			Estoque ex = new Estoque();
			
			if(!entrada.isNew()) {
				ex.setEntradaRef(entrada);
				ex.setCozinha(entrada.getCozinha());
			}
			
			// estoque gerado para as cozinha da entrada
			final Estoque estRef = (!entrada.isNew()) ? eao.find(ex) : null;
			
			if(estRef!=null) {
				// Alteracao da entrada, isto é, possui entrada e estoque na base
				// recupera estoque anterior
				Estoque estAnterior = eao.getAnterior(estRef.getId(), estRef.getCozinha());
				// recupera os estoques da cozinha gerados a posteriore, se houver
				List<Estoque> post = eao.getPosteriores(estRef.getId(), estRef.getCozinha());
				//separa os itens a serem analisados
				List<EstoqueItem> iEstAtual = estRef.getItens();
				List<EntradaItem> iEntAtual = entrada.getItens();
				List<EntradaItem> iEntSaved = entSaved.getItens();
				//itens removidos que precisam ser subtraidos do estoque
				List<EntradaItem> iEntRem = ListUtils.removeAll(iEntSaved, iEntAtual);
				//itens adicionados que precisam ser incrementados no estoque
				List<EntradaItem> iEntNew = ListUtils.removeAll(iEntAtual, iEntSaved);
				//itens salvos co-existentes que precisam ser analisados 
				List<EntradaItem> iEntRetSav = ListUtils.retainAll(iEntSaved, iEntAtual);
				
				final Map<Produto, Integer> values = new HashMap<>();
				prods.forEach(p -> {
					
					EntradaItem ei = new EntradaItem(p);
					if(iEntNew!=null && iEntNew.contains(ei)) {
						ei = iEntNew.get(iEntNew.indexOf(ei));
						//recupera estoque item 
						EstoqueItem estItemAtual = new EstoqueItem(p);
						if(iEstAtual.contains(estItemAtual)) {
							estItemAtual = iEstAtual.get(iEstAtual.indexOf(estItemAtual));
						}else {
							estItemAtual.setEstoque(estRef);
							
						}
						//recupera estoque config 
						EstoqueConfig ec = null;
						if(eCon!=null && !eCon.isEmpty()) {
							for(EstoqueConfig e : eCon) {
								if(e.getProduto().getCodigo().equals(p.getCodigo())) {
									ec = e;
									break;
								}
							}
						}
						
						Integer qtd = ei.getQuantidade();
						Integer qtdCalc = estItemAtual.sumQuantidade(qtd);
						
						if(ec==null) {
							ec = new EstoqueConfig();
							ec.setProduto(p);
							ec.setCozinha(entrada.getCozinha());
							ec.setQtdInicial(qtdCalc);
							ec.setQtdMinima(10);
						}
						
						
					}
					
					
				});
				
				
			}else {
				// Primeira vez que a entrada é processada
				//estRef = eao.getUltimo(entrada.getCozinha());
			
				/*if(estRef==null) {
					estRef = ex;
					
				}*/
			}
		}else {
			//cozinha alterada
		}
	}
	public static void main(String[] args) {
		
		EntradaItem ei1 = new EntradaItem(new Produto("1"));
		EntradaItem ei2 = new EntradaItem(new Produto("2"));
		EntradaItem ei3 = new EntradaItem(new Produto("3"));
		EntradaItem ei4 = new EntradaItem(new Produto("4"));
		EntradaItem eis2 = new EntradaItem(new Produto("2"));
		EntradaItem eis3 = new EntradaItem(new Produto("3"));
		EntradaItem eis5 = new EntradaItem(new Produto("5"));
		
		eis2.setQuantidade(10);
		eis3.setQuantidade(10);
		
		List<EntradaItem> iEntAtual = Arrays.asList(ei1,ei2,ei3,ei4);
		List<EntradaItem> iEntSaved = Arrays.asList(eis2,eis3,eis5);
		//itens removidos que precisam ser subtraidos do estoque
		List<EntradaItem> iEntRem = ListUtils.removeAll(iEntSaved, iEntAtual);
		//itens adicionados que precisam ser incrementados no estoque
		List<EntradaItem> iEntNew = ListUtils.removeAll(iEntAtual, iEntSaved);
		//itens salvos co-existentes que precisam ser analisados 
		List<EntradaItem> iEntRetSav = ListUtils.retainAll(iEntSaved, iEntAtual);
		
		System.out.println("Novos -> "+iEntNew);
		System.out.println("Rem -> "+iEntRem);
		System.out.println("Retidos -> "+iEntRetSav);
	}

}
