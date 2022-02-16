/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package org.somos.server.website.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.somos.ejb.controller.ISiteMidiaSocialController;
import org.somos.model.SiteMidiaSocial;
import org.somos.server.base.service.TStatelessService;

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
@Stateless(name="ISiteMidiaSocialController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class SiteMidiaSocialController extends TSecureEjbController<SiteMidiaSocial> implements ISiteMidiaSocialController,ITSecurity {
	
	@EJB
	private TStatelessService<SiteMidiaSocial> serv;

	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<SiteMidiaSocial> getService() {
		return serv;
	}

	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}
}
