package com.tedros.core.ejb.controller;

import javax.ejb.Remote;

import com.tedros.core.security.model.TUser;
import com.tedros.ejb.base.controller.ITBaseController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.security.TAccessToken;

@Remote
public interface ITLoginController extends ITBaseController{
	
	public TResult<TUser> login(String login, String password);
	
	public TResult<Boolean> logout(TAccessToken token);
	
}
