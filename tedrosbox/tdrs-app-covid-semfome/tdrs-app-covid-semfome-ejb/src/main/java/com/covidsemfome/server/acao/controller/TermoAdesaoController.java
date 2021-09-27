/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.server.acao.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.covidsemfome.ejb.controller.ITermoAdesaoController;
import com.covidsemfome.model.TermoAdesao;
import com.covidsemfome.server.acao.service.TermoAdesaoService;
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
@Stateless(name="ITermoAdesaoController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TermoAdesaoController extends TSecureEjbController<TermoAdesao> implements ITermoAdesaoController, ITSecurity {
	
	@EJB
	private TermoAdesaoService serv;

	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<TermoAdesao> getService() {
		return serv;
	}

	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}
	

}
