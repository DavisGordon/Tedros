package com.tedros.core.ejb.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.tedros.core.ejb.domain.TPropertieKey;
import com.tedros.core.ejb.service.TPropertieService;
import com.tedros.core.ejb.service.TSecurityService;
import com.tedros.core.ejb.service.TUserService;
import com.tedros.core.security.model.TProfile;
import com.tedros.core.security.model.TUser;
import com.tedros.core.setting.model.TPropertie;
import com.tedros.core.setting.model.TType;
import com.tedros.ejb.base.controller.ITSecurityController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;
import com.tedros.ejb.base.security.ITSecurity;
import com.tedros.ejb.base.security.TAccessToken;
import com.tedros.ejb.base.security.TSecurityInterceptor;

@TSecurityInterceptor
@Stateless(name="ITLoginController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TLoginController implements ITLoginController, ITSecurity {

	@EJB
	private TUserService serv;
	
	@EJB
	private TPropertieService propServ;
	
	@EJB
	private ITSecurityController securityController;
	
	@EJB
	private TSecurityService securityService;
	
	@Override
	public TResult<Boolean> isInitialConfigEnable() {
		
		try{
			Boolean enabled = false; 
			TPropertie p = new TPropertie();
			p.setKey(TPropertieKey.ENABLE_INITIAL_CONFIG.getValue());
			p.setType(TType.SYSTEM);
			p = propServ.find(p);
			
			if(p==null || (p!=null && p.getValue()!=null && p.getValue().equals("true")))
				enabled = true;
			
			return new TResult<>(EnumResult.SUCESS, enabled);
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<>(EnumResult.ERROR, e.getMessage());
		}
	}
	
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
	
	@Override
	public TResult<TUser> saveActiveProfile(TAccessToken token, TProfile profile, Long userId) {
		try {
			TUser user = this.serv.saveActiveProfile(profile, userId);
			return new TResult<TUser>(EnumResult.SUCESS, user);
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<TUser>(EnumResult.ERROR, e.getMessage());
		}
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

	/**
	 * @return the securityController
	 */
	public ITSecurityController getSecurityController() {
		return securityController;
	}
}
