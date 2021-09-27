/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.server.estoque.service;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.covidsemfome.model.Entrada;
import com.covidsemfome.model.EntradaItem;
import com.covidsemfome.report.model.EstocavelReportModel;
import com.covidsemfome.server.estoque.bo.EntradaBO;
import com.covidsemfome.server.estoque.bo.EstocavelExecutor;
import com.covidsemfome.server.estoque.bo.EstoqueBO;
import com.covidsemfome.server.estoque.bo.EstoqueConfigBO;
import com.covidsemfome.server.estoque.bo.ProdutoBO;
import com.tedros.ejb.base.service.TEjbService;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@Local
@Stateless(name="EntradaService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class EntradaService extends TEjbService<Entrada> {
	
	@Inject
	private EntradaBO bo;
	
	@Inject
	private EstoqueBO estoqueBO;
	
	@Inject
	private EstoqueConfigBO estConBO;
	
	@Inject
	private ProdutoBO prodBO;
	
	@EJB
	private EstoqueService estServ;
	
	@Override
	public EntradaBO getBussinesObject() {
		return bo;
	}
	
	public EstocavelReportModel pesquisar(EstocavelReportModel m){
		return bo.pesquisar(m);
	}
	
	@Override
	@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
	public void remove(Entrada entidade) throws Exception {
		EstocavelExecutor<Entrada, EntradaItem> executor = new EstocavelExecutor<>(bo, estoqueBO, estConBO, prodBO);
		entidade.setItens(new ArrayList<>());
		executor.removerEstoque(entidade, null, () -> {return new Entrada();}, () -> {return new EntradaItem();});

		bo.remove(entidade);
	}
	
	@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
	public Entrada save(Entrada entrada, Entrada entradaOld) throws Exception {
		
		EstocavelExecutor<Entrada, EntradaItem> executor = new EstocavelExecutor<>(bo, estoqueBO, estConBO, prodBO);
		
		
		if(entradaOld!=null && (!entradaOld.getCozinha().getId().equals(entrada.getCozinha().getId())
					|| !entradaOld.getData().equals(entrada.getData()))) {
			entradaOld.setItens(new ArrayList<>());
			executor.removerEstoque(entradaOld, null, () -> {return new Entrada();}, () -> {return new EntradaItem();});
		}
		Entrada res = super.save(entrada);
		
		executor.gerarEstoque(entrada, entradaOld, () -> {return new Entrada();}, () -> {return new EntradaItem();});
		return res;
	}
	

}
