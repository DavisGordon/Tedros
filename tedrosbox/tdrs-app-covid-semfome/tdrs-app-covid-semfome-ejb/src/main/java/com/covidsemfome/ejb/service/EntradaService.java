/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.ejb.service;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.covidsemfome.ejb.bo.EntradaBO;
import com.covidsemfome.model.Entrada;
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
	
	@EJB
	private EstoqueService estServ;
	
	@Override
	public EntradaBO getBussinesObject() {
		return bo;
	}
	
	@Override
	@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
	public void remove(Entrada entidade) throws Exception {
		estServ.removerEstoque(entidade, null);
		bo.remove(entidade);
	}
	
	@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
	public Entrada save(Entrada entrada, Entrada entSaved) throws Exception {
		if(entSaved!=null && (!entSaved.getCozinha().getId().equals(entrada.getCozinha().getId())
					|| !entSaved.getData().equals(entrada.getData())))
				estServ.removerEstoque(entSaved, null);
		
		Entrada res = super.save(entrada);
		
		estServ.gerarEstoque(entrada, entSaved);
		return res;
	}
	

}
