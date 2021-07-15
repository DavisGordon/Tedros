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

import com.solidarity.ejb.controller.IProdutoController;
import com.solidarity.ejb.service.ProdutoService;
import com.solidarity.model.Produto;
import com.tedros.ejb.base.controller.TEjbController;
import com.tedros.ejb.base.service.ITEjbService;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@Stateless(name="IProdutoController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class ProdutoController extends TEjbController<Produto> implements IProdutoController {
	
	@EJB
	private ProdutoService serv;
	
	@Override
	public ITEjbService<Produto> getService() {
		return serv;
	}

}
