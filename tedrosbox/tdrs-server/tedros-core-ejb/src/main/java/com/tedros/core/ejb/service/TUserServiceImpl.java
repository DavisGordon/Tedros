package com.tedros.core.ejb.service;

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
public class TUserServiceImpl extends TEjbService<TUser> {

	@Inject
	private TUserBO bo;
	
	@Override
	public ITGenericBO<TUser> getBussinesObject() {
		return bo;
	}

	
	public TUser login(String login, String password) {
		return bo.getUserByLoginPassword(login, password);
	}
	
	@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
	public void saveActiveProfile(TProfile profile, Long userId) throws Exception {
		bo.saveActiveProfile(profile, userId);
	}

	
}
