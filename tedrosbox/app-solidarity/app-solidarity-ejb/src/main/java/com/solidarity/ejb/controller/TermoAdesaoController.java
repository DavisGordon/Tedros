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

import com.solidarity.ejb.controller.ITermoAdesaoController;
import com.solidarity.ejb.service.TermoAdesaoService;
import com.solidarity.model.TermoAdesao;
import com.tedros.ejb.base.controller.TEjbController;
import com.tedros.ejb.base.service.ITEjbService;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@Stateless(name="ITermoAdesaoController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TermoAdesaoController extends TEjbController<TermoAdesao> implements ITermoAdesaoController {
	
	@EJB
	private TermoAdesaoService serv;
	
	@Override
	public ITEjbService<TermoAdesao> getService() {
		return serv;
	}
	
	

}
