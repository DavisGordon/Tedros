package org.tedros.core.ejb.controller;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;

import org.tedros.core.controller.TProfileController;
import org.tedros.core.domain.DomainApp;
import org.tedros.core.ejb.service.TCoreService;
import org.tedros.core.security.model.TProfile;
import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.ejb.controller.TSecureEjbController;
import org.tedros.server.security.ITSecurity;
import org.tedros.server.security.TAccessPolicie;
import org.tedros.server.security.TBeanPolicie;
import org.tedros.server.security.TBeanSecurity;
import org.tedros.server.security.TSecurityInterceptor;
import org.tedros.server.service.ITEjbService;

@TSecurityInterceptor
@Stateless(name="TProfileController")
@TBeanSecurity(@TBeanPolicie(id=DomainApp.PROFILE_FORM_ID, 
policie= {TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS}))
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TProfileControllerImpl extends TSecureEjbController<TProfile> implements TProfileController, ITSecurity {

	@EJB
	private TCoreService<TProfile> serv;
	
	@EJB
	private ITSecurityController securityController;

	@Override
	public ITEjbService<TProfile> getService() {
		return serv;
	}

	/**
	 * @return the securityController
	 */
	public ITSecurityController getSecurityController() {
		return securityController;
	}
	
}
