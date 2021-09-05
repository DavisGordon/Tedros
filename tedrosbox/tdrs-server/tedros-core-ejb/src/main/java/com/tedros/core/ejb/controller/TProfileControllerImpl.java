package com.tedros.core.ejb.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.tedros.core.ejb.service.TProfileService;
import com.tedros.core.security.model.TProfile;
import com.tedros.ejb.base.controller.ITSecurityController;
import com.tedros.ejb.base.controller.TSecureEjbController;
import com.tedros.ejb.base.security.ITSecurity;
import com.tedros.ejb.base.security.TRemoteSecurity;
import com.tedros.ejb.base.service.ITEjbService;

@TRemoteSecurity
@Stateless(name="TProfileController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TProfileControllerImpl extends TSecureEjbController<TProfile> implements TProfileController, ITSecurity {

	@EJB
	private TProfileService serv;
	
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
