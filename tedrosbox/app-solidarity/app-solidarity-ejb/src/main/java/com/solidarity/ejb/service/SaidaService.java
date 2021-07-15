/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.solidarity.ejb.service;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.solidarity.ejb.bo.EstocavelExecutor;
import com.solidarity.ejb.bo.EstoqueBO;
import com.solidarity.ejb.bo.EstoqueConfigBO;
import com.solidarity.ejb.bo.ProdutoBO;
import com.solidarity.ejb.bo.SaidaBO;
import com.solidarity.model.Saida;
import com.solidarity.model.SaidaItem;
import com.tedros.ejb.base.service.TEjbService;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@Local
@Stateless(name="SaidaService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class SaidaService extends TEjbService<Saida> {
	
	@Inject
	private EstoqueBO estoqueBO;
	
	@Inject
	private EstoqueConfigBO estConBO;
	
	@Inject
	private ProdutoBO prodBO;
	
	@EJB
	private EstoqueService estServ;
	
	@Inject
	private SaidaBO bo;
	
	@Override
	public SaidaBO getBussinesObject() {
		return bo;
	}
	
	@Override
	@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
	public void remove(Saida entidade) throws Exception {
		EstocavelExecutor<Saida, SaidaItem> executor = new EstocavelExecutor<>(bo, estoqueBO, estConBO, prodBO);
		entidade.setItens(new ArrayList<>());
		executor.removerEstoque(entidade, null, () -> {return new Saida();}, () -> {return new SaidaItem();});

		bo.remove(entidade);
	}
	
	@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
	public Saida save(Saida saida, Saida saidaOld) throws Exception {
		
		EstocavelExecutor<Saida, SaidaItem> executor = new EstocavelExecutor<>(bo, estoqueBO, estConBO, prodBO);
		
		
		if(saidaOld!=null && (!saidaOld.getCozinha().getId().equals(saida.getCozinha().getId())
					|| !saidaOld.getData().equals(saida.getData()))) {
			saidaOld.setItens(new ArrayList<>());
			executor.removerEstoque(saidaOld, null, () -> {return new Saida();}, () -> {return new SaidaItem();});
		}
		Saida res = super.save(saida);
		
		executor.gerarEstoque(saida, saidaOld, () -> {return new Saida();}, () -> {return new SaidaItem();});
		return res;
	}

}
