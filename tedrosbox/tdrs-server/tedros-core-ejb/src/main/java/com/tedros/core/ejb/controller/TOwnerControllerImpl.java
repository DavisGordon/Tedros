package com.tedros.core.ejb.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.tedros.core.domain.DomainApp;
import com.tedros.core.ejb.service.TCoreService;
import com.tedros.core.ejb.service.TOwnerService;
import com.tedros.core.owner.model.TOwner;
import com.tedros.ejb.base.controller.ITSecurityController;
import com.tedros.ejb.base.controller.TSecureEjbController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.TState;
import com.tedros.ejb.base.security.ITSecurity;
import com.tedros.ejb.base.security.TAccessPolicie;
import com.tedros.ejb.base.security.TAccessToken;
import com.tedros.ejb.base.security.TBeanPolicie;
import com.tedros.ejb.base.security.TBeanSecurity;
import com.tedros.ejb.base.security.TSecurityInterceptor;
import com.tedros.ejb.base.service.ITEjbService;

@TSecurityInterceptor
@Stateless(name="TOwnerController")
@TBeanSecurity(@TBeanPolicie(id=DomainApp.OWNER_FORM_ID, 
policie= {TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS}))
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TOwnerControllerImpl extends TSecureEjbController<TOwner> implements TOwnerController, ITSecurity {

	@EJB
	private TOwnerService serv;
	
	@EJB
	private ITSecurityController securityController;

	@Override
	public ITEjbService<TOwner> getService() {
		return serv;
	}

	/**
	 * @return the securityController
	 */
	public ITSecurityController getSecurityController() {
		return securityController;
	}

	@Override
	public TResult<TOwner> getOwner(TAccessToken token) {
		try {
			TOwner e = serv.getOwner();
			return new TResult<>(TState.SUCCESS, e);
		}catch(Exception e) {
			return super.processException(token, null, e);
		}
	}
	
}
