package com.tedros.core.ejb.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.tedros.core.ejb.service.TUserService;
import com.tedros.core.security.model.TUser;
import com.tedros.ejb.base.controller.ITSecurityController;
import com.tedros.ejb.base.controller.TSecureEjbController;
import com.tedros.ejb.base.security.ITSecurity;
import com.tedros.ejb.base.security.TAccessPolicie;
import com.tedros.ejb.base.security.TBeanPolicie;
import com.tedros.ejb.base.security.TBeanSecurity;
import com.tedros.ejb.base.security.TSecurityInterceptor;
import com.tedros.ejb.base.service.ITEjbService;

@TSecurityInterceptor
@Stateless(name="TUserController")
@TBeanSecurity(@TBeanPolicie(id="T_CUSTOM_SECURITY_USER", 
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
