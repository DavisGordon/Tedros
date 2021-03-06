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

import com.solidarity.ejb.controller.ICozinhaController;
import com.solidarity.ejb.service.CozinhaService;
import com.solidarity.model.Cozinha;
import com.tedros.ejb.base.controller.TEjbController;
import com.tedros.ejb.base.service.ITEjbService;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@Stateless(name="ICozinhaController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class CozinhaController extends TEjbController<Cozinha> implements ICozinhaController {
	
	@EJB
	private CozinhaService serv;
	
	@Override
	public ITEjbService<Cozinha> getService() {
		return serv;
	}

}
