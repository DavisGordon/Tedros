package org.tedros.core.controller;

import java.util.List;

import javax.ejb.Remote;

import org.tedros.core.security.model.TAuthorization;
import org.tedros.core.security.model.TProfile;
import org.tedros.core.security.model.TUser;
import org.tedros.server.controller.ITBaseController;
import org.tedros.server.result.TResult;
import org.tedros.server.security.TAccessToken;

@Remote
public interface ITLoginController extends ITBaseController{
	

	static final String JNDI_NAME = "ITLoginControllerRemote";
	
	TResult<TUser> login(String login, String password);
	
	TResult<TUser> createFirstUser(TUser user, List<TAuthorization> authorizations);
	
	TResult<Boolean> logout(TAccessToken token);

	TResult<TUser> saveActiveProfile(TAccessToken token, TProfile profile, Long userId);

	TResult<Boolean> isSystemWithoutUsers();
	
}
