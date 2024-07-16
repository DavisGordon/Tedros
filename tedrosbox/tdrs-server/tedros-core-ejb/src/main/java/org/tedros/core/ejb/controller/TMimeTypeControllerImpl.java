package org.tedros.core.ejb.controller;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;

import org.tedros.common.model.TMimeType;
import org.tedros.core.controller.TMimeTypeController;
import org.tedros.core.domain.DomainApp;
import org.tedros.core.ejb.service.TCoreService;
import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.ejb.controller.TSecureEjbController;
import org.tedros.server.security.ITSecurity;
import org.tedros.server.security.TAccessPolicie;
import org.tedros.server.security.TBeanPolicie;
import org.tedros.server.security.TBeanSecurity;
import org.tedros.server.security.TSecurityInterceptor;
import org.tedros.server.service.ITEjbService;

@TSecurityInterceptor
@Stateless(name="TMimeTypeController")
@TBeanSecurity(@TBeanPolicie(id=DomainApp.MIMETYPE_FORM_ID, 
policie= {TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS}))
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TMimeTypeControllerImpl extends TSecureEjbController<TMimeType> implements TMimeTypeController, ITSecurity {

	@EJB
	private TCoreService<TMimeType> serv;
	
	@EJB
	private ITSecurityController securityController;

	@Override
	public ITEjbService<TMimeType> getService() {
		return serv;
	}

	/**
	 * @return the securityController
	 */
	public ITSecurityController getSecurityController() {
		return securityController;
	}
	
}
