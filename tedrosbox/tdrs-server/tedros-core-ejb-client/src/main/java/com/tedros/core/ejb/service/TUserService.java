package com.tedros.core.ejb.service;

import javax.ejb.Local;
import javax.ejb.Remote;

import com.tedros.core.security.model.TProfile;
import com.tedros.core.security.model.TUser;
import com.tedros.ejb.base.service.ITEjbService;
import com.tedros.ejb.base.service.TResult;

@Remote
@Local
public interface TUserService extends ITEjbService<TUser>{
	
	@SuppressWarnings("rawtypes")
	public TResult login(String login, String password);
	
	public TResult<TUser> saveActiveProfile(TProfile profile, Long userId);
	
}
