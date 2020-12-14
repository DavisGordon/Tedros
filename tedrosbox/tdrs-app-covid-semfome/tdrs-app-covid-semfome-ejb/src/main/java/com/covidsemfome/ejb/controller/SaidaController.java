/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.ejb.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.covidsemfome.ejb.service.EstoqueService;
import com.covidsemfome.ejb.service.SaidaService;
import com.covidsemfome.model.Saida;
import com.tedros.ejb.base.controller.TEjbController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.service.ITEjbService;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@Stateless(name="ISaidaController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class SaidaController extends TEjbController<Saida> implements ISaidaController {
	
	@EJB
	private SaidaService serv;
	
	@EJB
	private EstoqueService estServ;
	
	@Override
	public ITEjbService<Saida> getService() {
		return serv;
	}
	
	@Override
	public TResult<Saida> save(Saida entidade) {
		
		try{
			estServ.gerarEstoque(entidade);
			TResult<Saida> res = super.save(entidade);
			return res;
		}catch(Exception e) {
			return processException(entidade, e);
		}
	}
	
	@Override
	public TResult<Saida> remove(Saida entidade) {
		try{
			estServ.removerEstoque(entidade);
			TResult<Saida> res = super.remove(entidade);
			return res;
		}catch(Exception e) {
			return processException(entidade, e);
		}
	}

}
