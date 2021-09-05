package com.tedros.core.ejb.service;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.tedros.core.ejb.bo.TUserBO;
import com.tedros.core.security.model.TProfile;
import com.tedros.core.security.model.TUser;
import com.tedros.ejb.base.bo.ITGenericBO;
import com.tedros.ejb.base.service.TEjbService;

@Local
@Stateless(name="TUserService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TUserService extends TEjbService<TUser> {

	@Inject
	private TUserBO bo;
	
	@EJB
	private TSecurityService serv;
	
	@Override
	public ITGenericBO<TUser> getBussinesObject() {
		return bo;
	}

	
	public TUser login(String login, String password) {
		TUser user = bo.getUserByLoginPassword(login, password);
		serv.addUser(user);
		return user;
	}
	
	@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
	public TUser saveActiveProfile(TProfile profile, Long userId) throws Exception {
		TUser user = bo.saveActiveProfile(profile, userId);
		serv.addUser(user);
		return user;
	}

	
}
