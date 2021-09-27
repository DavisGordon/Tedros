/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.server.parceiro.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.covidsemfome.ejb.controller.ISiteConteudoController;
import com.covidsemfome.parceiro.model.SiteConteudo;
import com.covidsemfome.server.base.service.TStatelessService;
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
@Stateless(name="ISiteConteudoController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class SiteConteudoController extends TSecureEjbController<SiteConteudo> implements ISiteConteudoController, ITSecurity {
	
	@EJB
	private TStatelessService<SiteConteudo> serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<SiteConteudo> getService() {
		return serv;
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}

}
