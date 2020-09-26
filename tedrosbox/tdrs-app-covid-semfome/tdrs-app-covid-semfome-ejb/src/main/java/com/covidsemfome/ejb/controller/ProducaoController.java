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

import com.covidsemfome.ejb.service.ProducaoService;
import com.covidsemfome.model.Producao;
import com.tedros.ejb.base.controller.TEjbController;
import com.tedros.ejb.base.service.ITEjbService;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@Stateless(name="IProducaoController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class ProducaoController extends TEjbController<Producao> implements IProducaoController {
	
	@EJB
	private ProducaoService serv;
	
	@Override
	public ITEjbService<Producao> getService() {
		return serv;
	}

}
