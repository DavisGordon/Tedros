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
import com.covidsemfome.model.Estoque;
import com.tedros.ejb.base.controller.ITSecurityController;
import com.tedros.ejb.base.controller.TSecureEjbController;
import com.tedros.ejb.base.security.ITSecurity;
import com.tedros.ejb.base.security.TRemoteSecurity;
import com.tedros.ejb.base.service.ITEjbService;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@TRemoteSecurity
@Stateless(name="IEstoqueController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class EstoqueController extends TSecureEjbController<Estoque> implements IEstoqueController, ITSecurity {
	
	@EJB
	private EstoqueService serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<Estoque> getService() {
		return serv;
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}

}
