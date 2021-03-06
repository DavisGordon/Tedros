/**
 * 
 */
package com.tedros.settings.security.process;

import java.util.List;

import javax.naming.NamingException;

import com.tedros.core.ejb.controller.TUserController;
import com.tedros.core.security.model.TProfile;
import com.tedros.core.security.model.TUser;
import com.tedros.core.service.remote.ServiceLocator;
import com.tedros.ejb.base.result.TResult;
import com.tedros.fxapi.exception.TProcessException;
import com.tedros.fxapi.process.TEntityProcess;

/**
 * @author Davis Gordon
 *
 */
public class TUserProcess extends TEntityProcess<TUser> {
	
	private static final String SERV_NAME = "TUserControllerRemote";
	
	private String login;
	private String password;
	
	private TProfile profile;
	private Long userId;
	
	public TUserProcess() throws TProcessException {
		super(TUser.class, SERV_NAME);
	} 

	@SuppressWarnings("unchecked")
	@Override
	public boolean runBefore(List<TResult<TUser>> resultList) {
		ServiceLocator loc = ServiceLocator.getInstance();
		TUserController service;
		try {
			service = (TUserController) loc.lookup(SERV_NAME);
		
			if(login!=null && password!=null){
				resultList.add(service.login(login, password));
				login = null;
				password = null;
			}
			
			if(profile!=null && userId!=null){
				TUser user = new TUser();
				user.setId(userId);
				TResult<TUser> res = service.findById(user);
				user = res.getValue();
				user.setActiveProfile(profile);
				//resultList.add(service.saveActiveProfile(profile, userId));
				resultList.add(service.save(user));
				profile = null;
				userId = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}

	public void setProfile(TProfile profile) {
		this.profile = profile;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

}
