package org.tedros.core.ejb.controller;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;

import org.tedros.core.controller.TUserController;
import org.tedros.core.domain.DomainApp;
import org.tedros.core.ejb.service.TUserService;
import org.tedros.core.security.model.TUser;
import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.ejb.controller.TSecureEjbController;
import org.tedros.server.security.ITSecurity;
import org.tedros.server.security.TAccessPolicie;
import org.tedros.server.security.TBeanPolicie;
import org.tedros.server.security.TBeanSecurity;
import org.tedros.server.security.TSecurityInterceptor;
import org.tedros.server.service.ITEjbService;

@TSecurityInterceptor
@Stateless(name="TUserController")
@TBeanSecurity(@TBeanPolicie(id=DomainApp.USER_FORM_ID, 
policie= {TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS}))
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TUserControllerImpl extends TSecureEjbController<TUser> implements	ITSecurity, TUserController {

	@EJB
	private TUserService serv;
	
	@EJB
	private ITSecurityController security;
	
	@Override
	public ITEjbService<TUser> getService() {
		return serv;
	}

	@Override
	public ITSecurityController getSecurityController() {
		return security;
	}
	
	
}
