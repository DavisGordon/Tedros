package org.tedros.core.ejb.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.tedros.core.controller.ITLoginController;
import org.tedros.core.ejb.service.TAuthorizationService;
import org.tedros.core.ejb.service.TCoreService;
import org.tedros.core.ejb.service.TSecurityService;
import org.tedros.core.ejb.service.TUserService;
import org.tedros.core.security.model.TAuthorization;
import org.tedros.core.security.model.TProfile;
import org.tedros.core.security.model.TUser;
import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.server.security.ITSecurity;
import org.tedros.server.security.TAccessToken;
import org.tedros.server.security.TSecurityInterceptor;

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
				profs.add(p);
				user.setProfiles(profs);
				user.setActive("T");
				user = serv.save(user);
				user = serv.login(user.getLogin(), user.getPassword());
				
				return new TResult<>(TState.SUCCESS, user);
			}else {
				return new TResult<>(TState.WARNING, "The system already have users.");
			}
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<>(TState.ERROR, e.getMessage());
		}
	}
	
	@Override
	public TResult<Boolean> isSystemWithoutUsers() {
		
		try{
			Boolean f = serv.listAll(TUser.class).isEmpty();
			
			return new TResult<>(TState.SUCCESS, f);
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<>(TState.ERROR, e.getMessage());
		}
	}
	
	@Override
	public TResult<TUser> login(String login, String password) {
		try{
			TUser entity = serv.login(login, password);
			return new TResult<TUser>(TState.SUCCESS, entity);
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<TUser>(TState.ERROR, e.getMessage());
		}
	}
	
	@Override
	public TResult<TUser> saveActiveProfile(TAccessToken token, TProfile profile, Long userId) {
		try {
			TUser user = this.serv.saveActiveProfile(profile, userId);
			return new TResult<TUser>(TState.SUCCESS, user);
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<TUser>(TState.ERROR, e.getMessage());
		}
	}

	@Override
	public TResult<Boolean> logout(TAccessToken token) {
		try {
			this.securityService.remove(token);
			return new TResult<>(TState.SUCCESS, true);
		}catch(Exception e) {
			e.printStackTrace();
			return new TResult<>(TState.ERROR, e.getMessage(), false);
		}
	}

	/**
	 * @return the securityController
	 */
	public ITSecurityController getSecurityController() {
		return securityController;
	}
}
