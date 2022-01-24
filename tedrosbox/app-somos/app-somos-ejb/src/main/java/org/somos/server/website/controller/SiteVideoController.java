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

import org.somos.ejb.controller.ISiteVideoController;
import org.somos.model.SiteVideo;
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
@Stateless(name="ISiteVideoController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class SiteVideoController extends TSecureEjbController<SiteVideo> implements ISiteVideoController,ITSecurity {
	
	@EJB
	private TStatelessService<SiteVideo> serv;

	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<SiteVideo> getService() {
		return serv;
	}

	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}
}
