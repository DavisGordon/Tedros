package com.tedros.core.ejb.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.tedros.core.ejb.service.TSecurityService;
import com.tedros.core.ejb.service.TUserService;
import com.tedros.core.security.model.TUser;
import com.tedros.ejb.base.controller.ITSecurityController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;
import com.tedros.ejb.base.security.ITSecurity;
import com.tedros.ejb.base.security.TAccessToken;
import com.tedros.ejb.base.security.TRemoteSecurity;

@TRemoteSecurity
@Stateless(name="ITLoginController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TLoginController implements ITLoginController, ITSecurity {

	@EJB
	private TUserService serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@EJB
	private TSecurityService securityService;
	
	@Override
	public TResult<TUser> login(String login, String password) {
		try{
			TUser entity = serv.login(login, password);
			return new TResult<TUser>(EnumResult.SUCESS, entity);
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<TUser>(EnumResult.ERROR, e.getMessage());
		}
	}

	/**
	 * @return the securityController
	 */
	public ITSecurityController getSecurityController() {
		return securityController;
	}

	@Override
	public TResult<Boolean> logout(TAccessToken token) {
		try {
			this.securityService.remove(token);
			return new TResult<>(EnumResult.SUCESS, true);
		}catch(Exception e) {
			e.printStackTrace();
			return new TResult<>(EnumResult.ERROR, e.getMessage(), false);
		}
		
	}
}
