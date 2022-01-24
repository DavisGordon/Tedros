/**
 * 
 */
package org.somos.server.estoque.bo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import org.apache.commons.collections4.ListUtils;
import org.somos.model.Cozinha;
import org.somos.model.Entrada;
import org.somos.model.Estocavel;
import org.somos.model.EstocavelItem;
import org.somos.model.Estoque;
import org.somos.model.EstoqueConfig;
import org.somos.model.EstoqueItem;
import org.somos.model.Produto;
import org.somos.server.estoque.eao.EstoqueEAO;

import com.tedros.ejb.base.bo.TGenericBO;

/**
 * @author Davis Gordon
 *
 */
public class EstocavelExecutor<T extends Estocavel, I extends EstocavelItem> {

	private TGenericBO<T> estocavelBO;
	
	private EstoqueBO estoqueBO;
	private EstoqueConfigBO estConBO;
	private ProdutoBO prodBO;	
	
	/**
	 * @param estocavelBO
	 * @param estoqueBO
	 * @param estConBO
	 * @param prodBO
	 */
	public EstocavelExecutor(TGenericBO<T> estocavelBO, EstoqueBO estoqueBO, EstoqueConfigBO estConBO,
			ProdutoBO prodBO) {
		this.estocavelBO = estocavelBO;
		this.estoqueBO = estoqueBO;
		this.estConBO = estConBO;
		this.prodBO = prodBO;
	}


	public void removerEstoque(T estocavel, T estocavelSaved, Supplier<T> estocavelSupplier, Supplier<I> estocavelItemSupplier) throws Exception {
		
		gerarEstoque(estocavel, estocavelSaved, estocavelSupplier, estocavelItemSupplier);
		
		T target = estocavel;
		Cozinha cozX = estocavel.getCozinha();
		
		if(!estocavel.isNew()) {
			target = estocavelSupplier.get();
			target.setId(estocavel.getId());
			
			cozX = new Cozinha();
			cozX.setId(estocavel.getCozinha().getId());
		}
		
		Estoque ex = new Estoque(target);
		ex.setCozinha(cozX);
		
		// remove
		Estoque est = (!estocavel.isNew()) ? estoqueBO.find(ex) : null;
		if(est!=null)
			estoqueBO.remove(est);
	}
	

	@SuppressWarnings("unchecked")
	public void gerarEstoque(T estocavel, T estocavelOld, Supplier<T> estocavelSupplier, Supplier<I> estocavelItemSupplier) throws Exception {
		
		EstoqueEAO eao = estoqueBO.getEao();
		
		if(estocavelOld==null && !estocavel.isNew())
			estocavelOld = estocavelBO.findById(estocavel);
		
		List<Produto> produtos = prodBO.listAll(Produto.class);
		List<EstoqueConfig> estoqueConfigList = estConBO.list(estocavel.getCozinha());
		
		T estocavelExample = estocavel;
		Cozinha cozX = estocavel.getCozinha();
		
		if(!estocavel.isNew()) {
			estocavelExample = estocavelSupplier.get();
			estocavelExample.setId(estocavel.getId());
			
			cozX = new Cozinha();
			cozX.setId(estocavel.getCozinha().getId());
		}
		
		Estoque estoqueExample = new Estoque(estocavelExample);
		estoqueExample.setCozinha(cozX);
		
		// estoque gerado para o estocavel
		Estoque estoqueRef = (!estocavel.isNew()) ? eao.find(estoqueExample) : null;
		
		if(estoqueRef!=null) {
			// Alteracao da entrada/saida, isto é, possui estoque associado na base
			// recupera estoque anterior
			Estoque estAnterior = eao.getAnterior(estoqueRef.getId(), estoqueRef.getCozinha());
			// recupera os estoques da cozinha gerados a posteriore, se houver
			List<Estoque> post = eao.getPosteriores(estoqueRef.getId(), estoqueRef.getCozinha());
			//separa os itens a serem analisados
			final List<EstoqueItem> iEstAtual = estoqueRef.getItens();
			final List<? extends EstocavelItem> estocavelItens = estocavel.getItens();
			final List<? extends EstocavelItem> estocavelItensOld = estocavelOld.getItens();
			//itens removidos que precisam ser subtraidos do estoque
			final List<? extends EstocavelItem> itensRemovidos = ListUtils.removeAll(estocavelItensOld, estocavelItens);
			//itens adicionados que precisam ser incrementados no estoque
			final List<? extends EstocavelItem> itensNovos = ListUtils.removeAll(estocavelItens, estocavelItensOld);
			//itens salvos co-existentes que precisam ser analisados 
			final List<? extends EstocavelItem> itensRetidos = ListUtils.retainAll(estocavelItensOld, estocavelItens);
			
			I estocavelItem = estocavelItemSupplier.get();
			final Map<Produto, Integer> values = new HashMap<>();
			
			for(Produto p : produtos) {
				
				estocavelItem.setProduto(p);
				
				// item novo
				if(itensNovos!=null && itensNovos.contains(estocavelItem)) {
					I estocavelItemNovo = (I) itensNovos.get(itensNovos.indexOf(estocavelItem));
					//recupera estoque item 
					EstoqueItem estItemAtual = getEstoqueItemAtual(estoqueRef, iEstAtual, p);
					//recupera estoque config 
					EstoqueConfig ec = getEstoqueConfig(estoqueConfigList, p);
					
					Integer qtd = 0;
					
					if(estAnterior!=null) {
						//recupera estoque item anterior
						EstoqueItem estItemAnt = new EstoqueItem(p);
						if(estAnterior.getItens().contains(estItemAnt)) {
							estItemAnt = estAnterior.getItens().get(estAnterior.getItens().indexOf(estItemAnt));
							qtd = estItemAtual.calcularESomar(estocavelItemNovo, estItemAnt);
						}
					}else if(ec!=null) {
						qtd = estItemAtual.calcularESomar(estocavelItemNovo, ec);
					} else {
						qtd = estItemAtual.calcular(estocavelItemNovo);
						Integer qtdInicial = estItemAtual.somar(estocavelItemNovo);
						ec = new EstoqueConfig();
						ec.setProduto(p);
						ec.setCozinha(estocavel.getCozinha());
						ec.setQtdInicial(qtdInicial);
						ec.setQtdMinima(10);
						estConBO.save(ec);
					}
					
					estItemAtual.setProduto(p);
					estItemAtual.setQtdMinima(ec.getQtdMinima());
					values.put(p, qtd);
					continue;
				}
				
				// item retido
				if(itensRetidos!=null && itensRetidos.contains(estocavelItem)) {
					I eirt = (I) itensRetidos.get(itensRetidos.indexOf(estocavelItem));
					//recupera estoque item 
					EstoqueItem estItemAtual = getEstoqueItemAtual(estoqueRef, iEstAtual, p);
					//recupera estoque config 
					EstoqueConfig ec = getEstoqueConfig(estoqueConfigList, p);
					
					I eia = (I) estocavelItens.get(estocavelItens.indexOf(estocavelItem));
					
					if(eia.getQuantidade().equals(eirt.getQuantidade()))
						continue;
					
					Integer qtd = estItemAtual.calcular(eia, eirt);
					Integer qtdInicial = estItemAtual.somar(eia, eirt);
					
					if(ec==null){
						ec = new EstoqueConfig();
						ec.setProduto(p);
						ec.setCozinha(estocavel.getCozinha());
						ec.setQtdInicial(qtdInicial);
						ec.setQtdMinima(10);
						estConBO.save(ec);
					}
					estItemAtual.setQtdMinima(ec.getQtdMinima());
					values.put(p, qtd);
					continue;
				}
				
				//itens removidos
				if(itensRemovidos!=null && itensRemovidos.contains(estocavelItem)) {
					I eir = (I) itensRemovidos.get(itensRemovidos.indexOf(estocavelItem));
					//recupera estoque item 
					EstoqueItem estItemAtual = new EstoqueItem(p);
					if(iEstAtual.contains(estItemAtual)) {
						estItemAtual = iEstAtual.get(iEstAtual.indexOf(estItemAtual));
						Integer qtd = estItemAtual.calcularESubtrair(eir);
						values.put(p, qtd);
					}
				}
				
			}
			
			estoqueBO.save(estoqueRef);
			
			atualizarEstoquePosteriores(post, values);
			
		}else {
			// Primeira vez que a entrada/saida é processada
			// recupera o ultimo estoque anterior a data
			estoqueRef = eao.getUltimo(estocavel.getCozinha(), estocavel.getData());
			List<EstoqueItem> itens = (estoqueRef==null) ? null : estoqueRef.getItens();
			
			List<Estoque> post = eao.getPosteriores(estocavel.getCozinha(), estocavel.getData());
			final Map<Produto, Integer> values = new HashMap<>();
			
			Estoque estoqueNovo = new Estoque(estocavel);
			estoqueNovo.setData(estocavel.getData());
			estoqueNovo.setCozinha(estocavel.getCozinha());
			estoqueNovo.setObservacao(estocavel.getObservacaoEstoque());
			List<? extends EstocavelItem> estocavelItens = estocavel.getItens();
			I estocavelItem = estocavelItemSupplier.get();
			for(Produto p : produtos) {
				
				Integer qtd = 0;
				Integer qtdInic = 0;
				
				// estoque item 
				EstoqueItem estItemAtual = new EstoqueItem();
				
				estocavelItem.setProduto(p);
				
				// item novo
				if(estocavelItens!=null && estocavelItens.contains(estocavelItem)) {
					I ei = (I) estocavelItens.get(estocavelItens.indexOf(estocavelItem));
					qtd = estItemAtual.calcular(ei);
					values.put(p, qtd);
				}
				
				boolean firstTime = true;
				if(itens!=null) {
					EstoqueItem eItem = new EstoqueItem(p);
					if(itens.contains(eItem)) {
						eItem = itens.get(itens.indexOf(eItem));
						qtdInic = eItem.getVlrAjustado();
						qtd += qtdInic;
						firstTime = false;
					}
				}
				//recupera estoque config 
				EstoqueConfig ec = getEstoqueConfig(estoqueConfigList, p);
				if(ec!=null) {
					if(firstTime) {
						qtdInic = ec.getQtdInicial();
						qtd += qtdInic;
					}
					estItemAtual.addQuantidade(qtd);
					estItemAtual.setQtdInicial(qtdInic);
				} else {
					qtdInic = estItemAtual.addQuantidade(qtd);
					ec = new EstoqueConfig();
					ec.setProduto(p);
					ec.setCozinha(estocavel.getCozinha());
					ec.setQtdInicial(qtdInic);
					ec.setQtdMinima(10);
					estConBO.save(ec);
				}
				
				estItemAtual.setProduto(p);
				estItemAtual.setQtdMinima(ec.getQtdMinima());
				
				estoqueNovo.addItem(estItemAtual);
				
			}
			
			estoqueBO.save(estoqueNovo);
			
			atualizarEstoquePosteriores(post, values);
		}
	}


	private void atualizarEstoquePosteriores(List<Estoque> post, final Map<Produto, Integer> values) throws Exception {
		if(post!=null && !post.isEmpty()) {
		
			for(Estoque e : post) {
				for(Produto p : values.keySet()) {
					Integer qtd = values.get(p);
					EstoqueItem ei = new EstoqueItem(p);
					if(e.getItens().contains(ei)) {
						final EstoqueItem ei2 = e.getItens().get(e.getItens().indexOf(ei));
						ei2.setQtdInicial(ei2.getQtdInicial() + qtd);
						ei2.addQuantidade(qtd);
					}
				}
				estoqueBO.save(e);
			}
		}
	}

	
	private EstoqueItem getEstoqueItemAtual(Estoque estRef, List<EstoqueItem> iEstAtual, Produto p) {
		EstoqueItem estItemAtual = new EstoqueItem(p);
		if(iEstAtual.contains(estItemAtual)) {
			estItemAtual = iEstAtual.get(iEstAtual.indexOf(estItemAtual));
		}else {
			estItemAtual.setEstoque(estRef);
			iEstAtual.add(estItemAtual);
		}
		return estItemAtual;
	}

	private EstoqueConfig getEstoqueConfig(List<EstoqueConfig> eCon, Produto p) {
		EstoqueConfig ec = null;
		if(eCon!=null && !eCon.isEmpty()) {
			for(EstoqueConfig e : eCon) {
				if(e.getProduto().getCodigo().equals(p.getCodigo())) {
					ec = e;
					break;
				}
			}
		}
		return ec;
	}
	
}
