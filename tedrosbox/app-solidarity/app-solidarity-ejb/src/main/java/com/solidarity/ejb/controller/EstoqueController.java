/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.solidarity.ejb.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.solidarity.ejb.controller.IEstoqueController;
import com.solidarity.ejb.service.EstoqueService;
import com.solidarity.model.Estoque;
import com.tedros.ejb.base.controller.TEjbController;
import com.tedros.ejb.base.service.ITEjbService;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@Stateless(name="IEstoqueController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class EstoqueController extends TEjbController<Estoque> implements IEstoqueController {
	
	@EJB
	private EstoqueService serv;
	
	@Override
	public ITEjbService<Estoque> getService() {
		return serv;
	}

}
