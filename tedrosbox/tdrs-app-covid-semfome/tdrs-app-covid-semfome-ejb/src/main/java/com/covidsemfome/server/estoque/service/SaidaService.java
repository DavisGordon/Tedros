/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.server.estoque.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.covidsemfome.model.Cozinha;
import com.covidsemfome.model.Saida;
import com.covidsemfome.model.SaidaItem;
import com.covidsemfome.report.model.EstocavelReportModel;
import com.covidsemfome.server.estoque.bo.EstocavelExecutor;
import com.covidsemfome.server.estoque.bo.EstoqueBO;
import com.covidsemfome.server.estoque.bo.EstoqueConfigBO;
import com.covidsemfome.server.estoque.bo.ProdutoBO;
import com.covidsemfome.server.estoque.bo.SaidaBO;
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
	
	public List<Saida> pesquisar(List<Long> idsl, Cozinha coz, Date dataInicio, Date dataFim, String orderby, String ordertype){
		return bo.pesquisar(idsl, coz, dataInicio, dataFim, orderby, ordertype);
	}
	
	public EstocavelReportModel pesquisar(EstocavelReportModel m){
		return bo.pesquisar(m);
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
