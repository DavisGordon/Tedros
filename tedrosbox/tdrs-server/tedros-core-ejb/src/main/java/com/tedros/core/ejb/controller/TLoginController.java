package com.tedros.core.ejb.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.tedros.core.ejb.service.TAuthorizationService;
import com.tedros.core.ejb.service.TCoreService;
import com.tedros.core.ejb.service.TSecurityService;
import com.tedros.core.ejb.service.TUserService;
import com.tedros.core.security.model.TAuthorization;
import com.tedros.core.security.model.TProfile;
import com.tedros.core.security.model.TUser;
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
	private TAuthorizationService autServ;
	
	@EJB
	private TCoreService<TProfile> profServ;
	
	@EJB
	private ITSecurityController securityController;
	
	@EJB
	private TSecurityService securityService;
	
	@Override
	public TResult<TUser> createFirstUser(TUser user, List<TAuthorization> newLst) {
		try{
			if(serv.listAll(TUser.class).isEmpty()) {
				List<TAuthorization> lst = autServ.listAll(TAuthorization.class);
				autServ.process(newLst, lst);
				lst = autServ.listAll(TAuthorization.class);
				
				TProfile p = new TProfile();
				p.setName("Master");
				p.setDescription("The master profile created for the first user.");
				p.setAutorizations(lst);
				p = profServ.save(p);
				
				Set<TProfile> profs = new HashSet<>();
				user.setProfiles(profs);
				
				user = serv.save(user);
				user = serv.login(user.getLogin(), user.getPassword());
				
				return new TResult<>(EnumResult.SUCESS, user);
			}else {
				return new TResult<>(EnumResult.WARNING, "The system already have users.");
			}
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<>(EnumResult.ERROR, e.getMessage());
		}
	}
	
	@Override
	public TResult<Boolean> isSystemWithoutUsers() {
		
		try{
			Boolean f = serv.listAll(TUser.class).isEmpty();
			
			return new TResult<>(EnumResult.SUCESS, f);
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
