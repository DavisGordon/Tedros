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

import com.covidsemfome.ejb.service.EstoqueConfigService;
import com.covidsemfome.model.EstoqueConfig;
import com.tedros.ejb.base.controller.TEjbController;
import com.tedros.ejb.base.service.ITEjbService;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@Stateless(name="IEstoqueConfigController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class EstoqueConfigController extends TEjbController<EstoqueConfig> implements IEstoqueConfigController {
	
	@EJB
	private EstoqueConfigService serv;
	
	@Override
	public ITEjbService<EstoqueConfig> getService() {
		return serv;
	}

}
