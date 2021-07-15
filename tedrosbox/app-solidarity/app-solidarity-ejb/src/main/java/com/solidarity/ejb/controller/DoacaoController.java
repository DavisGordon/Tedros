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

import com.solidarity.ejb.controller.IDoacaoController;
import com.solidarity.ejb.service.DoacaoService;
import com.solidarity.model.Doacao;
import com.tedros.ejb.base.controller.TEjbController;
import com.tedros.ejb.base.service.ITEjbService;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 **/
@Stateless(name="IDoacaoController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class DoacaoController extends TEjbController<Doacao> implements IDoacaoController {
	
	@EJB
	private DoacaoService serv;
	


	@Override
	public ITEjbService<Doacao> getService() {
		return serv;
	}

	
}
