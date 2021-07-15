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

import com.solidarity.ejb.controller.IUFController;
import com.solidarity.ejb.service.UFService;
import com.solidarity.model.UF;
import com.tedros.ejb.base.controller.TEjbController;
import com.tedros.ejb.base.service.ITEjbService;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@Stateless(name="IUFController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class UFController extends TEjbController<UF> implements IUFController {
	
	@EJB
	private UFService serv;
	
	@Override
	public ITEjbService<UF> getService() {
		return serv;
	}

}
