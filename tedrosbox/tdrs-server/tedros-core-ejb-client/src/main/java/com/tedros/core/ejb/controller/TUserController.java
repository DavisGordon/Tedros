package com.tedros.core.ejb.controller;

import javax.ejb.Remote;

import com.tedros.core.security.model.TProfile;
import com.tedros.core.security.model.TUser;
import com.tedros.ejb.base.controller.ITEjbController;
import com.tedros.ejb.base.result.TResult;

@Remote
public interface TUserController extends ITEjbController<TUser>{
	
	@SuppressWarnings("rawtypes")
	public TResult login(String login, String password);
	
	public TResult<TUser> saveActiveProfile(TProfile profile, Long userId);
	
}
