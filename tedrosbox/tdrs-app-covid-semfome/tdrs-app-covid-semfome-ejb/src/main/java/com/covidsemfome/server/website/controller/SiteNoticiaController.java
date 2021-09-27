/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.server.website.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.covidsemfome.ejb.controller.ISiteNoticiaController;
import com.covidsemfome.model.SiteNoticia;
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
@Stateless(name="ISiteNoticiaController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class SiteNoticiaController extends TSecureEjbController<SiteNoticia> implements ISiteNoticiaController, ITSecurity {
	
	@EJB
	private TStatelessService<SiteNoticia> serv;

	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<SiteNoticia> getService() {
		return serv;
	}

	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}

}
