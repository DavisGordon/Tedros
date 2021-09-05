package com.tedros.core.ejb.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.tedros.core.ejb.service.TAuthorizationService;
import com.tedros.core.security.model.TAuthorization;
import com.tedros.ejb.base.controller.ITSecurityController;
import com.tedros.ejb.base.controller.TSecureEjbController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;
import com.tedros.ejb.base.security.ITSecurity;
import com.tedros.ejb.base.security.TAccessToken;
import com.tedros.ejb.base.security.TRemoteSecurity;
import com.tedros.ejb.base.service.ITEjbService;

@TRemoteSecurity
@Stateless(name="TAuthorizationController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TAuthorizationControllerImpl extends TSecureEjbController<TAuthorization> implements TAuthorizationController, ITSecurity {

	@EJB
	private TAuthorizationService serv;
	
	@EJB
	private ITSecurityController securityController;

	@Override
	public ITEjbService<TAuthorization> getService() {
		return serv;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public TResult process(TAccessToken token, List<TAuthorization> newLst) {
		try{
			List<TAuthorization> oldLst = serv.listAll(TAuthorization.class);
			List<String> msg = serv.process(newLst, oldLst);
			return new TResult<List<String>>(EnumResult.SUCESS, msg);
		}catch(Exception e){
			e.printStackTrace();
			return new TResult(EnumResult.ERROR, e.getMessage());
		}
	}

	/**
	 * @return the securityController
	 */
	public ITSecurityController getSecurityController() {
		return securityController;
	}

	
}
