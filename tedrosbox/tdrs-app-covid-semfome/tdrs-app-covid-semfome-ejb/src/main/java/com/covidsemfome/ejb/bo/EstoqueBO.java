/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.ejb.bo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.commons.collections4.ListUtils;

import com.covidsemfome.ejb.eao.EstoqueEAO;
import com.covidsemfome.model.Cozinha;
import com.covidsemfome.model.Entrada;
import com.covidsemfome.model.EntradaItem;
import com.covidsemfome.model.Estoque;
import com.covidsemfome.model.EstoqueConfig;
import com.covidsemfome.model.EstoqueItem;
import com.covidsemfome.model.Produto;
import com.covidsemfome.model.Saida;
import com.covidsemfome.model.SaidaItem;
import com.covidsemfome.report.model.EstoqueModel;
import com.covidsemfome.report.model.EstoqueReportModel;
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
	private SaidaBO prdBO;
	
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
							ei2.sumQuantidade(qtd);
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
	
	public void removerEstoque(Saida producao) throws Exception {
		
		producao.setItens(new ArrayList<>());
		gerarEstoque(producao);
		
		Saida prdX = producao;
		Cozinha cozX = producao.getCozinha();
		
		if(!producao.isNew()) {
			prdX = new Saida();
			prdX.setId(producao.getId());
			
			cozX = new Cozinha();
			cozX.setId(producao.getCozinha().getId());
		}
		
		Estoque ex = new Estoque();
		ex.setSaidaRef(prdX);
		ex.setCozinha(cozX);
		
		// estoque gerado para as cozinha da producao
		Estoque est = (!producao.isNew()) ? eao.find(ex) : null;
		if(est!=null)
			super.remove(est);
	}
	
	
	public void gerarEstoque(Saida producao) throws Exception {
		Saida prdSaved = null; 
		boolean isCozAlterada = false;
		if(!producao.isNew()) {
			prdSaved = prdBO.findById(producao);
			isCozAlterada = !prdSaved.getCozinha().getId().equals(producao.getCozinha().getId());
			
		}
		
		if(isCozAlterada)
			this.removerEstoque(prdSaved);
		
		this.gerarEstoque(producao, prdSaved);
	}

	private void gerarEstoque(Saida producao, Saida prdSaved) throws Exception {
		// Cozinha nao foi alterada
		List<Produto> prods = prodBO.listAll(Produto.class);
		List<EstoqueConfig> eCon = estConBO.list(producao.getCozinha());
		Saida prdX = producao;
		Cozinha cozX = producao.getCozinha();
		
		if(!producao.isNew()) {
			prdX = new Saida();
			prdX.setId(producao.getId());
			
			cozX = new Cozinha();
			cozX.setId(producao.getCozinha().getId());
		}
		
		Estoque ex = new Estoque();
		ex.setSaidaRef(prdX);
		ex.setCozinha(cozX);
		
		// estoque gerado para as cozinha da producao
		Estoque estRef = (!producao.isNew()) ? eao.find(ex) : null;
		
		if(estRef!=null) {
			// Alteracao da producao, isto é, possui producao e estoque na base
			// recupera estoque anterior
			Estoque estAnterior = eao.getAnterior(estRef.getId(), estRef.getCozinha());
			// recupera os estoques da cozinha gerados a posteriore, se houver
			List<Estoque> post = eao.getPosteriores(estRef.getId(), estRef.getCozinha());
			//separa os itens a serem analisados
			final List<EstoqueItem> iEstAtual = estRef.getItens();
			final List<SaidaItem> iPrdAtual = producao.getItens();
			final List<SaidaItem> iPrdSaved = prdSaved.getItens();
			//itens removidos que precisam ser subtraidos do estoque
			final List<SaidaItem> iPrdRem = ListUtils.removeAll(iPrdSaved, iPrdAtual);
			//itens adicionados que precisam ser incrementados no estoque
			final List<SaidaItem> iPrdNew = ListUtils.removeAll(iPrdAtual, iPrdSaved);
			//itens salvos co-existentes que precisam ser analisados 
			final List<SaidaItem> iPrdRetSav = ListUtils.retainAll(iPrdSaved, iPrdAtual);
			
			final Map<Produto, Integer> values = new HashMap<>();
			for(Produto p : prods) {
				// item novo
				SaidaItem pi = new SaidaItem(p);
				if(iPrdNew!=null && iPrdNew.contains(pi)) {
					pi = iPrdNew.get(iPrdNew.indexOf(pi));
					//recupera estoque item 
					EstoqueItem estItemAtual = getEstoqueItemAtual(estRef, iEstAtual, p);
					//recupera estoque config 
					EstoqueConfig ec = getEstoqueConfig(eCon, p);
					
					Integer qtd = pi.getQuantidade() * -1;
					
					if(estAnterior!=null) {
						//recupera estoque item anterior
						EstoqueItem estItemAnt = new EstoqueItem(p);
						if(estAnterior.getItens().contains(estItemAnt)) {
							estItemAnt = estAnterior.getItens().get(estAnterior.getItens().indexOf(estItemAnt));
							estItemAtual.sumQuantidade(estItemAnt.getVlrAjustado() + qtd);
							estItemAtual.setQtdInicial(estItemAnt.getVlrAjustado());
						}
					}else if(ec!=null) {
						estItemAtual.sumQuantidade(qtd + ec.getQtdInicial());
						estItemAtual.setQtdInicial(ec.getQtdInicial());
					} else {
						Integer qtdCalc = estItemAtual.sumQuantidade(qtd);
						ec = new EstoqueConfig();
						ec.setProduto(p);
						ec.setCozinha(producao.getCozinha());
						ec.setQtdInicial(qtdCalc);
						ec.setQtdMinima(10);
						estConBO.save(ec);
					}
					
					estItemAtual.setProduto(p);
					estItemAtual.setQtdMinima(ec.getQtdMinima());
					values.put(p, qtd);
					continue;
				}
				
				// item retido
				SaidaItem pirt = new SaidaItem(p);
				if(iPrdRetSav!=null && iPrdRetSav.contains(pirt)) {
					pirt = iPrdRetSav.get(iPrdRetSav.indexOf(pirt));
					//recupera estoque item 
					EstoqueItem estItemAtual = getEstoqueItemAtual(estRef, iEstAtual, p);
					//recupera estoque config 
					EstoqueConfig ec = getEstoqueConfig(eCon, p);
					
					SaidaItem pia = new SaidaItem(p);
					pia = iPrdAtual.get(iPrdAtual.indexOf(pia));
					
					if(pia.getQuantidade().equals(pirt.getQuantidade()))
						continue;
					
					Integer qtd = (pia.getQuantidade() - pirt.getQuantidade()) * -1;
					
					if(ec==null){
						Integer qtdCalc = estItemAtual.sumQuantidade(qtd);
						ec = new EstoqueConfig();
						ec.setProduto(p);
						ec.setCozinha(producao.getCozinha());
						ec.setQtdInicial(qtdCalc);
						ec.setQtdMinima(10);
						estConBO.save(ec);
					}else
						estItemAtual.sumQuantidade(qtd);
					
					estItemAtual.setQtdMinima(ec.getQtdMinima());
					values.put(p, qtd);
					continue;
				}
				
				//itens removidos
				SaidaItem pir = new SaidaItem(p);
				if(iPrdRem!=null && iPrdRem.contains(pir)) {
					pir = iPrdRem.get(iPrdRem.indexOf(pir));
					//recupera estoque item 
					EstoqueItem estItemAtual = new EstoqueItem(p);
					if(iEstAtual.contains(estItemAtual)) {
						estItemAtual = iEstAtual.get(iEstAtual.indexOf(estItemAtual));
						Integer qtd = pir.getQuantidade();
						estItemAtual.sumQuantidade(qtd);
						values.put(p, qtd);
					}
				}
				
			}
			
			super.save(estRef);
			
			if(post!=null && !post.isEmpty()) {
				
				for(Estoque e : post) {
					for(Produto p : values.keySet()) {
						Integer qtd = values.get(p);
						EstoqueItem ei = new EstoqueItem(p);
						if(e.getItens().contains(ei)) {
							final EstoqueItem ei2 = e.getItens().get(e.getItens().indexOf(ei));
							ei2.setQtdInicial(ei2.getQtdInicial() + qtd);
							ei2.sumQuantidade(qtd);
						}
					}
					super.save(e);
				}
				
			}
			
		}else {
			// Primeira vez que a producao é processada
			estRef = eao.getUltimo(producao.getCozinha(), producao.getData());
			List<EstoqueItem> itens = (estRef==null) ? null : estRef.getItens();
			ex.setData(new Date());
			List<SaidaItem> iPrdAtual = producao.getItens();
			for(Produto p : prods) {
				Integer qtd = 0;
				Integer qtdInic = 0;
				// item novo
				SaidaItem pi = new SaidaItem(p);
				if(iPrdAtual!=null && iPrdAtual.contains(pi)) {
					pi = iPrdAtual.get(iPrdAtual.indexOf(pi));
					qtd = pi.getQuantidade() * -1;
				}
				// estoque item 
				EstoqueItem estItemAtual = new EstoqueItem();
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
				EstoqueConfig ec = getEstoqueConfig(eCon, p);
				if(ec!=null) {
					if(firstTime) {
						qtdInic = ec.getQtdInicial();
						qtd += qtdInic;
					}
					estItemAtual.sumQuantidade(qtd);
					estItemAtual.setQtdInicial(qtdInic);
				} else {
					qtdInic = estItemAtual.sumQuantidade(qtd);
					ec = new EstoqueConfig();
					ec.setProduto(p);
					ec.setCozinha(producao.getCozinha());
					ec.setQtdInicial(qtdInic);
					ec.setQtdMinima(10);
					estConBO.save(ec);
				}
				estItemAtual.setProduto(p);
				estItemAtual.setQtdMinima(ec.getQtdMinima());
				ex.addItem(estItemAtual);
			}
			super.save(ex);
		}
	}
	
	public void removerEstoque(Entrada entrada, Entrada entSaved) throws Exception {
		
		entrada.setItens(new ArrayList<>());
		gerarEstoque(entrada, entSaved);
		
		Entrada entX = entrada;
		Cozinha cozX = entrada.getCozinha();
		
		if(!entrada.isNew()) {
			entX = new Entrada();
			entX.setId(entrada.getId());
			
			cozX = new Cozinha();
			cozX.setId(entrada.getCozinha().getId());
		}
		
		Estoque ex = new Estoque();
		ex.setEntradaRef(entX);
		ex.setCozinha(cozX);
		
		// estoque gerado para as cozinha da entrada
		Estoque est = (!entrada.isNew()) ? eao.find(ex) : null;
		if(est!=null)
			super.remove(est);
	}
	
	public void gerarEstoque(Entrada entrada, Entrada entSaved) throws Exception {
		
		if(entSaved==null && !entrada.isNew())
			entSaved = entBO.findById(entrada);
		
		List<Produto> prods = prodBO.listAll(Produto.class);
		List<EstoqueConfig> eCon = estConBO.list(entrada.getCozinha());
		
		Entrada entX = entrada;
		Cozinha cozX = entrada.getCozinha();
		
		if(!entrada.isNew()) {
			entX = new Entrada();
			entX.setId(entrada.getId());
			
			cozX = new Cozinha();
			cozX.setId(entrada.getCozinha().getId());
		}
		
		Estoque ex = new Estoque();
		ex.setEntradaRef(entX);
		ex.setCozinha(cozX);
		
		// estoque gerado para as cozinha da entrada
		Estoque estRef = (!entrada.isNew()) ? eao.find(ex) : null;
		
		if(estRef!=null) {
			// Alteracao da entrada, isto é, possui entrada e estoque na base
			// recupera estoque anterior
			Estoque estAnterior = eao.getAnterior(estRef.getId(), estRef.getCozinha());
			// recupera os estoques da cozinha gerados a posteriore, se houver
			List<Estoque> post = eao.getPosteriores(estRef.getId(), estRef.getCozinha());
			//separa os itens a serem analisados
			final List<EstoqueItem> iEstAtual = estRef.getItens();
			final List<EntradaItem> iEntAtual = entrada.getItens();
			final List<EntradaItem> iEntSaved = entSaved.getItens();
			//itens removidos que precisam ser subtraidos do estoque
			final List<EntradaItem> iEntRem = ListUtils.removeAll(iEntSaved, iEntAtual);
			//itens adicionados que precisam ser incrementados no estoque
			final List<EntradaItem> iEntNew = ListUtils.removeAll(iEntAtual, iEntSaved);
			//itens salvos co-existentes que precisam ser analisados 
			final List<EntradaItem> iEntRetSav = ListUtils.retainAll(iEntSaved, iEntAtual);
			
			final Map<Produto, Integer> values = new HashMap<>();
			for(Produto p : prods) {
				// item novo
				EntradaItem ei = new EntradaItem(p);
				if(iEntNew!=null && iEntNew.contains(ei)) {
					ei = iEntNew.get(iEntNew.indexOf(ei));
					//recupera estoque item 
					EstoqueItem estItemAtual = getEstoqueItemAtual(estRef, iEstAtual, p);
					//recupera estoque config 
					EstoqueConfig ec = getEstoqueConfig(eCon, p);
					
					Integer qtd = ei.getQuantidade();
					
					if(estAnterior!=null) {
						//recupera estoque item anterior
						EstoqueItem estItemAnt = new EstoqueItem(p);
						if(estAnterior.getItens().contains(estItemAnt)) {
							estItemAnt = estAnterior.getItens().get(estAnterior.getItens().indexOf(estItemAnt));
							estItemAtual.sumQuantidade(qtd);
							estItemAtual.setQtdInicial(estItemAnt.getVlrAjustado());
						}
					}else if(ec!=null) {
						estItemAtual.sumQuantidade(qtd + ec.getQtdInicial());
						estItemAtual.setQtdInicial(ec.getQtdInicial());
					} else {
						Integer qtdCalc = estItemAtual.sumQuantidade(qtd);
						ec = new EstoqueConfig();
						ec.setProduto(p);
						ec.setCozinha(entrada.getCozinha());
						ec.setQtdInicial(qtdCalc);
						ec.setQtdMinima(10);
						estConBO.save(ec);
					}
					
					estItemAtual.setProduto(p);
					estItemAtual.setQtdMinima(ec.getQtdMinima());
					values.put(p, qtd);
					continue;
				}
				
				// item retido
				EntradaItem eirt = new EntradaItem(p);
				if(iEntRetSav!=null && iEntRetSav.contains(eirt)) {
					eirt = iEntRetSav.get(iEntRetSav.indexOf(eirt));
					//recupera estoque item 
					EstoqueItem estItemAtual = getEstoqueItemAtual(estRef, iEstAtual, p);
					//recupera estoque config 
					EstoqueConfig ec = getEstoqueConfig(eCon, p);
					
					EntradaItem eia = new EntradaItem(p);
					eia = iEntAtual.get(iEntAtual.indexOf(eia));
					
					if(eia.getQuantidade().equals(eirt.getQuantidade()))
						continue;
					
					Integer qtd = eia.getQuantidade() - eirt.getQuantidade();
					
					if(ec==null){
						Integer qtdCalc = estItemAtual.sumQuantidade(qtd);
						ec = new EstoqueConfig();
						ec.setProduto(p);
						ec.setCozinha(entrada.getCozinha());
						ec.setQtdInicial(qtdCalc);
						ec.setQtdMinima(10);
						estConBO.save(ec);
					}else
						estItemAtual.sumQuantidade(qtd);
					
					estItemAtual.setQtdMinima(ec.getQtdMinima());
					values.put(p, qtd);
					continue;
				}
				
				//itens removidos
				EntradaItem eir = new EntradaItem(p);
				if(iEntRem!=null && iEntRem.contains(eir)) {
					eir = iEntRem.get(iEntRem.indexOf(eir));
					//recupera estoque item 
					EstoqueItem estItemAtual = new EstoqueItem(p);
					if(iEstAtual.contains(estItemAtual)) {
						estItemAtual = iEstAtual.get(iEstAtual.indexOf(estItemAtual));
						Integer qtd = eir.getQuantidade();
						estItemAtual.subtractQuantidade(qtd);
						values.put(p, qtd * -1);
					}
				}
				
			}
			
			super.save(estRef);
			
			if(post!=null && !post.isEmpty()) {
				
				for(Estoque e : post) {
					for(Produto p : values.keySet()) {
						Integer qtd = values.get(p);
						EstoqueItem ei = new EstoqueItem(p);
						if(e.getItens().contains(ei)) {
							final EstoqueItem ei2 = e.getItens().get(e.getItens().indexOf(ei));
							ei2.setQtdInicial(ei2.getQtdInicial() + qtd);
							ei2.sumQuantidade(qtd);
						}
					}
					super.save(e);
				}
				
			}
			
		}else {
			// Primeira vez que a entrada é processada
			// recupera os estoques da cozinha gerados a posteriore, se houver
			estRef = eao.getUltimo(entrada.getCozinha(), entrada.getData());
			
			List<Estoque> post = eao.getPosteriores(entrada.getCozinha(), entrada.getData());
			final Map<Produto, Integer> values = new HashMap<>();
			
			List<EstoqueItem> itens = (estRef==null) ? null : estRef.getItens();
			ex.setData(entrada.getData());
			ex.setEntradaRef(entrada);
			ex.setCozinha(entrada.getCozinha());
			List<EntradaItem> iEntAtual = entrada.getItens();
			for(Produto p : prods) {
				Integer qtd = 0;
				Integer qtdInic = 0;
				// item novo
				EntradaItem ei = new EntradaItem(p);
				if(iEntAtual!=null && iEntAtual.contains(ei)) {
					ei = iEntAtual.get(iEntAtual.indexOf(ei));
					qtd = ei.getQuantidade();
					values.put(p, qtd);
				}
				// estoque item 
				EstoqueItem estItemAtual = new EstoqueItem();
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
				EstoqueConfig ec = getEstoqueConfig(eCon, p);
				if(ec!=null) {
					if(firstTime) {
						qtdInic = ec.getQtdInicial();
						qtd += qtdInic;
					}
					estItemAtual.sumQuantidade(qtd);
					estItemAtual.setQtdInicial(qtdInic);
				} else {
					qtdInic = estItemAtual.sumQuantidade(qtd);
					ec = new EstoqueConfig();
					ec.setProduto(p);
					ec.setCozinha(entrada.getCozinha());
					ec.setQtdInicial(qtdInic);
					ec.setQtdMinima(10);
					estConBO.save(ec);
				}
				estItemAtual.setProduto(p);
				estItemAtual.setQtdMinima(ec.getQtdMinima());
				ex.addItem(estItemAtual);
				
			}
			
			super.save(ex);
			
			if(post!=null && !post.isEmpty()) {
			
				for(Estoque e : post) {
					for(Produto p : values.keySet()) {
						Integer qtd = values.get(p);
						EstoqueItem ei = new EstoqueItem(p);
						if(e.getItens().contains(ei)) {
							final EstoqueItem ei2 = e.getItens().get(e.getItens().indexOf(ei));
							ei2.setQtdInicial(ei2.getQtdInicial() + qtd);
							ei2.sumQuantidade(qtd);
						}
					}
					super.save(e);
				}
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
