package com.tedros.core.ejb.controller;

import javax.ejb.Remote;

import com.tedros.core.security.model.TProfile;
import com.tedros.core.security.model.TUser;
import com.tedros.ejb.base.controller.ITBaseController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.security.TAccessToken;

@Remote
public interface ITLoginController extends ITBaseController{
	
	TResult<TUser> login(String login, String password);
	
	TResult<Boolean> logout(TAccessToken token);

	TResult<TUser> saveActiveProfile(TAccessToken token, TProfile profile, Long userId);

	TResult<Boolean> isInitialConfigEnable();
	
}
