/**
 * 
 */
package com.tedros.login.model;

import java.util.List;

import com.tedros.core.ejb.controller.ITLoginController;
import com.tedros.core.ejb.controller.TUserController;
import com.tedros.core.security.model.TUser;
import com.tedros.core.service.remote.ServiceLocator;
import com.tedros.ejb.base.result.TResult;
import com.tedros.fxapi.exception.TProcessException;
import com.tedros.fxapi.process.TEntityProcess;

/**
 * @author Davis Gordon
 *
 */
 public class TLoginProcess extends TEntityProcess<TUser> {
	
	private static final String SERV_NAME = "ITLoginControllerRemote";
	
	private String login;
	private String password;
	
	private TUser user;
	
	public TLoginProcess() throws TProcessException {
		super(TUser.class, SERV_NAME);
	} 

	@SuppressWarnings("unchecked")
	@Override
	public boolean runBefore(List<TResult<TUser>> resultList) {
		ServiceLocator loc = ServiceLocator.getInstance();
		try {
			if(login!=null && password!=null){
				ITLoginController service = (ITLoginController) loc.lookup(SERV_NAME);
				resultList.add(service.login(login, password));
				login = null;
				password = null;
			}
			
			if(user!=null){
				TUserController service = (TUserController) loc.lookup("TUserControllerRemote");
				resultList.add(service.save(user.getAccessToken(), user));
				user = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			loc.close();
		}
		
		return false;
	}

	public void setUser(TUser user) {
		this.user = user;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

}
